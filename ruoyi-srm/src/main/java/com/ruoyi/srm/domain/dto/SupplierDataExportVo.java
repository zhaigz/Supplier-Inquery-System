package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 供应商主数据导出对象（页面19 分项导出/ERP 入围）。 */
public class SupplierDataExportVo
{
    @Excel(name = "供应商编码")
    private String supplierCode;

    @Excel(name = "供应商名称")
    private String supplierName;

    @Excel(name = "统一社会信用代码")
    private String creditCode;

    @Excel(name = "法定代表人")
    private String legalPerson;

    @Excel(name = "注册资本")
    private String regCapital;

    @Excel(name = "成立日期")
    private String establishDate;

    @Excel(name = "经营状态")
    private String bizStatus;

    @Excel(name = "所属行业")
    private String industry;

    @Excel(name = "注册地址")
    private String regAddress;

    @Excel(name = "是否监控")
    private String monitorFlag;

    @Excel(name = "数据来源")
    private String dataSource;

    public String getSupplierCode() { return supplierCode; }
    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getCreditCode() { return creditCode; }
    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }
    public String getLegalPerson() { return legalPerson; }
    public void setLegalPerson(String legalPerson) { this.legalPerson = legalPerson; }
    public String getRegCapital() { return regCapital; }
    public void setRegCapital(String regCapital) { this.regCapital = regCapital; }
    public String getEstablishDate() { return establishDate; }
    public void setEstablishDate(String establishDate) { this.establishDate = establishDate; }
    public String getBizStatus() { return bizStatus; }
    public void setBizStatus(String bizStatus) { this.bizStatus = bizStatus; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public String getRegAddress() { return regAddress; }
    public void setRegAddress(String regAddress) { this.regAddress = regAddress; }
    public String getMonitorFlag() { return monitorFlag; }
    public void setMonitorFlag(String monitorFlag) { this.monitorFlag = monitorFlag; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
}