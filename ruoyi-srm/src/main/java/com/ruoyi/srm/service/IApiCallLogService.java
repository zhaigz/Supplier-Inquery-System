package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.ApiCallLog;

public interface IApiCallLogService
{
    ApiCallLog selectApiCallLogById(Long id);
    List<ApiCallLog> selectApiCallLogList(ApiCallLog entity);
    int insertApiCallLog(ApiCallLog entity);
    int updateApiCallLog(ApiCallLog entity);
    int deleteApiCallLogById(Long id);
    int deleteApiCallLogByIds(Long[] ids);
}
