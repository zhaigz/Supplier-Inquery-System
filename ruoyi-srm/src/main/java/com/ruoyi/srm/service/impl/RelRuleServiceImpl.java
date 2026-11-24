package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.RelRule;
import com.ruoyi.srm.mapper.RelRuleMapper;
import com.ruoyi.srm.service.IRelRuleService;

@Service
public class RelRuleServiceImpl implements IRelRuleService
{
    @Autowired
    private RelRuleMapper RelRuleMapper;

    public RelRule selectRelRuleById(Long id) { return RelRuleMapper.selectRelRuleById(id); }
    public List<RelRule> selectRelRuleList(RelRule entity) { return RelRuleMapper.selectRelRuleList(entity); }
    public int insertRelRule(RelRule entity) { return RelRuleMapper.insertRelRule(entity); }
    public int updateRelRule(RelRule entity) { return RelRuleMapper.updateRelRule(entity); }
    public int deleteRelRuleById(Long id) { return RelRuleMapper.deleteRelRuleById(id); }
    public int deleteRelRuleByIds(Long[] ids) { return RelRuleMapper.deleteRelRuleByIds(ids); }
}
