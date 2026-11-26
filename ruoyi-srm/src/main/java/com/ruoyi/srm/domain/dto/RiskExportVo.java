package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 页面4 风险清单导出行。 */
public class RiskExportVo
{
    @Excel(name = "供应商ID")
    private Long supplierId;

    @Excel(name = "风险类别")
    private String riskCategory;

    @Excel(name = "案号/标识")
    private String bizNo;

    @Excel(name = "法院/机关")
    private String org;

    @Excel(name = "当事人")
    private String partyName;

    @Excel(name = "金额")
    private String amount;

    @Excel(name = "日期")
    private String happenedAt;

    @Excel(name = "状态/原因")
    private String detail;

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public String getRiskCategory() { return riskCategory; }
    public void setRiskCategory(String riskCategory) { this.riskCategory = riskCategory; }
    public String getBizNo() { return bizNo; }
    public void setBizNo(String bizNo) { this.bizNo = bizNo; }
    public String getOrg() { return org; }
    public void setOrg(String org) { this.org = org; }
    public String getPartyName() { return partyName; }
    public void setPartyName(String partyName) { this.partyName = partyName; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
    public String getHappenedAt() { return happenedAt; }
    public void setHappenedAt(String happenedAt) { this.happenedAt = happenedAt; }
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
}