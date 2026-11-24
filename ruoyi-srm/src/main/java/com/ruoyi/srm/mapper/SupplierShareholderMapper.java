package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.SupplierShareholder;

public interface SupplierShareholderMapper
{
    SupplierShareholder selectSupplierShareholderById(Long id);
    List<SupplierShareholder> selectSupplierShareholderList(SupplierShareholder entity);
    int insertSupplierShareholder(SupplierShareholder entity);
    int updateSupplierShareholder(SupplierShareholder entity);
    int deleteSupplierShareholderById(Long id);
    int deleteSupplierShareholderByIds(Long[] ids);
}
