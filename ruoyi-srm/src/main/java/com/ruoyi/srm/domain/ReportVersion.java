package com.ruoyi.srm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_report_version */
public class ReportVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long projectId;

    /** 版本号，连续不重复（页面13 表 13-1 第 1 列） */
    private Integer versionNo;

    /** 来源分析任务 */
    private Long taskId;

    /** 分析时间 */
    private Date analysisAt;

    /** 分析范围/分析供应商清单（固化，含供应商编码与名称快照） */
    private String supplierScope;

    /** 分析供应商数 */
    private Integer memberCount;

    /** 关系线索数 */
    private Integer clueCount;

    /** 向上穿透层级（固化） */
    private Integer upDepth;

    /** 向下穿透层级（固化） */
    private Integer downDepth;

    /** 最低权重（固化） */
    private Integer minWeight;

    /** 关系口径（固化） */
    private String relScope;

    /** 规则快照版本（引用 srm_rel_rule_version） */
    private Integer ruleVersionNo;

    /** 启用规则快照原文（页面13 规则快照） */
    private String ruleSnapshot;

    /** 命中规则清单（固化） */
    private String hitRules;

    /** 数据来源（接口编号集合） */
    private String dataSource;

    /** 预估/估算调用单元（本地估算，非计费字段） */
    private BigDecimal estUnit;

    /** 数据性质：正式数据 / 演示数据（原型标注「前端模拟数据」，正式库必须区分） */
    private String dataNature;

    /** 定稿锁定：1 后本行禁止任何 UPDATE（页面13 只读快照） */
    private String isLocked;

    /** 导出次数（导出动作明细在审计日志） */
    private Integer exportCount;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getProjectId() { return projectId; }

    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Integer getVersionNo() { return versionNo; }

    public void setVersionNo(Integer versionNo) { this.versionNo = versionNo; }

    public Long getTaskId() { return taskId; }

    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public Date getAnalysisAt() { return analysisAt; }

    public void setAnalysisAt(Date analysisAt) { this.analysisAt = analysisAt; }

    public String getSupplierScope() { return supplierScope; }

    public void setSupplierScope(String supplierScope) { this.supplierScope = supplierScope; }

    public Integer getMemberCount() { return memberCount; }

    public void setMemberCount(Integer memberCount) { this.memberCount = memberCount; }

    public Integer getClueCount() { return clueCount; }

    public void setClueCount(Integer clueCount) { this.clueCount = clueCount; }

    public Integer getUpDepth() { return upDepth; }

    public void setUpDepth(Integer upDepth) { this.upDepth = upDepth; }

    public Integer getDownDepth() { return downDepth; }

    public void setDownDepth(Integer downDepth) { this.downDepth = downDepth; }

    public Integer getMinWeight() { return minWeight; }

    public void setMinWeight(Integer minWeight) { this.minWeight = minWeight; }

    public String getRelScope() { return relScope; }

    public void setRelScope(String relScope) { this.relScope = relScope; }

    public Integer getRuleVersionNo() { return ruleVersionNo; }

    public void setRuleVersionNo(Integer ruleVersionNo) { this.ruleVersionNo = ruleVersionNo; }

    public String getRuleSnapshot() { return ruleSnapshot; }

    public void setRuleSnapshot(String ruleSnapshot) { this.ruleSnapshot = ruleSnapshot; }

    public String getHitRules() { return hitRules; }

    public void setHitRules(String hitRules) { this.hitRules = hitRules; }

    public String getDataSource() { return dataSource; }

    public void setDataSource(String dataSource) { this.dataSource = dataSource; }

    public BigDecimal getEstUnit() { return estUnit; }

    public void setEstUnit(BigDecimal estUnit) { this.estUnit = estUnit; }

    public String getDataNature() { return dataNature; }

    public void setDataNature(String dataNature) { this.dataNature = dataNature; }

    public String getIsLocked() { return isLocked; }

    public void setIsLocked(String isLocked) { this.isLocked = isLocked; }

    public Integer getExportCount() { return exportCount; }

    public void setExportCount(Integer exportCount) { this.exportCount = exportCount; }

}
