package com.ruoyi.srm.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.domain.ApiCallLog;
import com.ruoyi.srm.domain.BudgetPolicy;
import com.ruoyi.srm.domain.ChangeEvent;
import com.ruoyi.srm.domain.ClueReview;
import com.ruoyi.srm.domain.MonitorPolicy;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.UsageStat;
import com.ruoyi.srm.service.IApiCallLogService;
import com.ruoyi.srm.service.IBudgetPolicyService;
import com.ruoyi.srm.service.IChangeEventService;
import com.ruoyi.srm.service.IClueReviewService;
import com.ruoyi.srm.service.IMonitorPolicyService;
import com.ruoyi.srm.service.ISupplierService;
import com.ruoyi.srm.service.IUsageStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面18「数据总览」接口，共 4 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/overview")
public class SrmOverviewController extends BaseController
{
    @Autowired private ISupplierService supplierService;
    @Autowired private IMonitorPolicyService monitorPolicyService;
    @Autowired private IChangeEventService changeEventService;
    @Autowired private IClueReviewService clueReviewService;
    @Autowired private IApiCallLogService apiCallLogService;
    @Autowired private IUsageStatService usageStatService;
    @Autowired private IBudgetPolicyService budgetPolicyService;

    /**
     * KPI 卡
     *
     * <p>页面 18「数据总览」；权限 srm:overview:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:overview:list')")
    @GetMapping("/kpi")
    public AjaxResult kpi()
    {
        List<Supplier> suppliers = supplierService.selectSupplierList(new Supplier());
        int monitored = 0;
        for (Supplier s : nvl(suppliers)) if ("1".equals(s.getMonitorFlag())) monitored++;
        List<MonitorPolicy> policies = monitorPolicyService.selectMonitorPolicyList(new MonitorPolicy());
        int enabledPolicies = 0;
        for (MonitorPolicy p : nvl(policies)) if ("1".equals(p.getEnabled())) enabledPolicies++;
        List<ChangeEvent> events = changeEventService.selectChangeEventList(new ChangeEvent());
        int pending = 0;
        for (ChangeEvent e : nvl(events)) if (e.getHandleStatus() == null || "待处置".equals(e.getHandleStatus())) pending++;
        List<ClueReview> reviews = clueReviewService.selectClueReviewList(new ClueReview());
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("supplierTotal", suppliers == null ? 0 : suppliers.size());
        data.put("monitoredTotal", monitored);
        data.put("monitorPolicyTotal", policies == null ? 0 : policies.size());
        data.put("enabledPolicyTotal", enabledPolicies);
        data.put("changeEventTotal", events == null ? 0 : events.size());
        data.put("pendingChangeTotal", pending);
        data.put("clueReviewTotal", reviews == null ? 0 : reviews.size());
        data.put("callTotal", nvl(apiCallLogService.selectApiCallLogList(new ApiCallLog())).size());
        return success(data);
    }

    /**
     * 分布图
     *
     * <p>页面 18「数据总览」；权限 srm:overview:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:overview:list')")
    @GetMapping("/distribution")
    public AjaxResult distribution()
    {
        Map<String,Integer> byDataSource = new LinkedHashMap<>();
        Map<String,Integer> byMonitor = new LinkedHashMap<>();
        Map<String,Integer> byIndustry = new LinkedHashMap<>();
        Map<String,Integer> byProvince = new LinkedHashMap<>();
        for (Supplier s : nvl(supplierService.selectSupplierList(new Supplier())))
        {
            inc(byDataSource, nvl(s.getDataSource(), "未知"));
            inc(byMonitor, "1".equals(s.getMonitorFlag()) ? "已监控" : "未监控");
            inc(byIndustry, nvl(s.getIndustry(), "未知行业"));
            inc(byProvince, nvl(s.getProvince(), "未知省份"));
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("byDataSource", byDataSource);
        data.put("byMonitorFlag", byMonitor);
        data.put("byIndustry", byIndustry);
        data.put("byProvince", byProvince);
        return success(data);
    }

    /**
     * 预算进度（口径=页面16）
     *
     * <p>页面 18「数据总览」；权限 srm:overview:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:overview:list')")
    @GetMapping("/budget")
    public AjaxResult budget()
    {
        BudgetPolicy policy = firstPolicy();
        BigDecimal consumed = BigDecimal.ZERO;
        BigDecimal cost = BigDecimal.ZERO;
        for (UsageStat s : nvl(usageStatService.selectUsageStatList(new UsageStat())))
        {
            if (s.getUnitConsumed() != null) consumed = consumed.add(s.getUnitConsumed());
            if (s.getCostAmount() != null) cost = cost.add(s.getCostAmount());
        }
        BigDecimal daily = policy == null || policy.getDailyLimit() == null ? null : new BigDecimal(policy.getDailyLimit());
        BigDecimal monthly = policy == null || policy.getMonthlyLimit() == null ? null : new BigDecimal(policy.getMonthlyLimit());
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("dailyLimit", daily);
        data.put("monthlyLimit", monthly);
        data.put("consumed", consumed);
        data.put("costAmount", cost);
        data.put("remainingDaily", daily == null ? null : daily.subtract(consumed));
        data.put("remainingMonthly", monthly == null ? null : monthly.subtract(consumed));
        data.put("dailyUsageRatio", daily == null || daily.compareTo(BigDecimal.ZERO) == 0 ? null : consumed.divide(daily, 4, java.math.RoundingMode.HALF_UP));
        data.put("monthlyUsageRatio", monthly == null || monthly.compareTo(BigDecimal.ZERO) == 0 ? null : consumed.divide(monthly, 4, java.math.RoundingMode.HALF_UP));
        data.put("warnRatios", policy == null ? null : policy.getWarnRatios());
        return success(data);
    }

    /**
     * 最近动态
     *
     * <p>页面 18「数据总览」；权限 srm:overview:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:overview:list')")
    @GetMapping("/recent")
    public AjaxResult recent()
    {
        List<Map<String,Object>> items = new ArrayList<>();
        List<ChangeEvent> events = changeEventService.selectChangeEventList(new ChangeEvent());
        int n = 0;
        for (ChangeEvent e : nvl(events))
        {
            if (n++ >= 20) break;
            items.add(item("变更", e.getSupplierName(), e.getChangeItem(), e.getChangeAt()));
        }
        List<ApiCallLog> logs = apiCallLogService.selectApiCallLogList(new ApiCallLog());
        n = 0;
        for (ApiCallLog l : nvl(logs))
        {
            if (n++ >= 20) break;
            items.add(item("外调", l.getApiName(), l.getSubjectKey(), l.getCallTime()));
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return success(data);
    }

    private Map<String,Object> item(String type, String name, String detail, Date time)
    {
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("type", type);
        m.put("name", name);
        m.put("detail", detail);
        m.put("time", time);
        return m;
    }

    private void inc(Map<String,Integer> map, String key)
    {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

    private String nvl(String v, String def) { return StringUtils.isEmpty(v) ? def : v; }
    private <T> List<T> nvl(List<T> v) { return v == null ? new ArrayList<T>() : v; }
    private BudgetPolicy firstPolicy()
    {
        List<BudgetPolicy> rows = budgetPolicyService.selectBudgetPolicyList(new BudgetPolicy());
        return rows == null || rows.isEmpty() ? null : rows.get(0);
    }
}