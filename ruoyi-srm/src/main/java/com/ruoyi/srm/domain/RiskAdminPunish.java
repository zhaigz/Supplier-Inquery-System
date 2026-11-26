package com.ruoyi.srm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_risk_admin_punish */
public class RiskAdminPunish extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** illegal 严重违法(846) / abnormal 经营异常(848) */
    private String riskType;

    /** 列入原因 / 列入异常名录原因 */
    private String listReason;

    /** 决定列入部门（作出决定机关）/ 决定列入异常名录部门 */
    private String listDept;

    /** 列入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date listDate;

    /** 移除原因 / 移除异常名录原因 */
    private String removeReason;

    /** 决定移除部门 / 移出部门 */
    private String removeDept;

    /** 移除日期 / 移出日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date removeDate;

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

    public String getRiskType() { return riskType; }

    public void setRiskType(String riskType) { this.riskType = riskType; }

    public String getListReason() { return listReason; }

    public void setListReason(String listReason) { this.listReason = listReason; }

    public String getListDept() { return listDept; }

    public void setListDept(String listDept) { this.listDept = listDept; }

    public Date getListDate() { return listDate; }

    public void setListDate(Date listDate) { this.listDate = listDate; }

    public String getRemoveReason() { return removeReason; }

    public void setRemoveReason(String removeReason) { this.removeReason = removeReason; }

    public String getRemoveDept() { return removeDept; }

    public void setRemoveDept(String removeDept) { this.removeDept = removeDept; }

    public Date getRemoveDate() { return removeDate; }

    public void setRemoveDate(Date removeDate) { this.removeDate = removeDate; }

    public Integer getTotalCount() { return totalCount; }

    public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
