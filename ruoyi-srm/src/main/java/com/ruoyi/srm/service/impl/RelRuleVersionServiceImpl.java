package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.RelRuleVersion;
import com.ruoyi.srm.mapper.RelRuleVersionMapper;
import com.ruoyi.srm.service.IRelRuleVersionService;

@Service
public class RelRuleVersionServiceImpl implements IRelRuleVersionService
{
    @Autowired
    private RelRuleVersionMapper RelRuleVersionMapper;

    public RelRuleVersion selectRelRuleVersionById(Long id) { return RelRuleVersionMapper.selectRelRuleVersionById(id); }
    public List<RelRuleVersion> selectRelRuleVersionList(RelRuleVersion entity) { return RelRuleVersionMapper.selectRelRuleVersionList(entity); }
    public int insertRelRuleVersion(RelRuleVersion entity) { return RelRuleVersionMapper.insertRelRuleVersion(entity); }
    public int updateRelRuleVersion(RelRuleVersion entity) { return RelRuleVersionMapper.updateRelRuleVersion(entity); }
    public int deleteRelRuleVersionById(Long id) { return RelRuleVersionMapper.deleteRelRuleVersionById(id); }
    public int deleteRelRuleVersionByIds(Long[] ids) { return RelRuleVersionMapper.deleteRelRuleVersionByIds(ids); }
}
