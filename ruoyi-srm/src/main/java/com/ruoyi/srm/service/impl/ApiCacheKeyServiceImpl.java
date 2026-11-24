package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ApiCacheKey;
import com.ruoyi.srm.mapper.ApiCacheKeyMapper;
import com.ruoyi.srm.service.IApiCacheKeyService;

@Service
public class ApiCacheKeyServiceImpl implements IApiCacheKeyService
{
    @Autowired
    private ApiCacheKeyMapper ApiCacheKeyMapper;

    public ApiCacheKey selectApiCacheKeyById(Long id) { return ApiCacheKeyMapper.selectApiCacheKeyById(id); }
    public List<ApiCacheKey> selectApiCacheKeyList(ApiCacheKey entity) { return ApiCacheKeyMapper.selectApiCacheKeyList(entity); }
    public int insertApiCacheKey(ApiCacheKey entity) { return ApiCacheKeyMapper.insertApiCacheKey(entity); }
    public int updateApiCacheKey(ApiCacheKey entity) { return ApiCacheKeyMapper.updateApiCacheKey(entity); }
    public int deleteApiCacheKeyById(Long id) { return ApiCacheKeyMapper.deleteApiCacheKeyById(id); }
    public int deleteApiCacheKeyByIds(Long[] ids) { return ApiCacheKeyMapper.deleteApiCacheKeyByIds(ids); }
}
