package com.ruoyi.srm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_graph_edge */
public class GraphEdge extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long taskId;

    /** 连线唯一标识：1161 关系id，用于去重 */
    private String edgeKey;

    /** 开始节点 / 起始节点 */
    private String fromNodeKey;

    /** 结束节点 */
    private String toNodeKey;

    /** 开始节点（对应节点idNew字段）原文映射 */
    private String fromIdNew;

    /** 结束节点（对应节点idNew字段）原文映射 */
    private String toIdNew;

    /** 类型：SERVE_ALL-董监高 / INVEST-股东 / BRANCH-分支机构 / HISTORICAL_SHAREHOLDER-历史股东 / HISTORICAL_SERVE_ALL-历史董监高 / ALL-所有类型（默认） */
    private String relType;

    /** 占比（股东关系）/ 股份比例 / 占比：按来源接口分列存 source_api_id */
    private BigDecimal ratio;

    /** 连线标注原文（不同接口同叫占比，界面须逐字区分） */
    private String ratioLabel;

    /** 关系发现标识（1161/1141） */
    private String foundFlag;

    /** 本地规则权重 1—5（非接口字段，页面7 连线标注组成部分） */
    private Integer weight;

    /** 命中的本地规则 srm_rel_rule.id */
    private Long ruleId;

    /** 命中的线索 srm_rel_clue.id（连线=启用规则命中的关联线索） */
    private Long clueId;

    /**  */
    private Integer sourceApiId;

    /**  */
    private Long rawId;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getTaskId() { return taskId; }

    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public String getEdgeKey() { return edgeKey; }

    public void setEdgeKey(String edgeKey) { this.edgeKey = edgeKey; }

    public String getFromNodeKey() { return fromNodeKey; }

    public void setFromNodeKey(String fromNodeKey) { this.fromNodeKey = fromNodeKey; }

    public String getToNodeKey() { return toNodeKey; }

    public void setToNodeKey(String toNodeKey) { this.toNodeKey = toNodeKey; }

    public String getFromIdNew() { return fromIdNew; }

    public void setFromIdNew(String fromIdNew) { this.fromIdNew = fromIdNew; }

    public String getToIdNew() { return toIdNew; }

    public void setToIdNew(String toIdNew) { this.toIdNew = toIdNew; }

    public String getRelType() { return relType; }

    public void setRelType(String relType) { this.relType = relType; }

    public BigDecimal getRatio() { return ratio; }

    public void setRatio(BigDecimal ratio) { this.ratio = ratio; }

    public String getRatioLabel() { return ratioLabel; }

    public void setRatioLabel(String ratioLabel) { this.ratioLabel = ratioLabel; }

    public String getFoundFlag() { return foundFlag; }

    public void setFoundFlag(String foundFlag) { this.foundFlag = foundFlag; }

    public Integer getWeight() { return weight; }

    public void setWeight(Integer weight) { this.weight = weight; }

    public Long getRuleId() { return ruleId; }

    public void setRuleId(Long ruleId) { this.ruleId = ruleId; }

    public Long getClueId() { return clueId; }

    public void setClueId(Long clueId) { this.clueId = clueId; }

    public Integer getSourceApiId() { return sourceApiId; }

    public void setSourceApiId(Integer sourceApiId) { this.sourceApiId = sourceApiId; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

}
