package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.RiskDishonest;

public interface IRiskDishonestService
{
    RiskDishonest selectRiskDishonestById(Long id);
    List<RiskDishonest> selectRiskDishonestList(RiskDishonest entity);
    int insertRiskDishonest(RiskDishonest entity);
    int updateRiskDishonest(RiskDishonest entity);
    int deleteRiskDishonestById(Long id);
    int deleteRiskDishonestByIds(Long[] ids);
}
