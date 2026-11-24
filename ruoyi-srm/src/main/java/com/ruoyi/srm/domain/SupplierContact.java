package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_supplier_contact */
public class SupplierContact extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 类型：phone 电话 / email 邮箱 / url 网址 / address 注册地址 */
    private String contactType;

    /** 规范化后的联系值（页面11：电话、地址、人员需规范化） */
    private String contactValue;

    /** 接口原值（未规范化，供追溯） */
    private String rawValue;

    /** 电话来源（818 电话来源 / 1046 号码来源），非电话类为空 */
    private String phoneSource;

    /** 电话标签：正常电话/客服电话/大陆座机/空号（818）；1046 为标签 */
    private String phoneLabel;

    /** 是否主用值：主档 phone/email 取本字段=1 的第一条 */
    private String isPrimary;

    /** 818 / 1046 */
    private Integer sourceApiId;

    /**  */
    private Long rawId;

    /**  */
    private Date fetchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getContactType() { return contactType; }

    public void setContactType(String contactType) { this.contactType = contactType; }

    public String getContactValue() { return contactValue; }

    public void setContactValue(String contactValue) { this.contactValue = contactValue; }

    public String getRawValue() { return rawValue; }

    public void setRawValue(String rawValue) { this.rawValue = rawValue; }

    public String getPhoneSource() { return phoneSource; }

    public void setPhoneSource(String phoneSource) { this.phoneSource = phoneSource; }

    public String getPhoneLabel() { return phoneLabel; }

    public void setPhoneLabel(String phoneLabel) { this.phoneLabel = phoneLabel; }

    public String getIsPrimary() { return isPrimary; }

    public void setIsPrimary(String isPrimary) { this.isPrimary = isPrimary; }

    public Integer getSourceApiId() { return sourceApiId; }

    public void setSourceApiId(Integer sourceApiId) { this.sourceApiId = sourceApiId; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
