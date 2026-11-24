package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.SupplierExtField;
import com.ruoyi.srm.mapper.SupplierExtFieldMapper;
import com.ruoyi.srm.service.ISupplierExtFieldService;

@Service
public class SupplierExtFieldServiceImpl implements ISupplierExtFieldService
{
    @Autowired
    private SupplierExtFieldMapper SupplierExtFieldMapper;

    public SupplierExtField selectSupplierExtFieldById(Long id) { return SupplierExtFieldMapper.selectSupplierExtFieldById(id); }
    public List<SupplierExtField> selectSupplierExtFieldList(SupplierExtField entity) { return SupplierExtFieldMapper.selectSupplierExtFieldList(entity); }
    public int insertSupplierExtField(SupplierExtField entity) { return SupplierExtFieldMapper.insertSupplierExtField(entity); }
    public int updateSupplierExtField(SupplierExtField entity) { return SupplierExtFieldMapper.updateSupplierExtField(entity); }
    public int deleteSupplierExtFieldById(Long id) { return SupplierExtFieldMapper.deleteSupplierExtFieldById(id); }
    public int deleteSupplierExtFieldByIds(Long[] ids) { return SupplierExtFieldMapper.deleteSupplierExtFieldByIds(ids); }
}
