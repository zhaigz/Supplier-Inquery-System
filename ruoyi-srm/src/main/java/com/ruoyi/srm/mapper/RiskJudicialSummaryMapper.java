package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.RiskJudicialSummary;

public interface RiskJudicialSummaryMapper
{
    RiskJudicialSummary selectRiskJudicialSummaryById(Long id);
    List<RiskJudicialSummary> selectRiskJudicialSummaryList(RiskJudicialSummary entity);
    int insertRiskJudicialSummary(RiskJudicialSummary entity);
    int updateRiskJudicialSummary(RiskJudicialSummary entity);
    int deleteRiskJudicialSummaryById(Long id);
    int deleteRiskJudicialSummaryByIds(Long[] ids);
}
