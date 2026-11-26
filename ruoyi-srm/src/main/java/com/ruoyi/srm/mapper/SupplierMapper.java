package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.Supplier;

public interface SupplierMapper
{
    Supplier selectSupplierById(Long id);
    List<Supplier> selectSupplierList(Supplier entity);
    int insertSupplier(Supplier entity);
    int updateSupplier(Supplier entity);
    int deleteSupplierById(Long id);
    int deleteSupplierByIds(Long[] ids);
    int updateSupplierMonitorFlag(@org.apache.ibatis.annotations.Param("ids") Long[] ids, @org.apache.ibatis.annotations.Param("monitorFlag") String monitorFlag);
}
