package com.ruoyi.srm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_api_call_log */
public class ApiCallLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 全链路号：一次项目分析会产生多条调用，用同一个 trace 串起来 */
    private String traceId;

    /** 关联 srm_analysis_task.id（异步任务发起的调用） */
    private Long taskId;

    /** 调用时间 */
    private Date callTime;

    /** 调用人（系统账号） */
    private Long userId;

    /** 调用人名称冗余，避免账号变更导致日志不可读 */
    private String userName;

    /** 所属部门（来自系统侧账号信息） */
    private Long deptId;

    /**  */
    private String deptName;

    /** 来源 IP（需求新增维度） */
    private String sourceIp;

    /** 终端标识：区分同一账号多终端 */
    private String terminalId;

    /** 接口编号 */
    private Integer apiId;

    /** 接口名称 */
    private String apiName;

    /** 调用范围/入参摘要（企业主体） */
    private String subjectKey;

    /** 入参摘要，不含敏感值 */
    private String paramDigest;

    /** 分页接口当前页 */
    private Integer pageNo;

    /** 调用结果：成功 / 失败 / 被拦截 */
    private String callResult;

    /** 被拦截原因：日预算超限/月预算超限/并发超限/套餐未含该接口/主体不存在 */
    private String blockReason;

    /**  */
    private String respMsg;

    /** 响应耗时 */
    private Integer costMs;

    /** 消耗调用单元（本条请求实际计费次数，缓存命中与拦截记 0） */
    private BigDecimal unitConsumed;

    /** 是否缓存命中：命中则不计量、不新发请求 */
    private String cacheHit;

    /** prod/test：测试额度调用单独统计，不占正式额度 */
    private String env;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTraceId() { return traceId; }

    public void setTraceId(String traceId) { this.traceId = traceId; }

    public Long getTaskId() { return taskId; }

    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public Date getCallTime() { return callTime; }

    public void setCallTime(Date callTime) { this.callTime = callTime; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public Long getDeptId() { return deptId; }

    public void setDeptId(Long deptId) { this.deptId = deptId; }

    public String getDeptName() { return deptName; }

    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getSourceIp() { return sourceIp; }

    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }

    public String getTerminalId() { return terminalId; }

    public void setTerminalId(String terminalId) { this.terminalId = terminalId; }

    public Integer getApiId() { return apiId; }

    public void setApiId(Integer apiId) { this.apiId = apiId; }

    public String getApiName() { return apiName; }

    public void setApiName(String apiName) { this.apiName = apiName; }

    public String getSubjectKey() { return subjectKey; }

    public void setSubjectKey(String subjectKey) { this.subjectKey = subjectKey; }

    public String getParamDigest() { return paramDigest; }

    public void setParamDigest(String paramDigest) { this.paramDigest = paramDigest; }

    public Integer getPageNo() { return pageNo; }

    public void setPageNo(Integer pageNo) { this.pageNo = pageNo; }

    public String getCallResult() { return callResult; }

    public void setCallResult(String callResult) { this.callResult = callResult; }

    public String getBlockReason() { return blockReason; }

    public void setBlockReason(String blockReason) { this.blockReason = blockReason; }

    public String getRespMsg() { return respMsg; }

    public void setRespMsg(String respMsg) { this.respMsg = respMsg; }

    public Integer getCostMs() { return costMs; }

    public void setCostMs(Integer costMs) { this.costMs = costMs; }

    public BigDecimal getUnitConsumed() { return unitConsumed; }

    public void setUnitConsumed(BigDecimal unitConsumed) { this.unitConsumed = unitConsumed; }

    public String getCacheHit() { return cacheHit; }

    public void setCacheHit(String cacheHit) { this.cacheHit = cacheHit; }

    public String getEnv() { return env; }

    public void setEnv(String env) { this.env = env; }

}
