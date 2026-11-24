package com.ruoyi.srm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_analysis_task */
public class AnalysisTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 任务号（页面6 留痕字段，前端轮询凭据） */
    private String taskNo;

    /** 同一次业务动作下的多任务归组 */
    private String traceId;

    /** equity 股权穿透图 / backstage 幕后关系 / shortest 最短路径 / project 项目关联分析 / monitor 监控比对 */
    private String taskType;

    /** 页面标识：page5/page6/page7/page8/page13 */
    private String bizScene;

    /** 项目单（项目分析必填，单主体查询为空） */
    private Long projectId;

    /** 产出的报告版本 */
    private Long reportVersionId;

    /** 目标主体（页面6 留痕） */
    private String subjectKey;

    /** 源主体（1161 源公司id/名，页面6 留痕） */
    private String sourceSubject;

    /** 目标主体（1161 目标公司id/名） */
    private String targetSubject;

    /** 关系口径：选中维度集合（页面6 留痕） */
    private String relScope;

    /** 穿透方向 up/down（1160 单向，一个需求方向拆两条任务） */
    private String direction;

    /** 深度/层数 1—10，区间外返回无数据 → 发起前校验并阻止，不静默截断 */
    private Integer depth;

    /** 比例下限 */
    private BigDecimal ratioMin;

    /** 比例上限 */
    private BigDecimal ratioMax;

    /** 本次使用的规则版本（留痕 + 复现） */
    private Integer ruleVersionNo;

    /** 规则最低权重（页面7：1 及以上…5） */
    private Integer minWeight;

    /** 是否包含代记账公司电话关系：1 包含 / 2 不包含（1156 入参） */
    private String includeBookkeeping;

    /** 状态机：QUEUED→RUNNING→SUCCESS / FAIL / CANCELED；超时写 FAIL + err_code=TASK_TIMEOUT */
    private String taskStatus;

    /** 前端轮询次数 */
    private Integer pollCount;

    /** 首次触发时间（纪要：首次触发不计费） */
    private Date firstTriggerAt;

    /** 计费时间（纪要：第二次拉取到终态才计费） */
    private Date billedAt;

    /** 本任务是否已产生计费 */
    private String billFlag;

    /** 本任务累计消耗调用单元 */
    private BigDecimal unitConsumed;

    /** 预估调用单元（本地估算，页面13 显示，非计费字段） */
    private BigDecimal estUnit;

    /** 当前步骤：STEP-{done}/{total}（如 STEP-3/10，总步数按任务类型固定） */
    private String progressStep;

    /** 失败码 */
    private String errCode;

    /** 失败原因（不透出敏感响应内容） */
    private String errMsg;

    /** 结果规模：节点数/线索数 */
    private Integer resultCount;

    /**  */
    private Date startTime;

    /**  */
    private Date finishTime;

    /**  */
    private Integer costMs;

    /**  */
    private String env;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTaskNo() { return taskNo; }

    public void setTaskNo(String taskNo) { this.taskNo = taskNo; }

    public String getTraceId() { return traceId; }

    public void setTraceId(String traceId) { this.traceId = traceId; }

    public String getTaskType() { return taskType; }

    public void setTaskType(String taskType) { this.taskType = taskType; }

    public String getBizScene() { return bizScene; }

    public void setBizScene(String bizScene) { this.bizScene = bizScene; }

    public Long getProjectId() { return projectId; }

    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getReportVersionId() { return reportVersionId; }

    public void setReportVersionId(Long reportVersionId) { this.reportVersionId = reportVersionId; }

    public String getSubjectKey() { return subjectKey; }

    public void setSubjectKey(String subjectKey) { this.subjectKey = subjectKey; }

    public String getSourceSubject() { return sourceSubject; }

    public void setSourceSubject(String sourceSubject) { this.sourceSubject = sourceSubject; }

    public String getTargetSubject() { return targetSubject; }

    public void setTargetSubject(String targetSubject) { this.targetSubject = targetSubject; }

    public String getRelScope() { return relScope; }

    public void setRelScope(String relScope) { this.relScope = relScope; }

    public String getDirection() { return direction; }

    public void setDirection(String direction) { this.direction = direction; }

    public Integer getDepth() { return depth; }

    public void setDepth(Integer depth) { this.depth = depth; }

    public BigDecimal getRatioMin() { return ratioMin; }

    public void setRatioMin(BigDecimal ratioMin) { this.ratioMin = ratioMin; }

    public BigDecimal getRatioMax() { return ratioMax; }

    public void setRatioMax(BigDecimal ratioMax) { this.ratioMax = ratioMax; }

    public Integer getRuleVersionNo() { return ruleVersionNo; }

    public void setRuleVersionNo(Integer ruleVersionNo) { this.ruleVersionNo = ruleVersionNo; }

    public Integer getMinWeight() { return minWeight; }

    public void setMinWeight(Integer minWeight) { this.minWeight = minWeight; }

    public String getIncludeBookkeeping() { return includeBookkeeping; }

    public void setIncludeBookkeeping(String includeBookkeeping) { this.includeBookkeeping = includeBookkeeping; }

    public String getTaskStatus() { return taskStatus; }

    public void setTaskStatus(String taskStatus) { this.taskStatus = taskStatus; }

    public Integer getPollCount() { return pollCount; }

    public void setPollCount(Integer pollCount) { this.pollCount = pollCount; }

    public Date getFirstTriggerAt() { return firstTriggerAt; }

    public void setFirstTriggerAt(Date firstTriggerAt) { this.firstTriggerAt = firstTriggerAt; }

    public Date getBilledAt() { return billedAt; }

    public void setBilledAt(Date billedAt) { this.billedAt = billedAt; }

    public String getBillFlag() { return billFlag; }

    public void setBillFlag(String billFlag) { this.billFlag = billFlag; }

    public BigDecimal getUnitConsumed() { return unitConsumed; }

    public void setUnitConsumed(BigDecimal unitConsumed) { this.unitConsumed = unitConsumed; }

    public BigDecimal getEstUnit() { return estUnit; }

    public void setEstUnit(BigDecimal estUnit) { this.estUnit = estUnit; }

    public String getProgressStep() { return progressStep; }

    public void setProgressStep(String progressStep) { this.progressStep = progressStep; }

    public String getErrCode() { return errCode; }

    public void setErrCode(String errCode) { this.errCode = errCode; }

    public String getErrMsg() { return errMsg; }

    public void setErrMsg(String errMsg) { this.errMsg = errMsg; }

    public Integer getResultCount() { return resultCount; }

    public void setResultCount(Integer resultCount) { this.resultCount = resultCount; }

    public Date getStartTime() { return startTime; }

    public void setStartTime(Date startTime) { this.startTime = startTime; }

    public Date getFinishTime() { return finishTime; }

    public void setFinishTime(Date finishTime) { this.finishTime = finishTime; }

    public Integer getCostMs() { return costMs; }

    public void setCostMs(Integer costMs) { this.costMs = costMs; }

    public String getEnv() { return env; }

    public void setEnv(String env) { this.env = env; }

}
