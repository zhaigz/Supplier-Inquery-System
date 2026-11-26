package com.ruoyi.srm.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.ApiDict;
import com.ruoyi.srm.domain.BudgetPolicy;
import com.ruoyi.srm.domain.TriggerRule;
import com.ruoyi.srm.domain.UsageStat;
import com.ruoyi.srm.domain.dto.BudgetExportVo;
import com.ruoyi.srm.service.IApiDictService;
import com.ruoyi.srm.service.IBudgetPolicyService;
import com.ruoyi.srm.service.ITriggerRuleService;
import com.ruoyi.srm.service.IUsageStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面16「预算与触发规则」接口，共 5 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/budget-policy")
public class SrmBudgetPolicyController extends BaseController
{
    @Autowired private IBudgetPolicyService budgetPolicyService;
    @Autowired private ITriggerRuleService triggerRuleService;
    @Autowired private IApiDictService apiDictService;
    @Autowired private IUsageStatService usageStatService;

    /**
     * 预算参数
     *
     * <p>页面 16「预算与触发规则」；权限 srm:budgetPolicy:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:budgetPolicy:list')")
    @GetMapping("/list")
    public TableDataInfo list(BudgetPolicy query)
    {
        startPage(); startOrderBy();
        return getDataTable(budgetPolicyService.selectBudgetPolicyList(query == null ? new BudgetPolicy() : query));
    }

    /**
     * 保存预算（@RepeatSubmit）
     *
     * <p>页面 16「预算与触发规则」；权限 srm:budgetPolicy:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:budgetPolicy:edit')")
    @Log(title="预算策略", businessType=BusinessType.UPDATE)
    @RepeatSubmit
    @SrmAudit(module="预算策略", action="保存", object="{p:id}", detail="保存预算参数")
    @PutMapping
    public AjaxResult edit(@RequestBody BudgetPolicy policy)
    {
        if (policy == null) throw new ServiceException("请求体不能为空", SrmErrorCode.CODE_60008);
        if (policy.getDailyLimit() != null && policy.getDailyLimit() < 0) throw new ServiceException("dailyLimit不能为负", SrmErrorCode.CODE_60008);
        if (policy.getMonthlyLimit() != null && policy.getMonthlyLimit() < 0) throw new ServiceException("monthlyLimit不能为负", SrmErrorCode.CODE_60008);
        if (policy.getDailyLimit() != null && policy.getMonthlyLimit() != null && policy.getDailyLimit() > policy.getMonthlyLimit())
            throw new ServiceException("dailyLimit不能大于monthlyLimit", SrmErrorCode.CODE_60008);
        if (policy.getId() == null)
        {
            if (StringUtils.isEmpty(policy.getPolicyName())) policy.setPolicyName("默认预算策略");
            policy.setCreateBy(getUsername());
            policy.setCreateTime(new Date());
            budgetPolicyService.insertBudgetPolicy(policy);
        }
        else
        {
            requirePolicy(policy.getId());
            policy.setUpdateBy(getUsername());
            policy.setUpdateTime(new Date());
            budgetPolicyService.updateBudgetPolicy(policy);
        }
        return success(budgetPolicyService.selectBudgetPolicyById(policy.getId()));
    }

    /**
     * 已用量/剩余额度
     *
     * <p>页面 16「预算与触发规则」；权限 srm:budgetPolicy:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:budgetPolicy:list')")
    @GetMapping("/usage")
    public AjaxResult usage(BudgetPolicy query)
    {
        BudgetPolicy policy = firstPolicy();
        UsageStat uq = new UsageStat();
        List<UsageStat> stats = usageStatService.selectUsageStatList(uq);
        BigDecimal consumed = BigDecimal.ZERO;
        BigDecimal cost = BigDecimal.ZERO;
        int cacheHit = 0, blocked = 0, failed = 0;
        for (UsageStat s : stats == null ? new ArrayList<UsageStat>() : stats)
        {
            if (s.getUnitConsumed() != null) consumed = consumed.add(s.getUnitConsumed());
            if (s.getCostAmount() != null) cost = cost.add(s.getCostAmount());
            if (s.getCacheHitCount() != null) cacheHit += s.getCacheHitCount();
            if (s.getBlockCount() != null) blocked += s.getBlockCount();
            if (s.getFailCount() != null) failed += s.getFailCount();
        }
        BigDecimal daily = policy == null || policy.getDailyLimit() == null ? null : new BigDecimal(policy.getDailyLimit());
        BigDecimal monthly = policy == null || policy.getMonthlyLimit() == null ? null : new BigDecimal(policy.getMonthlyLimit());
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("dailyLimit", daily);
        data.put("monthlyLimit", monthly);
        data.put("consumed", consumed);
        data.put("remainingDaily", daily == null ? null : daily.subtract(consumed));
        data.put("remainingMonthly", monthly == null ? null : monthly.subtract(consumed));
        data.put("cacheHitCount", cacheHit);
        data.put("blockCount", blocked);
        data.put("failCount", failed);
        data.put("costAmount", cost);
        data.put("warnRatios", policy == null ? null : policy.getWarnRatios());
        return success(data);
    }

    /**
     * 缓存有效期覆盖
     *
     * <p>页面 16「预算与触发规则」；权限 srm:budgetPolicy:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:budgetPolicy:edit')")
    @SrmAudit(module="预算策略", action="缓存", object="{p:id}/{p:cacheTtlSec}", detail="缓存有效期覆盖")
    @PutMapping("/cache-ttl")
    public AjaxResult cacheTtl(@RequestBody Map<String,Object> body)
    {
        Long id = longOf(body == null ? null : body.get("id"));
        Integer ttl = intOf(body == null ? null : body.get("cacheTtlSec"));
        if (ttl == null || ttl < 0) throw new ServiceException("cacheTtlSec不合法", SrmErrorCode.CODE_60008);
        BudgetPolicy target;
        if (id == null)
        {
            target = firstPolicy();
            if (target == null)
            {
                target = new BudgetPolicy();
                target.setPolicyName("默认预算策略");
                target.setCacheTtlSec(ttl);
                target.setCreateBy(getUsername());
                target.setCreateTime(new Date());
                budgetPolicyService.insertBudgetPolicy(target);
                return success(budgetPolicyService.selectBudgetPolicyById(target.getId()));
            }
            id = target.getId();
        }
        else
        {
            requirePolicy(id);
        }
        BudgetPolicy patch = new BudgetPolicy();
        patch.setId(id);
        patch.setCacheTtlSec(ttl);
        patch.setUpdateBy(getUsername());
        patch.setUpdateTime(new Date());
        budgetPolicyService.updateBudgetPolicy(patch);
        return success(budgetPolicyService.selectBudgetPolicyById(id));
    }

    /**
     * 导出配置
     *
     * <p>页面 16「预算与触发规则」；权限 srm:budgetPolicy:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:budgetPolicy:export')")
    @Log(title="预算策略", businessType=BusinessType.EXPORT)
    @SrmAudit(module="预算策略", action="导出", object="配置", detail="导出预算/触发/字典配置")
    @PostMapping("/export")
    public void export(HttpServletResponse response)
    {
        List<BudgetExportVo> rows = new ArrayList<>();
        for (BudgetPolicy p : nvl(budgetPolicyService.selectBudgetPolicyList(new BudgetPolicy())))
        {
            rows.add(new BudgetExportVo("预算策略", p.getPolicyName(), p.getPolicyName(),
                    "日限额=" + value(p.getDailyLimit()) + "；月限额=" + value(p.getMonthlyLimit()) + "；预警比例=" + value(p.getWarnRatios())
                            + "；最大并发=" + value(p.getMaxConcurrency()) + "；缓存TTL=" + value(p.getCacheTtlSec()), p.getStatus()));
        }
        for (TriggerRule r : nvl(triggerRuleService.selectTriggerRuleList(new TriggerRule())))
        {
            rows.add(new BudgetExportVo("触发规则", r.getRuleCode(), r.getRuleName(),
                    "触发方式=" + value(r.getTriggerMode()) + "；接口=" + value(r.getApiIds()) + "；允许自动=" + value(r.getAllowAuto()), r.getEnabled()));
        }
        for (ApiDict d : nvl(apiDictService.selectApiDictList(new ApiDict())))
        {
            rows.add(new BudgetExportVo("接口字典", String.valueOf(d.getApiId()), d.getApiName(),
                    "单价=" + value(d.getUnitPrice()) + "；计费=" + value(d.getBillingMode()) + "；缓存TTL=" + value(d.getCacheTtlSec()), d.getStatus()));
        }
        new ExcelUtil<BudgetExportVo>(BudgetExportVo.class).exportExcel(response, rows, "预算与触发配置");
    }

    private BudgetPolicy firstPolicy()
    {
        List<BudgetPolicy> rows = budgetPolicyService.selectBudgetPolicyList(new BudgetPolicy());
        return rows == null || rows.isEmpty() ? null : rows.get(0);
    }

    private BudgetPolicy requirePolicy(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        BudgetPolicy policy = budgetPolicyService.selectBudgetPolicyById(id);
        if (policy == null) throw new ServiceException("预算策略不存在", SrmErrorCode.CODE_60001);
        return policy;
    }

    private String value(Object v) { return v == null ? "-" : String.valueOf(v); }
    private <T> List<T> nvl(List<T> v) { return v == null ? new ArrayList<T>() : v; }
    private Long longOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).longValue(); return Long.valueOf(String.valueOf(v)); }
    private Integer intOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).intValue(); return Integer.valueOf(String.valueOf(v)); }
}