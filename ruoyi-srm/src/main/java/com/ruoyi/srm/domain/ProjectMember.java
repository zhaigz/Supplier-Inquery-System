package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_project_member */
public class ProjectMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long projectId;

    /**  */
    private Long supplierId;

    /** 供应商编码（页面7 成员行列） */
    private String supplierCode;

    /** 企业名称 */
    private String supplierName;

    /** 重点监控状态快照（成员加入时的主档值，图谱图例区分普通/重点） */
    private String monitorFlag;

    /** 加入项目时间 */
    private Date joinTime;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getProjectId() { return projectId; }

    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getSupplierCode() { return supplierCode; }

    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }

    public String getSupplierName() { return supplierName; }

    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getMonitorFlag() { return monitorFlag; }

    public void setMonitorFlag(String monitorFlag) { this.monitorFlag = monitorFlag; }

    public Date getJoinTime() { return joinTime; }

    public void setJoinTime(Date joinTime) { this.joinTime = joinTime; }

}
