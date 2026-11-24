package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_risk_enforce */
public class RiskEnforce extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 0 当前被执行人(839) / 1 历史被执行人(871)，同构字段合并一表 */
    private String histFlag;

    /** 案号 */
    private String caseNo;

    /** 执行法院 */
    private String court;

    /** 被执行人名称 */
    private String personName;

    /** 身份证号／组织机构代码（敏感：入库加密，日志与导出不落全值） */
    private String idNo;

    /** 创建时间 */
    private Date createDate;

    /** 执行标的（元） */
    private String targetAmount;

    /** 接口返回总数（分页统计，仅当页快照） */
    private Integer totalCount;

    /**  */
    private Long rawId;

    /**  */
    private Date fetchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getHistFlag() { return histFlag; }

    public void setHistFlag(String histFlag) { this.histFlag = histFlag; }

    public String getCaseNo() { return caseNo; }

    public void setCaseNo(String caseNo) { this.caseNo = caseNo; }

    public String getCourt() { return court; }

    public void setCourt(String court) { this.court = court; }

    public String getPersonName() { return personName; }

    public void setPersonName(String personName) { this.personName = personName; }

    public String getIdNo() { return idNo; }

    public void setIdNo(String idNo) { this.idNo = idNo; }

    public Date getCreateDate() { return createDate; }

    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public String getTargetAmount() { return targetAmount; }

    public void setTargetAmount(String targetAmount) { this.targetAmount = targetAmount; }

    public Integer getTotalCount() { return totalCount; }

    public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
