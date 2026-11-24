package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.RelRuleVersion;

public interface RelRuleVersionMapper
{
    RelRuleVersion selectRelRuleVersionById(Long id);
    List<RelRuleVersion> selectRelRuleVersionList(RelRuleVersion entity);
    int insertRelRuleVersion(RelRuleVersion entity);
    int updateRelRuleVersion(RelRuleVersion entity);
    int deleteRelRuleVersionById(Long id);
    int deleteRelRuleVersionByIds(Long[] ids);
}
