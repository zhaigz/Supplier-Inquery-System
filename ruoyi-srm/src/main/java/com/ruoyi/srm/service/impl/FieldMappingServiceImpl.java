package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.FieldMapping;
import com.ruoyi.srm.mapper.FieldMappingMapper;
import com.ruoyi.srm.service.IFieldMappingService;

@Service
public class FieldMappingServiceImpl implements IFieldMappingService
{
    @Autowired
    private FieldMappingMapper FieldMappingMapper;

    public FieldMapping selectFieldMappingById(Long id) { return FieldMappingMapper.selectFieldMappingById(id); }
    public List<FieldMapping> selectFieldMappingList(FieldMapping entity) { return FieldMappingMapper.selectFieldMappingList(entity); }
    public int insertFieldMapping(FieldMapping entity) { return FieldMappingMapper.insertFieldMapping(entity); }
    public int updateFieldMapping(FieldMapping entity) { return FieldMappingMapper.updateFieldMapping(entity); }
    public int deleteFieldMappingById(Long id) { return FieldMappingMapper.deleteFieldMappingById(id); }
    public int deleteFieldMappingByIds(Long[] ids) { return FieldMappingMapper.deleteFieldMappingByIds(ids); }
}
