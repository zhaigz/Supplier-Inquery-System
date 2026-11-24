package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.AuditLog;
import com.ruoyi.srm.mapper.AuditLogMapper;
import com.ruoyi.srm.service.IAuditLogService;

@Service
public class AuditLogServiceImpl implements IAuditLogService
{
    @Autowired
    private AuditLogMapper AuditLogMapper;

    public AuditLog selectAuditLogById(Long id) { return AuditLogMapper.selectAuditLogById(id); }
    public List<AuditLog> selectAuditLogList(AuditLog entity) { return AuditLogMapper.selectAuditLogList(entity); }
    public int insertAuditLog(AuditLog entity) { return AuditLogMapper.insertAuditLog(entity); }
    public int updateAuditLog(AuditLog entity) { return AuditLogMapper.updateAuditLog(entity); }
    public int deleteAuditLogById(Long id) { return AuditLogMapper.deleteAuditLogById(id); }
    public int deleteAuditLogByIds(Long[] ids) { return AuditLogMapper.deleteAuditLogByIds(ids); }
}
