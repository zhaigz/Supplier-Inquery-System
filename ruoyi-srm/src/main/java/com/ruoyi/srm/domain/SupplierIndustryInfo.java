package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_supplier_industry_info */
public class SupplierIndustryInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 主档 id */
    private Long supplierId;

    /** 企业id */
    private String tycCompanyId;

    /** 企业名（接口原文名，可能与主档名不同，用于曾用名/别名核对） */
    private String entName;

    /** 企业类型（818；1047 企业类型可覆盖） */
    private String entType;

    /** 组织机构：机构类型/一级机构类型/二级机构类型/经济类型（1168） */
    private String orgType;

    /** 经营范围（818） */
    private String scope;

    /** 登记机关（818） */
    private String regOrg;

    /** 核准时间（818） */
    private Date approveDate;

    /** 统一社会信用代码（接口返回值，与主档不一致时以主档为准并记差异） */
    private String socialCreditCode;

    /** 纳税人识别号（818） */
    private String taxpayerNo;

    /** 组织机构代码（818） */
    private String orgCode;

    /** 英文名（818） */
    private String engName;

    /** 简称（818） */
    private String shortName;

    /** 经营开始时间（818） */
    private Date fromTime;

    /** 经营结束时间（818） */
    private Date toTime;

    /** 注销日期（818） */
    private Date cancelDate;

    /** 注销原因（818） */
    private String cancelReason;

    /** 吊销日期（818） */
    private Date revokeDate;

    /** 吊销原因（818） */
    private String revokeReason;

    /** 注册资本 */
    private String regCapital;

    /** 注册资本币种（人民币/美元/欧元等） */
    private String regCapitaCurrency;

    /** 实收注册资金 */
    private String actualCapital;

    /** 人员规模（818） */
    private String staffNum;

    /** 参保人数（818） */
    private String socialPersonNum;

    /** 是否小微企业：0 不是 / 1 是（818） */
    private String isMicro;

    /** 企业标签（818） */
    private String entLabel;

    /** 规上企业（818） */
    private String regressionUp;

    /** 经济功能区1（818） */
    private String econZone1;

    /** 经济功能区2（818） */
    private String econZone2;

    /** 国民经济行业分类门类 */
    private String industryGate;

    /** 国民经济行业分类大类 */
    private String industryBig;

    /** 国民经济行业分类中类 */
    private String industryMid;

    /** 国民经济行业分类小类 */
    private String industrySmall;

    /** 门类行业代码 */
    private String industryGateCode;

    /** 大类行业代码 */
    private String industryBigCode;

    /** 中类行业代码 */
    private String industryMidCode;

    /** 小类行业代码 */
    private String industrySmallCode;

    /** 股票名（818） */
    private String stockName;

    /** 股票号（818） */
    private String stockCode;

    /** 股票类型（818） */
    private String stockType;

    /** 股票曾用名（818） */
    private String stockUsedName;

    /** 商业登记号（818） */
    private String commercialNo;

    /** 企业评分：外部字段原样留存，本期不作内部评分展示（页面3 表 3-6 口径） */
    private String entScore;

    /** 接口更新时间（818 更新时间） */
    private String updateFrom;

    /** 来源接口：818 / 819 / 1047 / 1168 */
    private Integer sourceApiId;

    /** 原始响应留痕 srm_api_raw.id */
    private Long rawId;

    /** 取数时间 */
    private Date fetchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getTycCompanyId() { return tycCompanyId; }

    public void setTycCompanyId(String tycCompanyId) { this.tycCompanyId = tycCompanyId; }

    public String getEntName() { return entName; }

    public void setEntName(String entName) { this.entName = entName; }

    public String getEntType() { return entType; }

    public void setEntType(String entType) { this.entType = entType; }

    public String getOrgType() { return orgType; }

    public void setOrgType(String orgType) { this.orgType = orgType; }

    public String getScope() { return scope; }

    public void setScope(String scope) { this.scope = scope; }

    public String getRegOrg() { return regOrg; }

    public void setRegOrg(String regOrg) { this.regOrg = regOrg; }

    public Date getApproveDate() { return approveDate; }

    public void setApproveDate(Date approveDate) { this.approveDate = approveDate; }

    public String getSocialCreditCode() { return socialCreditCode; }

    public void setSocialCreditCode(String socialCreditCode) { this.socialCreditCode = socialCreditCode; }

    public String getTaxpayerNo() { return taxpayerNo; }

    public void setTaxpayerNo(String taxpayerNo) { this.taxpayerNo = taxpayerNo; }

    public String getOrgCode() { return orgCode; }

    public void setOrgCode(String orgCode) { this.orgCode = orgCode; }

    public String getEngName() { return engName; }

    public void setEngName(String engName) { this.engName = engName; }

    public String getShortName() { return shortName; }

    public void setShortName(String shortName) { this.shortName = shortName; }

    public Date getFromTime() { return fromTime; }

    public void setFromTime(Date fromTime) { this.fromTime = fromTime; }

    public Date getToTime() { return toTime; }

    public void setToTime(Date toTime) { this.toTime = toTime; }

    public Date getCancelDate() { return cancelDate; }

    public void setCancelDate(Date cancelDate) { this.cancelDate = cancelDate; }

    public String getCancelReason() { return cancelReason; }

    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }

    public Date getRevokeDate() { return revokeDate; }

    public void setRevokeDate(Date revokeDate) { this.revokeDate = revokeDate; }

    public String getRevokeReason() { return revokeReason; }

    public void setRevokeReason(String revokeReason) { this.revokeReason = revokeReason; }

    public String getRegCapital() { return regCapital; }

    public void setRegCapital(String regCapital) { this.regCapital = regCapital; }

    public String getRegCapitaCurrency() { return regCapitaCurrency; }

    public void setRegCapitaCurrency(String regCapitaCurrency) { this.regCapitaCurrency = regCapitaCurrency; }

    public String getActualCapital() { return actualCapital; }

    public void setActualCapital(String actualCapital) { this.actualCapital = actualCapital; }

    public String getStaffNum() { return staffNum; }

    public void setStaffNum(String staffNum) { this.staffNum = staffNum; }

    public String getSocialPersonNum() { return socialPersonNum; }

    public void setSocialPersonNum(String socialPersonNum) { this.socialPersonNum = socialPersonNum; }

    public String getIsMicro() { return isMicro; }

    public void setIsMicro(String isMicro) { this.isMicro = isMicro; }

    public String getEntLabel() { return entLabel; }

    public void setEntLabel(String entLabel) { this.entLabel = entLabel; }

    public String getRegressionUp() { return regressionUp; }

    public void setRegressionUp(String regressionUp) { this.regressionUp = regressionUp; }

    public String getEconZone1() { return econZone1; }

    public void setEconZone1(String econZone1) { this.econZone1 = econZone1; }

    public String getEconZone2() { return econZone2; }

    public void setEconZone2(String econZone2) { this.econZone2 = econZone2; }

    public String getIndustryGate() { return industryGate; }

    public void setIndustryGate(String industryGate) { this.industryGate = industryGate; }

    public String getIndustryBig() { return industryBig; }

    public void setIndustryBig(String industryBig) { this.industryBig = industryBig; }

    public String getIndustryMid() { return industryMid; }

    public void setIndustryMid(String industryMid) { this.industryMid = industryMid; }

    public String getIndustrySmall() { return industrySmall; }

    public void setIndustrySmall(String industrySmall) { this.industrySmall = industrySmall; }

    public String getIndustryGateCode() { return industryGateCode; }

    public void setIndustryGateCode(String industryGateCode) { this.industryGateCode = industryGateCode; }

    public String getIndustryBigCode() { return industryBigCode; }

    public void setIndustryBigCode(String industryBigCode) { this.industryBigCode = industryBigCode; }

    public String getIndustryMidCode() { return industryMidCode; }

    public void setIndustryMidCode(String industryMidCode) { this.industryMidCode = industryMidCode; }

    public String getIndustrySmallCode() { return industrySmallCode; }

    public void setIndustrySmallCode(String industrySmallCode) { this.industrySmallCode = industrySmallCode; }

    public String getStockName() { return stockName; }

    public void setStockName(String stockName) { this.stockName = stockName; }

    public String getStockCode() { return stockCode; }

    public void setStockCode(String stockCode) { this.stockCode = stockCode; }

    public String getStockType() { return stockType; }

    public void setStockType(String stockType) { this.stockType = stockType; }

    public String getStockUsedName() { return stockUsedName; }

    public void setStockUsedName(String stockUsedName) { this.stockUsedName = stockUsedName; }

    public String getCommercialNo() { return commercialNo; }

    public void setCommercialNo(String commercialNo) { this.commercialNo = commercialNo; }

    public String getEntScore() { return entScore; }

    public void setEntScore(String entScore) { this.entScore = entScore; }

    public String getUpdateFrom() { return updateFrom; }

    public void setUpdateFrom(String updateFrom) { this.updateFrom = updateFrom; }

    public Integer getSourceApiId() { return sourceApiId; }

    public void setSourceApiId(Integer sourceApiId) { this.sourceApiId = sourceApiId; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
