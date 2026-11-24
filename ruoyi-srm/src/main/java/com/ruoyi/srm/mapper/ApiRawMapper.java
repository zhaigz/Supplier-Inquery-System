package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.ApiRaw;

public interface ApiRawMapper
{
    ApiRaw selectApiRawById(Long id);
    List<ApiRaw> selectApiRawList(ApiRaw entity);
    int insertApiRaw(ApiRaw entity);
    int updateApiRaw(ApiRaw entity);
    int deleteApiRawById(Long id);
    int deleteApiRawByIds(Long[] ids);
}
