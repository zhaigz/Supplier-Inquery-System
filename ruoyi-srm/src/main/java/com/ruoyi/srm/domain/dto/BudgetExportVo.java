package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 预算/触发/接口字典配置导出对象（页面16 导出配置）。 */
public class BudgetExportVo
{
    @Excel(name = "配置类型")
    private String configType;

    @Excel(name = "配置标识")
    private String configKey;

    @Excel(name = "配置名称")
    private String configName;

    @Excel(name = "配置内容")
    private String configValue;

    @Excel(name = "状态")
    private String status;

    public BudgetExportVo() { }

    public BudgetExportVo(String configType, String configKey, String configName, String configValue, String status)
    {
        this.configType = configType;
        this.configKey = configKey;
        this.configName = configName;
        this.configValue = configValue;
        this.status = status;
    }

    public String getConfigType() { return configType; }
    public void setConfigType(String configType) { this.configType = configType; }
    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }
    public String getConfigName() { return configName; }
    public void setConfigName(String configName) { this.configName = configName; }
    public String getConfigValue() { return configValue; }
    public void setConfigValue(String configValue) { this.configValue = configValue; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}