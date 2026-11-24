package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.AnalysisTask;
import com.ruoyi.srm.mapper.AnalysisTaskMapper;
import com.ruoyi.srm.service.IAnalysisTaskService;

@Service
public class AnalysisTaskServiceImpl implements IAnalysisTaskService
{
    @Autowired
    private AnalysisTaskMapper AnalysisTaskMapper;

    public AnalysisTask selectAnalysisTaskById(Long id) { return AnalysisTaskMapper.selectAnalysisTaskById(id); }
    public List<AnalysisTask> selectAnalysisTaskList(AnalysisTask entity) { return AnalysisTaskMapper.selectAnalysisTaskList(entity); }
    public int insertAnalysisTask(AnalysisTask entity) { return AnalysisTaskMapper.insertAnalysisTask(entity); }
    public int updateAnalysisTask(AnalysisTask entity) { return AnalysisTaskMapper.updateAnalysisTask(entity); }
    public int deleteAnalysisTaskById(Long id) { return AnalysisTaskMapper.deleteAnalysisTaskById(id); }
    public int deleteAnalysisTaskByIds(Long[] ids) { return AnalysisTaskMapper.deleteAnalysisTaskByIds(ids); }
}
