package com.ruoyi.srm.controller;

import java.util.Date;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.ApiDict;
import com.ruoyi.srm.service.IApiDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/srm/api-dict")
public class SrmApiDictController extends BaseController
{
    @Autowired private IApiDictService apiDictService;

    /**
     * 接口字典
     *
     * <p>页面 16「预算与触发规则」；权限 srm:budgetPolicy:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:budgetPolicy:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApiDict query)
    {
        startPage(); startOrderBy();
        return getDataTable(apiDictService.selectApiDictList(query == null ? new ApiDict() : query));
    }

    /**
     * 固化单价/计费/启停
     *
     * <p>页面 16「预算与触发规则」；权限 srm:budgetPolicy:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:budgetPolicy:edit')")
    @Log(title="接口字典", businessType=BusinessType.UPDATE)
    @SrmAudit(module="接口字典", action="修改", object="{p:id}", detail="固化单价/计费/启停")
    @PutMapping("/{id}")
    public AjaxResult edit(@PathVariable Long id, @RequestBody ApiDict dict)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        ApiDict exist = apiDictService.selectApiDictById(id);
        if (exist == null) throw new ServiceException("接口字典不存在", SrmErrorCode.CODE_60001);
        ApiDict patch = dict == null ? new ApiDict() : dict;
        patch.setId(id);
        patch.setUpdateBy(getUsername());
        patch.setUpdateTime(new Date());
        apiDictService.updateApiDict(patch);
        return success(apiDictService.selectApiDictById(id));
    }
}