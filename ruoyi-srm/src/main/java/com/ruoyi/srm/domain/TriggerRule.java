package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_trigger_rule */
public class TriggerRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 规则码：supplier_save / single_enrich / monitor_scan / project_analysis / repeat_query */
    private String ruleCode;

    /** 规则名（页面16 五条触发规则原文） */
    private String ruleName;

    /** never 不自动调用 / manual 经办人点击 / scoped 仅已勾选 / member 仅项目成员 / cache 缓存控制 */
    private String triggerMode;

    /** 允许调用的接口编号，逗号分隔；空表示由业务页面自带清单 */
    private String apiIds;

    /** 是否允许自动发起：0 否（需求默认）/ 1 是 */
    private String allowAuto;

    /** 启用状态 */
    private String enabled;

    /**  */
    private Integer sortNo;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getRuleCode() { return ruleCode; }

    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }

    public String getRuleName() { return ruleName; }

    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getTriggerMode() { return triggerMode; }

    public void setTriggerMode(String triggerMode) { this.triggerMode = triggerMode; }

    public String getApiIds() { return apiIds; }

    public void setApiIds(String apiIds) { this.apiIds = apiIds; }

    public String getAllowAuto() { return allowAuto; }

    public void setAllowAuto(String allowAuto) { this.allowAuto = allowAuto; }

    public String getEnabled() { return enabled; }

    public void setEnabled(String enabled) { this.enabled = enabled; }

    public Integer getSortNo() { return sortNo; }

    public void setSortNo(Integer sortNo) { this.sortNo = sortNo; }

}
