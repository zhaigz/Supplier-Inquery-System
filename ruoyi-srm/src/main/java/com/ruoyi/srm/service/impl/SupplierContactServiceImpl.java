package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.SupplierContact;
import com.ruoyi.srm.mapper.SupplierContactMapper;
import com.ruoyi.srm.service.ISupplierContactService;

@Service
public class SupplierContactServiceImpl implements ISupplierContactService
{
    @Autowired
    private SupplierContactMapper SupplierContactMapper;

    public SupplierContact selectSupplierContactById(Long id) { return SupplierContactMapper.selectSupplierContactById(id); }
    public List<SupplierContact> selectSupplierContactList(SupplierContact entity) { return SupplierContactMapper.selectSupplierContactList(entity); }
    public int insertSupplierContact(SupplierContact entity) { return SupplierContactMapper.insertSupplierContact(entity); }
    public int updateSupplierContact(SupplierContact entity) { return SupplierContactMapper.updateSupplierContact(entity); }
    public int deleteSupplierContactById(Long id) { return SupplierContactMapper.deleteSupplierContactById(id); }
    public int deleteSupplierContactByIds(Long[] ids) { return SupplierContactMapper.deleteSupplierContactByIds(ids); }
}
