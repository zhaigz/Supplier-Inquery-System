package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.ApiCacheKey;

public interface ApiCacheKeyMapper
{
    ApiCacheKey selectApiCacheKeyById(Long id);
    List<ApiCacheKey> selectApiCacheKeyList(ApiCacheKey entity);
    int insertApiCacheKey(ApiCacheKey entity);
    int updateApiCacheKey(ApiCacheKey entity);
    int deleteApiCacheKeyById(Long id);
    int deleteApiCacheKeyByIds(Long[] ids);
}
