package com.ruoyi.srm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_usage_stat */
public class UsageStat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 统计日期（按月统计时 stat_date 取当月 1 日，period_type=month） */
    private Date statDate;

    /** day / month */
    private String periodType;

    /** 接口编号，0 表示全部接口合计 */
    private Integer apiId;

    /** 发起调用次数 */
    private Integer callCount;

    /** 缓存命中次数（节省量） */
    private Integer cacheHitCount;

    /** 被拦截次数 */
    private Integer blockCount;

    /** 失败次数 */
    private Integer failCount;

    /** 消耗调用单元合计 */
    private BigDecimal unitConsumed;

    /** 按字典单价折算的金额，供对账，不落前端 */
    private BigDecimal costAmount;

    /** 平均响应耗时 */
    private Integer avgCostMs;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Date getStatDate() { return statDate; }

    public void setStatDate(Date statDate) { this.statDate = statDate; }

    public String getPeriodType() { return periodType; }

    public void setPeriodType(String periodType) { this.periodType = periodType; }

    public Integer getApiId() { return apiId; }

    public void setApiId(Integer apiId) { this.apiId = apiId; }

    public Integer getCallCount() { return callCount; }

    public void setCallCount(Integer callCount) { this.callCount = callCount; }

    public Integer getCacheHitCount() { return cacheHitCount; }

    public void setCacheHitCount(Integer cacheHitCount) { this.cacheHitCount = cacheHitCount; }

    public Integer getBlockCount() { return blockCount; }

    public void setBlockCount(Integer blockCount) { this.blockCount = blockCount; }

    public Integer getFailCount() { return failCount; }

    public void setFailCount(Integer failCount) { this.failCount = failCount; }

    public BigDecimal getUnitConsumed() { return unitConsumed; }

    public void setUnitConsumed(BigDecimal unitConsumed) { this.unitConsumed = unitConsumed; }

    public BigDecimal getCostAmount() { return costAmount; }

    public void setCostAmount(BigDecimal costAmount) { this.costAmount = costAmount; }

    public Integer getAvgCostMs() { return avgCostMs; }

    public void setAvgCostMs(Integer avgCostMs) { this.avgCostMs = avgCostMs; }

}
