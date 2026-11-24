package com.ruoyi.srm.enums;

/**
 * 外部接口调用结果。来源：srm_api_call_log.call_result DDL 注释。
 */
public enum CallResult
{
    SUCCESS("成功"),
    FAIL("失败"),
    BLOCKED("被拦截");

    private final String value;

    CallResult(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
