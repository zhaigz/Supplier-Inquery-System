package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.MonitorPolicy;
import com.ruoyi.srm.mapper.MonitorPolicyMapper;
import com.ruoyi.srm.service.IMonitorPolicyService;

@Service
public class MonitorPolicyServiceImpl implements IMonitorPolicyService
{
    @Autowired
    private MonitorPolicyMapper MonitorPolicyMapper;

    public MonitorPolicy selectMonitorPolicyById(Long id) { return MonitorPolicyMapper.selectMonitorPolicyById(id); }
    public List<MonitorPolicy> selectMonitorPolicyList(MonitorPolicy entity) { return MonitorPolicyMapper.selectMonitorPolicyList(entity); }
    public int insertMonitorPolicy(MonitorPolicy entity) { return MonitorPolicyMapper.insertMonitorPolicy(entity); }
    public int updateMonitorPolicy(MonitorPolicy entity) { return MonitorPolicyMapper.updateMonitorPolicy(entity); }
    public int deleteMonitorPolicyById(Long id) { return MonitorPolicyMapper.deleteMonitorPolicyById(id); }
    public int deleteMonitorPolicyByIds(Long[] ids) { return MonitorPolicyMapper.deleteMonitorPolicyByIds(ids); }
}
