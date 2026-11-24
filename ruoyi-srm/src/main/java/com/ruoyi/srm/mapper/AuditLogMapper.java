package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.AuditLog;

public interface AuditLogMapper
{
    AuditLog selectAuditLogById(Long id);
    List<AuditLog> selectAuditLogList(AuditLog entity);
    int insertAuditLog(AuditLog entity);
    int updateAuditLog(AuditLog entity);
    int deleteAuditLogById(Long id);
    int deleteAuditLogByIds(Long[] ids);
}
