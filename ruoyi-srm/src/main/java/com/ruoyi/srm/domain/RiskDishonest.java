package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_risk_dishonest */
public class RiskDishonest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 失信人名称 */
    private String personName;

    /** 法人 */
    private String legalPerson;

    /** 负责人姓名 */
    private String managerName;

    /** 执行依据文号 */
    private String basisNo;

    /** 省份地区（本接口唯一行政区划字段） */
    private String provinceArea;

    /** 身份证号码/组织机构代码（敏感加密） */
    private String idNo;

    /** 法院 */
    private String court;

    /** 发布时间 */
    private Date publishTime;

    /** 失信人类型 */
    private String dishonestType;

    /** 做出执行的依据单位 */
    private String basisOrg;

    /** 生效法律文书确定的义务 */
    private String obligation;

    /** 履行情况 */
    private String performStatus;

    /** 立案时间 */
    private Date filingDate;

    /** 案号 */
    private String caseNo;

    /** 失信被执行人行为具体情形 */
    private String conductType;

    /** 涉案金额（单位：元） */
    private String amount;

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

    public String getPersonName() { return personName; }

    public void setPersonName(String personName) { this.personName = personName; }

    public String getLegalPerson() { return legalPerson; }

    public void setLegalPerson(String legalPerson) { this.legalPerson = legalPerson; }

    public String getManagerName() { return managerName; }

    public void setManagerName(String managerName) { this.managerName = managerName; }

    public String getBasisNo() { return basisNo; }

    public void setBasisNo(String basisNo) { this.basisNo = basisNo; }

    public String getProvinceArea() { return provinceArea; }

    public void setProvinceArea(String provinceArea) { this.provinceArea = provinceArea; }

    public String getIdNo() { return idNo; }

    public void setIdNo(String idNo) { this.idNo = idNo; }

    public String getCourt() { return court; }

    public void setCourt(String court) { this.court = court; }

    public Date getPublishTime() { return publishTime; }

    public void setPublishTime(Date publishTime) { this.publishTime = publishTime; }

    public String getDishonestType() { return dishonestType; }

    public void setDishonestType(String dishonestType) { this.dishonestType = dishonestType; }

    public String getBasisOrg() { return basisOrg; }

    public void setBasisOrg(String basisOrg) { this.basisOrg = basisOrg; }

    public String getObligation() { return obligation; }

    public void setObligation(String obligation) { this.obligation = obligation; }

    public String getPerformStatus() { return performStatus; }

    public void setPerformStatus(String performStatus) { this.performStatus = performStatus; }

    public Date getFilingDate() { return filingDate; }

    public void setFilingDate(Date filingDate) { this.filingDate = filingDate; }

    public String getCaseNo() { return caseNo; }

    public void setCaseNo(String caseNo) { this.caseNo = caseNo; }

    public String getConductType() { return conductType; }

    public void setConductType(String conductType) { this.conductType = conductType; }

    public String getAmount() { return amount; }

    public void setAmount(String amount) { this.amount = amount; }

    public Integer getTotalCount() { return totalCount; }

    public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
