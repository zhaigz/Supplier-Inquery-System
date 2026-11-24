package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.RiskAdminPunish;

public interface RiskAdminPunishMapper
{
    RiskAdminPunish selectRiskAdminPunishById(Long id);
    List<RiskAdminPunish> selectRiskAdminPunishList(RiskAdminPunish entity);
    int insertRiskAdminPunish(RiskAdminPunish entity);
    int updateRiskAdminPunish(RiskAdminPunish entity);
    int deleteRiskAdminPunishById(Long id);
    int deleteRiskAdminPunishByIds(Long[] ids);
}
