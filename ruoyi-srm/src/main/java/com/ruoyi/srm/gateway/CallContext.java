package com.ruoyi.srm.gateway;

import com.ruoyi.srm.constant.SrmConstants;

/**
 * TycGateway 调用上下文。来源：API接口文档 §3.2 建议签名。
 */
public class CallContext
{
    /** 链路号，串联 srm_audit_log 与 srm_api_call_log。 */
    private String traceId;

    /** 异步任务 id，可空。 */
    private Long taskId;

    /** 分桶环境 prod / test。 */
    private String env = SrmConstants.CACHE_ENV_PROD;

    /** 是否手动触发：allow_auto=0 时仅手动动作可放行（§3.2 ③）。 */
    private boolean manual;

    /** 触发规则编码：supplier_save/single_enrich/monitor_scan/project_analysis/repeat_query。 */
    private String scene;

    /** 跳过缓存前置（对应 force=true），仍过预算闸门。 */
    private boolean force;

    public String getTraceId()
    {
        return traceId;
    }

    public void setTraceId(String traceId)
    {
        this.traceId = traceId;
    }

    public Long getTaskId()
    {
        return taskId;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public String getEnv()
    {
        return env;
    }

    public void setEnv(String env)
    {
        this.env = env;
    }

    public boolean isManual()
    {
        return manual;
    }

    public void setManual(boolean manual)
    {
        this.manual = manual;
    }

    public String getScene()
    {
        return scene;
    }

    public void setScene(String scene)
    {
        this.scene = scene;
    }

    public boolean isForce()
    {
        return force;
    }

    public void setForce(boolean force)
    {
        this.force = force;
    }
}
