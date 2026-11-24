package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.ApiCacheKey;

public interface IApiCacheKeyService
{
    ApiCacheKey selectApiCacheKeyById(Long id);
    List<ApiCacheKey> selectApiCacheKeyList(ApiCacheKey entity);
    int insertApiCacheKey(ApiCacheKey entity);
    int updateApiCacheKey(ApiCacheKey entity);
    int deleteApiCacheKeyById(Long id);
    int deleteApiCacheKeyByIds(Long[] ids);
}
