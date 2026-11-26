package com.ruoyi.srm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.AuditLog;
import com.ruoyi.srm.domain.dto.AuditLogExportVo;
import com.ruoyi.srm.service.IAuditLogService;
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
 * 页面17「操作审计」接口，共 4 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/audit-log")
public class SrmAuditLogController extends BaseController
{
    @Autowired private IAuditLogService auditLogService;

    /**
     * 审计清单
     *
     * <p>页面 17「操作审计」；权限 srm:auditLog:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:auditLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(AuditLog query)
    {
        startPage(); startOrderBy();
        return getDataTable(auditLogService.selectAuditLogList(query == null ? new AuditLog() : query));
    }

    /**
     * 详情（五列+前后 JSON）
     *
     * <p>页面 17「操作审计」；权限 srm:auditLog:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:auditLog:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(requireLog(id));
    }

    /**
     * 按链路聚合
     *
     * <p>页面 17「操作审计」；权限 srm:auditLog:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:auditLog:query')")
    @GetMapping("/trace/{traceId}")
    public AjaxResult trace(@PathVariable String traceId)
    {
        AuditLog q = new AuditLog();
        q.setTraceId(traceId);
        List<AuditLog> rows = auditLogService.selectAuditLogList(q);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("traceId", traceId);
        data.put("total", rows == null ? 0 : rows.size());
        data.put("nodes", rows == null ? new ArrayList<AuditLog>() : rows);
        return success(data);
    }

    /**
     * 导出
     *
     * <p>页面 17「操作审计」；权限 srm:auditLog:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:auditLog:export')")
    @Log(title="操作审计", businessType=BusinessType.EXPORT)
    @SrmAudit(module="操作审计", action="导出", object="审计清单", detail="导出审计清单")
    @PostMapping("/export")
    public void export(@RequestBody(required=false) AuditLog query, HttpServletResponse response)
    {
        List<AuditLog> rows = auditLogService.selectAuditLogList(query == null ? new AuditLog() : query);
        List<AuditLogExportVo> out = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (AuditLog row : rows == null ? new ArrayList<AuditLog>() : rows)
        {
            AuditLogExportVo v = new AuditLogExportVo();
            v.setAuditTime(row.getAuditTime() == null ? null : df.format(row.getAuditTime()));
            v.setModuleName(row.getModuleName());
            v.setActionName(row.getActionName());
            v.setObjectName(row.getObjectName());
            v.setDetail(row.getDetail());
            v.setScopeType(row.getScopeType());
            v.setOperatorName(row.getOperatorName());
            v.setDeptName(row.getDeptName());
            v.setSourceIp(row.getSourceIp());
            v.setTerminalId(row.getTerminalId());
            v.setTraceId(row.getTraceId());
            out.add(v);
        }
        new ExcelUtil<AuditLogExportVo>(AuditLogExportVo.class).exportExcel(response, out, "操作审计");
    }

    private AuditLog requireLog(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        AuditLog row = auditLogService.selectAuditLogById(id);
        if (row == null) throw new ServiceException("审计记录不存在", SrmErrorCode.CODE_60001);
        return row;
    }
}