package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.BackupRecord;

public interface BackupRecordMapper
{
    BackupRecord selectBackupRecordById(Long id);
    List<BackupRecord> selectBackupRecordList(BackupRecord entity);
    int insertBackupRecord(BackupRecord entity);
    int updateBackupRecord(BackupRecord entity);
    int deleteBackupRecordById(Long id);
    int deleteBackupRecordByIds(Long[] ids);
}
