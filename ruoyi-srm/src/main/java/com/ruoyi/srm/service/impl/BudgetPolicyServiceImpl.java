package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.BudgetPolicy;
import com.ruoyi.srm.mapper.BudgetPolicyMapper;
import com.ruoyi.srm.service.IBudgetPolicyService;

@Service
public class BudgetPolicyServiceImpl implements IBudgetPolicyService
{
    @Autowired
    private BudgetPolicyMapper BudgetPolicyMapper;

    public BudgetPolicy selectBudgetPolicyById(Long id) { return BudgetPolicyMapper.selectBudgetPolicyById(id); }
    public List<BudgetPolicy> selectBudgetPolicyList(BudgetPolicy entity) { return BudgetPolicyMapper.selectBudgetPolicyList(entity); }
    public int insertBudgetPolicy(BudgetPolicy entity) { return BudgetPolicyMapper.insertBudgetPolicy(entity); }
    public int updateBudgetPolicy(BudgetPolicy entity) { return BudgetPolicyMapper.updateBudgetPolicy(entity); }
    public int deleteBudgetPolicyById(Long id) { return BudgetPolicyMapper.deleteBudgetPolicyById(id); }
    public int deleteBudgetPolicyByIds(Long[] ids) { return BudgetPolicyMapper.deleteBudgetPolicyByIds(ids); }
}
