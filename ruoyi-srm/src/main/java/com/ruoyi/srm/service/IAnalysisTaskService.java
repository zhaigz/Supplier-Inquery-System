package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.AnalysisTask;

public interface IAnalysisTaskService
{
    AnalysisTask selectAnalysisTaskById(Long id);
    List<AnalysisTask> selectAnalysisTaskList(AnalysisTask entity);
    int insertAnalysisTask(AnalysisTask entity);
    int updateAnalysisTask(AnalysisTask entity);
    int deleteAnalysisTaskById(Long id);
    int deleteAnalysisTaskByIds(Long[] ids);
}
