package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.SupplierShareholder;
import com.ruoyi.srm.mapper.SupplierShareholderMapper;
import com.ruoyi.srm.service.ISupplierShareholderService;

@Service
public class SupplierShareholderServiceImpl implements ISupplierShareholderService
{
    @Autowired
    private SupplierShareholderMapper SupplierShareholderMapper;

    public SupplierShareholder selectSupplierShareholderById(Long id) { return SupplierShareholderMapper.selectSupplierShareholderById(id); }
    public List<SupplierShareholder> selectSupplierShareholderList(SupplierShareholder entity) { return SupplierShareholderMapper.selectSupplierShareholderList(entity); }
    public int insertSupplierShareholder(SupplierShareholder entity) { return SupplierShareholderMapper.insertSupplierShareholder(entity); }
    public int updateSupplierShareholder(SupplierShareholder entity) { return SupplierShareholderMapper.updateSupplierShareholder(entity); }
    public int deleteSupplierShareholderById(Long id) { return SupplierShareholderMapper.deleteSupplierShareholderById(id); }
    public int deleteSupplierShareholderByIds(Long[] ids) { return SupplierShareholderMapper.deleteSupplierShareholderByIds(ids); }
}
