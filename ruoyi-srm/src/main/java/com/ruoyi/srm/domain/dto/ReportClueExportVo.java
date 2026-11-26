package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 报告版本线索导出行。 */
public class ReportClueExportVo
{
    @Excel(name = "供应商A")
    private String entAName;
    @Excel(name = "供应商B")
    private String entBName;
    @Excel(name = "线索类型")
    private String clueType;
    @Excel(name = "命中规则")
    private String ruleName;
    @Excel(name = "权重")
    private Integer weight;
    @Excel(name = "关系路径")
    private String relPath;
    @Excel(name = "复核状态")
    private String reviewStatus;

    public String getEntAName() { return entAName; }
    public void setEntAName(String entAName) { this.entAName = entAName; }
    public String getEntBName() { return entBName; }
    public void setEntBName(String entBName) { this.entBName = entBName; }
    public String getClueType() { return clueType; }
    public void setClueType(String clueType) { this.clueType = clueType; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }
    public String getRelPath() { return relPath; }
    public void setRelPath(String relPath) { this.relPath = relPath; }
    public String getReviewStatus() { return reviewStatus; }
    public void setReviewStatus(String reviewStatus) { this.reviewStatus = reviewStatus; }
}