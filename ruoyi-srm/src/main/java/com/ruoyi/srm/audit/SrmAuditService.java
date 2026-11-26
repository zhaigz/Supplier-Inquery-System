package com.ruoyi.srm.audit;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.srm.domain.AuditLog;
import com.ruoyi.srm.service.IAuditLogService;
import com.ruoyi.srm.util.SrmUtils;

/**
 * SRM 审计服务：写 srm_audit_log（APPEND-ONLY）。
 * 五列必填 audit_time/module_name/action_name/object_name/detail 在此兜底。规则来源：API接口文档 §2.1。
 */
@Service
public class SrmAuditService
{
    private static final Logger log = LoggerFactory.getLogger(SrmAuditService.class);

    @Autowired
    private IAuditLogService auditLogService;

    /**
     * 写入一条审计。任何字段缺失都用 "-" 占位，保证 DDL 五列 NOT NULL 不被违反。
     */
    public void record(AuditRecord record, String explicitTraceId)
    {
        if (record == null)
        {
            return;
        }
        AuditLog row = new AuditLog();
        row.setAuditTime(new Date());
        row.setModuleName(fill(record.getModuleName()));
        row.setActionName(fill(record.getActionName()));
        row.setObjectName(fill(record.getObjectName()));
        row.setDetail(fill(record.getDetail()));
        row.setScopeType(StringUtils.isEmpty(record.getScopeType()) ? null : record.getScopeType());
        row.setOperatorId(safeUserId());
        row.setOperatorName(SecurityUtils.getUsername());
        try
        {
            row.setDeptName(null);
        }
        catch (Exception ignored)
        {
            // 匿名/任务上下文无登录用户
        }
        row.setSourceIp(IpUtils.getIpAddr());
        row.setTerminalId(null);
        row.setTraceId(StringUtils.isEmpty(explicitTraceId)
                ? (StringUtils.isEmpty(record.getTraceId()) ? newTraceId() : record.getTraceId())
                : explicitTraceId);
        auditLogService.insertAuditLog(row);
    }

    /** 便捷入口：五列必填的最小写法。 */
    public void record(String module, String action, String object, String detail, String scopeType, String traceId)
    {
        record(new AuditRecord(module, action, object, detail).setScopeType(scopeType).setTraceId(traceId), traceId);
    }

    public String newTraceId()
    {
        return SrmUtils.md5("srm-audit-" + System.nanoTime() + "-" + System.identityHashCode(this));
    }

    private static String fill(String value)
    {
        return StringUtils.isEmpty(value) ? "-" : value;
    }

    private Long safeUserId()
    {
        try
        {
            return SecurityUtils.getUserId();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
