package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_supplier_former_name */
public class SupplierFormerName extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 曾用名（458） */
    private String formerName;

    /** 开始时间 yyyy-MM（458） */
    private String startTime;

    /** 结束时间 yyyy-MM（458） */
    private String endTime;

    /**  */
    private Long rawId;

    /**  */
    private Date fetchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getFormerName() { return formerName; }

    public void setFormerName(String formerName) { this.formerName = formerName; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
