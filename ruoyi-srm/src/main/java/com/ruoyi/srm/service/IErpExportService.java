package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.ErpExport;

public interface IErpExportService
{
    ErpExport selectErpExportById(Long id);
    List<ErpExport> selectErpExportList(ErpExport entity);
    int insertErpExport(ErpExport entity);
    int updateErpExport(ErpExport entity);
    int deleteErpExportById(Long id);
    int deleteErpExportByIds(Long[] ids);
}
