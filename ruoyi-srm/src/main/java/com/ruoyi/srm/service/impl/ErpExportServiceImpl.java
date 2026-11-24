package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ErpExport;
import com.ruoyi.srm.mapper.ErpExportMapper;
import com.ruoyi.srm.service.IErpExportService;

@Service
public class ErpExportServiceImpl implements IErpExportService
{
    @Autowired
    private ErpExportMapper ErpExportMapper;

    public ErpExport selectErpExportById(Long id) { return ErpExportMapper.selectErpExportById(id); }
    public List<ErpExport> selectErpExportList(ErpExport entity) { return ErpExportMapper.selectErpExportList(entity); }
    public int insertErpExport(ErpExport entity) { return ErpExportMapper.insertErpExport(entity); }
    public int updateErpExport(ErpExport entity) { return ErpExportMapper.updateErpExport(entity); }
    public int deleteErpExportById(Long id) { return ErpExportMapper.deleteErpExportById(id); }
    public int deleteErpExportByIds(Long[] ids) { return ErpExportMapper.deleteErpExportByIds(ids); }
}
