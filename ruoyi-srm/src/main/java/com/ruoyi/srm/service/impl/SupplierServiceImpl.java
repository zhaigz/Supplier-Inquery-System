package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.mapper.SupplierMapper;
import com.ruoyi.srm.service.ISupplierService;

@Service
public class SupplierServiceImpl implements ISupplierService
{
    @Autowired
    private SupplierMapper SupplierMapper;

    public Supplier selectSupplierById(Long id) { return SupplierMapper.selectSupplierById(id); }
    public List<Supplier> selectSupplierList(Supplier entity) { return SupplierMapper.selectSupplierList(entity); }
    public int insertSupplier(Supplier entity) { return SupplierMapper.insertSupplier(entity); }
    public int updateSupplier(Supplier entity) { return SupplierMapper.updateSupplier(entity); }
    public int deleteSupplierById(Long id) { return SupplierMapper.deleteSupplierById(id); }
    public int deleteSupplierByIds(Long[] ids) { return SupplierMapper.deleteSupplierByIds(ids); }
    public int updateSupplierMonitorFlag(Long[] ids, String monitorFlag) { return SupplierMapper.updateSupplierMonitorFlag(ids, monitorFlag); }
}
