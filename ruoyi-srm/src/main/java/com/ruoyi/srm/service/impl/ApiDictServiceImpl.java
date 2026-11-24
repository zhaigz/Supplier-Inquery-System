package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ApiDict;
import com.ruoyi.srm.mapper.ApiDictMapper;
import com.ruoyi.srm.service.IApiDictService;

@Service
public class ApiDictServiceImpl implements IApiDictService
{
    @Autowired
    private ApiDictMapper ApiDictMapper;

    public ApiDict selectApiDictById(Long id) { return ApiDictMapper.selectApiDictById(id); }
    public List<ApiDict> selectApiDictList(ApiDict entity) { return ApiDictMapper.selectApiDictList(entity); }
    public int insertApiDict(ApiDict entity) { return ApiDictMapper.insertApiDict(entity); }
    public int updateApiDict(ApiDict entity) { return ApiDictMapper.updateApiDict(entity); }
    public int deleteApiDictById(Long id) { return ApiDictMapper.deleteApiDictById(id); }
    public int deleteApiDictByIds(Long[] ids) { return ApiDictMapper.deleteApiDictByIds(ids); }
}
