package com.ruoyi.srm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import com.ruoyi.srm.domain.FieldMapping;
import com.ruoyi.srm.domain.SupplierExtField;
import com.ruoyi.srm.service.IFieldMappingService;
import com.ruoyi.srm.service.ISupplierExtFieldService;
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
 * 页面11「外部数据字段映射」接口，共 8 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/field-mapping")
public class SrmFieldMappingController extends BaseController
{
    @Autowired private IFieldMappingService fieldMappingService;
    @Autowired private ISupplierExtFieldService extFieldService;

    /**
     * 映射清单
     *
     * <p>页面 11「外部数据字段映射」；权限 srm:fieldMapping:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:fieldMapping:list')")
    @GetMapping("/list")
    public TableDataInfo list(FieldMapping query)
    {
        startPage(); startOrderBy();
        return getDataTable(fieldMappingService.selectFieldMappingList(query == null ? new FieldMapping() : query));
    }

    /**
     * 新增映射
     *
     * <p>页面 11「外部数据字段映射」；权限 srm:fieldMapping:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:fieldMapping:add')")
    @Log(title="字段映射", businessType=BusinessType.INSERT)
    @SrmAudit(module="字段映射", action="新增", object="映射", detail="新增映射")
    @PostMapping
    public AjaxResult add(@RequestBody FieldMapping mapping)
    {
        if (mapping == null || StringUtils.isEmpty(mapping.getBizField())) throw new ServiceException("bizField不能为空", SrmErrorCode.CODE_60008);
        mapping.setId(null);
        if (StringUtils.isEmpty(mapping.getStatus())) mapping.setStatus(SrmConstants.NO);
        mapping.setCreateBy(getUsername());
        mapping.setCreateTime(new Date());
        fieldMappingService.insertFieldMapping(mapping);
        return success(mapping.getId());
    }

    /**
     * 修改映射
     *
     * <p>页面 11「外部数据字段映射」；权限 srm:fieldMapping:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:fieldMapping:edit')")
    @Log(title="字段映射", businessType=BusinessType.UPDATE)
    @SrmAudit(module="字段映射", action="修改", object="{p:id}", detail="修改映射")
    @PutMapping
    public AjaxResult edit(@RequestBody FieldMapping mapping)
    {
        if (mapping == null || mapping.getId() == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        requireMapping(mapping.getId());
        mapping.setUpdateBy(getUsername());
        mapping.setUpdateTime(new Date());
        fieldMappingService.updateFieldMapping(mapping);
        return success(fieldMappingService.selectFieldMappingById(mapping.getId()));
    }

    /**
     * 启用/停用
     *
     * <p>页面 11「外部数据字段映射」；权限 srm:fieldMapping:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:fieldMapping:edit')")
    @SrmAudit(module="字段映射", action="启停", object="{p:id}", detail="启用/停用映射")
    @PutMapping("/toggle")
    public AjaxResult toggle(@RequestBody Map<String,Object> body)
    {
        Long id = longOf(body == null ? null : body.get("id"));
        String status = str(body == null ? null : body.get("status"));
        requireMapping(id);
        if (!"0".equals(status) && !"1".equals(status)) throw new ServiceException("status只能为0或1", SrmErrorCode.CODE_60008);
        FieldMapping patch = new FieldMapping();
        patch.setId(id);
        patch.setStatus(status);
        patch.setUpdateBy(getUsername());
        patch.setUpdateTime(new Date());
        fieldMappingService.updateFieldMapping(patch);
        return success(fieldMappingService.selectFieldMappingById(id));
    }

    /**
     * 删除（内置禁删）
     *
     * <p>页面 11「外部数据字段映射」；权限 srm:fieldMapping:remove；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:fieldMapping:remove')")
    @Log(title="字段映射", businessType=BusinessType.DELETE)
    @SrmAudit(module="字段映射", action="删除", object="{arg0}", detail="删除映射（内置禁删）")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids)
    {
        Long[] values = parseIds(ids);
        if (values.length == 0) throw new ServiceException("ids不能为空", SrmErrorCode.CODE_60008);
        for (Long id : values)
        {
            FieldMapping mapping = requireMapping(id);
            if (SrmConstants.YES.equals(mapping.getActiveBiz())) throw new ServiceException("内置映射禁止删除: " + id, SrmErrorCode.CODE_60010);
        }
        return success(fieldMappingService.deleteFieldMappingByIds(values));
    }

    /**
     * 接入检查
     *
     * <p>页面 11「外部数据字段映射」；权限 srm:fieldMapping:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:fieldMapping:list')")
    @PostMapping("/check")
    public AjaxResult check()
    {
        FieldMapping q = new FieldMapping();
        List<FieldMapping> rows = fieldMappingService.selectFieldMappingList(q);
        List<Map<String,Object>> problems = new ArrayList<>();
        for (FieldMapping m : rows == null ? new ArrayList<FieldMapping>() : rows)
        {
            List<String> errors = new ArrayList<>();
            if (StringUtils.isEmpty(m.getBizField())) errors.add("bizField为空");
            if (StringUtils.isEmpty(m.getOuterKey())) errors.add("outerKey为空");
            if (StringUtils.isEmpty(m.getInnerKey())) errors.add("innerKey为空");
            if (StringUtils.isEmpty(m.getTargetTable())) errors.add("targetTable为空");
            if (("1".equals(m.getNotNullCheck())) && StringUtils.isEmpty(m.getNormRule())) errors.add("非空字段缺少normRule");
            if (!errors.isEmpty())
            {
                Map<String,Object> item = new LinkedHashMap<>();
                item.put("id", m.getId());
                item.put("bizField", m.getBizField());
                item.put("errors", errors);
                problems.add(item);
            }
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("total", rows == null ? 0 : rows.size());
        data.put("problemCount", problems.size());
        data.put("problems", problems);
        return success(data);
    }

    /**
     * 自定义字段定义清单
     *
     * <p>页面 11「外部数据字段映射」；权限 srm:fieldMapping:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:fieldMapping:list')")
    @GetMapping("/ext/list")
    public TableDataInfo extList(SupplierExtField query)
    {
        startPage(); startOrderBy();
        return getDataTable(extFieldService.selectSupplierExtFieldList(query == null ? new SupplierExtField() : query));
    }

    /**
     * 新增自定义字段定义
     *
     * <p>页面 11「外部数据字段映射」；权限 srm:fieldMapping:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:fieldMapping:add')")
    @Log(title="自定义字段", businessType=BusinessType.INSERT)
    @SrmAudit(module="字段映射", action="新增", object="自定义字段", detail="新增自定义字段定义")
    @PostMapping("/ext")
    public AjaxResult extAdd(@RequestBody SupplierExtField field)
    {
        if (field == null || StringUtils.isEmpty(field.getFieldCode())) throw new ServiceException("fieldCode不能为空", SrmErrorCode.CODE_60008);
        field.setId(null);
        field.setCreateBy(getUsername());
        field.setCreateTime(new Date());
        extFieldService.insertSupplierExtField(field);
        return success(field.getId());
    }

    private FieldMapping requireMapping(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        FieldMapping mapping = fieldMappingService.selectFieldMappingById(id);
        if (mapping == null) throw new ServiceException("映射不存在", SrmErrorCode.CODE_60001);
        return mapping;
    }

    private Long[] parseIds(String ids) { List<Long> list = new ArrayList<>(); for (String x : ids.split(",")) { String v = x.trim(); if (v.matches("\\d+")) list.add(Long.valueOf(v)); } return list.toArray(new Long[0]); }
    private Long longOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).longValue(); return Long.valueOf(String.valueOf(v)); }
    private String str(Object v) { return v == null ? null : String.valueOf(v); }
}