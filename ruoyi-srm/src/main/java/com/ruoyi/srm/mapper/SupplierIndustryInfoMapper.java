package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.SupplierIndustryInfo;

public interface SupplierIndustryInfoMapper
{
    SupplierIndustryInfo selectSupplierIndustryInfoById(Long id);
    List<SupplierIndustryInfo> selectSupplierIndustryInfoList(SupplierIndustryInfo entity);
    int insertSupplierIndustryInfo(SupplierIndustryInfo entity);
    int updateSupplierIndustryInfo(SupplierIndustryInfo entity);
    int deleteSupplierIndustryInfoById(Long id);
    int deleteSupplierIndustryInfoByIds(Long[] ids);
}
