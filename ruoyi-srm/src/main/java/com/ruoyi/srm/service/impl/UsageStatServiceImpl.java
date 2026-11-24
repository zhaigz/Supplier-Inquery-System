package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.UsageStat;
import com.ruoyi.srm.mapper.UsageStatMapper;
import com.ruoyi.srm.service.IUsageStatService;

@Service
public class UsageStatServiceImpl implements IUsageStatService
{
    @Autowired
    private UsageStatMapper UsageStatMapper;

    public UsageStat selectUsageStatById(Long id) { return UsageStatMapper.selectUsageStatById(id); }
    public List<UsageStat> selectUsageStatList(UsageStat entity) { return UsageStatMapper.selectUsageStatList(entity); }
    public int insertUsageStat(UsageStat entity) { return UsageStatMapper.insertUsageStat(entity); }
    public int updateUsageStat(UsageStat entity) { return UsageStatMapper.updateUsageStat(entity); }
    public int deleteUsageStatById(Long id) { return UsageStatMapper.deleteUsageStatById(id); }
    public int deleteUsageStatByIds(Long[] ids) { return UsageStatMapper.deleteUsageStatByIds(ids); }
}
