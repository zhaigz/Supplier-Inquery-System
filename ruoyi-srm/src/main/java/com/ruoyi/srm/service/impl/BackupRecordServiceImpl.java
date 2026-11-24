package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.BackupRecord;
import com.ruoyi.srm.mapper.BackupRecordMapper;
import com.ruoyi.srm.service.IBackupRecordService;

@Service
public class BackupRecordServiceImpl implements IBackupRecordService
{
    @Autowired
    private BackupRecordMapper BackupRecordMapper;

    public BackupRecord selectBackupRecordById(Long id) { return BackupRecordMapper.selectBackupRecordById(id); }
    public List<BackupRecord> selectBackupRecordList(BackupRecord entity) { return BackupRecordMapper.selectBackupRecordList(entity); }
    public int insertBackupRecord(BackupRecord entity) { return BackupRecordMapper.insertBackupRecord(entity); }
    public int updateBackupRecord(BackupRecord entity) { return BackupRecordMapper.updateBackupRecord(entity); }
    public int deleteBackupRecordById(Long id) { return BackupRecordMapper.deleteBackupRecordById(id); }
    public int deleteBackupRecordByIds(Long[] ids) { return BackupRecordMapper.deleteBackupRecordByIds(ids); }
}
