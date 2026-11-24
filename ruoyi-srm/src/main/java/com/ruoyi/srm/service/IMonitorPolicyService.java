package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.MonitorPolicy;

public interface IMonitorPolicyService
{
    MonitorPolicy selectMonitorPolicyById(Long id);
    List<MonitorPolicy> selectMonitorPolicyList(MonitorPolicy entity);
    int insertMonitorPolicy(MonitorPolicy entity);
    int updateMonitorPolicy(MonitorPolicy entity);
    int deleteMonitorPolicyById(Long id);
    int deleteMonitorPolicyByIds(Long[] ids);
}
