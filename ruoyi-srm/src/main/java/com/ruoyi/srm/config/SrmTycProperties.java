package com.ruoyi.srm.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 天眼查网关配置项（{@code srm.tyc.*}）。
 *
 * <p>base-url / token 只从配置读取，不写入代码，也不写入任何报告或日志。
 *
 * <p>开发阶段：{@code enabled=false} 或 token 为空时，六段流水线照常执行（套餐/缓存/触发规则/
 * 预算/限流/留痕），但第⑥段不发真实 HTTP，只写"外调开关未开启，未发起真实请求"。
 * 测试/生产：把 token 与 {@link #apis} 中对应编号的 path 填好、enabled 置 true 即可直连天眼查，
 * 页面接口无需改动（页面侧只认 {@code TycGateway.call(SrmApiId.X, ...)}）。
 */
@Component
@ConfigurationProperties(prefix = "srm.tyc")
public class SrmTycProperties
{
    private static final Logger log = LoggerFactory.getLogger(SrmTycProperties.class);

    /** 是否启用真实 HTTP 外调。默认 false：只做六段前置校验与留痕，不发网络请求。 */
    private boolean enabled = false;

    /** 天眼查开放接口 base url，不含具体编号路径。 */
    private String baseUrl = "https://open.api.tianyancha.com/services/open";

    /** 访问令牌（密钥），仅服务端读取；建议用环境变量 / 配置中心注入，禁止提交明文。 */
    private String token = "";

    /** 令牌请求头名称，天眼查开放平台默认为 Authorization。 */
    private String tokenHeader = "Authorization";

    /** 单次请求超时（毫秒）。 */
    private int timeoutMs = 10000;

    /** 建连超时（毫秒）。 */
    private int connectTimeoutMs = 5000;

    /** 失败重试次数（仅网络异常与 5xx 重试，4xx 与业务失败不重试）。 */
    private int retryTimes = 2;

    /** 重试间隔基数（毫秒），按 retryTimes 线性递增。 */
    private long retryBackoffMs = 500L;

    /** 默认分桶环境：prod / test。 */
    private String defaultEnv = "prod";

    /** 未在 {@link #apis} 显式配置时使用的默认请求方法（GET/POST）。 */
    private String defaultMethod = "GET";

    /** 未在 {@link #apis} 显式配置时使用的默认路径模板，{apiId} 会替换为外调编号。 */
    private String defaultPathTemplate = "/{apiId}";

    /** 原始响应超过该字节数时落文件（storage_type=file）。 */
    private long rawInlineMaxBytes = 262144L;

    /** 原始响应落文件的目录。 */
    private String rawFileDir = "./srm-raw";

    /**
     * 按外调编号的接入明细：key 为编号字符串（如 "818"），value 为 path / method。
     *
     * <p>路径与请求方法以《天眼查API接口.xlsx》的"详细URL"与开放平台在线文档为准，
     * 开发阶段可留空（走 {@link #defaultPathTemplate}），测试接真实接口时逐条填。
     */
    private Map<String, ApiEndpoint> apis = new LinkedHashMap<>();

    /**
     * 单个外调编号的接入明细。
     */
    public static class ApiEndpoint
    {
        /** 相对 base-url 的路径，如 /ic/baseinfo/2.0。 */
        private String path;

        /** 请求方法：GET 或 POST；空则用 {@link SrmTycProperties#getDefaultMethod()}。 */
        private String method;

        /** 单接口开关，false 时该编号直接拦截并留痕（不影响其它编号）。 */
        private boolean enabled = true;

        public String getPath()
        {
            return path;
        }

        public void setPath(String path)
        {
            this.path = path;
        }

        public String getMethod()
        {
            return method;
        }

        public void setMethod(String method)
        {
            this.method = method;
        }

        public boolean isEnabled()
        {
            return enabled;
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }
    }

    /**
     * 取某编号的接入明细；未配置返回 null（网关回落到默认路径/方法）。
     */
    public ApiEndpoint endpointOf(int apiId)
    {
        return apis == null ? null : apis.get(String.valueOf(apiId));
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getTokenHeader()
    {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader)
    {
        this.tokenHeader = tokenHeader;
    }

    public int getTimeoutMs()
    {
        return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs)
    {
        this.timeoutMs = timeoutMs;
    }

    public int getConnectTimeoutMs()
    {
        return connectTimeoutMs;
    }

    public void setConnectTimeoutMs(int connectTimeoutMs)
    {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    public int getRetryTimes()
    {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes)
    {
        this.retryTimes = retryTimes;
    }

    public long getRetryBackoffMs()
    {
        return retryBackoffMs;
    }

    public void setRetryBackoffMs(long retryBackoffMs)
    {
        this.retryBackoffMs = retryBackoffMs;
    }

    public String getDefaultEnv()
    {
        return defaultEnv;
    }

    public void setDefaultEnv(String defaultEnv)
    {
        this.defaultEnv = defaultEnv;
    }

    public String getDefaultMethod()
    {
        return defaultMethod;
    }

    public void setDefaultMethod(String defaultMethod)
    {
        this.defaultMethod = defaultMethod;
    }

    public String getDefaultPathTemplate()
    {
        return defaultPathTemplate;
    }

    public void setDefaultPathTemplate(String defaultPathTemplate)
    {
        this.defaultPathTemplate = defaultPathTemplate;
    }

    public long getRawInlineMaxBytes()
    {
        return rawInlineMaxBytes;
    }

    public void setRawInlineMaxBytes(long rawInlineMaxBytes)
    {
        this.rawInlineMaxBytes = rawInlineMaxBytes;
    }

    public String getRawFileDir()
    {
        return rawFileDir;
    }

    public void setRawFileDir(String rawFileDir)
    {
        this.rawFileDir = rawFileDir;
    }

    public Map<String, ApiEndpoint> getApis()
    {
        return apis;
    }

    public void setApis(Map<String, ApiEndpoint> apis)
    {
        this.apis = apis;
    }

    /**
     * 启动时打印一次接入概况：总开关、令牌是否已注入、逐编号转接点数量。
     *
     * <p>用途：① 开发阶段确认 {@code srm.tyc.apis} 的数字编号键已被 Spring 正确绑定；
     * ② 上线前核对"配置了哪些编号、哪些编号被单点关闭"。不打印令牌明文，只打印是否为空。
     */
    @PostConstruct
    public void logSummary()
    {
        List<String> disabled = new ArrayList<>();
        if (apis != null)
        {
            for (Map.Entry<String, ApiEndpoint> e : apis.entrySet())
            {
                if (e.getValue() == null || !e.getValue().isEnabled())
                {
                    disabled.add(e.getKey());
                }
            }
        }
        log.info("天眼查接入配置: enabled={}, token={}, 编号数={}, 单点关闭编号={}, baseUrl={}",
                enabled, token == null || token.isEmpty() ? "未注入" : "已注入",
                apis == null ? 0 : apis.size(), disabled.isEmpty() ? "无" : disabled, baseUrl);
    }
}
