package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ReportVersion;
import com.ruoyi.srm.mapper.ReportVersionMapper;
import com.ruoyi.srm.service.IReportVersionService;

@Service
public class ReportVersionServiceImpl implements IReportVersionService
{
    @Autowired
    private ReportVersionMapper ReportVersionMapper;

    public ReportVersion selectReportVersionById(Long id) { return ReportVersionMapper.selectReportVersionById(id); }
    public List<ReportVersion> selectReportVersionList(ReportVersion entity) { return ReportVersionMapper.selectReportVersionList(entity); }
    public int insertReportVersion(ReportVersion entity) { return ReportVersionMapper.insertReportVersion(entity); }
    public int updateReportVersion(ReportVersion entity) { return ReportVersionMapper.updateReportVersion(entity); }
    public int deleteReportVersionById(Long id) { return ReportVersionMapper.deleteReportVersionById(id); }
    public int deleteReportVersionByIds(Long[] ids) { return ReportVersionMapper.deleteReportVersionByIds(ids); }
}
