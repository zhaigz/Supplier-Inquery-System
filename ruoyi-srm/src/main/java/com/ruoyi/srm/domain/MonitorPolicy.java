package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_monitor_policy */
public class MonitorPolicy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 策略码：biz_info / shareholder / legal_person / person / abnormal_punish / judicial / invest（7 项逐字取自原型 monitorTopics） */
    private String policyCode;

    /** 监控维度名：工商基本信息变更/股东及股权变更/法定代表人变更/主要人员变更/经营异常及行政处罚/司法风险/对外投资变更 */
    private String policyName;

    /** 支撑接口：822/458/821/819/848/1002 等；本期不接入的维度写 待确认 且默认关闭 */
    private String supportApi;

    /** 刷新频率：daily 每日一次 / weekly 每周一次 / manual 人工触发 / push 实时推送（需服务支持），默认人工触发 */
    private String frequency;

    /** 定时表达式（同步到 sys_job，由若依 Quartz 调度，不引新框架） */
    private String cronExpr;

    /** 启用：本期整体后置，默认 0；纪要口径：不采购付费监控接口，改按 3天/5天/1个月周期调基础接口 */
    private String enabled;

    /** 范围：monitor_flag 仅已勾选重点监控 / all 全库（禁止，字段保留仅为可观测） */
    private String scanScope;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPolicyCode() { return policyCode; }

    public void setPolicyCode(String policyCode) { this.policyCode = policyCode; }

    public String getPolicyName() { return policyName; }

    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public String getSupportApi() { return supportApi; }

    public void setSupportApi(String supportApi) { this.supportApi = supportApi; }

    public String getFrequency() { return frequency; }

    public void setFrequency(String frequency) { this.frequency = frequency; }

    public String getCronExpr() { return cronExpr; }

    public void setCronExpr(String cronExpr) { this.cronExpr = cronExpr; }

    public String getEnabled() { return enabled; }

    public void setEnabled(String enabled) { this.enabled = enabled; }

    public String getScanScope() { return scanScope; }

    public void setScanScope(String scanScope) { this.scanScope = scanScope; }

}
