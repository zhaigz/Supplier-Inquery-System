package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_audit_log */
public class AuditLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 时间（必填） */
    private Date auditTime;

    /** 模块（必填）：供应商主档/项目管理/项目分析/接口预算/关联规则/字段映射/复核处置/备份恢复… */
    private String moduleName;

    /** 动作（必填）：新增 / 修改 / 导入 / 导出 / 启停 / 移出 */
    private String actionName;

    /** 对象（必填）：供应商编码、项目单据号、规则名、报告版本… */
    private String objectName;

    /** 详情（必填）：变更前后值或动作说明 */
    private String detail;

    /** 记录范围：主档增改/监控变更/项目名单与参数/规则变更/报告导出/字段映射变更/备份与恢复/预算拦截 */
    private String scopeType;

    /** 操作人 */
    private Long operatorId;

    /**  */
    private String operatorName;

    /**  */
    private String deptName;

    /**  */
    private String sourceIp;

    /**  */
    private String terminalId;

    /** 与接口调用日志串联（预算拦截场景） */
    private String traceId;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Date getAuditTime() { return auditTime; }

    public void setAuditTime(Date auditTime) { this.auditTime = auditTime; }

    public String getModuleName() { return moduleName; }

    public void setModuleName(String moduleName) { this.moduleName = moduleName; }

    public String getActionName() { return actionName; }

    public void setActionName(String actionName) { this.actionName = actionName; }

    public String getObjectName() { return objectName; }

    public void setObjectName(String objectName) { this.objectName = objectName; }

    public String getDetail() { return detail; }

    public void setDetail(String detail) { this.detail = detail; }

    public String getScopeType() { return scopeType; }

    public void setScopeType(String scopeType) { this.scopeType = scopeType; }

    public Long getOperatorId() { return operatorId; }

    public void setOperatorId(Long operatorId) { this.operatorId = operatorId; }

    public String getOperatorName() { return operatorName; }

    public void setOperatorName(String operatorName) { this.operatorName = operatorName; }

    public String getDeptName() { return deptName; }

    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getSourceIp() { return sourceIp; }

    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }

    public String getTerminalId() { return terminalId; }

    public void setTerminalId(String terminalId) { this.terminalId = terminalId; }

    public String getTraceId() { return traceId; }

    public void setTraceId(String traceId) { this.traceId = traceId; }

}
