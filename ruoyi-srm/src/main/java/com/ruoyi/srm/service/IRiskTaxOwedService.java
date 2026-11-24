package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.RiskTaxOwed;

public interface IRiskTaxOwedService
{
    RiskTaxOwed selectRiskTaxOwedById(Long id);
    List<RiskTaxOwed> selectRiskTaxOwedList(RiskTaxOwed entity);
    int insertRiskTaxOwed(RiskTaxOwed entity);
    int updateRiskTaxOwed(RiskTaxOwed entity);
    int deleteRiskTaxOwedById(Long id);
    int deleteRiskTaxOwedByIds(Long[] ids);
}
