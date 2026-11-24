package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.FieldMapping;

public interface FieldMappingMapper
{
    FieldMapping selectFieldMappingById(Long id);
    List<FieldMapping> selectFieldMappingList(FieldMapping entity);
    int insertFieldMapping(FieldMapping entity);
    int updateFieldMapping(FieldMapping entity);
    int deleteFieldMappingById(Long id);
    int deleteFieldMappingByIds(Long[] ids);
}
