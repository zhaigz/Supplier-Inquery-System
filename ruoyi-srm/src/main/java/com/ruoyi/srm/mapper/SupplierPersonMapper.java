package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.SupplierPerson;

public interface SupplierPersonMapper
{
    SupplierPerson selectSupplierPersonById(Long id);
    List<SupplierPerson> selectSupplierPersonList(SupplierPerson entity);
    int insertSupplierPerson(SupplierPerson entity);
    int updateSupplierPerson(SupplierPerson entity);
    int deleteSupplierPersonById(Long id);
    int deleteSupplierPersonByIds(Long[] ids);
}
