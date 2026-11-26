package com.ruoyi.srm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据中心本地存储配置（页面19）。备份与导出文件目录、下载大小上限均走配置项。
 */
@Component
@ConfigurationProperties(prefix = "srm.data-center")
public class SrmDataCenterProperties
{
    /** 备份 JSON 与 ERP/分项导出文件落盘目录 */
    private String dir = "./srm-backup";

    public String getDir() { return dir; }

    public void setDir(String dir) { this.dir = dir; }
}