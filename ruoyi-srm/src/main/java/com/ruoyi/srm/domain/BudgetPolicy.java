package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_budget_policy */
public class BudgetPolicy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 策略名称（预留多策略，按环境/部门） */
    private String policyName;

    /** 每日调用预算上限（次），0 表示不限制；数值由采购方给定 */
    private Integer dailyLimit;

    /** 月度调用预算上限（次） */
    private Integer monthlyLimit;

    /** 预算预警阈值档位，多选逗号分隔，取值 60/70/80/90 */
    private String warnRatios;

    /** 并发数（同一时刻允许的外部请求条数），以采购合同为准，先给保守值 */
    private Integer maxConcurrency;

    /** 自动全量查询：0 关闭（需求默认，且不允许改 1）/ 1 开启 */
    private String autoFullScan;

    /** 缓存有效期（秒），页面3 缓存有效期对应项 */
    private Integer cacheTtlSec;

    /** 重点供应商监控频率：daily/weekly/manual/push（默认人工触发） */
    private String monitorFrequency;

    /** 监控企业上限（家），0 表示不额外限制，以合同为准 */
    private Integer monitorMaxEnt;

    /** 0 启用 / 1 停用 */
    private String status;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPolicyName() { return policyName; }

    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public Integer getDailyLimit() { return dailyLimit; }

    public void setDailyLimit(Integer dailyLimit) { this.dailyLimit = dailyLimit; }

    public Integer getMonthlyLimit() { return monthlyLimit; }

    public void setMonthlyLimit(Integer monthlyLimit) { this.monthlyLimit = monthlyLimit; }

    public String getWarnRatios() { return warnRatios; }

    public void setWarnRatios(String warnRatios) { this.warnRatios = warnRatios; }

    public Integer getMaxConcurrency() { return maxConcurrency; }

    public void setMaxConcurrency(Integer maxConcurrency) { this.maxConcurrency = maxConcurrency; }

    public String getAutoFullScan() { return autoFullScan; }

    public void setAutoFullScan(String autoFullScan) { this.autoFullScan = autoFullScan; }

    public Integer getCacheTtlSec() { return cacheTtlSec; }

    public void setCacheTtlSec(Integer cacheTtlSec) { this.cacheTtlSec = cacheTtlSec; }

    public String getMonitorFrequency() { return monitorFrequency; }

    public void setMonitorFrequency(String monitorFrequency) { this.monitorFrequency = monitorFrequency; }

    public Integer getMonitorMaxEnt() { return monitorMaxEnt; }

    public void setMonitorMaxEnt(Integer monitorMaxEnt) { this.monitorMaxEnt = monitorMaxEnt; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

}
