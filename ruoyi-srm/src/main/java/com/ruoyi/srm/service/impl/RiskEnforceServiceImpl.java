package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.RiskEnforce;
import com.ruoyi.srm.mapper.RiskEnforceMapper;
import com.ruoyi.srm.service.IRiskEnforceService;

@Service
public class RiskEnforceServiceImpl implements IRiskEnforceService
{
    @Autowired
    private RiskEnforceMapper RiskEnforceMapper;

    public RiskEnforce selectRiskEnforceById(Long id) { return RiskEnforceMapper.selectRiskEnforceById(id); }
    public List<RiskEnforce> selectRiskEnforceList(RiskEnforce entity) { return RiskEnforceMapper.selectRiskEnforceList(entity); }
    public int insertRiskEnforce(RiskEnforce entity) { return RiskEnforceMapper.insertRiskEnforce(entity); }
    public int updateRiskEnforce(RiskEnforce entity) { return RiskEnforceMapper.updateRiskEnforce(entity); }
    public int deleteRiskEnforceById(Long id) { return RiskEnforceMapper.deleteRiskEnforceById(id); }
    public int deleteRiskEnforceByIds(Long[] ids) { return RiskEnforceMapper.deleteRiskEnforceByIds(ids); }
}
