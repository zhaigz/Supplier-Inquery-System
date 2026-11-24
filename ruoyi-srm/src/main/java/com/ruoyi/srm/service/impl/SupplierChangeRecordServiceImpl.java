package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.SupplierChangeRecord;
import com.ruoyi.srm.mapper.SupplierChangeRecordMapper;
import com.ruoyi.srm.service.ISupplierChangeRecordService;

@Service
public class SupplierChangeRecordServiceImpl implements ISupplierChangeRecordService
{
    @Autowired
    private SupplierChangeRecordMapper SupplierChangeRecordMapper;

    public SupplierChangeRecord selectSupplierChangeRecordById(Long id) { return SupplierChangeRecordMapper.selectSupplierChangeRecordById(id); }
    public List<SupplierChangeRecord> selectSupplierChangeRecordList(SupplierChangeRecord entity) { return SupplierChangeRecordMapper.selectSupplierChangeRecordList(entity); }
    public int insertSupplierChangeRecord(SupplierChangeRecord entity) { return SupplierChangeRecordMapper.insertSupplierChangeRecord(entity); }
    public int updateSupplierChangeRecord(SupplierChangeRecord entity) { return SupplierChangeRecordMapper.updateSupplierChangeRecord(entity); }
    public int deleteSupplierChangeRecordById(Long id) { return SupplierChangeRecordMapper.deleteSupplierChangeRecordById(id); }
    public int deleteSupplierChangeRecordByIds(Long[] ids) { return SupplierChangeRecordMapper.deleteSupplierChangeRecordByIds(ids); }
}
