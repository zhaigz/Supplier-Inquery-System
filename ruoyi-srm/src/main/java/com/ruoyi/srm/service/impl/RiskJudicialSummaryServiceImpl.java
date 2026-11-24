package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.RiskJudicialSummary;
import com.ruoyi.srm.mapper.RiskJudicialSummaryMapper;
import com.ruoyi.srm.service.IRiskJudicialSummaryService;

@Service
public class RiskJudicialSummaryServiceImpl implements IRiskJudicialSummaryService
{
    @Autowired
    private RiskJudicialSummaryMapper RiskJudicialSummaryMapper;

    public RiskJudicialSummary selectRiskJudicialSummaryById(Long id) { return RiskJudicialSummaryMapper.selectRiskJudicialSummaryById(id); }
    public List<RiskJudicialSummary> selectRiskJudicialSummaryList(RiskJudicialSummary entity) { return RiskJudicialSummaryMapper.selectRiskJudicialSummaryList(entity); }
    public int insertRiskJudicialSummary(RiskJudicialSummary entity) { return RiskJudicialSummaryMapper.insertRiskJudicialSummary(entity); }
    public int updateRiskJudicialSummary(RiskJudicialSummary entity) { return RiskJudicialSummaryMapper.updateRiskJudicialSummary(entity); }
    public int deleteRiskJudicialSummaryById(Long id) { return RiskJudicialSummaryMapper.deleteRiskJudicialSummaryById(id); }
    public int deleteRiskJudicialSummaryByIds(Long[] ids) { return RiskJudicialSummaryMapper.deleteRiskJudicialSummaryByIds(ids); }
}
