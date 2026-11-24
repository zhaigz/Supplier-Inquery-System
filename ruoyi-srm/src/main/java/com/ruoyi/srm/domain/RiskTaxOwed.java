package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_risk_tax_owed */
public class RiskTaxOwed extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 纳税人识别号 */
    private String taxpayerNo;

    /** 纳税人名称 */
    private String taxpayerName;

    /** 证件号码（敏感加密） */
    private String idNo;

    /** 纳税人类型 */
    private String taxpayerType;

    /** 注册类型 */
    private String regType;

    /** 欠税税种 */
    private String taxType;

    /** 税务类型 */
    private String taxCategory;

    /** 欠税金额 */
    private String owedAmount;

    /** 欠税余额 */
    private String owedBalance;

    /** 当前新发生欠税余额 */
    private String newOwedBalance;

    /** 发布时间 */
    private Date publishTime;

    /** 经营地点 */
    private String bizAddress;

    /** 税务机关 */
    private String taxAuthority;

    /** 法人或负责人名称 */
    private String legalName;

    /** 法人证件名称 */
    private String legalIdType;

    /** 接口返回总数 */
    private Integer totalCount;

    /**  */
    private Long rawId;

    /**  */
    private Date fetchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getTaxpayerNo() { return taxpayerNo; }

    public void setTaxpayerNo(String taxpayerNo) { this.taxpayerNo = taxpayerNo; }

    public String getTaxpayerName() { return taxpayerName; }

    public void setTaxpayerName(String taxpayerName) { this.taxpayerName = taxpayerName; }

    public String getIdNo() { return idNo; }

    public void setIdNo(String idNo) { this.idNo = idNo; }

    public String getTaxpayerType() { return taxpayerType; }

    public void setTaxpayerType(String taxpayerType) { this.taxpayerType = taxpayerType; }

    public String getRegType() { return regType; }

    public void setRegType(String regType) { this.regType = regType; }

    public String getTaxType() { return taxType; }

    public void setTaxType(String taxType) { this.taxType = taxType; }

    public String getTaxCategory() { return taxCategory; }

    public void setTaxCategory(String taxCategory) { this.taxCategory = taxCategory; }

    public String getOwedAmount() { return owedAmount; }

    public void setOwedAmount(String owedAmount) { this.owedAmount = owedAmount; }

    public String getOwedBalance() { return owedBalance; }

    public void setOwedBalance(String owedBalance) { this.owedBalance = owedBalance; }

    public String getNewOwedBalance() { return newOwedBalance; }

    public void setNewOwedBalance(String newOwedBalance) { this.newOwedBalance = newOwedBalance; }

    public Date getPublishTime() { return publishTime; }

    public void setPublishTime(Date publishTime) { this.publishTime = publishTime; }

    public String getBizAddress() { return bizAddress; }

    public void setBizAddress(String bizAddress) { this.bizAddress = bizAddress; }

    public String getTaxAuthority() { return taxAuthority; }

    public void setTaxAuthority(String taxAuthority) { this.taxAuthority = taxAuthority; }

    public String getLegalName() { return legalName; }

    public void setLegalName(String legalName) { this.legalName = legalName; }

    public String getLegalIdType() { return legalIdType; }

    public void setLegalIdType(String legalIdType) { this.legalIdType = legalIdType; }

    public Integer getTotalCount() { return totalCount; }

    public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
