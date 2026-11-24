package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.TriggerRule;
import com.ruoyi.srm.mapper.TriggerRuleMapper;
import com.ruoyi.srm.service.ITriggerRuleService;

@Service
public class TriggerRuleServiceImpl implements ITriggerRuleService
{
    @Autowired
    private TriggerRuleMapper TriggerRuleMapper;

    public TriggerRule selectTriggerRuleById(Long id) { return TriggerRuleMapper.selectTriggerRuleById(id); }
    public List<TriggerRule> selectTriggerRuleList(TriggerRule entity) { return TriggerRuleMapper.selectTriggerRuleList(entity); }
    public int insertTriggerRule(TriggerRule entity) { return TriggerRuleMapper.insertTriggerRule(entity); }
    public int updateTriggerRule(TriggerRule entity) { return TriggerRuleMapper.updateTriggerRule(entity); }
    public int deleteTriggerRuleById(Long id) { return TriggerRuleMapper.deleteTriggerRuleById(id); }
    public int deleteTriggerRuleByIds(Long[] ids) { return TriggerRuleMapper.deleteTriggerRuleByIds(ids); }
}
