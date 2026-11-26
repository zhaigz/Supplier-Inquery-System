package com.ruoyi.srm.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.ApiCallLog;
import com.ruoyi.srm.domain.ApiRaw;
import com.ruoyi.srm.domain.BudgetPolicy;
import com.ruoyi.srm.domain.UsageStat;
import com.ruoyi.srm.service.IApiCallLogService;
import com.ruoyi.srm.service.IApiRawService;
import com.ruoyi.srm.service.IBudgetPolicyService;
import com.ruoyi.srm.service.IUsageStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面9「接口调用信息」接口，共 5 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/api-log")
public class SrmApiCallLogController extends BaseController
{
    @Autowired private IApiCallLogService apiCallLogService;
    @Autowired private IApiRawService apiRawService;
    @Autowired private IUsageStatService usageStatService;
    @Autowired private IBudgetPolicyService budgetPolicyService;

    /**
     * 调用明细
     *
     * <p>页面 9「接口调用信息」；权限 srm:apiLog:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:apiLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApiCallLog query)
    {
        startPage(); startOrderBy();
        return getDataTable(apiCallLogService.selectApiCallLogList(query == null ? new ApiCallLog() : query));
    }

    /**
     * 单次调用详情
     *
     * <p>页面 9「接口调用信息」；权限 srm:apiLog:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:apiLog:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        ApiCallLog row = requireLog(id);
        ApiRaw rq = new ApiRaw();
        rq.setCallLogId(id);
        List<ApiRaw> raws = apiRawService.selectApiRawList(rq);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("callLog", row);
        if (row.getApiId() != null && com.ruoyi.srm.constant.SrmApiId.inScope(row.getApiId()))
        {
            // 外调接口信息：编号 / 常量名 / 天眼查接口名 / 分类 / 在线文档，来源 SrmApiId（同源天眼查API接口.xlsx）
            Map<String,Object> tyc = new LinkedHashMap<>();
            tyc.put("apiId", row.getApiId());
            tyc.put("constant", com.ruoyi.srm.constant.SrmApiId.symbolOf(row.getApiId()));
            tyc.put("apiName", com.ruoyi.srm.constant.SrmApiId.name(row.getApiId()));
            tyc.put("category", com.ruoyi.srm.constant.SrmApiId.category(row.getApiId()));
            tyc.put("docUrl", com.ruoyi.srm.constant.SrmApiId.docUrl(row.getApiId()));
            data.put("tycApi", tyc);
        }
        data.put("raw", raws == null || raws.isEmpty() ? null : raws.get(0));
        return success(data);
    }

    /**
     * 原始响应（仅管理员）
     *
     * <p>页面 9「接口调用信息」；权限 srm:apiLog:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:apiLog:query')")
    @SrmAudit(module="接口调用", action="查询", object="{p:id}", detail="读取原始响应")
    @GetMapping("/raw/{id}")
    public AjaxResult raw(@PathVariable Long id)
    {
        if (!SecurityUtils.isAdmin()) throw new ServiceException("仅管理员可查看原始响应", SrmErrorCode.CODE_60011);
        ApiCallLog log = requireLog(id);
        ApiRaw q = new ApiRaw();
        q.setCallLogId(id);
        List<ApiRaw> rows = apiRawService.selectApiRawList(q);
        ApiRaw raw = rows == null || rows.isEmpty() ? null : rows.get(0);
        if (raw == null) throw new ServiceException("原始响应不存在", SrmErrorCode.CODE_60001);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("callLogId", log.getId());
        data.put("apiId", raw.getApiId());
        data.put("requestJson", raw.getRequestJson());
        data.put("responseJson", raw.getResponseJson());
        data.put("httpStatus", raw.getHttpStatus());
        data.put("fetchedAt", raw.getFetchedAt());
        return success(data);
    }

    /**
     * 区间汇总+预算对照
     *
     * <p>页面 9「接口调用信息」；权限 srm:apiLog:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:apiLog:list')")
    @GetMapping("/stat")
    public AjaxResult stat(ApiCallLog query)
    {
        List<ApiCallLog> logs = apiCallLogService.selectApiCallLogList(query == null ? new ApiCallLog() : query);
        BigDecimal unit = BigDecimal.ZERO;
        int cacheHit = 0;
        int blocked = 0;
        for (ApiCallLog row : logs == null ? new ArrayList<ApiCallLog>() : logs)
        {
            if (row.getUnitConsumed() != null) unit = unit.add(row.getUnitConsumed());
            if ("1".equals(row.getCacheHit())) cacheHit++;
            if (StringUtils.isNotEmpty(row.getBlockReason())) blocked++;
        }
        BudgetPolicy policy = firstBudget();
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("callCount", logs == null ? 0 : logs.size());
        data.put("cacheHitCount", cacheHit);
        data.put("blockCount", blocked);
        data.put("unitConsumed", unit);
        data.put("dailyLimit", policy == null ? null : policy.getDailyLimit());
        data.put("monthlyLimit", policy == null ? null : policy.getMonthlyLimit());
        data.put("remaining", policy == null || policy.getDailyLimit() == null ? null : new BigDecimal(policy.getDailyLimit()).subtract(unit));
        return success(data);
    }

    /**
     * 导出对账表
     *
     * <p>页面 9「接口调用信息」；权限 srm:apiLog:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:apiLog:export')")
    @Log(title="接口调用", businessType=BusinessType.EXPORT)
    @SrmAudit(module="接口调用", action="导出", object="调用明细", detail="导出对账表")
    @PostMapping("/export")
    public void export(@RequestBody(required=false) ApiCallLog query, HttpServletResponse response)
    {
        List<ApiCallLog> rows = apiCallLogService.selectApiCallLogList(query == null ? new ApiCallLog() : query);
        new ExcelUtil<ApiCallLog>(ApiCallLog.class).exportExcel(response, rows == null ? new ArrayList<>() : rows, "接口调用明细");
    }

    private BudgetPolicy firstBudget()
    {
        List<BudgetPolicy> rows = budgetPolicyService.selectBudgetPolicyList(new BudgetPolicy());
        return rows == null || rows.isEmpty() ? null : rows.get(0);
    }

    private ApiCallLog requireLog(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        ApiCallLog row = apiCallLogService.selectApiCallLogById(id);
        if (row == null) throw new ServiceException("调用记录不存在", SrmErrorCode.CODE_60001);
        return row;
    }
}