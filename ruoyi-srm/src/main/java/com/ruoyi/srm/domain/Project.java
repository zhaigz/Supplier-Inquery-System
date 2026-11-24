package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_project */
public class Project extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 项目单据号（页面7 列表列、审计留痕必填） */
    private String projectNo;

    /** 项目名称 */
    private String projectName;

    /** 项目日期 */
    private Date projectDate;

    /** 发起部门（数据权限按部门隔离的落点） */
    private Long deptId;

    /**  */
    private String deptName;

    /** 项目说明 */
    private String projectDesc;

    /** 穿透参数快照：upDepth/downDepth/ratioMin/ratioMax/minWeight/relScope/includeBookkeeping（页面7 编辑与回显唯一来源） */
    private String analysisParamJson;

    /** 供应商数量（冗余统计，成员表为准） */
    private Integer supplierCount;

    /** 分析状态：未分析/分析中/已完成/部分失败/已失败/已取消（页面7 列表列） */
    private String analysisStatus;

    /** 最近一次分析任务 */
    private Long lastTaskId;

    /** 最新可展示版本（历史版本不被覆盖） */
    private Long lastReportVersionId;

    /**  */
    private String status;

    /**  */
    private String delFlag;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getProjectNo() { return projectNo; }

    public void setProjectNo(String projectNo) { this.projectNo = projectNo; }

    public String getProjectName() { return projectName; }

    public void setProjectName(String projectName) { this.projectName = projectName; }

    public Date getProjectDate() { return projectDate; }

    public void setProjectDate(Date projectDate) { this.projectDate = projectDate; }

    public Long getDeptId() { return deptId; }

    public void setDeptId(Long deptId) { this.deptId = deptId; }

    public String getDeptName() { return deptName; }

    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getProjectDesc() { return projectDesc; }

    public void setProjectDesc(String projectDesc) { this.projectDesc = projectDesc; }

    public String getAnalysisParamJson() { return analysisParamJson; }

    public void setAnalysisParamJson(String analysisParamJson) { this.analysisParamJson = analysisParamJson; }

    public Integer getSupplierCount() { return supplierCount; }

    public void setSupplierCount(Integer supplierCount) { this.supplierCount = supplierCount; }

    public String getAnalysisStatus() { return analysisStatus; }

    public void setAnalysisStatus(String analysisStatus) { this.analysisStatus = analysisStatus; }

    public Long getLastTaskId() { return lastTaskId; }

    public void setLastTaskId(Long lastTaskId) { this.lastTaskId = lastTaskId; }

    public Long getLastReportVersionId() { return lastReportVersionId; }

    public void setLastReportVersionId(Long lastReportVersionId) { this.lastReportVersionId = lastReportVersionId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getDelFlag() { return delFlag; }

    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }

}
