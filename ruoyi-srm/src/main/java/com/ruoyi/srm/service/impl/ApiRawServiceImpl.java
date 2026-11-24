package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ApiRaw;
import com.ruoyi.srm.mapper.ApiRawMapper;
import com.ruoyi.srm.service.IApiRawService;

@Service
public class ApiRawServiceImpl implements IApiRawService
{
    @Autowired
    private ApiRawMapper ApiRawMapper;

    public ApiRaw selectApiRawById(Long id) { return ApiRawMapper.selectApiRawById(id); }
    public List<ApiRaw> selectApiRawList(ApiRaw entity) { return ApiRawMapper.selectApiRawList(entity); }
    public int insertApiRaw(ApiRaw entity) { return ApiRawMapper.insertApiRaw(entity); }
    public int updateApiRaw(ApiRaw entity) { return ApiRawMapper.updateApiRaw(entity); }
    public int deleteApiRawById(Long id) { return ApiRawMapper.deleteApiRawById(id); }
    public int deleteApiRawByIds(Long[] ids) { return ApiRawMapper.deleteApiRawByIds(ids); }
}
