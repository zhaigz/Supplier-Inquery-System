package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 审计日志导出对象（页面17 导出）。 */
public class AuditLogExportVo
{
    @Excel(name = "审计时间")
    private String auditTime;

    @Excel(name = "模块")
    private String moduleName;

    @Excel(name = "动作")
    private String actionName;

    @Excel(name = "对象")
    private String objectName;

    @Excel(name = "详情")
    private String detail;

    @Excel(name = "范围")
    private String scopeType;

    @Excel(name = "操作人")
    private String operatorName;

    @Excel(name = "部门")
    private String deptName;

    @Excel(name = "来源IP")
    private String sourceIp;

    @Excel(name = "终端")
    private String terminalId;

    @Excel(name = "链路ID")
    private String traceId;

    public String getAuditTime() { return auditTime; }
    public void setAuditTime(String auditTime) { this.auditTime = auditTime; }
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