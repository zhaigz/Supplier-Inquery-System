package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_graph_node */
public class GraphNode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 归属任务（图谱结果按任务/版本留档，不同任务节点互不复用 id） */
    private Long taskId;

    /** 节点唯一标识：接口 节点id/节点图id 或 公司或人id/自然人pid */
    private String nodeKey;

    /** Company 公司 / Human 人（1160「对象类型 Company-公司, Human-人」、1161 同名字段）/ Other 其他 */
    private String nodeType;

    /** 节点名 / 节点名称（两字段来源不同时以返回顺序记录，不擅自合并） */
    private String nodeName;

    /** 统一社会信用代码（1160） */
    private String creditCode;

    /** 注册状态 / 企业状态（1160/1161），注销吊销打角标 */
    private String regStatus;

    /** 层级，逐层展开定位用 */
    private Integer levelNo;

    /** 上一层节点（1160 子对象递归还原父子链） */
    private String parentNodeKey;

    /** true-可延伸 false-不可延伸（1160 原文字段名逐字保留），仅 true 显示展开入口 */
    private String canExtend;

    /** 是否标红（1123），控制人/重点监控节点高亮 */
    private String redFlag;

    /** 是否重点监控供应商（本地主档 join，图例区分） */
    private String isMonitor;

    /** 命中本地主档时的 id */
    private Long supplierId;

    /** 节点聚合（1161 原文重复出现 2 次，按返回顺序记 ext_json.group_seq） */
    private String groupKey;

    /** 属性信息 / 属性（接口原样，详情面板读） */
    private String attrsJson;

    /** 其它未固化字段 */
    private String extJson;

    /**  */
    private Integer sourceApiId;

    /**  */
    private Long rawId;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getTaskId() { return taskId; }

    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public String getNodeKey() { return nodeKey; }

    public void setNodeKey(String nodeKey) { this.nodeKey = nodeKey; }

    public String getNodeType() { return nodeType; }

    public void setNodeType(String nodeType) { this.nodeType = nodeType; }

    public String getNodeName() { return nodeName; }

    public void setNodeName(String nodeName) { this.nodeName = nodeName; }

    public String getCreditCode() { return creditCode; }

    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }

    public String getRegStatus() { return regStatus; }

    public void setRegStatus(String regStatus) { this.regStatus = regStatus; }

    public Integer getLevelNo() { return levelNo; }

    public void setLevelNo(Integer levelNo) { this.levelNo = levelNo; }

    public String getParentNodeKey() { return parentNodeKey; }

    public void setParentNodeKey(String parentNodeKey) { this.parentNodeKey = parentNodeKey; }

    public String getCanExtend() { return canExtend; }

    public void setCanExtend(String canExtend) { this.canExtend = canExtend; }

    public String getRedFlag() { return redFlag; }

    public void setRedFlag(String redFlag) { this.redFlag = redFlag; }

    public String getIsMonitor() { return isMonitor; }

    public void setIsMonitor(String isMonitor) { this.isMonitor = isMonitor; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getGroupKey() { return groupKey; }

    public void setGroupKey(String groupKey) { this.groupKey = groupKey; }

    public String getAttrsJson() { return attrsJson; }

    public void setAttrsJson(String attrsJson) { this.attrsJson = attrsJson; }

    public String getExtJson() { return extJson; }

    public void setExtJson(String extJson) { this.extJson = extJson; }

    public Integer getSourceApiId() { return sourceApiId; }

    public void setSourceApiId(Integer sourceApiId) { this.sourceApiId = sourceApiId; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

}
