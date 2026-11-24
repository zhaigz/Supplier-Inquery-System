package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.SupplierPerson;
import com.ruoyi.srm.mapper.SupplierPersonMapper;
import com.ruoyi.srm.service.ISupplierPersonService;

@Service
public class SupplierPersonServiceImpl implements ISupplierPersonService
{
    @Autowired
    private SupplierPersonMapper SupplierPersonMapper;

    public SupplierPerson selectSupplierPersonById(Long id) { return SupplierPersonMapper.selectSupplierPersonById(id); }
    public List<SupplierPerson> selectSupplierPersonList(SupplierPerson entity) { return SupplierPersonMapper.selectSupplierPersonList(entity); }
    public int insertSupplierPerson(SupplierPerson entity) { return SupplierPersonMapper.insertSupplierPerson(entity); }
    public int updateSupplierPerson(SupplierPerson entity) { return SupplierPersonMapper.updateSupplierPerson(entity); }
    public int deleteSupplierPersonById(Long id) { return SupplierPersonMapper.deleteSupplierPersonById(id); }
    public int deleteSupplierPersonByIds(Long[] ids) { return SupplierPersonMapper.deleteSupplierPersonByIds(ids); }
}
