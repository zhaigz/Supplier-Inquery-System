package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.ApiCallLog;

public interface ApiCallLogMapper
{
    ApiCallLog selectApiCallLogById(Long id);
    List<ApiCallLog> selectApiCallLogList(ApiCallLog entity);
    int insertApiCallLog(ApiCallLog entity);
    int updateApiCallLog(ApiCallLog entity);
    int deleteApiCallLogById(Long id);
    int deleteApiCallLogByIds(Long[] ids);
}
