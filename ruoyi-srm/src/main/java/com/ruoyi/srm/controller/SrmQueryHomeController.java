package com.ruoyi.srm.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.SearchHistory;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.SupplierIndustryInfo;
import com.ruoyi.srm.service.ISearchHistoryService;
import com.ruoyi.srm.service.ISupplierIndustryInfoService;
import com.ruoyi.srm.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面2「查询首页」接口，共 5 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/query")
public class SrmQueryHomeController extends BaseController
{
    @Autowired private ISupplierService supplierService;
    @Autowired private ISupplierIndustryInfoService industryInfoService;
    @Autowired private ISearchHistoryService searchHistoryService;

    /**
     * 本地包含匹配检索
     *
     * <p>页面 2「查询首页」；权限 srm:query:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:query:list')")
    @GetMapping("/match")
    public TableDataInfo match(@RequestParam(required=false) String keyword,
                               @RequestParam(required=false) String subjectType)
    {
        String key = keyword == null ? "" : keyword.trim();
        if (StringUtils.isEmpty(key)) throw new ServiceException("keyword不能为空", SrmErrorCode.CODE_60008);
        List<Supplier> rows = new ArrayList<>();
        Supplier name = new Supplier();
        name.setSupplierName(key);
        name.setDelFlag("0");
        addUnique(rows, supplierService.selectSupplierList(name));
        Supplier exact = new Supplier();
        exact.setCreditCode(key);
        exact.setDelFlag("0");
        addUnique(rows, supplierService.selectSupplierList(exact));
        Supplier reg = new Supplier();
        reg.setRegNo(key);
        reg.setDelFlag("0");
        addUnique(rows, supplierService.selectSupplierList(reg));
        Supplier tyc = new Supplier();
        tyc.setTycCompanyId(key);
        tyc.setDelFlag("0");
        addUnique(rows, supplierService.selectSupplierList(tyc));
        startPage();
        return getDataTable(rows);
    }

    /**
     * 最近查询/常用入口
     *
     * <p>页面 2「查询首页」；权限 srm:query:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:query:list')")
    @GetMapping("/history")
    public TableDataInfo historyList(SearchHistory query)
    {
        SearchHistory entity = query == null ? new SearchHistory() : query;
        entity.setUserId(getUserId());
        List<SearchHistory> rows = searchHistoryService.selectSearchHistoryList(entity);
        if (rows == null) rows = new ArrayList<>();
        rows.sort(Comparator.comparing(SearchHistory::getSearchedAt, Comparator.nullsLast(Comparator.reverseOrder())));
        if (rows.size() > 20) rows = new ArrayList<>(rows.subList(0, 20));
        startPage();
        return getDataTable(rows);
    }

    /**
     * 记录本次检索
     *
     * <p>页面 2「查询首页」；权限 srm:query:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:query:add')")
    @SrmAudit(module="查询首页", action="新增", object="最近查询", detail="记录本次检索")
    @PostMapping("/history")
    public AjaxResult historyAdd(@RequestBody SearchHistory entity)
    {
        if (entity == null || StringUtils.isEmpty(entity.getSubjectKey())) throw new ServiceException("subjectKey不能为空", SrmErrorCode.CODE_60008);
        if (StringUtils.isEmpty(entity.getSubjectType())) entity.setSubjectType("company_name");
        if (StringUtils.isEmpty(entity.getMatchMode())) entity.setMatchMode("模糊");
        entity.setUserId(getUserId());
        entity.setSearchedAt(new Date());
        entity.setCreateBy(getUsername());
        entity.setCreateTime(new Date());
        searchHistoryService.insertSearchHistory(entity);
        return success(entity.getId());
    }

    /**
     * 清空/删除最近查询
     *
     * <p>页面 2「查询首页」；权限 srm:query:remove；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:query:remove')")
    @SrmAudit(module="查询首页", action="删除", object="{arg0}", detail="删除最近查询")
    @DeleteMapping("/history/{ids}")
    public AjaxResult remove(@PathVariable String ids)
    {
        Long[] values = parseIds(ids);
        if (values.length == 0) throw new ServiceException("ids不能为空", SrmErrorCode.CODE_60008);
        return success(searchHistoryService.deleteSearchHistoryByIds(values));
    }

    /**
     * 命中项工商简卡
     *
     * <p>页面 2「查询首页」；权限 srm:query:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:query:query')")
    @GetMapping("/card/{supplierId}")
    public AjaxResult card(@PathVariable Long supplierId)
    {
        Supplier supplier = supplierService.selectSupplierById(supplierId);
        if (supplier == null) throw new ServiceException("供应商不存在", SrmErrorCode.CODE_60001);
        SupplierIndustryInfo q = new SupplierIndustryInfo();
        q.setSupplierId(supplierId);
        List<SupplierIndustryInfo> industries = industryInfoService.selectSupplierIndustryInfoList(q);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("supplier", supplier);
        data.put("industry", industries == null || industries.isEmpty() ? null : industries.get(0));
        data.put("dataVersion", supplier.getDataVersion());
        data.put("dataSource", supplier.getDataSource());
        data.put("lastUpdateAt", supplier.getLastUpdateAt());
        return success(data);
    }

    private void addUnique(List<Supplier> rows, List<Supplier> part)
    {
        if (part == null) return;
        for (Supplier s : part)
        {
            boolean exists = false;
            for (Supplier x : rows) if (x.getId() != null && x.getId().equals(s.getId())) { exists = true; break; }
            if (!exists) rows.add(s);
        }
    }

    private Long[] parseIds(String ids)
    {
        List<Long> list = new ArrayList<>();
        for (String x : ids.split(","))
        {
            String v = x.trim();
            if (v.matches("\\d+")) list.add(Long.valueOf(v));
        }
        return list.toArray(new Long[0]);
    }
}