package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_supplier_change_record */
public class SupplierChangeRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 变更事项（822） */
    private String changeItem;

    /** 变更前 */
    private String beforeValue;

    /** 变更后 */
    private String afterValue;

    /** 变更后值 md5，把长文本纳入唯一键以避开索引 3072 字节上限
     * 生成列只读，业务代码不得赋值。
     */
    private String afterHash;

    /** 变更时间 */
    private Date changeDate;

    /** 创建时间（接口值） */
    private Date createDate;

    /**  */
    private Long rawId;

    /**  */
    private Date fetchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getChangeItem() { return changeItem; }

    public void setChangeItem(String changeItem) { this.changeItem = changeItem; }

    public String getBeforeValue() { return beforeValue; }

    public void setBeforeValue(String beforeValue) { this.beforeValue = beforeValue; }

    public String getAfterValue() { return afterValue; }

    public void setAfterValue(String afterValue) { this.afterValue = afterValue; }

    public String getAfterHash() { return afterHash; }

    public void setAfterHash(String afterHash) { this.afterHash = afterHash; }

    public Date getChangeDate() { return changeDate; }

    public void setChangeDate(Date changeDate) { this.changeDate = changeDate; }

    public Date getCreateDate() { return createDate; }

    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
