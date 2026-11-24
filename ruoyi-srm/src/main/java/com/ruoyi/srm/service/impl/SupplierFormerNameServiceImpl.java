package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.SupplierFormerName;
import com.ruoyi.srm.mapper.SupplierFormerNameMapper;
import com.ruoyi.srm.service.ISupplierFormerNameService;

@Service
public class SupplierFormerNameServiceImpl implements ISupplierFormerNameService
{
    @Autowired
    private SupplierFormerNameMapper SupplierFormerNameMapper;

    public SupplierFormerName selectSupplierFormerNameById(Long id) { return SupplierFormerNameMapper.selectSupplierFormerNameById(id); }
    public List<SupplierFormerName> selectSupplierFormerNameList(SupplierFormerName entity) { return SupplierFormerNameMapper.selectSupplierFormerNameList(entity); }
    public int insertSupplierFormerName(SupplierFormerName entity) { return SupplierFormerNameMapper.insertSupplierFormerName(entity); }
    public int updateSupplierFormerName(SupplierFormerName entity) { return SupplierFormerNameMapper.updateSupplierFormerName(entity); }
    public int deleteSupplierFormerNameById(Long id) { return SupplierFormerNameMapper.deleteSupplierFormerNameById(id); }
    public int deleteSupplierFormerNameByIds(Long[] ids) { return SupplierFormerNameMapper.deleteSupplierFormerNameByIds(ids); }
}
