package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ImportBatch;
import com.ruoyi.srm.mapper.ImportBatchMapper;
import com.ruoyi.srm.service.IImportBatchService;

@Service
public class ImportBatchServiceImpl implements IImportBatchService
{
    @Autowired
    private ImportBatchMapper ImportBatchMapper;

    public ImportBatch selectImportBatchById(Long id) { return ImportBatchMapper.selectImportBatchById(id); }
    public List<ImportBatch> selectImportBatchList(ImportBatch entity) { return ImportBatchMapper.selectImportBatchList(entity); }
    public int insertImportBatch(ImportBatch entity) { return ImportBatchMapper.insertImportBatch(entity); }
    public int updateImportBatch(ImportBatch entity) { return ImportBatchMapper.updateImportBatch(entity); }
    public int deleteImportBatchById(Long id) { return ImportBatchMapper.deleteImportBatchById(id); }
    public int deleteImportBatchByIds(Long[] ids) { return ImportBatchMapper.deleteImportBatchByIds(ids); }
}
