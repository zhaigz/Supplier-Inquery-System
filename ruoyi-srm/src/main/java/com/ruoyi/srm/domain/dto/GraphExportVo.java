package com.ruoyi.srm.domain.dto;

import com.ruoyi.common.annotation.Excel;

/** 图谱节点/连线导出行。 */
public class GraphExportVo
{
    @Excel(name = "任务号")
    private String taskNo;
    @Excel(name = "记录类型")
    private String itemType;
    @Excel(name = "节点标识")
    private String nodeKey;
    @Excel(name = "节点类型")
    private String nodeType;
    @Excel(name = "节点名称")
    private String nodeName;
    @Excel(name = "统一社会信用代码")
    private String creditCode;
    @Excel(name = "层级")
    private Integer levelNo;
    @Excel(name = "经营状态")
    private String regStatus;
    @Excel(name = "起点")
    private String fromNodeKey;
    @Excel(name = "终点")
    private String toNodeKey;
    @Excel(name = "关系类型")
    private String relType;
    @Excel(name = "比例")
    private String ratio;
    @Excel(name = "比例标签")
    private String ratioLabel;
    @Excel(name = "共同指向")
    private String sharedTarget;
    @Excel(name = "对齐结果")
    private String alignResult;

    public String getTaskNo() { return taskNo; }
    public void setTaskNo(String taskNo) { this.taskNo = taskNo; }
    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public String getNodeKey() { return nodeKey; }
    public void setNodeKey(String nodeKey) { this.nodeKey = nodeKey; }
    public String getNodeType() { return nodeType; }
    public void setNodeType(String nodeType) { this.nodeType = nodeType; }
    public String getNodeName() { return nodeName; }
    public void setNodeName(String nodeName) { this.nodeName = nodeName; }
    public String getCreditCode() { return creditCode; }
    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }
    public Integer getLevelNo() { return levelNo; }
    public void setLevelNo(Integer levelNo) { this.levelNo = levelNo; }
    public String getRegStatus() { return regStatus; }
    public void setRegStatus(String regStatus) { this.regStatus = regStatus; }
    public String getFromNodeKey() { return fromNodeKey; }
    public void setFromNodeKey(String fromNodeKey) { this.fromNodeKey = fromNodeKey; }
    public String getToNodeKey() { return toNodeKey; }
    public void setToNodeKey(String toNodeKey) { this.toNodeKey = toNodeKey; }
    public String getRelType() { return relType; }
    public void setRelType(String relType) { this.relType = relType; }
    public String getRatio() { return ratio; }
    public void setRatio(String ratio) { this.ratio = ratio; }
    public String getRatioLabel() { return ratioLabel; }
    public void setRatioLabel(String ratioLabel) { this.ratioLabel = ratioLabel; }
    public String getSharedTarget() { return sharedTarget; }
    public void setSharedTarget(String sharedTarget) { this.sharedTarget = sharedTarget; }
    public String getAlignResult() { return alignResult; }
    public void setAlignResult(String alignResult) { this.alignResult = alignResult; }
}