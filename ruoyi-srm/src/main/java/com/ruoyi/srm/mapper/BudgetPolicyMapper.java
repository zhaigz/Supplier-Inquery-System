package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.BudgetPolicy;

public interface BudgetPolicyMapper
{
    BudgetPolicy selectBudgetPolicyById(Long id);
    List<BudgetPolicy> selectBudgetPolicyList(BudgetPolicy entity);
    int insertBudgetPolicy(BudgetPolicy entity);
    int updateBudgetPolicy(BudgetPolicy entity);
    int deleteBudgetPolicyById(Long id);
    int deleteBudgetPolicyByIds(Long[] ids);
}
