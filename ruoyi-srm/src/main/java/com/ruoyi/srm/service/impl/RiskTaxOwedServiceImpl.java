package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.RiskTaxOwed;
import com.ruoyi.srm.mapper.RiskTaxOwedMapper;
import com.ruoyi.srm.service.IRiskTaxOwedService;

@Service
public class RiskTaxOwedServiceImpl implements IRiskTaxOwedService
{
    @Autowired
    private RiskTaxOwedMapper RiskTaxOwedMapper;

    public RiskTaxOwed selectRiskTaxOwedById(Long id) { return RiskTaxOwedMapper.selectRiskTaxOwedById(id); }
    public List<RiskTaxOwed> selectRiskTaxOwedList(RiskTaxOwed entity) { return RiskTaxOwedMapper.selectRiskTaxOwedList(entity); }
    public int insertRiskTaxOwed(RiskTaxOwed entity) { return RiskTaxOwedMapper.insertRiskTaxOwed(entity); }
    public int updateRiskTaxOwed(RiskTaxOwed entity) { return RiskTaxOwedMapper.updateRiskTaxOwed(entity); }
    public int deleteRiskTaxOwedById(Long id) { return RiskTaxOwedMapper.deleteRiskTaxOwedById(id); }
    public int deleteRiskTaxOwedByIds(Long[] ids) { return RiskTaxOwedMapper.deleteRiskTaxOwedByIds(ids); }
}
