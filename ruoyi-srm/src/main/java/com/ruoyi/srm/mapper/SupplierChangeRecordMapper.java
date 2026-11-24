package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.SupplierChangeRecord;

public interface SupplierChangeRecordMapper
{
    SupplierChangeRecord selectSupplierChangeRecordById(Long id);
    List<SupplierChangeRecord> selectSupplierChangeRecordList(SupplierChangeRecord entity);
    int insertSupplierChangeRecord(SupplierChangeRecord entity);
    int updateSupplierChangeRecord(SupplierChangeRecord entity);
    int deleteSupplierChangeRecordById(Long id);
    int deleteSupplierChangeRecordByIds(Long[] ids);
}
