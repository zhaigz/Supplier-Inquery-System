package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.UsageStat;

public interface UsageStatMapper
{
    UsageStat selectUsageStatById(Long id);
    List<UsageStat> selectUsageStatList(UsageStat entity);
    int insertUsageStat(UsageStat entity);
    int updateUsageStat(UsageStat entity);
    int deleteUsageStatById(Long id);
    int deleteUsageStatByIds(Long[] ids);
}
