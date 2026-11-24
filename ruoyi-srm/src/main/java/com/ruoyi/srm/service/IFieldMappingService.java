package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.FieldMapping;

public interface IFieldMappingService
{
    FieldMapping selectFieldMappingById(Long id);
    List<FieldMapping> selectFieldMappingList(FieldMapping entity);
    int insertFieldMapping(FieldMapping entity);
    int updateFieldMapping(FieldMapping entity);
    int deleteFieldMappingById(Long id);
    int deleteFieldMappingByIds(Long[] ids);
}
