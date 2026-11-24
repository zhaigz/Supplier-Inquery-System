package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.ImportBatch;

public interface IImportBatchService
{
    ImportBatch selectImportBatchById(Long id);
    List<ImportBatch> selectImportBatchList(ImportBatch entity);
    int insertImportBatch(ImportBatch entity);
    int updateImportBatch(ImportBatch entity);
    int deleteImportBatchById(Long id);
    int deleteImportBatchByIds(Long[] ids);
}
