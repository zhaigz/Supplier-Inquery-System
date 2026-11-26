package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 页面8 变更清单导出行（13 列）。 */
public class ChangeEventExportVo
{
    @Excel(name = "供应商编码")
    private String supplierCode;
    @Excel(name = "供应商名称")
    private String supplierName;
    @Excel(name = "统一社会信用代码")
    private String creditCode;
    @Excel(name = "法定代表人")
    private String legalPerson;
    @Excel(name = "所属行业")
    private String industry;
    @Excel(name = "监控状态")
    private String monitorStatus;
    @Excel(name = "变更时间")
    private String changeAt;
    @Excel(name = "变更事项")
    private String changeItem;
    @Excel(name = "变更维度")
    private String dimension;
    @Excel(name = "风险等级")
    private String levelTag;
    @Excel(name = "变更前")
    private String beforeValue;
    @Excel(name = "变更后")
    private String afterValue;
    @Excel(name = "处置状态")
    private String handleStatus;

    public String getSupplierCode() { return supplierCode; }
    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getCreditCode() { return creditCode; }
    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }
    public String getLegalPerson() { return legalPerson; }
    public void setLegalPerson(String legalPerson) { this.legalPerson = legalPerson; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public String getMonitorStatus() { return monitorStatus; }
    public void setMonitorStatus(String monitorStatus) { this.monitorStatus = monitorStatus; }
    public String getChangeAt() { return changeAt; }
    public void setChangeAt(String changeAt) { this.changeAt = changeAt; }
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
}