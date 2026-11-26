package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.Supplier;

public interface ISupplierService
{
    Supplier selectSupplierById(Long id);
    List<Supplier> selectSupplierList(Supplier entity);
    int insertSupplier(Supplier entity);
    int updateSupplier(Supplier entity);
    int deleteSupplierById(Long id);
    int deleteSupplierByIds(Long[] ids);
    int updateSupplierMonitorFlag(Long[] ids, String monitorFlag);
}
