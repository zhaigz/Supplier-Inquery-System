package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ImportRow;
import com.ruoyi.srm.mapper.ImportRowMapper;
import com.ruoyi.srm.service.IImportRowService;

@Service
public class ImportRowServiceImpl implements IImportRowService
{
    @Autowired
    private ImportRowMapper ImportRowMapper;

    public ImportRow selectImportRowById(Long id) { return ImportRowMapper.selectImportRowById(id); }
    public List<ImportRow> selectImportRowList(ImportRow entity) { return ImportRowMapper.selectImportRowList(entity); }
    public int insertImportRow(ImportRow entity) { return ImportRowMapper.insertImportRow(entity); }
    public int updateImportRow(ImportRow entity) { return ImportRowMapper.updateImportRow(entity); }
    public int deleteImportRowById(Long id) { return ImportRowMapper.deleteImportRowById(id); }
    public int deleteImportRowByIds(Long[] ids) { return ImportRowMapper.deleteImportRowByIds(ids); }
}
