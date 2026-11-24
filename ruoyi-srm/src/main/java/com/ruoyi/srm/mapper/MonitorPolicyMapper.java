package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.MonitorPolicy;

public interface MonitorPolicyMapper
{
    MonitorPolicy selectMonitorPolicyById(Long id);
    List<MonitorPolicy> selectMonitorPolicyList(MonitorPolicy entity);
    int insertMonitorPolicy(MonitorPolicy entity);
    int updateMonitorPolicy(MonitorPolicy entity);
    int deleteMonitorPolicyById(Long id);
    int deleteMonitorPolicyByIds(Long[] ids);
}
