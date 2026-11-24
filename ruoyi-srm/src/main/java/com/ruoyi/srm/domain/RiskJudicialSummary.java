package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_risk_judicial_summary */
public class RiskJudicialSummary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 维度名：开庭公告/被执行人/法院公告/立案信息/失信人/法律诉讼/送达公告（1002 七维度） */
    private String dimension;

    /** 该维度记录数（接口返回，明细字段以 open.tianyancha.com/open/1002 为准） */
    private Integer itemCount;

    /** 该维度明细原样留存，字段名不在库表固化，由页面11 映射解释 */
    private String detailJson;

    /**  */
    private Long rawId;

    /**  */
    private Date fetchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getDimension() { return dimension; }

    public void setDimension(String dimension) { this.dimension = dimension; }

    public Integer getItemCount() { return itemCount; }

    public void setItemCount(Integer itemCount) { this.itemCount = itemCount; }

    public String getDetailJson() { return detailJson; }

    public void setDetailJson(String detailJson) { this.detailJson = detailJson; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
