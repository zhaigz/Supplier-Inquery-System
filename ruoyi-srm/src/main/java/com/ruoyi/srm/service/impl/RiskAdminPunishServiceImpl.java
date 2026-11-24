package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.RiskAdminPunish;
import com.ruoyi.srm.mapper.RiskAdminPunishMapper;
import com.ruoyi.srm.service.IRiskAdminPunishService;

@Service
public class RiskAdminPunishServiceImpl implements IRiskAdminPunishService
{
    @Autowired
    private RiskAdminPunishMapper RiskAdminPunishMapper;

    public RiskAdminPunish selectRiskAdminPunishById(Long id) { return RiskAdminPunishMapper.selectRiskAdminPunishById(id); }
    public List<RiskAdminPunish> selectRiskAdminPunishList(RiskAdminPunish entity) { return RiskAdminPunishMapper.selectRiskAdminPunishList(entity); }
    public int insertRiskAdminPunish(RiskAdminPunish entity) { return RiskAdminPunishMapper.insertRiskAdminPunish(entity); }
    public int updateRiskAdminPunish(RiskAdminPunish entity) { return RiskAdminPunishMapper.updateRiskAdminPunish(entity); }
    public int deleteRiskAdminPunishById(Long id) { return RiskAdminPunishMapper.deleteRiskAdminPunishById(id); }
    public int deleteRiskAdminPunishByIds(Long[] ids) { return RiskAdminPunishMapper.deleteRiskAdminPunishByIds(ids); }
}
