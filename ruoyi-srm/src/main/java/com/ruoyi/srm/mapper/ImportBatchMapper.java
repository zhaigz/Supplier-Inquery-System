package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.ImportBatch;

public interface ImportBatchMapper
{
    ImportBatch selectImportBatchById(Long id);
    List<ImportBatch> selectImportBatchList(ImportBatch entity);
    int insertImportBatch(ImportBatch entity);
    int updateImportBatch(ImportBatch entity);
    int deleteImportBatchById(Long id);
    int deleteImportBatchByIds(Long[] ids);
}
