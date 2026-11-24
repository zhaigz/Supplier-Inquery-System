package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.SupplierFormerName;

public interface SupplierFormerNameMapper
{
    SupplierFormerName selectSupplierFormerNameById(Long id);
    List<SupplierFormerName> selectSupplierFormerNameList(SupplierFormerName entity);
    int insertSupplierFormerName(SupplierFormerName entity);
    int updateSupplierFormerName(SupplierFormerName entity);
    int deleteSupplierFormerNameById(Long id);
    int deleteSupplierFormerNameByIds(Long[] ids);
}
