package com.ruoyi.srm.enums;

/**
 * 接口结果分桶环境。来源：srm_api_cache_key.env 与 srm_api_call_log.env DDL 注释。
 */
public enum CacheEnv
{
    PROD("prod"),
    TEST("test");

    private final String value;

    CacheEnv(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
