package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_change_event */
public class ChangeEvent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 供应商（页面8 表 8-2 列） */
    private String supplierName;

    /** 统一社会信用代码 */
    private String creditCode;

    /** 法定代表人 */
    private String legalPerson;

    /** 最近工商更新 */
    private Date lastIndustryUpdate;

    /** 监控状态：重点监控 / 普通供应商 */
    private String monitorStatus;

    /** 变更时间 */
    private Date changeAt;

    /** 变更事项 */
    private String changeItem;

    /** 所属监控维度（srm_monitor_policy.policy_code） */
    private String dimension;

    /** 等级（本地配置规则标记，非接口字段） */
    private String levelTag;

    /** 变更前 */
    private String beforeValue;

    /** 变更后 */
    private String afterValue;

    /** 处理状态：待处理 / 已确认（页面8 标记已处理） */
    private String handleStatus;

    /** 高风险事件：按配置规则标记，页面8 指标卡计数 */
    private String highRiskFlag;

    /** 比对基准快照 */
    private Long prevSnapshotId;

    /** 本次快照 */
    private Long currSnapshotId;

    /**  */
    private String handleBy;

    /**  */
    private Date handleAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }

    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getCreditCode() { return creditCode; }

    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }

    public String getLegalPerson() { return legalPerson; }

    public void setLegalPerson(String legalPerson) { this.legalPerson = legalPerson; }

    public Date getLastIndustryUpdate() { return lastIndustryUpdate; }

    public void setLastIndustryUpdate(Date lastIndustryUpdate) { this.lastIndustryUpdate = lastIndustryUpdate; }

    public String getMonitorStatus() { return monitorStatus; }

    public void setMonitorStatus(String monitorStatus) { this.monitorStatus = monitorStatus; }

    public Date getChangeAt() { return changeAt; }

    public void setChangeAt(Date changeAt) { this.changeAt = changeAt; }

    public String getChangeItem() { return changeItem; }

    public void setChangeItem(String changeItem) { this.changeItem = changeItem; }

    public String getDimension() { return dimension; }

    public void setDimension(String dimension) { this.dimension = dimension; }

    public String getLevelTag() { return levelTag; }

    public void setLevelTag(String levelTag) { this.levelTag = levelTag; }

    public String getBeforeValue() { return beforeValue; }

    public void setBeforeValue(String beforeValue) { this.beforeValue = beforeValue; }

    public String getAfterValue() { return afterValue; }

    public void setAfterValue(String afterValue) { this.afterValue = afterValue; }

    public String getHandleStatus() { return handleStatus; }

    public void setHandleStatus(String handleStatus) { this.handleStatus = handleStatus; }

    public String getHighRiskFlag() { return highRiskFlag; }

    public void setHighRiskFlag(String highRiskFlag) { this.highRiskFlag = highRiskFlag; }

    public Long getPrevSnapshotId() { return prevSnapshotId; }

    public void setPrevSnapshotId(Long prevSnapshotId) { this.prevSnapshotId = prevSnapshotId; }

    public Long getCurrSnapshotId() { return currSnapshotId; }

    public void setCurrSnapshotId(Long currSnapshotId) { this.currSnapshotId = currSnapshotId; }

    public String getHandleBy() { return handleBy; }

    public void setHandleBy(String handleBy) { this.handleBy = handleBy; }

    public Date getHandleAt() { return handleAt; }

    public void setHandleAt(Date handleAt) { this.handleAt = handleAt; }

}
