package com.ruoyi.srm.gateway;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.srm.config.SrmTycProperties;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.constant.SrmConstants;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.ApiCacheKey;
import com.ruoyi.srm.domain.ApiCallLog;
import com.ruoyi.srm.domain.ApiDict;
import com.ruoyi.srm.domain.ApiRaw;
import com.ruoyi.srm.domain.BudgetPolicy;
import com.ruoyi.srm.domain.TriggerRule;
import com.ruoyi.srm.domain.UsageStat;
import com.ruoyi.srm.service.IApiCacheKeyService;
import com.ruoyi.srm.service.IApiCallLogService;
import com.ruoyi.srm.service.IApiDictService;
import com.ruoyi.srm.service.IApiRawService;
import com.ruoyi.srm.service.IBudgetPolicyService;
import com.ruoyi.srm.service.ITriggerRuleService;
import com.ruoyi.srm.service.IUsageStatService;
import com.ruoyi.srm.util.SrmUtils;

/**
 * 天眼查唯一出口网关。六段流水线固定顺序见 API接口文档 §3.2：
 * ① 套餐校验 → ② 缓存前置 → ③ 触发规则 → ④ 预算闸门 → ⑤ 限流并发 → ⑥ 留痕与投影。
 *
 * <p>铁律：所有天眼查 HTTP 调用只能出自本类；页面 Service 一律不许直接发 HTTP（§1.1）。
 *
 * <p>apiId 一律传 {@link SrmApiId} 常量，任何形如 {@code call(818, ...)} 的字面量都会被对账脚本判为越界。
 */
@Component
public class TycGateway
{
    private static final Logger log = LoggerFactory.getLogger(TycGateway.class);

    @Autowired
    private SrmTycProperties properties;

    @Autowired
    private IApiDictService apiDictService;

    @Autowired
    private IApiCacheKeyService apiCacheKeyService;

    @Autowired
    private IApiCallLogService apiCallLogService;

    @Autowired
    private IApiRawService apiRawService;

    @Autowired
    private ITriggerRuleService triggerRuleService;

    @Autowired
    private IBudgetPolicyService budgetPolicyService;

    @Autowired
    private IUsageStatService usageStatService;

    @Autowired
    private com.ruoyi.common.core.redis.RedisCache redisCache;

    /** 各页面的投影实现，可为空。 */
    @Autowired(required = false)
    private List<TycProjector> projectors = new ArrayList<>();

    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

    /**
     * 六段流水线入口。
     *
     * @param apiId      外调编号，只能传 {@link SrmApiId} 常量（134 条清单去重后共 16 个）；
     *                   日志与 srm_api_call_log 中统一渲染为 {@code 编号（常量名 · 天眼查接口名）}
     * @param subjectKey 主体键：信用代码优先，缺失用规范化企业名或 tyc 公司 id
     * @param params     入参（不含密钥）
     * @param ctx        调用上下文
     */
    public TycResult call(int apiId, String subjectKey, Map<String, Object> params, CallContext ctx)
    {
        if (!SrmApiId.inScope(apiId))
        {
            throw new ServiceException("外调编号不在本轮 134 条清单范围内: " + apiId, SrmErrorCode.CODE_60008);
        }
        CallContext context = ctx == null ? new CallContext() : ctx;
        if (StringUtils.isEmpty(context.getEnv()))
        {
            context.setEnv(StringUtils.isEmpty(properties.getDefaultEnv()) ? SrmConstants.CACHE_ENV_PROD : properties.getDefaultEnv());
        }
        if (StringUtils.isEmpty(context.getTraceId()))
        {
            context.setTraceId(SrmUtils.md5(apiId + "" + subjectKey + System.nanoTime()));
        }
        if (log.isDebugEnabled())
        {
            // TYC-CALL 外调天眼查：显式打印外调编号/常量名/接口名，便于日志定位是哪一支天眼查接口
            log.debug("TYC-CALL apiId={} subjectKey={} scene={} env={}",
                    SrmApiId.label(apiId), subjectKey, context.getScene(), context.getEnv());
        }
        Map<String, Object> safeParams = params == null ? new LinkedHashMap<>() : params;
        TycResult result = new TycResult();
        result.setApiId(apiId);

        // ⓪ 编号级接入开关：srm.tyc.apis.<编号>.enabled=false 时只对该编号生效，不影响其它外调
        SrmTycProperties.ApiEndpoint endpoint = properties.endpointOf(apiId);
        if (endpoint != null && !endpoint.isEnabled())
        {
            return block(apiId, subjectKey, safeParams, context, result, SrmErrorCode.CODE_60002,
                    "该外调编号未开启接入（srm.tyc.apis." + apiId + ".enabled=false）");
        }

        // ① 套餐校验：status=0 且 show_flag=1，否则 60002（BLOCK 留痕）
        ApiDict dict = selectDict(apiId);
        if (dict == null || !"0".equals(dict.getStatus()) || !"1".equals(dict.getShowFlag()))
        {
            return block(apiId, subjectKey, safeParams, context, result, SrmErrorCode.CODE_60002, "套餐未含该接口");
        }

        // ② 缓存前置：命中且未过期直接返回，cache_hit=1、unit_consumed=0
        String cacheKey = SrmUtils.cacheKey(apiId, subjectKey, safeParams);
        ApiCacheKey cached = selectCache(cacheKey, context.getEnv());
        if (!context.isForce() && cached != null && cached.getExpireAt() != null && cached.getExpireAt().after(new Date()))
        {
            ApiRaw raw = cached.getRawId() == null ? null : apiRawService.selectApiRawById(cached.getRawId());
            result.setCacheHit(true);
            result.setRawId(cached.getRawId());
            result.setCacheKeyId(cached.getId());
            result.setData(raw == null || StringUtils.isEmpty(raw.getResponseJson()) ? null : JSON.parseObject(raw.getResponseJson()));
            result.setFetchedAt(cached.getFetchedAt());
            result.setCacheExpireAt(cached.getExpireAt());
            bumpHitCount(cached);
            writeCallLog(apiId, dict, subjectKey, safeParams, context, null, "1", BigDecimal.ZERO,
                    SrmConstants.CALL_RESULT_SUCCESS, null, "缓存命中");
            return result;
        }

        // ③ 触发规则：allow_auto=0 时非手动动作直接 60006
        if (!triggerAllowed(context))
        {
            return block(apiId, subjectKey, safeParams, context, result, SrmErrorCode.CODE_60006, "触发规则不允许自动调用");
        }

        // ④ 预算闸门：daily_limit / monthly_limit 对照 srm_usage_stat 已用量
        Integer budgetCode = checkBudget();
        if (budgetCode != null)
        {
            String reason = budgetCode == SrmErrorCode.CODE_60003 ? "日预算超限" : "月预算超限";
            return block(apiId, subjectKey, safeParams, context, result, budgetCode, reason);
        }

        // ⑤ 限流并发：max_concurrency 计数器
        String concurrencyKey = "srm:tyc:conc:" + context.getEnv();
        if (!acquire(concurrencyKey, maxConcurrency()))
        {
            return block(apiId, subjectKey, safeParams, context, result, SrmErrorCode.CODE_60005, "并发超限");
        }

        // ⑥ 留痕与投影
        long started = System.currentTimeMillis();
        try
        {
            if (!properties.isEnabled() || StringUtils.isEmpty(properties.getToken()))
            {
                long cost = System.currentTimeMillis() - started;
                ApiCallLog logRow = writeCallLog(apiId, dict, subjectKey, safeParams, context, null, "0", BigDecimal.ZERO,
                        SrmConstants.CALL_RESULT_BLOCKED, SrmErrorCode.CODE_60002 + "", "外调开关未开启，未发起真实请求");
                logRow.setCostMs((int) cost);
                apiCallLogService.updateApiCallLog(logRow);
                result.setBlocked(true);
                result.setBlockCode(SrmErrorCode.CODE_60002);
                result.setBlockReason("外调开关未开启，未发起真实请求");
                result.setFetchedAt(new Date());
                return result;
            }

            HttpResponse<String> response = sendWithRetry(apiId, dict, safeParams);
            long cost = System.currentTimeMillis() - started;
            String body = response.body();
            boolean httpOk = response.statusCode() >= 200 && response.statusCode() < 300;
            boolean bizOk = httpOk && isBizSuccess(body);

            ApiRaw raw = saveRaw(apiId, subjectKey, safeParams, body, response.statusCode(), (int) cost);
            BigDecimal unit = bizOk ? nvl(dict.getUnitPrice()) : BigDecimal.ZERO;
            writeCallLog(apiId, dict, subjectKey, safeParams, context, raw.getId(), "0", unit,
                    bizOk ? SrmConstants.CALL_RESULT_SUCCESS : SrmConstants.CALL_RESULT_FAIL,
                    bizOk ? null : "respStatus=" + response.statusCode(), null);

            result.setRawId(raw.getId());
            result.setUnitConsumed(unit);
            result.setData(body == null ? null : JSON.parseObject(body));
            result.setFetchedAt(raw.getFetchedAt());

            if (bizOk)
            {
                refreshCache(apiId, subjectKey, safeParams, cached, raw, dict, context);
                result.setCacheKeyId(cached == null ? null : cached.getId());
                result.setCacheExpireAt(expireAt(dict));
                project(apiId, subjectKey, safeParams, result.getData(), raw.getId());
            }
            return result;
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new ServiceException("天眼查请求被中断", SrmErrorCode.CODE_60007);
        }
        catch (Exception e)
        {
            long cost = System.currentTimeMillis() - started;
            log.error("天眼查外调失败 apiId={} subjectKey={} ", SrmApiId.label(apiId), subjectKey, e);
            writeCallLog(apiId, dict, subjectKey, safeParams, context, null, "0", BigDecimal.ZERO,
                    SrmConstants.CALL_RESULT_FAIL, null, StringUtils.substring(e.getMessage(), 0, 500));
            throw new ServiceException("天眼查外调失败: " + e.getMessage(), SrmErrorCode.CODE_60007);
        }
        finally
        {
            release(concurrencyKey);
            if (log.isDebugEnabled())
            {
                log.debug("tyc call done apiId={} cost={}ms", apiId, System.currentTimeMillis() - started);
            }
        }
    }

    private ApiDict selectDict(int apiId)
    {
        ApiDict query = new ApiDict();
        query.setApiId(apiId);
        List<ApiDict> list = apiDictService.selectApiDictList(query);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    private ApiCacheKey selectCache(String cacheKey, String env)
    {
        ApiCacheKey query = new ApiCacheKey();
        query.setCacheKey(cacheKey);
        query.setEnv(env);
        List<ApiCacheKey> list = apiCacheKeyService.selectApiCacheKeyList(query);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    private boolean triggerAllowed(CallContext ctx)
    {
        List<TriggerRule> rules = triggerRuleService.selectTriggerRuleList(new TriggerRule());
        String scene = ctx.getScene();
        if (StringUtils.isEmpty(scene))
        {
            return ctx.isManual();
        }
        for (TriggerRule rule : rules)
        {
            if (scene.equals(rule.getRuleCode()))
            {
                if ("0".equals(rule.getEnabled()))
                {
                    return false;
                }
                return ctx.isManual() || "1".equals(rule.getAllowAuto());
            }
        }
        return ctx.isManual();
    }

    private Integer checkBudget()
    {
        List<BudgetPolicy> policies = budgetPolicyService.selectBudgetPolicyList(new BudgetPolicy());
        if (policies == null || policies.isEmpty())
        {
            return null;
        }
        BudgetPolicy policy = policies.get(0);
        BigDecimal dailyUsed = used("day");
        BigDecimal monthlyUsed = used("month");
        if (policy.getDailyLimit() != null && policy.getDailyLimit() > 0 && dailyUsed.compareTo(BigDecimal.valueOf(policy.getDailyLimit())) >= 0)
        {
            return SrmErrorCode.CODE_60003;
        }
        if (policy.getMonthlyLimit() != null && policy.getMonthlyLimit() > 0 && monthlyUsed.compareTo(BigDecimal.valueOf(policy.getMonthlyLimit())) >= 0)
        {
            return SrmErrorCode.CODE_60004;
        }
        return null;
    }

    private BigDecimal used(String periodType)
    {
        UsageStat query = new UsageStat();
        query.setPeriodType(periodType);
        List<UsageStat> rows = usageStatService.selectUsageStatList(query);
        BigDecimal sum = BigDecimal.ZERO;
        if (rows != null)
        {
            for (UsageStat row : rows)
            {
                sum = sum.add(nvl(row.getUnitConsumed()));
            }
        }
        return sum;
    }

    private int maxConcurrency()
    {
        List<BudgetPolicy> policies = budgetPolicyService.selectBudgetPolicyList(new BudgetPolicy());
        if (policies == null || policies.isEmpty() || policies.get(0).getMaxConcurrency() == null)
        {
            return 2;
        }
        return Math.max(policies.get(0).getMaxConcurrency(), 1);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean acquire(String key, int max)
    {
        try
        {
            Object value = redisCache.redisTemplate.opsForValue().increment(key);
            long current = value == null ? 1L : Long.parseLong(value.toString());
            redisCache.expire(key, 60, TimeUnit.SECONDS);
            if (current > max)
            {
                redisCache.redisTemplate.opsForValue().decrement(key);
                return false;
            }
            return true;
        }
        catch (Exception e)
        {
            // 缓存不可用时不阻断业务，仅记 WARN（可用性优先，预算闸门仍生效）
            log.warn("并发计数器不可用，跳过限流: {}", e.getMessage());
            return true;
        }
    }

    @SuppressWarnings({ "rawtypes" })
    private void release(String key)
    {
        try
        {
            redisCache.redisTemplate.opsForValue().decrement(key);
        }
        catch (Exception ignored)
        {
            // 计数器释放失败不影响主流程
        }
    }

    /**
     * 发送真实请求，网络异常与 5xx 按 srm.tyc.retry-times 重试；4xx 与业务失败不重试。
     */
    private HttpResponse<String> sendWithRetry(int apiId, ApiDict dict, Map<String, Object> params) throws Exception
    {
        Exception last = null;
        int times = Math.max(1, properties.getRetryTimes() + 1);
        for (int attempt = 1; attempt <= times; attempt++)
        {
            try
            {
                HttpRequest request = buildRequest(apiId, dict, params);
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() < 500 || attempt == times)
                {
                    return response;
                }
                // 5xx：换一次重试
                if (log.isDebugEnabled())
                {
                    log.debug("TYC-CALL 外调天眼查 {} 返回 {}，第 {} 次重试", SrmApiId.label(apiId),
                            response.statusCode(), attempt);
                }
            }
            catch (java.io.IOException e)
            {
                last = e;
                if (attempt == times)
                {
                    throw e;
                }
                if (log.isDebugEnabled())
                {
                    log.debug("TYC-CALL 外调天眼查 {} 网络异常，第 {} 次重试：{}", SrmApiId.label(apiId),
                            attempt, e.getMessage());
                }
            }
            Thread.sleep(properties.getRetryBackoffMs() * attempt);
        }
        throw last == null ? new IllegalStateException("天眼查请求失败") : last;
    }

    /**
     * 按 srm.tyc.apis.<编号> 构造真实请求；路径/方法以《天眼查API接口.xlsx》详细URL与在线文档为准。
     *
     * <p>未配置编号时回落 srm.tyc.default-path-template（默认 /{apiId}）与 srm.tyc.default-method；
     * GET 把入参拼到 query string，POST 把入参放到 JSON body。
     */
    private HttpRequest buildRequest(int apiId, ApiDict dict, Map<String, Object> params) throws Exception
    {
        SrmTycProperties.ApiEndpoint endpoint = properties.endpointOf(apiId);
        String method = endpoint != null && StringUtils.isNotEmpty(endpoint.getMethod())
                ? endpoint.getMethod() : properties.getDefaultMethod();
        String path = endpoint != null && StringUtils.isNotEmpty(endpoint.getPath())
                ? endpoint.getPath()
                : properties.getDefaultPathTemplate().replace("{apiId}", String.valueOf(apiId));
        URI uri = resolveUri(path, "GET".equalsIgnoreCase(method) ? params : null);
        String body = JSON.toJSONString(params == null ? new LinkedHashMap<>() : params);
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofMillis(properties.getTimeoutMs()))
                .header("Content-Type", "application/json")
                .header(properties.getTokenHeader(), properties.getToken());
        if ("GET".equalsIgnoreCase(method))
        {
            builder.GET();
        }
        else if ("PUT".equalsIgnoreCase(method))
        {
            builder.PUT(HttpRequest.BodyPublishers.ofString(body));
        }
        else
        {
            builder.POST(HttpRequest.BodyPublishers.ofString(body));
        }
        return builder.build();
    }

    /**
     * 拼装最终 URI；query 非空时按 UTF-8 编码拼到 query string（GET 用）。
     */
    private URI resolveUri(String path, Map<String, Object> query) throws Exception
    {
        URI base = URI.create(properties.getBaseUrl());
        String basePath = base.getPath() == null ? "" : base.getPath();
        String full = (basePath.endsWith("/") ? basePath.substring(0, basePath.length() - 1) : basePath)
                + (path.startsWith("/") ? path : "/" + path);
        StringBuilder qs = new StringBuilder();
        if (query != null)
        {
            for (Map.Entry<String, Object> e : query.entrySet())
            {
                if (e.getValue() == null)
                {
                    continue;
                }
                if (qs.length() > 0)
                {
                    qs.append('&');
                }
                qs.append(java.net.URLEncoder.encode(e.getKey(), java.nio.charset.StandardCharsets.UTF_8))
                  .append('=')
                  .append(java.net.URLEncoder.encode(String.valueOf(e.getValue()),
                          java.nio.charset.StandardCharsets.UTF_8));
            }
        }
        return new URI(base.getScheme(), base.getUserInfo(), base.getHost(), base.getPort(),
                full, qs.length() == 0 ? null : qs.toString(), null);
    }

    private boolean isBizSuccess(String body)
    {
        if (StringUtils.isEmpty(body))
        {
            return false;
        }
        try
        {
            JSONObject json = JSON.parseObject(body);
            Object code = json.get("error_code");
            return code == null || "0".equals(code.toString());
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private ApiRaw saveRaw(int apiId, String subjectKey, Map<String, Object> params, String body, int httpStatus, int costMs)
    {
        ApiRaw raw = new ApiRaw();
        raw.setApiId(apiId);
        raw.setSubjectKey(subjectKey);
        raw.setRequestJson(JSON.toJSONString(params));
        raw.setHttpStatus(httpStatus);
        raw.setRespStatus(httpStatus == 200 ? "0" : String.valueOf(httpStatus));
        raw.setCostMs(costMs);
        raw.setFetchedAt(new Date());
        int byteSize = body == null ? 0 : body.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;
        raw.setByteSize(byteSize);
        if (byteSize > properties.getRawInlineMaxBytes())
        {
            raw.setStorageType("file");
            raw.setStoragePath(writeRawFile(apiId, subjectKey, body));
            raw.setResponseJson(null);
        }
        else
        {
            raw.setStorageType("inline");
            raw.setResponseJson(body);
        }
        apiRawService.insertApiRaw(raw);
        return raw;
    }

    private String writeRawFile(int apiId, String subjectKey, String body)
    {
        try
        {
            java.nio.file.Path dir = java.nio.file.Paths.get(properties.getRawFileDir());
            java.nio.file.Files.createDirectories(dir);
            java.nio.file.Path file = dir.resolve(apiId + "-" + SrmUtils.md5(subjectKey + System.nanoTime()) + ".json");
            java.nio.file.Files.writeString(file, body == null ? "" : body, java.nio.charset.StandardCharsets.UTF_8);
            return file.toAbsolutePath().toString();
        }
        catch (Exception e)
        {
            log.warn("原始响应落文件失败: {}", e.getMessage());
            return null;
        }
    }

    private void refreshCache(int apiId, String subjectKey, Map<String, Object> params, ApiCacheKey cached,
                              ApiRaw raw, ApiDict dict, CallContext ctx)
    {
        Date expireAt = expireAt(dict);
        if (cached == null)
        {
            ApiCacheKey row = new ApiCacheKey();
            row.setCacheKey(SrmUtils.cacheKey(apiId, subjectKey, params));
            row.setApiId(apiId);
            row.setSubjectKey(subjectKey);
            row.setParamJson(SrmUtils.canonicalJson(params));
            row.setRawId(raw.getId());
            row.setHitCount(0);
            row.setExpireAt(expireAt);
            row.setFetchedAt(raw.getFetchedAt());
            row.setEnv(ctx.getEnv());
            apiCacheKeyService.insertApiCacheKey(row);
            cached = row;
        }
        else
        {
            cached.setRawId(raw.getId());
            cached.setExpireAt(expireAt);
            cached.setFetchedAt(raw.getFetchedAt());
            apiCacheKeyService.updateApiCacheKey(cached);
        }
    }

    private void bumpHitCount(ApiCacheKey cached)
    {
        try
        {
            cached.setHitCount((cached.getHitCount() == null ? 0 : cached.getHitCount()) + 1);
            apiCacheKeyService.updateApiCacheKey(cached);
        }
        catch (Exception e)
        {
            log.warn("缓存命中计数更新失败: {}", e.getMessage());
        }
    }

    private Date expireAt(ApiDict dict)
    {
        int ttl = dict.getCacheTtlSec() == null ? 86400 : dict.getCacheTtlSec();
        return new Date(System.currentTimeMillis() + ttl * 1000L);
    }

    private TycResult block(int apiId, String subjectKey, Map<String, Object> params, CallContext ctx,
                            TycResult result, int code, String reason)
    {
        result.setBlocked(true);
        result.setBlockCode(code);
        result.setBlockReason(reason);
        result.setFetchedAt(new Date());
        writeCallLog(apiId, selectDict(apiId), subjectKey, params, ctx, null, "0", BigDecimal.ZERO,
                SrmConstants.CALL_RESULT_BLOCKED, String.valueOf(code), reason);
        return result;
    }

    private ApiCallLog writeCallLog(int apiId, ApiDict dict, String subjectKey, Map<String, Object> params,
                                    CallContext ctx, Long rawId, String cacheHit, BigDecimal unit,
                                    String callResult, String blockReason, String respMsg)
    {
        ApiCallLog row = new ApiCallLog();
        row.setTraceId(ctx.getTraceId());
        row.setTaskId(ctx.getTaskId());
        row.setCallTime(new Date());
        // 缺口③配套：cron 调度线程（SrmMonitorScheduler）无登录态，SecurityUtils 会抛异常；
        // 改为鉴权存在才取，缺失则留空（IpUtils 无请求时自带 unknown 兑底）。
        if (com.ruoyi.common.utils.SecurityUtils.getAuthentication() != null)
        {
            row.setUserId(com.ruoyi.common.utils.SecurityUtils.getUserId());
            row.setUserName(com.ruoyi.common.utils.SecurityUtils.getUsername());
            row.setDeptId(com.ruoyi.common.utils.SecurityUtils.getDeptId());
        }
        row.setSourceIp(IpUtils.getIpAddr());
        row.setApiId(apiId);
        // TYC-CALL 外调天眼查：字典缺失时回退 SrmApiId 元数据，保证留痕一定能看出编号/接口名
        row.setApiName(dict == null || StringUtils.isEmpty(dict.getApiName())
                ? SrmApiId.label(apiId) : dict.getApiName() + "（" + apiId + "）");
        row.setSubjectKey(subjectKey);
        row.setParamDigest(SrmUtils.paramDigest(params));
        row.setPageNo(pageNo(params));
        row.setCallResult(callResult);
        row.setBlockReason(blockReason);
        row.setRespMsg(respMsg);
        row.setCostMs(0);
        row.setUnitConsumed(unit);
        row.setCacheHit(cacheHit);
        row.setEnv(ctx.getEnv());
        apiCallLogService.insertApiCallLog(row);
        return row;
    }

    private Integer pageNo(Map<String, Object> params)
    {
        Object v = params.get("pageNum");
        if (v == null)
        {
            return null;
        }
        try
        {
            return Integer.parseInt(v.toString());
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    private void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject data, Long rawId)
    {
        if (projectors == null || projectors.isEmpty())
        {
            return;
        }
        for (TycProjector projector : projectors)
        {
            if (projector.supports(apiId))
            {
                try
                {
                    projector.project(apiId, subjectKey, params, data, rawId);
                }
                catch (Exception e)
                {
                    log.error("外调结果投影失败 apiId={} subjectKey={}", apiId, subjectKey, e);
                }
            }
        }
    }

    private static BigDecimal nvl(BigDecimal value)
    {
        return value == null ? BigDecimal.ZERO : value;
    }
}
