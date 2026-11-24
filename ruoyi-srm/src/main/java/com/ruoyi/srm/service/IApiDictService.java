package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.ApiDict;

public interface IApiDictService
{
    ApiDict selectApiDictById(Long id);
    List<ApiDict> selectApiDictList(ApiDict entity);
    int insertApiDict(ApiDict entity);
    int updateApiDict(ApiDict entity);
    int deleteApiDictById(Long id);
    int deleteApiDictByIds(Long[] ids);
}
