package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.ApiRaw;

public interface IApiRawService
{
    ApiRaw selectApiRawById(Long id);
    List<ApiRaw> selectApiRawList(ApiRaw entity);
    int insertApiRaw(ApiRaw entity);
    int updateApiRaw(ApiRaw entity);
    int deleteApiRawById(Long id);
    int deleteApiRawByIds(Long[] ids);
}
