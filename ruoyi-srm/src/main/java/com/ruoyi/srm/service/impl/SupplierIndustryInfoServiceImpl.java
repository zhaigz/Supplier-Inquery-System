package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.SupplierIndustryInfo;
import com.ruoyi.srm.mapper.SupplierIndustryInfoMapper;
import com.ruoyi.srm.service.ISupplierIndustryInfoService;

@Service
public class SupplierIndustryInfoServiceImpl implements ISupplierIndustryInfoService
{
    @Autowired
    private SupplierIndustryInfoMapper SupplierIndustryInfoMapper;

    public SupplierIndustryInfo selectSupplierIndustryInfoById(Long id) { return SupplierIndustryInfoMapper.selectSupplierIndustryInfoById(id); }
    public List<SupplierIndustryInfo> selectSupplierIndustryInfoList(SupplierIndustryInfo entity) { return SupplierIndustryInfoMapper.selectSupplierIndustryInfoList(entity); }
    public int insertSupplierIndustryInfo(SupplierIndustryInfo entity) { return SupplierIndustryInfoMapper.insertSupplierIndustryInfo(entity); }
    public int updateSupplierIndustryInfo(SupplierIndustryInfo entity) { return SupplierIndustryInfoMapper.updateSupplierIndustryInfo(entity); }
    public int deleteSupplierIndustryInfoById(Long id) { return SupplierIndustryInfoMapper.deleteSupplierIndustryInfoById(id); }
    public int deleteSupplierIndustryInfoByIds(Long[] ids) { return SupplierIndustryInfoMapper.deleteSupplierIndustryInfoByIds(ids); }
}
