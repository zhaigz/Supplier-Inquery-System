package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_field_mapping */
public class FieldMapping extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 业务字段（页面11 8 列之一） */
    private String bizField;

    /** 数据来源：授权外部数据源 / 本地主档（自定义） */
    private String dataSource;

    /** 对应接口编号（本地自定义字段为空） */
    private Integer apiId;

    /** 外部字段标识：以接口响应文档中的真实键名为准，禁止填样例占位键名 */
    private String outerKey;

    /** 内部字段标识：内部稳定标识，按内部字段标识规范维护 */
    private String innerKey;

    /** 数据类型：列表 / 文本 */
    private String dataType;

    /** 关联分析：1 参与关联分析 / 0 未参与 */
    private String analysisFlag;

    /** 持续监控：1 参与监控 / 0 未监控 */
    private String monitorFlag;

    /** 结构化落库目标表（raw→结构化投影的可重建依据） */
    private String targetTable;

    /** 规范化规则：电话/地址/人员的去空、全半角、分隔符处理 */
    private String normRule;

    /** 空值不参与关系匹配（需求逐字要求），固定为 1 */
    private String notNullCheck;

    /** 0 有效 / 1 停用；同一业务字段只允许一条有效映射 */
    private String status;

    /** 有效映射唯一性承载列：停用的历史映射取 NULL，不参与唯一约束，从而只约束"有效"这一态
     * 生成列只读，业务代码不得赋值。
     */
    private String activeBiz;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getBizField() { return bizField; }

    public void setBizField(String bizField) { this.bizField = bizField; }

    public String getDataSource() { return dataSource; }

    public void setDataSource(String dataSource) { this.dataSource = dataSource; }

    public Integer getApiId() { return apiId; }

    public void setApiId(Integer apiId) { this.apiId = apiId; }

    public String getOuterKey() { return outerKey; }

    public void setOuterKey(String outerKey) { this.outerKey = outerKey; }

    public String getInnerKey() { return innerKey; }

    public void setInnerKey(String innerKey) { this.innerKey = innerKey; }

    public String getDataType() { return dataType; }

    public void setDataType(String dataType) { this.dataType = dataType; }

    public String getAnalysisFlag() { return analysisFlag; }

    public void setAnalysisFlag(String analysisFlag) { this.analysisFlag = analysisFlag; }

    public String getMonitorFlag() { return monitorFlag; }

    public void setMonitorFlag(String monitorFlag) { this.monitorFlag = monitorFlag; }

    public String getTargetTable() { return targetTable; }

    public void setTargetTable(String targetTable) { this.targetTable = targetTable; }

    public String getNormRule() { return normRule; }

    public void setNormRule(String normRule) { this.normRule = normRule; }

    public String getNotNullCheck() { return notNullCheck; }

    public void setNotNullCheck(String notNullCheck) { this.notNullCheck = notNullCheck; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getActiveBiz() { return activeBiz; }

    public void setActiveBiz(String activeBiz) { this.activeBiz = activeBiz; }

}
