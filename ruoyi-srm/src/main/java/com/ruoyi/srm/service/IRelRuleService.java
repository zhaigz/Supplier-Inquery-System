package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.RelRule;

public interface IRelRuleService
{
    RelRule selectRelRuleById(Long id);
    List<RelRule> selectRelRuleList(RelRule entity);
    int insertRelRule(RelRule entity);
    int updateRelRule(RelRule entity);
    int deleteRelRuleById(Long id);
    int deleteRelRuleByIds(Long[] ids);
}
