package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.ImportRow;

public interface ImportRowMapper
{
    ImportRow selectImportRowById(Long id);
    List<ImportRow> selectImportRowList(ImportRow entity);
    int insertImportRow(ImportRow entity);
    int updateImportRow(ImportRow entity);
    int deleteImportRowById(Long id);
    int deleteImportRowByIds(Long[] ids);
}
