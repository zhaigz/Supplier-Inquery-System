package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.RiskJudicialSummary;

public interface IRiskJudicialSummaryService
{
    RiskJudicialSummary selectRiskJudicialSummaryById(Long id);
    List<RiskJudicialSummary> selectRiskJudicialSummaryList(RiskJudicialSummary entity);
    int insertRiskJudicialSummary(RiskJudicialSummary entity);
    int updateRiskJudicialSummary(RiskJudicialSummary entity);
    int deleteRiskJudicialSummaryById(Long id);
    int deleteRiskJudicialSummaryByIds(Long[] ids);
}
