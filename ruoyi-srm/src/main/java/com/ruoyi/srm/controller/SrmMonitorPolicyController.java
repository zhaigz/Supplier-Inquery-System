package com.ruoyi.srm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmConstants;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.ApiCallLog;
import com.ruoyi.srm.domain.DataSnapshot;
import com.ruoyi.srm.domain.MonitorPolicy;
import com.ruoyi.srm.service.IApiCallLogService;
import com.ruoyi.srm.service.IDataSnapshotService;
import com.ruoyi.srm.service.IMonitorPolicyService;
import com.ruoyi.srm.schedule.SrmMonitorScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面15「监控范围与频率」接口，共 7 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 *
 * <p>TODO[天眼查接入] 需求差异（本类当前无外调，清单口径）：本页 7 条清单接口均无外调。
 * 需求文档要求“监控范围与频率”在扫描时调用 848（ADMIN_PUNISH_ABNORMAL · 经营异常）与
 * 846（ADMIN_PUNISH_ILLEGAL · 严重违法）；因扫描接口不在本轮 134 条清单内，按“不增删条目”
 * 约束未补调用，待拍板后另行补入清单并经 TycGateway.call 落点。
 */
@RestController
@RequestMapping("/srm/monitor-policy")
public class SrmMonitorPolicyController extends BaseController
{
    @Autowired private IMonitorPolicyService monitorPolicyService;
    @Autowired private IDataSnapshotService dataSnapshotService;
    @Autowired private IApiCallLogService apiCallLogService;
    @Autowired private SrmMonitorScheduler monitorScheduler;

    /**
     * 策略清单
     *
     * <p>页面 15「监控范围与频率」；权限 srm:monitorPolicy:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:monitorPolicy:list')")
    @GetMapping("/list")
    public TableDataInfo list(MonitorPolicy query)
    {
        startPage(); startOrderBy();
        return getDataTable(monitorPolicyService.selectMonitorPolicyList(query == null ? new MonitorPolicy() : query));
    }

    /**
     * 策略详情
     *
     * <p>页面 15「监控范围与频率」；权限 srm:monitorPolicy:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:monitorPolicy:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(requirePolicy(id));
    }

    /**
     * 新增（保存即注册调度）
     *
     * <p>页面 15「监控范围与频率」；权限 srm:monitorPolicy:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:monitorPolicy:add')")
    @Log(title="监控策略", businessType=BusinessType.INSERT)
    @SrmAudit(module="监控策略", action="新增", object="策略", detail="新增监控策略并注册调度")
    @PostMapping
    public AjaxResult add(@RequestBody MonitorPolicy policy)
    {
        if (policy == null || StringUtils.isEmpty(policy.getPolicyName())) throw new ServiceException("policyName不能为空", SrmErrorCode.CODE_60008);
        if (StringUtils.isEmpty(policy.getPolicyCode())) policy.setPolicyCode("MP" + System.currentTimeMillis());
        if (StringUtils.isEmpty(policy.getEnabled())) policy.setEnabled(SrmConstants.NO);
        if (SrmConstants.YES.equals(policy.getEnabled()) && StringUtils.isEmpty(policy.getCronExpr()))
            throw new ServiceException("启用策略必须填写cronExpr", SrmErrorCode.CODE_60008);
        policy.setId(null);
        policy.setCreateBy(getUsername());
        policy.setCreateTime(new Date());
        monitorPolicyService.insertMonitorPolicy(policy);
        monitorScheduler.register(policy);
        return success(policy.getId());
    }

    /**
     * 修改（同步 sys_job cron）
     *
     * <p>页面 15「监控范围与频率」；权限 srm:monitorPolicy:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:monitorPolicy:edit')")
    @Log(title="监控策略", businessType=BusinessType.UPDATE)
    @SrmAudit(module="监控策略", action="修改", object="{p:policy.id}", detail="修改监控策略并同步调度cron")
    @PutMapping
    public AjaxResult edit(@RequestBody MonitorPolicy policy)
    {
        if (policy == null || policy.getId() == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        MonitorPolicy exist = requirePolicy(policy.getId());
        if (SrmConstants.YES.equals(policy.getEnabled()) && StringUtils.isEmpty(policy.getCronExpr()) && StringUtils.isEmpty(exist.getCronExpr()))
            throw new ServiceException("启用策略必须填写cronExpr", SrmErrorCode.CODE_60008);
        policy.setUpdateBy(getUsername());
        policy.setUpdateTime(new Date());
        monitorPolicyService.updateMonitorPolicy(policy);
        monitorScheduler.register(monitorPolicyService.selectMonitorPolicyById(policy.getId()));
        return success(monitorPolicyService.selectMonitorPolicyById(policy.getId()));
    }

    /**
     * 启停（停用即暂停 job）
     *
     * <p>页面 15「监控范围与频率」；权限 srm:monitorPolicy:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:monitorPolicy:edit')")
    @Log(title="监控策略", businessType=BusinessType.UPDATE)
    @SrmAudit(module="监控策略", action="启停", object="{p:id}", detail="启停监控策略并暂停/恢复调度")
    @PutMapping("/toggle")
    public AjaxResult toggle(@RequestBody Map<String,Object> body)
    {
        Long id = longOf(body == null ? null : body.get("id"));
        MonitorPolicy exist = requirePolicy(id);
        String enabled = str(body == null ? null : body.get("enabled"));
        if (StringUtils.isEmpty(enabled)) enabled = SrmConstants.YES.equals(exist.getEnabled()) ? SrmConstants.NO : SrmConstants.YES;
        if (SrmConstants.YES.equals(enabled) && StringUtils.isEmpty(exist.getCronExpr()))
            throw new ServiceException("无cron表达式，无法启用调度", SrmErrorCode.CODE_60008);
        MonitorPolicy patch = new MonitorPolicy();
        patch.setId(id);
        patch.setEnabled(enabled);
        patch.setUpdateBy(getUsername());
        patch.setUpdateTime(new Date());
        monitorPolicyService.updateMonitorPolicy(patch);
        monitorScheduler.register(monitorPolicyService.selectMonitorPolicyById(id));
        return success(monitorPolicyService.selectMonitorPolicyById(id));
    }

    /**
     * 删除并移除调度
     *
     * <p>页面 15「监控范围与频率」；权限 srm:monitorPolicy:remove；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:monitorPolicy:remove')")
    @Log(title="监控策略", businessType=BusinessType.DELETE)
    @SrmAudit(module="监控策略", action="删除", object="{arg0}", detail="删除监控策略并移除调度")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids)
    {
        Long[] values = parseIds(ids);
        if (values.length == 0) throw new ServiceException("ids不能为空", SrmErrorCode.CODE_60008);
        for (Long id : values) monitorScheduler.unregister(id);
        return success(monitorPolicyService.deleteMonitorPolicyByIds(values));
    }

    /**
     * 最近采集时间/快照数
     *
     * <p>页面 15「监控范围与频率」；权限 srm:monitorPolicy:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:monitorPolicy:query')")
    @GetMapping("/lastRun/{id}")
    public AjaxResult lastRun(@PathVariable Long id)
    {
        requirePolicy(id);
        DataSnapshot sq = new DataSnapshot();
        sq.setSupplierId(id);
        List<DataSnapshot> snapshots = dataSnapshotService.selectDataSnapshotList(sq);
        ApiCallLog lq = new ApiCallLog();
        List<ApiCallLog> logs = apiCallLogService.selectApiCallLogList(lq);
        Date lastCollect = null;
        if (snapshots != null)
        {
            for (DataSnapshot s : snapshots) if (s.getCapturedAt() != null && (lastCollect == null || s.getCapturedAt().after(lastCollect))) lastCollect = s.getCapturedAt();
        }
        Date lastCall = null;
        if (logs != null)
        {
            for (ApiCallLog l : logs) if (l.getCallTime() != null && (lastCall == null || l.getCallTime().after(lastCall))) lastCall = l.getCallTime();
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("policyId", id);
        data.put("lastCollectAt", lastCollect);
        data.put("lastCallAt", lastCall);
        data.put("snapshotCount", snapshots == null ? 0 : snapshots.size());
        return success(data);
    }

    private MonitorPolicy requirePolicy(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        MonitorPolicy policy = monitorPolicyService.selectMonitorPolicyById(id);
        if (policy == null) throw new ServiceException("监控策略不存在", SrmErrorCode.CODE_60001);
        return policy;
    }

    private Long[] parseIds(String ids) { List<Long> list = new ArrayList<>(); if (ids != null) for (String x : ids.split(",")) { String v = x.trim(); if (v.matches("\\d+")) list.add(Long.valueOf(v)); } return list.toArray(new Long[0]); }
    private Long longOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).longValue(); return Long.valueOf(String.valueOf(v)); }
    private String str(Object v) { return v == null ? null : String.valueOf(v); }
}