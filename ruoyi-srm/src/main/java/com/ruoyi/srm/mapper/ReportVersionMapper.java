package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.ReportVersion;

public interface ReportVersionMapper
{
    ReportVersion selectReportVersionById(Long id);
    List<ReportVersion> selectReportVersionList(ReportVersion entity);
    int insertReportVersion(ReportVersion entity);
    int updateReportVersion(ReportVersion entity);
    int deleteReportVersionById(Long id);
    int deleteReportVersionByIds(Long[] ids);
}
