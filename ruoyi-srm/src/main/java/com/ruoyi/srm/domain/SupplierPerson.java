package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_supplier_person */
public class SupplierPerson extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 主要人员名称（819 主要人员名称 / 820 人员） */
    private String personName;

    /** 1 公司 / 2 人（819 主要人员列表 1-公司 2-人） */
    private String personType;

    /** 人或公司id（819） */
    private String tycPersonId;

    /** 人员hcgid（820）：自然人跨企业聚合标识，幕后关系比对的常用主键 */
    private String hcgid;

    /** 职位 */
    private String position;

    /** logo */
    private String logo;

    /** 接口返回顺序（总数由 srm_api_raw 计数，不单独存） */
    private Integer sortNo;

    /** 819 / 820 */
    private Integer sourceApiId;

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

    public String getPersonType() { return personType; }

    public void setPersonType(String personType) { this.personType = personType; }

    public String getTycPersonId() { return tycPersonId; }

    public void setTycPersonId(String tycPersonId) { this.tycPersonId = tycPersonId; }

    public String getHcgid() { return hcgid; }

    public void setHcgid(String hcgid) { this.hcgid = hcgid; }

    public String getPosition() { return position; }

    public void setPosition(String position) { this.position = position; }

    public String getLogo() { return logo; }

    public void setLogo(String logo) { this.logo = logo; }

    public Integer getSortNo() { return sortNo; }

    public void setSortNo(Integer sortNo) { this.sortNo = sortNo; }

    public Integer getSourceApiId() { return sourceApiId; }

    public void setSourceApiId(Integer sourceApiId) { this.sourceApiId = sourceApiId; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
