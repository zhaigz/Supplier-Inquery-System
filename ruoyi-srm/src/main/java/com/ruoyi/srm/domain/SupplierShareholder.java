package com.ruoyi.srm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_supplier_shareholder */
public class SupplierShareholder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 股东名（821） */
    private String shareholderName;

    /** 股东类型：1 公司 / 2 人 / 3 其它（821） */
    private String shareholderType;

    /** 简称（821） */
    private String shortName;

    /** 对应表id（821） */
    private String tycRefId;

    /** 人员hcgid（821） */
    private String hcgid;

    /** 占比（821，小数表示百分比） */
    private BigDecimal ratio;

    /** 持股占比（821，与占比分列，页面10 比例区间用） */
    private BigDecimal holdRatio;

    /** 持股数量（821） */
    private String holdNum;

    /** 认缴出资金额（821） */
    private String subAmount;

    /** 认缴出资金额对应出资时间 / 认缴方式 */
    private String subDate;

    /** 认缴方式（821） */
    private String subWay;

    /** 实缴出资金额（821） */
    private String paidAmount;

    /** 实缴出资时间（821） */
    private String paidDate;

    /** 实缴方式（821） */
    private String paidWay;

    /** 首次持股日期（821） */
    private String firstHoldDate;

    /** 香港企业（821） */
    private String isHkEnterprise;

    /** 数据来源枚举：1 工商登记 / 2 最新公示（821 入参，不填默认工商登记） */
    private String dataFrom;

    /**  */
    private Integer sourceApiId;

    /**  */
    private Long rawId;

    /**  */
    private Date fetchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getShareholderName() { return shareholderName; }

    public void setShareholderName(String shareholderName) { this.shareholderName = shareholderName; }

    public String getShareholderType() { return shareholderType; }

    public void setShareholderType(String shareholderType) { this.shareholderType = shareholderType; }

    public String getShortName() { return shortName; }

    public void setShortName(String shortName) { this.shortName = shortName; }

    public String getTycRefId() { return tycRefId; }

    public void setTycRefId(String tycRefId) { this.tycRefId = tycRefId; }

    public String getHcgid() { return hcgid; }

    public void setHcgid(String hcgid) { this.hcgid = hcgid; }

    public BigDecimal getRatio() { return ratio; }

    public void setRatio(BigDecimal ratio) { this.ratio = ratio; }

    public BigDecimal getHoldRatio() { return holdRatio; }

    public void setHoldRatio(BigDecimal holdRatio) { this.holdRatio = holdRatio; }

    public String getHoldNum() { return holdNum; }

    public void setHoldNum(String holdNum) { this.holdNum = holdNum; }

    public String getSubAmount() { return subAmount; }

    public void setSubAmount(String subAmount) { this.subAmount = subAmount; }

    public String getSubDate() { return subDate; }

    public void setSubDate(String subDate) { this.subDate = subDate; }

    public String getSubWay() { return subWay; }

    public void setSubWay(String subWay) { this.subWay = subWay; }

    public String getPaidAmount() { return paidAmount; }

    public void setPaidAmount(String paidAmount) { this.paidAmount = paidAmount; }

    public String getPaidDate() { return paidDate; }

    public void setPaidDate(String paidDate) { this.paidDate = paidDate; }

    public String getPaidWay() { return paidWay; }

    public void setPaidWay(String paidWay) { this.paidWay = paidWay; }

    public String getFirstHoldDate() { return firstHoldDate; }

    public void setFirstHoldDate(String firstHoldDate) { this.firstHoldDate = firstHoldDate; }

    public String getIsHkEnterprise() { return isHkEnterprise; }

    public void setIsHkEnterprise(String isHkEnterprise) { this.isHkEnterprise = isHkEnterprise; }

    public String getDataFrom() { return dataFrom; }

    public void setDataFrom(String dataFrom) { this.dataFrom = dataFrom; }

    public Integer getSourceApiId() { return sourceApiId; }

    public void setSourceApiId(Integer sourceApiId) { this.sourceApiId = sourceApiId; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
