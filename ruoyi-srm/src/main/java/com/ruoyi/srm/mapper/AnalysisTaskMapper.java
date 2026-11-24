package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.AnalysisTask;

public interface AnalysisTaskMapper
{
    AnalysisTask selectAnalysisTaskById(Long id);
    List<AnalysisTask> selectAnalysisTaskList(AnalysisTask entity);
    int insertAnalysisTask(AnalysisTask entity);
    int updateAnalysisTask(AnalysisTask entity);
    int deleteAnalysisTaskById(Long id);
    int deleteAnalysisTaskByIds(Long[] ids);
}
