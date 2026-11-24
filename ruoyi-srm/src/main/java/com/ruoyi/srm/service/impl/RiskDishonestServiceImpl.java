package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.RiskDishonest;
import com.ruoyi.srm.mapper.RiskDishonestMapper;
import com.ruoyi.srm.service.IRiskDishonestService;

@Service
public class RiskDishonestServiceImpl implements IRiskDishonestService
{
    @Autowired
    private RiskDishonestMapper RiskDishonestMapper;

    public RiskDishonest selectRiskDishonestById(Long id) { return RiskDishonestMapper.selectRiskDishonestById(id); }
    public List<RiskDishonest> selectRiskDishonestList(RiskDishonest entity) { return RiskDishonestMapper.selectRiskDishonestList(entity); }
    public int insertRiskDishonest(RiskDishonest entity) { return RiskDishonestMapper.insertRiskDishonest(entity); }
    public int updateRiskDishonest(RiskDishonest entity) { return RiskDishonestMapper.updateRiskDishonest(entity); }
    public int deleteRiskDishonestById(Long id) { return RiskDishonestMapper.deleteRiskDishonestById(id); }
    public int deleteRiskDishonestByIds(Long[] ids) { return RiskDishonestMapper.deleteRiskDishonestByIds(ids); }
}
