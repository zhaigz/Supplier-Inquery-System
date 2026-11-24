package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ApiCallLog;
import com.ruoyi.srm.mapper.ApiCallLogMapper;
import com.ruoyi.srm.service.IApiCallLogService;

@Service
public class ApiCallLogServiceImpl implements IApiCallLogService
{
    @Autowired
    private ApiCallLogMapper ApiCallLogMapper;

    public ApiCallLog selectApiCallLogById(Long id) { return ApiCallLogMapper.selectApiCallLogById(id); }
    public List<ApiCallLog> selectApiCallLogList(ApiCallLog entity) { return ApiCallLogMapper.selectApiCallLogList(entity); }
    public int insertApiCallLog(ApiCallLog entity) { return ApiCallLogMapper.insertApiCallLog(entity); }
    public int updateApiCallLog(ApiCallLog entity) { return ApiCallLogMapper.updateApiCallLog(entity); }
    public int deleteApiCallLogById(Long id) { return ApiCallLogMapper.deleteApiCallLogById(id); }
    public int deleteApiCallLogByIds(Long[] ids) { return ApiCallLogMapper.deleteApiCallLogByIds(ids); }
}
