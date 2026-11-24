package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_rel_clue */
public class RelClue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 产出任务 */
    private Long taskId;

    /**  */
    private Long projectId;

    /** 所属报告版本（版本内线索只读） */
    private Long reportVersionId;

    /** 企业 A（主档 id） */
    private Long supplierA;

    /** 企业 B */
    private Long supplierB;

    /** 企业名称（1156 企业名称） */
    private String entAName;

    /** 企业名称 */
    private String entBName;

    /** 企业ID（1156） */
    private String entATycId;

    /**  */
    private String entBTycId;

    /** 法人类型：1 人 / 2 公司（1156 原文枚举） */
    private String legalTypeA;

    /**  */
    private String legalTypeB;

    /** 法定代表人 */
    private String legalPersonA;

    /**  */
    private String legalPersonB;

    /** 注册资本 */
    private String regCapitalA;

    /**  */
    private String regCapitalB;

    /** 成立时间 */
    private String establishA;

    /**  */
    private String establishB;

    /** 疑似关系类型（1156）→ 界面「命中规则」线索内容 */
    private String clueType;

    /** 疑似关系详情（1156）→ 界面「关联线索」 */
    private String clueDetail;

    /** 代记账公司电话（1156，受"是否包含代记账公司电话关系"入参控制） */
    private String bookkeepingPhone;

    /** 本地命中维度：同法定代表人/共同股东/共同董监高/同电话/同邮箱/同注册地址 */
    private String localRelType;

    /** 命中本地规则 */
    private Long ruleId;

    /** 命中规则名（本地关系规则，非接口字段） */
    private String ruleName;

    /** 权重（本地规则权重 1—5，非接口字段） */
    private Integer weight;

    /** 关联路径（节点名链，页面12/13 展示） */
    private String relPath;

    /** 本次命中使用的向上穿透层级 */
    private Integer upDepth;

    /** 向下穿透层级 */
    private Integer downDepth;

    /** 数据来源：接口编号 / 本地计算 */
    private String dataSource;

    /**  */
    private Integer sourceApiId;

    /**  */
    private Long rawId;

    /** 复核状态：待复核 / 已确认有关联 / 排除线索 / 需补充材料（页面12）。状态回写本行仅为展示，处置过程走 srm_clue_review 追加 */
    private String reviewStatus;

    /** 命中时的分页页码（1156 每页最大 20 条） */
    private Integer pageNo;

    /** 分析时间 */
    private Date analyzedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getTaskId() { return taskId; }

    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public Long getProjectId() { return projectId; }

    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getReportVersionId() { return reportVersionId; }

    public void setReportVersionId(Long reportVersionId) { this.reportVersionId = reportVersionId; }

    public Long getSupplierA() { return supplierA; }

    public void setSupplierA(Long supplierA) { this.supplierA = supplierA; }

    public Long getSupplierB() { return supplierB; }

    public void setSupplierB(Long supplierB) { this.supplierB = supplierB; }

    public String getEntAName() { return entAName; }

    public void setEntAName(String entAName) { this.entAName = entAName; }

    public String getEntBName() { return entBName; }

    public void setEntBName(String entBName) { this.entBName = entBName; }

    public String getEntATycId() { return entATycId; }

    public void setEntATycId(String entATycId) { this.entATycId = entATycId; }

    public String getEntBTycId() { return entBTycId; }

    public void setEntBTycId(String entBTycId) { this.entBTycId = entBTycId; }

    public String getLegalTypeA() { return legalTypeA; }

    public void setLegalTypeA(String legalTypeA) { this.legalTypeA = legalTypeA; }

    public String getLegalTypeB() { return legalTypeB; }

    public void setLegalTypeB(String legalTypeB) { this.legalTypeB = legalTypeB; }

    public String getLegalPersonA() { return legalPersonA; }

    public void setLegalPersonA(String legalPersonA) { this.legalPersonA = legalPersonA; }

    public String getLegalPersonB() { return legalPersonB; }

    public void setLegalPersonB(String legalPersonB) { this.legalPersonB = legalPersonB; }

    public String getRegCapitalA() { return regCapitalA; }

    public void setRegCapitalA(String regCapitalA) { this.regCapitalA = regCapitalA; }

    public String getRegCapitalB() { return regCapitalB; }

    public void setRegCapitalB(String regCapitalB) { this.regCapitalB = regCapitalB; }

    public String getEstablishA() { return establishA; }

    public void setEstablishA(String establishA) { this.establishA = establishA; }

    public String getEstablishB() { return establishB; }

    public void setEstablishB(String establishB) { this.establishB = establishB; }

    public String getClueType() { return clueType; }

    public void setClueType(String clueType) { this.clueType = clueType; }

    public String getClueDetail() { return clueDetail; }

    public void setClueDetail(String clueDetail) { this.clueDetail = clueDetail; }

    public String getBookkeepingPhone() { return bookkeepingPhone; }

    public void setBookkeepingPhone(String bookkeepingPhone) { this.bookkeepingPhone = bookkeepingPhone; }

    public String getLocalRelType() { return localRelType; }

    public void setLocalRelType(String localRelType) { this.localRelType = localRelType; }

    public Long getRuleId() { return ruleId; }

    public void setRuleId(Long ruleId) { this.ruleId = ruleId; }

    public String getRuleName() { return ruleName; }

    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public Integer getWeight() { return weight; }

    public void setWeight(Integer weight) { this.weight = weight; }

    public String getRelPath() { return relPath; }

    public void setRelPath(String relPath) { this.relPath = relPath; }

    public Integer getUpDepth() { return upDepth; }

    public void setUpDepth(Integer upDepth) { this.upDepth = upDepth; }

    public Integer getDownDepth() { return downDepth; }

    public void setDownDepth(Integer downDepth) { this.downDepth = downDepth; }

    public String getDataSource() { return dataSource; }

    public void setDataSource(String dataSource) { this.dataSource = dataSource; }

    public Integer getSourceApiId() { return sourceApiId; }

    public void setSourceApiId(Integer sourceApiId) { this.sourceApiId = sourceApiId; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public String getReviewStatus() { return reviewStatus; }

    public void setReviewStatus(String reviewStatus) { this.reviewStatus = reviewStatus; }

    public Integer getPageNo() { return pageNo; }

    public void setPageNo(Integer pageNo) { this.pageNo = pageNo; }

    public Date getAnalyzedAt() { return analyzedAt; }

    public void setAnalyzedAt(Date analyzedAt) { this.analyzedAt = analyzedAt; }

}
