package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.RiskEnforce;

public interface IRiskEnforceService
{
    RiskEnforce selectRiskEnforceById(Long id);
    List<RiskEnforce> selectRiskEnforceList(RiskEnforce entity);
    int insertRiskEnforce(RiskEnforce entity);
    int updateRiskEnforce(RiskEnforce entity);
    int deleteRiskEnforceById(Long id);
    int deleteRiskEnforceByIds(Long[] ids);
}
