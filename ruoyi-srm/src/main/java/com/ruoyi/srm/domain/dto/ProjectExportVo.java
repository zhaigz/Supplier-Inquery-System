package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 页面7 关系线索导出行。 */
public class ProjectExportVo
{
    @Excel(name = "供应商A")
    private String supplierA;
    @Excel(name = "供应商B")
    private String supplierB;
    @Excel(name = "线索类型")
    private String clueType;
    @Excel(name = "命中规则")
    private String ruleName;
    @Excel(name = "权重")
    private Integer weight;
    @Excel(name = "关系路径")
    private String relPath;
    @Excel(name = "数据来源")
    private String dataSource;

    public String getSupplierA() { return supplierA; }
    public void setSupplierA(String supplierA) { this.supplierA = supplierA; }
    public String getSupplierB() { return supplierB; }
    public void setSupplierB(String supplierB) { this.supplierB = supplierB; }
    public String getClueType() { return clueType; }
    public void setClueType(String clueType) { this.clueType = clueType; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }
    public String getRelPath() { return relPath; }
    public void setRelPath(String relPath) { this.relPath = relPath; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
}