package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.SupplierExtField;

public interface SupplierExtFieldMapper
{
    SupplierExtField selectSupplierExtFieldById(Long id);
    List<SupplierExtField> selectSupplierExtFieldList(SupplierExtField entity);
    int insertSupplierExtField(SupplierExtField entity);
    int updateSupplierExtField(SupplierExtField entity);
    int deleteSupplierExtFieldById(Long id);
    int deleteSupplierExtFieldByIds(Long[] ids);
}
