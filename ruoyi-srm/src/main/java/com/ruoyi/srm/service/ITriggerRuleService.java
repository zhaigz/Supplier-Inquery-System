package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.TriggerRule;

public interface ITriggerRuleService
{
    TriggerRule selectTriggerRuleById(Long id);
    List<TriggerRule> selectTriggerRuleList(TriggerRule entity);
    int insertTriggerRule(TriggerRule entity);
    int updateTriggerRule(TriggerRule entity);
    int deleteTriggerRuleById(Long id);
    int deleteTriggerRuleByIds(Long[] ids);
}
