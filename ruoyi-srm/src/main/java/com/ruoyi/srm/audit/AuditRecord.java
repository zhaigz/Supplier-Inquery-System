package com.ruoyi.srm.audit;

/**
 * 审计写入参数载体，由切面组装后交给 AuditLogService。
 */
public class AuditRecord
{
    private String moduleName;
    private String actionName;
    private String objectName;
    private String detail;
    private String scopeType;
    private String traceId;

    public AuditRecord(String moduleName, String actionName, String objectName, String detail)
    {
        this.moduleName = moduleName;
        this.actionName = actionName;
        this.objectName = objectName;
        this.detail = detail;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public String getActionName()
    {
        return actionName;
    }

    public String getObjectName()
    {
        return objectName;
    }

    public String getDetail()
    {
        return detail;
    }

    public String getScopeType()
    {
        return scopeType;
    }

    public AuditRecord setScopeType(String scopeType)
    {
        this.scopeType = scopeType;
        return this;
    }

    public String getTraceId()
    {
        return traceId;
    }

    public AuditRecord setTraceId(String traceId)
    {
        this.traceId = traceId;
        return this;
    }

    public AuditRecord setObjectName(String objectName)
    {
        this.objectName = objectName;
        return this;
    }

    public AuditRecord setDetail(String detail)
    {
        this.detail = detail;
        return this;
    }
}
