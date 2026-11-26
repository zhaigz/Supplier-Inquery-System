package com.ruoyi.srm.schedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.constant.SrmConstants;
import com.ruoyi.srm.domain.AnalysisTask;
import com.ruoyi.srm.domain.ChangeEvent;
import com.ruoyi.srm.domain.DataSnapshot;
import com.ruoyi.srm.domain.MonitorPolicy;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.gateway.CallContext;
import com.ruoyi.srm.gateway.TycGateway;
import com.ruoyi.srm.gateway.TycResult;
import com.ruoyi.srm.service.IAnalysisTaskService;
import com.ruoyi.srm.service.IChangeEventService;
import com.ruoyi.srm.service.IDataSnapshotService;
import com.ruoyi.srm.service.IMonitorPolicyService;
import com.ruoyi.srm.service.ISupplierService;
import com.ruoyi.srm.util.SrmUtils;

/**
 * 监控调度自管（用户决策 5：模块内自管）。
 *
 * <p>页面15 的新增/修改/启停/删除要求"保存即注册调度、停用即暂停 job、删除即移除调度"。
 * 这里用模块自带的 {@link ThreadPoolTaskScheduler} 注册 cron，不依赖 ruoyi-quartz 的 sys_job，
 * 避免 SRM 模块反向依赖调度中台；启动完成时按库内启用策略重建一次。
 */
@Component
public class SrmMonitorScheduler
{
    private static final Logger log = LoggerFactory.getLogger(SrmMonitorScheduler.class);

    private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private final Map<Long, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    @Autowired
    private IMonitorPolicyService monitorPolicyService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private TycGateway tycGateway;

    @Autowired
    private IDataSnapshotService dataSnapshotService;

    @Autowired
    private IChangeEventService changeEventService;

    @Autowired
    private IAnalysisTaskService analysisTaskService;

    /** 缺口③：监控维度 policyCode → 采集外调编号（纪要口径：不采购付费监控接口，按周期调基础接口；822/458 等文档标注接口不在 16 编号白名单内，用白名单接口近似）。 */
    private static final Map<String, int[]> POLICY_APIS = new LinkedHashMap<>();

    /** 缺口③：监控维度 policyCode → srm_data_snapshot.snapshot_type（与 mock 归档口径一致）。 */
    private static final Map<String, String> SNAPSHOT_TYPE = new LinkedHashMap<>();

    /** 缺口③：监控维度 policyCode → 变更事件默认风险等级（srm_change_event.level_tag / high_risk_flag）。 */
    private static final Map<String, String> DIM_LEVEL = new LinkedHashMap<>();

    static
    {
        POLICY_APIS.put("biz_info", new int[] { SrmApiId.BASIC_INFO });
        POLICY_APIS.put("shareholder", new int[] { SrmApiId.SHAREHOLDER });
        POLICY_APIS.put("legal_person", new int[] { SrmApiId.BASIC_INFO });
        POLICY_APIS.put("person", new int[] { SrmApiId.BASIC_INFO_PERSON });
        POLICY_APIS.put("abnormal_punish", new int[] { SrmApiId.ADMIN_PUNISH_ABNORMAL, SrmApiId.ADMIN_PUNISH_ILLEGAL });
        POLICY_APIS.put("judicial", new int[] { SrmApiId.JUDICIAL_SUMMARY });
        POLICY_APIS.put("invest", new int[] { SrmApiId.BASIC_INFO });
        SNAPSHOT_TYPE.put("biz_info", "industry_info");
        SNAPSHOT_TYPE.put("legal_person", "industry_info");
        SNAPSHOT_TYPE.put("invest", "industry_info");
        SNAPSHOT_TYPE.put("shareholder", "shareholder");
        SNAPSHOT_TYPE.put("person", "person");
        SNAPSHOT_TYPE.put("abnormal_punish", "risk_abnormal");
        SNAPSHOT_TYPE.put("judicial", "judicial");
        DIM_LEVEL.put("biz_info", "低");
        DIM_LEVEL.put("shareholder", "高");
        DIM_LEVEL.put("legal_person", "高");
        DIM_LEVEL.put("person", "中");
        DIM_LEVEL.put("abnormal_punish", "高");
        DIM_LEVEL.put("judicial", "高");
        DIM_LEVEL.put("invest", "中");
    }

    public SrmMonitorScheduler()
    {
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("srm-monitor-");
        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.initialize();
    }

    /** 应用启动完成后按库内启用策略重建调度。 */
    @EventListener(ApplicationReadyEvent.class)
    public void reload()
    {
        try
        {
            for (MonitorPolicy policy : nvl(monitorPolicyService.selectMonitorPolicyList(new MonitorPolicy())))
            {
                if (SrmConstants.YES.equals(policy.getEnabled())) register(policy);
                else unregister(policy.getId());
            }
        }
        catch (Exception e)
        {
            log.warn("监控策略调度重建失败（不影响启动）: {}", e.getMessage());
        }
    }

    /** 保存即注册调度；cron 缺失则先移除。 */
    public synchronized void register(MonitorPolicy policy)
    {
        if (policy == null || policy.getId() == null) return;
        unregister(policy.getId());
        if (!SrmConstants.YES.equals(policy.getEnabled())) return;
        if (StringUtils.isEmpty(policy.getCronExpr()))
        {
            log.warn("监控策略 {} 启用但无 cronExpr，跳过注册", policy.getId());
            return;
        }
        try
        {
            ScheduledFuture<?> future = scheduler.schedule(() -> runPolicy(policy),
                    new CronTrigger(policy.getCronExpr()));
            if (future != null) tasks.put(policy.getId(), future);
        }
        catch (Exception e)
        {
            log.error("监控策略 {} cron 注册失败: {}", policy.getId(), e.getMessage());
        }
    }

    /** 停用/删除即移除调度。 */
    public synchronized void unregister(Long policyId)
    {
        if (policyId == null) return;
        ScheduledFuture<?> future = tasks.remove(policyId);
        if (future != null) future.cancel(false);
    }

    /**
     * 缺口③修复：单次采集不再只打日志——按策略扫描范围内的重点监控供应商逐户调天眼查基础接口，
     * 结果落 srm_data_snapshot 基线，与上一次快照 hash 比对发现差异则写 srm_change_event（页面8 消费），
     * 全程经 TycGateway 六段流水线留痕（enabled=false 时照常留痕不发真实 HTTP，不产生事件）。
     */
    private void runPolicy(MonitorPolicy policy)
    {
        try
        {
            // 以库内最新配置为准（cron 注册后策略可能已被编辑）
            MonitorPolicy fresh = monitorPolicyService.selectMonitorPolicyById(policy.getId());
            MonitorPolicy p = fresh == null ? policy : fresh;
            if (!SrmConstants.YES.equals(p.getEnabled()))
            {
                log.info("监控策略 {} 已停用，跳过本次采集", p.getId());
                return;
            }
            log.info("监控策略触发: id={} code={} scope={}", p.getId(), p.getPolicyCode(), p.getScanScope());
            // scanScope=all 在 DDL 口径中标注「禁止」：全库付费采集不可行，直接移除调度并告警
            if ("all".equalsIgnoreCase(p.getScanScope() == null ? "" : p.getScanScope()))
            {
                log.warn("监控策略 {} scanScope=all 属禁止口径（全库付费采集），不执行", p.getId());
                unregister(p.getId());
                return;
            }
            // 默认 monitor_flag：仅扫描已勾选重点监控且未停用/未删除的供应商（页面1 勾 selection 进页面8 监控范围）
            Supplier query = new Supplier();
            query.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
            query.setStatus("0");
            query.setMonitorFlag(SrmConstants.YES);
            List<Supplier> suppliers = nvl(supplierService.selectSupplierList(query));
            if (suppliers.isEmpty())
            {
                log.info("监控策略 {} 扫描完成：无重点监控供应商", p.getId());
                return;
            }
            AnalysisTask task = new AnalysisTask();
            task.setTaskNo("MO" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            task.setTaskType(SrmConstants.TASK_TYPE_MONITOR);
            task.setBizScene(SrmConstants.BIZ_SCENE_PAGE15);
            task.setSubjectKey(p.getPolicyCode());
            task.setSourceSubject(p.getPolicyName());
            task.setTaskStatus(SrmConstants.TASK_STATUS_RUNNING);
            task.setProgressStep("RUNNING");
            task.setBillFlag(SrmConstants.BILL_FLAG_UNBILLED);
            task.setEnv(SrmConstants.CACHE_ENV_PROD);
            task.setCreateBy("system");
            task.setCreateTime(new Date());
            analysisTaskService.insertAnalysisTask(task);
            CallContext ctx = new CallContext();
            // 调度线程无登录态：manual=false，放行与否由 srm_trigger_rule（scene=monitor_scan）决定
            ctx.setManual(false);
            ctx.setTaskId(task.getId());
            ctx.setScene(SrmConstants.SCENE_MONITOR_SCAN);
            int changed = 0;
            int failed = 0;
            for (Supplier supplier : suppliers)
            {
                try
                {
                    if (scanOne(supplier, p, ctx)) changed++;
                }
                catch (Exception e)
                {
                    failed++;
                    log.error("监控采集失败 supplierId={} policy={}", supplier.getId(), p.getPolicyCode(), e);
                }
            }
            task.setTaskStatus(failed == suppliers.size() ? SrmConstants.TASK_STATUS_FAIL : SrmConstants.TASK_STATUS_SUCCESS);
            task.setProgressStep("DONE");
            task.setResultCount(changed);
            task.setFinishTime(new Date());
            task.setUpdateTime(new Date());
            task.setCostMs((int) (task.getFinishTime().getTime() - task.getCreateTime().getTime()));
            analysisTaskService.updateAnalysisTask(task);
            log.info("监控策略 {} 扫描完成：供应商 {} 家，变更事件 {} 条，失败 {} 家", p.getId(), suppliers.size(), changed, failed);
        }
        catch (Exception e)
        {
            log.error("监控策略 {} 执行失败", policy.getId(), e);
        }
    }

    /** 单供应商单维度采集：外调→建 payload→存快照→与上次 hash 比对，差异则写变更事件。返回是否产生变更事件。 */
    private boolean scanOne(Supplier supplier, MonitorPolicy policy, CallContext ctx)
    {
        int[] apiIds = POLICY_APIS.get(policy.getPolicyCode());
        if (apiIds == null)
        {
            log.warn("监控维度 {} 未配置采集接口，跳过 supplierId={}", policy.getPolicyCode(), supplier.getId());
            return false;
        }
        String subjectKey = StringUtils.isNotEmpty(supplier.getCreditCode()) ? supplier.getCreditCode() : supplier.getSupplierName();
        if (StringUtils.isEmpty(subjectKey)) return false;
        Map<Integer, JSONObject> bodies = new LinkedHashMap<>();
        for (int apiId : apiIds)
        {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("keyword", subjectKey);
            // biz_info 与 legal_person/invest 同调 818：网关缓存前置②命中免重复扣费
            // TODO[天眼查接入] 待接入天眼查对应编号：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
            // TYC-CALL 外调天眼查（监控周期采集，基础接口近似监控）
            TycResult r = tycGateway.call(apiId, subjectKey, params, ctx);
            if (r.isBlocked() || r.getData() == null) continue;
            Object code = r.getData().get("error_code");
            if (code != null && !"0".equals(String.valueOf(code))) continue;
            bodies.put(apiId, r.getData());
        }
        if (bodies.isEmpty()) return false;
        MonitorPayload mp = buildPayload(policy.getPolicyCode(), bodies);
        if (mp == null) return false;
        if (mp.unrecognized)
        {
            // 818 对外投资等字段未接权威映射：只建基线不判变更，避免臆造字段导致误报/漏报
            log.info("监控维度 {} 响应字段未识别，仅建基线 supplierId={}", policy.getPolicyCode(), supplier.getId());
        }
        DataSnapshot prev = lastSnapshot(supplier.getId(), policy.getPolicyCode());
        DataSnapshot curr = saveSnapshot(supplier, policy.getPolicyCode(), mp, bodies);
        if (prev != null && StringUtils.equals(prev.getSnapshotHash(), curr.getSnapshotHash())) return false;
        if (prev == null)
        {
            // 首次采集只立基线，无历史可比不产生变更事件
            return false;
        }
        if (mp.unrecognized)
        {
            return false;
        }
        writeEvent(supplier, policy, prev, curr, mp);
        return true;
    }

    /** 采集 payload：字段键严格按《天眼查API接口》真实响应逐字取键（与现有投影器同源），绝不臆造。 */
    private MonitorPayload buildPayload(String policyCode, Map<Integer, JSONObject> bodies)
    {
        JSONObject basic = bodies.get(SrmApiId.BASIC_INFO);
        JSONObject person = bodies.get(SrmApiId.BASIC_INFO_PERSON);
        JSONObject shareholder = bodies.get(SrmApiId.SHAREHOLDER);
        JSONObject abnormal = bodies.get(SrmApiId.ADMIN_PUNISH_ABNORMAL);
        JSONObject illegal = bodies.get(SrmApiId.ADMIN_PUNISH_ILLEGAL);
        JSONObject judicial = bodies.get(SrmApiId.JUDICIAL_SUMMARY);
        Map<String, Object> fields = new LinkedHashMap<>();
        MonitorPayload mp = new MonitorPayload();
        if ("biz_info".equals(policyCode))
        {
            JSONObject res = basic == null ? null : basic.getJSONObject("result");
            if (res == null) return null;
            fields.put("entName", str(res, "name", "entName"));
            fields.put("creditCode", str(res, "creditCode"));
            fields.put("legalPerson", str(res, "legalPerson", "legalPersonName"));
            fields.put("regCapital", str(res, "regCapital", "actualCapital"));
            fields.put("bizStatus", str(res, "regStatus", "bizStatus"));
            fields.put("regAddress", str(res, "regLocation", "regAddress"));
        }
        else if ("legal_person".equals(policyCode))
        {
            JSONObject res = basic == null ? null : basic.getJSONObject("result");
            if (res == null) return null;
            fields.put("legalPerson", str(res, "legalPerson", "legalPersonName"));
        }
        else if ("shareholder".equals(policyCode))
        {
            // 821 容器兼容 result 直接为数组与 result.items[]（同 ShareholderProjector）
            JSONArray items = itemsOf(shareholder);
            if (items == null) return null;
            Set<String> holders = new LinkedHashSet<>();
            for (int i = 0; i < items.size(); i++)
            {
                JSONObject item = items.getJSONObject(i);
                String name = item == null ? null : item.getString("name");
                if (StringUtils.isEmpty(name)) continue;
                String percent = null;
                JSONArray capital = item.getJSONArray("capital");
                if (capital != null && !capital.isEmpty())
                {
                    JSONObject c0 = capital.getJSONObject(0);
                    percent = c0 == null ? null : c0.getString("percent");
                }
                holders.add(StringUtils.isEmpty(percent) ? name : name + " " + percent.trim());
            }
            fields.put("holders", String.join("、", holders));
        }
        else if ("person".equals(policyCode))
        {
            // 819 主要人员容器候选键逐字保留，接权威映射后收敛
            JSONArray staff = firstArray(person == null ? null : person.getJSONObject("result"),
                    "staffList", "staff", "personList", "members");
            if (staff != null)
            {
                Set<String> names = new LinkedHashSet<>();
                for (int i = 0; i < staff.size(); i++)
                {
                    JSONObject j = staff.getJSONObject(i);
                    if (j == null) continue;
                    names.add(str(j, "name", "personName") + "(" + str(j, "office", "job") + ")");
                }
                fields.put("staff", String.join("、", names));
            }
            else if (person != null)
            {
                fields.put("resultSig", SrmUtils.md5(person.getJSONObject("result") == null
                        ? "" : person.getJSONObject("result").toJSONString()));
                mp.unrecognized = true;
            }
            else return null;
        }
        else if ("abnormal_punish".equals(policyCode))
        {
            if (abnormal == null && illegal == null) return null;
            fields.put("abnormal", riskSig(abnormal));
            fields.put("illegal", riskSig(illegal));
        }
        else if ("judicial".equals(policyCode))
        {
            if (judicial == null) return null;
            fields.put("judicial", riskSig(judicial));
        }
        else if ("invest".equals(policyCode))
        {
            // TODO[天眼查接入] 对外投资无独立白名单接口（822 不在 16 编号内），818 响应中投资列表字段名待权威映射确认；
            // 命中候选键则结构化比对，否则仅以 result 签名建基线不判变更。
            JSONArray inv = firstArray(basic == null ? null : basic.getJSONObject("result"),
                    "investmentList", "inverstTrees", "children");
            if (inv != null)
            {
                Set<String> names = new LinkedHashSet<>();
                for (int i = 0; i < inv.size(); i++)
                {
                    JSONObject j = inv.getJSONObject(i);
                    String name = j == null ? null : str(j, "name", "entName");
                    if (StringUtils.isNotEmpty(name)) names.add(name);
                }
                fields.put("investments", String.join("、", names));
            }
            else if (basic != null)
            {
                fields.put("resultSig", SrmUtils.md5(basic.getJSONObject("result") == null
                        ? "" : basic.getJSONObject("result").toJSONString()));
                mp.unrecognized = true;
            }
            else return null;
        }
        else return null;
        mp.canonical = SrmUtils.canonicalJson(fields);
        mp.hash = SrmUtils.md5(mp.canonical);
        mp.human = humanize(fields);
        return mp;
    }

    /** 风险类接口（846/848/1002）比对签名：条数 + 各条标识取键的有序集合 hash。 */
    private String riskSig(JSONObject body)
    {
        if (body == null) return null;
        JSONArray items = itemsOf(body);
        if (items == null) return "sig:" + SrmUtils.md5(String.valueOf(body.get("result")));
        Set<String> sig = new LinkedHashSet<>();
        for (int i = 0; i < items.size(); i++)
        {
            JSONObject item = items.getJSONObject(i);
            if (item == null) continue;
            sig.add(str(item, "casecode", "caseCode", "putReason", "decisionNo", "illegalReason"));
        }
        return "count=" + items.size() + ";items=" + String.join("|", sig);
    }

    /** 生成人可读的变更事项与前后值（对齐 mock 口径：如「新增股东」刘志强 55%、赵文博 45%）。 */
    private String humanize(Map<String, Object> fields)
    {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> e : fields.entrySet())
        {
            if (sb.length() > 0) sb.append("；");
            sb.append(e.getKey()).append("=").append(e.getValue());
        }
        return sb.toString();
    }

    /** 取上一个同维度基线快照（按 capturedAt 降序首条）。 */
    private DataSnapshot lastSnapshot(Long supplierId, String policyCode)
    {
        DataSnapshot q = new DataSnapshot();
        q.setSupplierId(supplierId);
        q.setSnapshotType(SNAPSHOT_TYPE.get(policyCode));
        List<DataSnapshot> rows = nvl(dataSnapshotService.selectDataSnapshotList(q));
        DataSnapshot last = null;
        for (DataSnapshot row : rows)
        {
            if (row.getSnapshotKey() != null && !row.getSnapshotKey().startsWith("SCHED|")) continue;
            if (last == null || (row.getCapturedAt() != null && last.getCapturedAt() != null
                    && row.getCapturedAt().after(last.getCapturedAt()))) last = row;
        }
        return last;
    }

    private DataSnapshot saveSnapshot(Supplier supplier, String policyCode, MonitorPayload mp, Map<Integer, JSONObject> bodies)
    {
        Date now = new Date();
        DataSnapshot snap = new DataSnapshot();
        snap.setSupplierId(supplier.getId());
        snap.setCreditCode(supplier.getCreditCode());
        snap.setSnapshotType(SNAPSHOT_TYPE.get(policyCode));
        // 调度采集键与手工回放键（T{taskId}|…）区分，前缀 SCHED| 便于 lastSnapshot 同族比对
        snap.setSnapshotKey("SCHED|" + policyCode);
        snap.setSnapshotHash(mp.hash);
        snap.setPayloadJson(mp.canonical);
        StringBuilder apiIds = new StringBuilder();
        for (Integer id : bodies.keySet())
        {
            if (apiIds.length() > 0) apiIds.append(",");
            apiIds.append(id);
        }
        snap.setApiIds(apiIds.toString());
        snap.setCapturedAt(now);
        snap.setCreateBy("system");
        snap.setCreateTime(now);
        dataSnapshotService.insertDataSnapshot(snap);
        return snap;
    }

    /** 写 srm_change_event（页面8 变更事件流消费），字段口径逐字对齐 mock 归档。 */
    private void writeEvent(Supplier supplier, MonitorPolicy policy, DataSnapshot prev, DataSnapshot curr, MonitorPayload mp)
    {
        Date now = new Date();
        ChangeEvent ev = new ChangeEvent();
        ev.setSupplierId(supplier.getId());
        ev.setSupplierName(supplier.getSupplierName());
        ev.setCreditCode(supplier.getCreditCode());
        ev.setLegalPerson(supplier.getLegalPerson());
        ev.setLastIndustryUpdate(now);
        ev.setMonitorStatus("重点监控");
        ev.setChangeAt(now);
        ev.setChangeItem(StringUtils.isNotEmpty(policy.getPolicyName()) ? policy.getPolicyName() + "变更" : policy.getPolicyCode() + " 变更");
        ev.setDimension(policy.getPolicyCode());
        String level = DIM_LEVEL.getOrDefault(policy.getPolicyCode(), "中");
        ev.setLevelTag(level);
        ev.setHighRiskFlag("高".equals(level) ? SrmConstants.YES : SrmConstants.NO);
        ev.setBeforeValue(prev == null ? null : humanOf(prev.getPayloadJson(), mp));
        ev.setAfterValue(mp.human);
        ev.setPrevSnapshotId(prev == null ? null : prev.getId());
        ev.setCurrSnapshotId(curr.getId());
        ev.setHandleStatus("待处理");
        ev.setCreateTime(now);
        changeEventService.insertChangeEvent(ev);
    }

    /** 变更前值：从上一次快照 payload_json 还原 human 形态（解析失败则直接展示原文）。 */
    private String humanOf(String payloadJson, MonitorPayload mp)
    {
        if (StringUtils.isEmpty(payloadJson)) return null;
        try
        {
            JSONObject obj = JSONObject.parseObject(payloadJson);
            StringBuilder sb = new StringBuilder();
            for (String key : obj.keySet())
            {
                if (sb.length() > 0) sb.append("；");
                sb.append(key).append("=").append(obj.getString(key));
            }
            return sb.length() == 0 ? payloadJson : sb.toString();
        }
        catch (Exception e)
        {
            return payloadJson;
        }
    }

    /** 单次采集的比对载荷。 */
    private static class MonitorPayload
    {
        String canonical;
        String hash;
        String human;
        /** 响应字段未命中权威键名（无 field_mapping 配置），只建基线不判变更 */
        boolean unrecognized;
    }

    /** 818 等接口的取值兼容：按候选键顺序取第一个非空（同 GraphProjectionServiceImpl.pick）。 */
    private String str(JSONObject obj, String... keys)
    {
        if (obj == null) return null;
        for (String k : keys)
        {
            Object v = obj.get(k);
            if (v != null && StringUtils.isNotEmpty(String.valueOf(v))) return String.valueOf(v);
        }
        return null;
    }

    /** 取 result.items 数组；兼容 result 直接为数组与 result.items 两种容器（同 ShareholderProjector）。 */
    private JSONArray itemsOf(JSONObject body)
    {
        if (body == null) return null;
        Object resNode = body.get("result");
        if (resNode instanceof JSONArray) return (JSONArray) resNode;
        if (resNode instanceof JSONObject) return ((JSONObject) resNode).getJSONArray("items");
        return null;
    }

    /** 按候选键顺序取第一个非空数组。 */
    private JSONArray firstArray(JSONObject obj, String... keys)
    {
        if (obj == null) return null;
        for (String k : keys)
        {
            JSONArray arr = obj.getJSONArray(k);
            if (arr != null && !arr.isEmpty()) return arr;
        }
        return null;
    }

    private <T> java.util.List<T> nvl(java.util.List<T> v) { return v == null ? new ArrayList<T>() : v; }
}