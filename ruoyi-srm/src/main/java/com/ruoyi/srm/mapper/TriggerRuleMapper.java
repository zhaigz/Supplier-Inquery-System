package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.TriggerRule;

public interface TriggerRuleMapper
{
    TriggerRule selectTriggerRuleById(Long id);
    List<TriggerRule> selectTriggerRuleList(TriggerRule entity);
    int insertTriggerRule(TriggerRule entity);
    int updateTriggerRule(TriggerRule entity);
    int deleteTriggerRuleById(Long id);
    int deleteTriggerRuleByIds(Long[] ids);
}
