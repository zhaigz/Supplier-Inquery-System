package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 导入行级校验结果导出对象（页面14 导出错误行）。 */
public class ImportRowExportVo
{
    @Excel(name = "行号")
    private Integer rowNo;

    @Excel(name = "供应商名称")
    private String supplierName;

    @Excel(name = "统一社会信用代码")
    private String creditCode;

    @Excel(name = "法定代表人")
    private String legalPerson;

    @Excel(name = "是否监控")
    private String monitorFlag;

    @Excel(name = "校验结果")
    private String checkResult;

    @Excel(name = "错误原因")
    private String errMsg;

    public Integer getRowNo() { return rowNo; }
    public void setRowNo(Integer rowNo) { this.rowNo = rowNo; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getCreditCode() { return creditCode; }
    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }
    public String getLegalPerson() { return legalPerson; }
    public void setLegalPerson(String legalPerson) { this.legalPerson = legalPerson; }
    public String getMonitorFlag() { return monitorFlag; }
    public void setMonitorFlag(String monitorFlag) { this.monitorFlag = monitorFlag; }
    public String getCheckResult() { return checkResult; }
    public void setCheckResult(String checkResult) { this.checkResult = checkResult; }
    public String getErrMsg() { return errMsg; }
    public void setErrMsg(String errMsg) { this.errMsg = errMsg; }
}