package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 备份记录导出对象（页面19）。 */
public class BackupRecordExportVo
{
    @Excel(name = "备份编号")
    private String backupNo;

    @Excel(name = "备份类型")
    private String backupType;

    @Excel(name = "触发方式")
    private String triggerType;

    @Excel(name = "覆盖范围")
    private String coverScope;

    @Excel(name = "供应商数")
    private Integer statSupplier;

    @Excel(name = "监控快照数")
    private Integer statMonitor;

    @Excel(name = "版本数")
    private Integer statVersion;

    @Excel(name = "复核数")
    private Integer statReview;

    @Excel(name = "字节数")
    private Long byteSize;

    @Excel(name = "SHA256")
    private String sha256;

    public String getBackupNo() { return backupNo; }
    public void setBackupNo(String backupNo) { this.backupNo = backupNo; }
    public String getBackupType() { return backupType; }
    public void setBackupType(String backupType) { this.backupType = backupType; }
    public String getTriggerType() { return triggerType; }
    public void setTriggerType(String triggerType) { this.triggerType = triggerType; }
    public String getCoverScope() { return coverScope; }
    public void setCoverScope(String coverScope) { this.coverScope = coverScope; }
    public Integer getStatSupplier() { return statSupplier; }
    public void setStatSupplier(Integer statSupplier) { this.statSupplier = statSupplier; }
    public Integer getStatMonitor() { return statMonitor; }
    public void setStatMonitor(Integer statMonitor) { this.statMonitor = statMonitor; }
    public Integer getStatVersion() { return statVersion; }
    public void setStatVersion(Integer statVersion) { this.statVersion = statVersion; }
    public Integer getStatReview() { return statReview; }
    public void setStatReview(Integer statReview) { this.statReview = statReview; }
    public Long getByteSize() { return byteSize; }
    public void setByteSize(Long byteSize) { this.byteSize = byteSize; }
    public String getSha256() { return sha256; }
    public void setSha256(String sha256) { this.sha256 = sha256; }
}