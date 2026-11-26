package com.ruoyi.srm.controller;

import java.util.Date;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmConstants;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.TriggerRule;
import com.ruoyi.srm.service.ITriggerRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面16「预算与触发规则」接口，共 2 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/trigger-rule")
public class SrmTriggerRuleController extends BaseController
{
    @Autowired private ITriggerRuleService triggerRuleService;

    /**
     * 五条触发规则
     *
     * <p>页面 16「预算与触发规则」；权限 srm:budgetPolicy:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:budgetPolicy:list')")
    @GetMapping("/list")
    public TableDataInfo list(TriggerRule query)
    {
        startPage(); startOrderBy();
        return getDataTable(triggerRuleService.selectTriggerRuleList(query == null ? new TriggerRule() : query));
    }

    /**
     * 编辑触发规则
     *
     * <p>页面 16「预算与触发规则」；权限 srm:budgetPolicy:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:budgetPolicy:edit')")
    @Log(title="触发规则", businessType=BusinessType.UPDATE)
    @SrmAudit(module="触发规则", action="修改", object="{p:id}", detail="编辑触发规则")
    @PutMapping
    public AjaxResult edit(@RequestBody TriggerRule rule)
    {
        if (rule == null) throw new ServiceException("请求体不能为空", SrmErrorCode.CODE_60008);
        if (rule.getEnabled() != null && SrmConstants.YES.equals(rule.getEnabled()) && StringUtils.isEmpty(rule.getApiIds()))
            throw new ServiceException("启用触发规则必须配置apiIds", SrmErrorCode.CODE_60008);
        if (rule.getId() == null)
        {
            if (StringUtils.isEmpty(rule.getRuleCode())) throw new ServiceException("ruleCode不能为空", SrmErrorCode.CODE_60008);
            rule.setCreateBy(getUsername());
            rule.setCreateTime(new Date());
            triggerRuleService.insertTriggerRule(rule);
        }
        else
        {
            TriggerRule exist = triggerRuleService.selectTriggerRuleById(rule.getId());
            if (exist == null) throw new ServiceException("触发规则不存在", SrmErrorCode.CODE_60001);
            if (SrmConstants.YES.equals(rule.getEnabled()) && StringUtils.isEmpty(rule.getApiIds()) && StringUtils.isEmpty(exist.getApiIds()))
                throw new ServiceException("启用触发规则必须配置apiIds", SrmErrorCode.CODE_60008);
            rule.setUpdateBy(getUsername());
            rule.setUpdateTime(new Date());
            triggerRuleService.updateTriggerRule(rule);
        }
        return success(triggerRuleService.selectTriggerRuleById(rule.getId()));
    }
}