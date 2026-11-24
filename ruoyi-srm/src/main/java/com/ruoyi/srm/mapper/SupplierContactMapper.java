package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.SupplierContact;

public interface SupplierContactMapper
{
    SupplierContact selectSupplierContactById(Long id);
    List<SupplierContact> selectSupplierContactList(SupplierContact entity);
    int insertSupplierContact(SupplierContact entity);
    int updateSupplierContact(SupplierContact entity);
    int deleteSupplierContactById(Long id);
    int deleteSupplierContactByIds(Long[] ids);
}
