package com.ruoyi.srm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson2.JSON;
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
import com.ruoyi.srm.domain.RelClue;
import com.ruoyi.srm.domain.RelRule;
import com.ruoyi.srm.domain.RelRuleVersion;
import com.ruoyi.srm.service.IRelClueService;
import com.ruoyi.srm.service.IRelRuleService;
import com.ruoyi.srm.service.IRelRuleVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面10「关联关系配置」接口，共 9 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/rel-rule")
public class SrmRelRuleController extends BaseController
{
    @Autowired private IRelRuleService relRuleService;
    @Autowired private IRelRuleVersionService relRuleVersionService;
    @Autowired private IRelClueService relClueService;

    /**
     * 规则清单
     *
     * <p>页面 10「关联关系配置」；权限 srm:relRule:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:relRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(RelRule query)
    {
        RelRule q = query == null ? new RelRule() : query;
        if (StringUtils.isEmpty(q.getDelFlag())) q.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
        startPage(); startOrderBy();
        return getDataTable(relRuleService.selectRelRuleList(q));
    }

    /**
     * 规则详情
     *
     * <p>页面 10「关联关系配置」；权限 srm:relRule:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:relRule:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(requireRule(id));
    }

    /**
     * 新增自定义维度
     *
     * <p>页面 10「关联关系配置」；权限 srm:relRule:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:relRule:add')")
    @Log(title="关联规则", businessType=BusinessType.INSERT)
    @SrmAudit(module="关联关系配置", action="新增", object="规则", detail="新增自定义维度")
    @PostMapping
    public AjaxResult add(@RequestBody RelRule rule)
    {
        if (rule == null || StringUtils.isEmpty(rule.getRuleName())) throw new ServiceException("ruleName不能为空", SrmErrorCode.CODE_60008);
        rule.setId(null);
        if (StringUtils.isEmpty(rule.getRecommendFlag())) rule.setRecommendFlag(SrmConstants.NO);
        if (StringUtils.isEmpty(rule.getDefaultOn())) rule.setDefaultOn(SrmConstants.NO);
        if (StringUtils.isEmpty(rule.getEnabled())) rule.setEnabled(SrmConstants.NO);
        if (StringUtils.isEmpty(rule.getDelFlag())) rule.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
        rule.setCreateBy(getUsername());
        rule.setCreateTime(new Date());
        relRuleService.insertRelRule(rule);
        saveVersion("新增", rule.getId(), null);
        return success(rule.getId());
    }

    /**
     * 编辑（追加版本快照）
     *
     * <p>页面 10「关联关系配置」；权限 srm:relRule:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:relRule:edit')")
    @Log(title="关联规则", businessType=BusinessType.UPDATE)
    @SrmAudit(module="关联关系配置", action="修改", object="{p:id}", detail="编辑并追加版本快照")
    @PutMapping
    public AjaxResult edit(@RequestBody RelRule rule)
    {
        if (rule == null || rule.getId() == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        RelRule old = requireRule(rule.getId());
        rule.setUpdateBy(getUsername());
        rule.setUpdateTime(new Date());
        relRuleService.updateRelRule(rule);
        saveVersion("编辑", rule.getId(), diff(old, requireRule(rule.getId())));
        return success(requireRule(rule.getId()));
    }

    /**
     * 启用/停用
     *
     * <p>页面 10「关联关系配置」；权限 srm:relRule:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:relRule:edit')")
    @SrmAudit(module="关联关系配置", action="启停", object="{p:id}", detail="启用/停用")
    @PutMapping("/toggle")
    public AjaxResult toggle(@RequestBody Map<String,Object> body)
    {
        Long id = longOf(body == null ? null : body.get("id"));
        String enabled = str(body == null ? null : body.get("enabled"));
        RelRule old = requireRule(id);
        if (!"0".equals(enabled) && !"1".equals(enabled)) throw new ServiceException("enabled只能为0或1", SrmErrorCode.CODE_60008);
        RelRule patch = new RelRule();
        patch.setId(id);
        patch.setEnabled(enabled);
        patch.setUpdateBy(getUsername());
        patch.setUpdateTime(new Date());
        relRuleService.updateRelRule(patch);
        saveVersion("启停", id, diff(old, requireRule(id)));
        return success(requireRule(id));
    }

    /**
     * 软删（保留版本流水）
     *
     * <p>页面 10「关联关系配置」；权限 srm:relRule:remove；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:relRule:remove')")
    @Log(title="关联规则", businessType=BusinessType.DELETE)
    @SrmAudit(module="关联关系配置", action="删除", object="{arg0}", detail="软删并保留版本流水")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids)
    {
        Long[] values = parseIds(ids);
        if (values.length == 0) throw new ServiceException("ids不能为空", SrmErrorCode.CODE_60008);
        for (Long id : values)
        {
            RelRule old = requireRule(id);
            RelRule patch = new RelRule();
            patch.setId(id);
            patch.setDelFlag(SrmConstants.DEL_FLAG_DELETED);
            patch.setUpdateBy(getUsername());
            patch.setUpdateTime(new Date());
            relRuleService.updateRelRule(patch);
            saveVersion("删除", id, diff(old, null));
        }
        return success();
    }

    /**
     * 一键恢复推荐规则
     *
     * <p>页面 10「关联关系配置」；权限 srm:relRule:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:relRule:add')")
    @SrmAudit(module="关联关系配置", action="恢复", object="推荐规则", detail="一键恢复推荐规则")
    @PostMapping("/restore")
    public AjaxResult restore()
    {
        RelRule q = new RelRule();
        q.setRecommendFlag(SrmConstants.YES);
        List<RelRule> rows = relRuleService.selectRelRuleList(q);
        int count = 0;
        for (RelRule rule : rows == null ? new ArrayList<RelRule>() : rows)
        {
            RelRule patch = new RelRule();
            patch.setId(rule.getId());
            patch.setEnabled(rule.getDefaultOn());
            patch.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
            patch.setUpdateBy(getUsername());
            patch.setUpdateTime(new Date());
            relRuleService.updateRelRule(patch);
            count++;
        }
        saveVersion("恢复推荐", null, "恢复推荐规则 " + count + " 条");
        return success(count);
    }

    /**
     * 版本流水
     *
     * <p>页面 10「关联关系配置」；权限 srm:relRule:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:relRule:list')")
    @GetMapping("/version/list")
    public TableDataInfo versionList(RelRuleVersion query)
    {
        startPage(); startOrderBy();
        return getDataTable(relRuleVersionService.selectRelRuleVersionList(query == null ? new RelRuleVersion() : query));
    }

    /**
     * 被线索引用数
     *
     * <p>页面 10「关联关系配置」；权限 srm:relRule:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:relRule:query')")
    @GetMapping("/refCount/{id}")
    public AjaxResult refCount(@PathVariable Long id)
    {
        requireRule(id);
        RelClue q = new RelClue();
        q.setRuleId(id);
        List<RelClue> rows = relClueService.selectRelClueList(q);
        return success(rows == null ? 0 : rows.size());
    }

    private void saveVersion(String changeType, Long ruleId, String diff)
    {
        RelRuleVersion q = new RelRuleVersion();
        List<RelRuleVersion> versions = relRuleVersionService.selectRelRuleVersionList(q);
        int next = 1;
        if (versions != null) for (RelRuleVersion v : versions) if (v.getVersionNo() != null && v.getVersionNo() >= next) next = v.getVersionNo() + 1;
        RelRuleVersion version = new RelRuleVersion();
        version.setVersionNo(next);
        version.setChangeType(changeType);
        RelRule snapshotQuery = new RelRule();
        snapshotQuery.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
        List<RelRule> all = relRuleService.selectRelRuleList(snapshotQuery);
        version.setSnapshotJson(JSON.toJSONString(all == null ? new ArrayList<>() : all));
        version.setDiffJson(diff == null ? "" : diff);
        version.setCreateBy(getUsername());
        version.setCreateTime(new Date());
        relRuleVersionService.insertRelRuleVersion(version);
    }

    private String diff(RelRule before, RelRule after)
    {
        Map<String,Object> m = new LinkedHashMap<>();
        if (before != null)
        {
            m.put("beforeEnabled", before.getEnabled());
            m.put("beforeName", before.getRuleName());
            m.put("beforeWeight", before.getWeight());
        }
        if (after != null)
        {
            m.put("afterEnabled", after.getEnabled());
            m.put("afterName", after.getRuleName());
            m.put("afterWeight", after.getWeight());
        }
        return JSON.toJSONString(m);
    }

    private RelRule requireRule(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        RelRule rule = relRuleService.selectRelRuleById(id);
        if (rule == null) throw new ServiceException("规则不存在", SrmErrorCode.CODE_60001);
        return rule;
    }

    private Long[] parseIds(String ids) { List<Long> list = new ArrayList<>(); for (String x : ids.split(",")) { String v = x.trim(); if (v.matches("\\d+")) list.add(Long.valueOf(v)); } return list.toArray(new Long[0]); }
    private Long longOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).longValue(); return Long.valueOf(String.valueOf(v)); }
    private String str(Object v) { return v == null ? null : String.valueOf(v); }
}