package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.RiskAdminPunish;

public interface IRiskAdminPunishService
{
    RiskAdminPunish selectRiskAdminPunishById(Long id);
    List<RiskAdminPunish> selectRiskAdminPunishList(RiskAdminPunish entity);
    int insertRiskAdminPunish(RiskAdminPunish entity);
    int updateRiskAdminPunish(RiskAdminPunish entity);
    int deleteRiskAdminPunishById(Long id);
    int deleteRiskAdminPunishByIds(Long[] ids);
}
