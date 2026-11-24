package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_backup_record */
public class BackupRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 备份号 */
    private String backupNo;

    /** full_json 完整备份 / csv_suppliers 供应商清单 / csv_projects 项目清单 / csv_reviews 复核清单 / csv_audit 审计日志 */
    private String backupType;

    /** manual 手动 / import 批量导入后 / analysis 项目批量分析后 / rule 重大规则调整后 / schedule 计划任务 */
    private String triggerType;

    /** 覆盖内容：主档、项目、规则、监控、复核、审计（页面19 六项） */
    private String coverScope;

    /** 统计：供应商数 */
    private Integer statSupplier;

    /** 统计：重点监控企业 */
    private Integer statMonitor;

    /** 统计：累计分析版本 */
    private Integer statVersion;

    /** 统计：待复核线索 */
    private Integer statReview;

    /** 备份文件路径（服务端存储，非浏览器） */
    private String storagePath;

    /**  */
    private Long byteSize;

    /** 摘要，恢复前校验 */
    private String sha256;

    /** 本文件是否已被用于恢复 */
    private String restoreFlag;

    /** 恢复时间 */
    private Date restoreAt;

    /** 危险操作二次确认：清除本机存档并恢复初始示例须先下载 JSON 备份 */
    private String dangerConfirm;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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

    public String getStoragePath() { return storagePath; }

    public void setStoragePath(String storagePath) { this.storagePath = storagePath; }

    public Long getByteSize() { return byteSize; }

    public void setByteSize(Long byteSize) { this.byteSize = byteSize; }

    public String getSha256() { return sha256; }

    public void setSha256(String sha256) { this.sha256 = sha256; }

    public String getRestoreFlag() { return restoreFlag; }

    public void setRestoreFlag(String restoreFlag) { this.restoreFlag = restoreFlag; }

    public Date getRestoreAt() { return restoreAt; }

    public void setRestoreAt(Date restoreAt) { this.restoreAt = restoreAt; }

    public String getDangerConfirm() { return dangerConfirm; }

    public void setDangerConfirm(String dangerConfirm) { this.dangerConfirm = dangerConfirm; }

}
