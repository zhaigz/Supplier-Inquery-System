package com.ruoyi.srm.controller;

import java.util.ArrayList;
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
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.constant.SrmConstants;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.SupplierChangeRecord;
import com.ruoyi.srm.domain.SupplierContact;
import com.ruoyi.srm.domain.SupplierExtField;
import com.ruoyi.srm.domain.SupplierFormerName;
import com.ruoyi.srm.domain.SupplierIndustryInfo;
import com.ruoyi.srm.domain.SupplierPerson;
import com.ruoyi.srm.domain.SupplierShareholder;
import com.ruoyi.srm.gateway.CallContext;
import com.ruoyi.srm.gateway.TycGateway;
import com.ruoyi.srm.gateway.TycResult;
import com.ruoyi.srm.service.ISupplierChangeRecordService;
import com.ruoyi.srm.service.ISupplierContactService;
import com.ruoyi.srm.service.ISupplierExtFieldService;
import com.ruoyi.srm.service.ISupplierFormerNameService;
import com.ruoyi.srm.service.ISupplierIndustryInfoService;
import com.ruoyi.srm.service.ISupplierPersonService;
import com.ruoyi.srm.service.ISupplierService;
import com.ruoyi.srm.service.ISupplierShareholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面3「供应商基本信息」接口，共 9 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 *
 * <p>TODO[天眼查接入] 本类调用的天眼查编号（共 4 个）。接入时只需在 application.yml 的 
 * <code>srm.tyc.apis.&lt;编号&gt;</code> 补 path/method，并把 TYC_TOKEN 注入环境变量，代码无需改动：
 * <ul><li>458（FORMER_NAME · 企业曾用名）</li></ul>
 * <ul><li>818（BASIC_INFO · 企业基本信息（含企业联系方式））</li></ul>
 * <ul><li>819（BASIC_INFO_PERSON · 企业基本信息（含主要人员））</li></ul>
 * <ul><li>1046（CONTACT · 企业联系方式）</li></ul>
 */
@RestController
@RequestMapping("/srm/supplier/detail")
public class SrmSupplierDetailController extends BaseController
{
    @Autowired private ISupplierService supplierService;
    @Autowired private ISupplierIndustryInfoService industryInfoService;
    @Autowired private ISupplierPersonService personService;
    @Autowired private ISupplierShareholderService shareholderService;
    @Autowired private ISupplierContactService contactService;
    @Autowired private ISupplierFormerNameService formerNameService;
    @Autowired private ISupplierChangeRecordService changeRecordService;
    @Autowired private ISupplierExtFieldService extFieldService;
    @Autowired private TycGateway tycGateway;

    /**
     * 主档 13 项+行业四级
     *
     * <p>页面 3「供应商基本信息」；权限 srm:detail:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:detail:query')")
    @GetMapping("/{supplierId}")
    public AjaxResult detail(@PathVariable Long supplierId)
    {
        Supplier supplier = requireSupplier(supplierId);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("supplier", supplier);
        data.put("industry", firstIndustry(supplierId));
        data.put("formerNames", formerNames(supplierId));
        return success(data);
    }

    /**
     * 工商信息补全（818+819）
     *
     * <p>页面 3「供应商基本信息」；权限 srm:detail:edit；外调 天眼查 818（BASIC_INFO）、819（BASIC_INFO_PERSON）
     */
    @PreAuthorize("@ss.hasPermi('srm:detail:edit')")
    @SrmAudit(module="供应商详情", action="补全", object="{p:supplierId}", detail="工商信息补全")
    @PostMapping("/enrich")
    public AjaxResult enrich(@RequestBody Map<String,Object> body)
    {
        Long supplierId = longOf(body == null ? null : body.get("supplierId"));
        boolean force = boolOf(body == null ? null : body.get("force"));
        Supplier supplier = requireSupplier(supplierId);
        // 本地优先（核心语义）：主要人员/联系方式已在本地库沉淀时直接回放本地数据，不重复付费外调天眼查。
        if (!force && localDetailSedimentHit(supplierId))
        {
            Map<String,Object> replay = new LinkedHashMap<>();
            replay.put("localHit", true);
            replay.put("dataSource", "本地库（历史天眼查 818/819 回写沉淀）");
            replay.put("unitConsumed", java.math.BigDecimal.ZERO);
            replay.put("supplierId", supplierId);
            replay.put("supplier", supplierService.selectSupplierById(supplierId));
            replay.put("industry", firstIndustry(supplierId));
            replay.put("personCount", personService.selectSupplierPersonList(personQuery(supplierId)).size());
            replay.put("contactCount", contactService.selectSupplierContactList(contactQuery(supplierId)).size());
            return success(replay);
        }
        String subjectKey = StringUtils.isNotEmpty(supplier.getCreditCode()) ? supplier.getCreditCode() : supplier.getSupplierName();
        CallContext ctx = new CallContext();
        ctx.setManual(true);
        ctx.setScene(SrmConstants.SCENE_SINGLE_ENRICH);
        ctx.setForce(force);
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("keyword", subjectKey);
        // TODO[天眼查接入] 待接入天眼查 818：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
        // TYC-CALL 外调天眼查 818（BASIC_INFO · 企业基本信息（含企业联系方式））0.20 元/次
        TycResult basic = tycGateway.call(SrmApiId.BASIC_INFO, subjectKey, params, ctx);
        // TODO[天眼查接入] 待接入天眼查 819：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
        // TYC-CALL 外调天眼查 819（BASIC_INFO_PERSON · 企业基本信息（含主要人员））0.25 元/次
        TycResult person = tycGateway.call(SrmApiId.BASIC_INFO_PERSON, subjectKey, params, ctx);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("basic", result(basic));
        data.put("person", result(person));
        data.put("supplierId", supplierId);
        data.put("supplier", supplierService.selectSupplierById(supplierId));
        data.put("industry", firstIndustry(supplierId));
        return success(data);
    }

    /**
     * 联系方式区（统一本地优先 + 来源标识透出 样板）
     *
     * <p>页面 3「供应商基本信息」；权限 srm:detail:list；外调 天眼查 1046（CONTACT）
     *
     * <p>核心语义：先查本地结构化表 srm_supplier_contact，命中直接返回并标 {@code source=local}
     * （即使 refresh=true 也不再付费，沉淀表永久有效、不受原始缓存 TTL 过期影响）；
     * 仅当本地为空才自动兜底外调天眼查 1046，投影回写后返回并标 {@code source=tyc} + 回传本次
     * {@code unitConsumed}（真实扣费额度）与 {@code cacheHit}（命中原始缓存则扣费为 0）。
     *
     * <p>返回体由 TableDataInfo 改为 AjaxResult：{@code data.rows}/{@code data.total} 保持列表语义，
     * 新增 {@code data.source}（local/tyc/blocked）、{@code data.unitConsumed}、{@code data.cacheHit} 供
     * 前端展示来源与成本对账。refresh=true 且需要走外调时作为 force 透传，绕过原始缓存强制重拉。
     */
    @PreAuthorize("@ss.hasPermi('srm:detail:list')")
    @GetMapping("/contact/{supplierId}")
    public AjaxResult contact(@PathVariable Long supplierId,
                              @RequestParam(required=false, defaultValue="false") boolean refresh)
    {
        requireSupplier(supplierId);
        Map<String,Object> data = new LinkedHashMap<>();
        // ① 本地优先：结构化表已有沉淀直接回放，即使用户点刷新也不再付费外调天眼查 1046
        List<SupplierContact> local = contactService.selectSupplierContactList(contactQuery(supplierId));
        if (local != null && !local.isEmpty())
        {
            data.put("source", "local");
            data.put("dataSource", "本地库（历史天眼查 1046 回写沉淀）");
            data.put("cacheHit", false);
            data.put("unitConsumed", java.math.BigDecimal.ZERO);
            data.put("rows", local);
            data.put("total", local.size());
            return success(data);
        }
        // ② 本地为空 => 自动兜底外调天眼查 1046（付费路径），ContactProjector 回写 srm_supplier_contact
        // TYC-CALL 外调天眼查 1046（CONTACT · 企业联系方式）0.10 元/次
        TycResult r = callSubject(SrmApiId.CONTACT, supplierId, refresh);
        List<SupplierContact> after = contactService.selectSupplierContactList(contactQuery(supplierId));
        data.put("source", r.isBlocked() ? "blocked" : "tyc");
        data.put("cacheHit", r.isCacheHit());
        data.put("unitConsumed", r.getUnitConsumed());
        data.put("blocked", r.isBlocked());
        data.put("blockReason", r.getBlockReason());
        data.put("rows", after == null ? new ArrayList<>() : after);
        data.put("total", after == null ? 0 : after.size());
        return success(data);
    }

    /**
     * 主要人员（819 沉淀）
     *
     * <p>页面 3「供应商基本信息」；权限 srm:detail:list；外调 天眼查 819（BASIC_INFO_PERSON）（读取本地已沉淀数据，不直发外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:detail:list')")
    @GetMapping("/person/{supplierId}")
    public TableDataInfo person(@PathVariable Long supplierId)
    {
        requireSupplier(supplierId);
        SupplierPerson q = new SupplierPerson();
        q.setSupplierId(supplierId);
        List<SupplierPerson> rows = personService.selectSupplierPersonList(q);
        if (rows != null && !rows.isEmpty() && rows.get(0).getSourceApiId() == null)
        {
            rows.get(0).setSourceApiId(SrmApiId.BASIC_INFO_PERSON);
        }
        return getDataTable(rows == null ? new ArrayList<>() : rows);
    }

    /**
     * 股东与占比（统一本地优先 + 来源标识透出 样板）
     *
     * <p>页面 3「供应商基本信息」；权限 srm:detail:list；外调 天眼查 821（SHAREHOLDER）
     *
     * <p>核心语义同曾用名：先查本地 srm_supplier_shareholder，命中直接返回并标 {@code source=local}；
     * 仅当本地为空才自动兜底外调天眼查 821，ShareholderProjector 回写后返回并标 {@code source=tyc} +
     * 回传 {@code unitConsumed}/{@code cacheHit}。返回体由 TableDataInfo 改为 AjaxResult。
     */
    @PreAuthorize("@ss.hasPermi('srm:detail:list')")
    @GetMapping("/shareholder/{supplierId}")
    public AjaxResult shareholder(@PathVariable Long supplierId,
                                  @RequestParam(required=false, defaultValue="false") boolean refresh)
    {
        requireSupplier(supplierId);
        Map<String,Object> data = new LinkedHashMap<>();
        // ① 本地优先：股东结构化表已有沉淀直接回放，不再付费外调天眼查 821
        List<SupplierShareholder> local = shareholders(supplierId);
        if (!local.isEmpty() && !refresh)
        {
            data.put("source", "local");
            data.put("dataSource", "本地库（历史天眼查 821 回写沉淀）");
            data.put("cacheHit", false);
            data.put("unitConsumed", java.math.BigDecimal.ZERO);
            data.put("rows", local);
            data.put("total", local.size());
            return success(data);
        }
        // ② 本地为空（或强制刷新）=> 自动兜底外调天眼查 821（付费路径），ShareholderProjector 回写 srm_supplier_shareholder
        // TYC-CALL 外调天眼查 821（SHAREHOLDER · 企业股东）0.15 元/次
        TycResult r = callSubject(SrmApiId.SHAREHOLDER, supplierId, refresh);
        List<SupplierShareholder> after = shareholders(supplierId);
        data.put("source", r.isBlocked() ? "blocked" : "tyc");
        data.put("cacheHit", r.isCacheHit());
        data.put("unitConsumed", r.getUnitConsumed());
        data.put("blocked", r.isBlocked());
        data.put("blockReason", r.getBlockReason());
        data.put("rows", after);
        data.put("total", after.size());
        return success(data);
    }

    /**
     * 曾用名时间轴（统一本地优先 + 来源标识透出 样板）
     *
     * <p>页面 3「供应商基本信息」；权限 srm:detail:list；外调 天眼查 458（FORMER_NAME）
     *
     * <p>核心语义同联系方式：先查本地 srm_supplier_former_name，命中直接返回并标 {@code source=local}；
     * 仅当本地为空才自动兜底外调天眼查 458，FormerNameProjector 回写后返回并标 {@code source=tyc} +
     * 回传 {@code unitConsumed}/{@code cacheHit}。返回体由 TableDataInfo 改为 AjaxResult。
     */
    @PreAuthorize("@ss.hasPermi('srm:detail:list')")
    @GetMapping("/former-name/{supplierId}")
    public AjaxResult formerName(@PathVariable Long supplierId,
                                 @RequestParam(required=false, defaultValue="false") boolean refresh)
    {
        requireSupplier(supplierId);
        Map<String,Object> data = new LinkedHashMap<>();
        // ① 本地优先：曾用名结构化表已有沉淀直接回放，不再付费外调天眼查 458
        List<SupplierFormerName> local = formerNames(supplierId);
        if (!local.isEmpty())
        {
            data.put("source", "local");
            data.put("dataSource", "本地库（历史天眼查 458 回写沉淀）");
            data.put("cacheHit", false);
            data.put("unitConsumed", java.math.BigDecimal.ZERO);
            data.put("rows", local);
            data.put("total", local.size());
            return success(data);
        }
        // ② 本地为空 => 自动兜底外调天眼查 458（付费路径），FormerNameProjector 回写 srm_supplier_former_name
        // TYC-CALL 外调天眼查 458（FORMER_NAME · 企业曾用名）0.10 元/次
        TycResult r = callSubject(SrmApiId.FORMER_NAME, supplierId, refresh);
        List<SupplierFormerName> after = formerNames(supplierId);
        data.put("source", r.isBlocked() ? "blocked" : "tyc");
        data.put("cacheHit", r.isCacheHit());
        data.put("unitConsumed", r.getUnitConsumed());
        data.put("blocked", r.isBlocked());
        data.put("blockReason", r.getBlockReason());
        data.put("rows", after);
        data.put("total", after.size());
        return success(data);
    }

    /**
     * 工商变更记录
     *
     * <p>页面 3「供应商基本信息」；权限 srm:detail:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:detail:list')")
    @GetMapping("/change/{supplierId}")
    public TableDataInfo change(@PathVariable Long supplierId)
    {
        requireSupplier(supplierId);
        SupplierChangeRecord q = new SupplierChangeRecord();
        q.setSupplierId(supplierId);
        List<SupplierChangeRecord> rows = changeRecordService.selectSupplierChangeRecordList(q);
        return getDataTable(rows == null ? new ArrayList<>() : rows);
    }

    /**
     * 自定义字段值
     *
     * <p>页面 3「供应商基本信息」；权限 srm:detail:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:detail:list')")
    @GetMapping("/ext/{supplierId}")
    public TableDataInfo ext(@PathVariable Long supplierId)
    {
        requireSupplier(supplierId);
        SupplierExtField q = new SupplierExtField();
        q.setSupplierId(supplierId);
        List<SupplierExtField> rows = extFieldService.selectSupplierExtFieldList(q);
        return getDataTable(rows == null ? new ArrayList<>() : rows);
    }

    /**
     * 编辑本地维护项
     *
     * <p>页面 3「供应商基本信息」；权限 srm:detail:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:detail:edit')")
    @SrmAudit(module="供应商详情", action="修改", object="{p:supplierId}", detail="编辑本地维护项")
    @PutMapping("/local")
    public AjaxResult local(@RequestBody Map<String,Object> body)
    {
        Long supplierId = longOf(body == null ? null : body.get("supplierId"));
        requireSupplier(supplierId);
        Supplier patch = new Supplier();
        patch.setId(supplierId);
        if (body != null)
        {
            patch.setSupplierName(str(body.get("supplierName")));
            patch.setPhone(str(body.get("phone")));
            patch.setEmail(str(body.get("email")));
            patch.setRegAddress(str(body.get("regAddress")));
            patch.setMonitorFlag(str(body.get("monitorFlag")));
            patch.setRemark(str(body.get("remark")));
        }
        patch.setUpdateBy(getUsername());
        patch.setUpdateTime(new Date());
        patch.setLastUpdateAt(new Date());
        supplierService.updateSupplier(patch);
        return success(supplierService.selectSupplierById(supplierId));
    }

    private TycResult callSubject(int apiId, Long supplierId)
    {
        return callSubject(apiId, supplierId, false);
    }

    private TycResult callSubject(int apiId, Long supplierId, boolean force)
    {
        Supplier supplier = requireSupplier(supplierId);
        String subjectKey = StringUtils.isNotEmpty(supplier.getCreditCode()) ? supplier.getCreditCode() : supplier.getSupplierName();
        CallContext ctx = new CallContext();
        ctx.setManual(true);
        ctx.setScene(SrmConstants.SCENE_REPEAT_QUERY);
        ctx.setForce(force);
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("keyword", subjectKey);
        // TODO[天眼查接入] 待接入天眼查 1046、458：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
        // TYC-CALL 外调天眼查 1046（CONTACT · 企业联系方式）0.10 元/次
        // TYC-CALL 外调天眼查 458（FORMER_NAME · 企业曾用名）0.10 元/次
        return tycGateway.call(apiId, subjectKey, params, ctx);
    }

    private Map<String,Object> result(TycResult r)
    {
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("apiId", r.getApiId());
        m.put("cacheHit", r.isCacheHit());
        m.put("blocked", r.isBlocked());
        m.put("blockReason", r.getBlockReason());
        m.put("unitConsumed", r.getUnitConsumed());
        m.put("fetchedAt", r.getFetchedAt());
        m.put("data", r.getData());
        return m;
    }

    private Supplier requireSupplier(Long supplierId)
    {
        if (supplierId == null) throw new ServiceException("supplierId不能为空", SrmErrorCode.CODE_60008);
        Supplier supplier = supplierService.selectSupplierById(supplierId);
        if (supplier == null) throw new ServiceException("供应商不存在", SrmErrorCode.CODE_60001);
        return supplier;
    }

    private SupplierIndustryInfo firstIndustry(Long supplierId)
    {
        SupplierIndustryInfo q = new SupplierIndustryInfo();
        q.setSupplierId(supplierId);
        List<SupplierIndustryInfo> rows = industryInfoService.selectSupplierIndustryInfoList(q);
        return rows == null || rows.isEmpty() ? null : rows.get(0);
    }

    private List<SupplierFormerName> formerNames(Long supplierId)
    {
        SupplierFormerName q = new SupplierFormerName();
        q.setSupplierId(supplierId);
        List<SupplierFormerName> rows = formerNameService.selectSupplierFormerNameList(q);
        return rows == null ? new ArrayList<>() : rows;
    }

    private List<SupplierShareholder> shareholders(Long supplierId)
    {
        SupplierShareholder q = new SupplierShareholder();
        q.setSupplierId(supplierId);
        List<SupplierShareholder> rows = shareholderService.selectSupplierShareholderList(q);
        return rows == null ? new ArrayList<>() : rows;
    }

    private String str(Object v) { return v == null ? null : String.valueOf(v); }

    private Long longOf(Object v)
    {
        if (v == null) return null;
        if (v instanceof Number) return ((Number) v).longValue();
        return Long.valueOf(String.valueOf(v));
    }

    private boolean boolOf(Object v)
    {
        if (v == null) return false;
        if (v instanceof Boolean) return (Boolean) v;
        return Boolean.parseBoolean(String.valueOf(v));
    }

    private SupplierPerson personQuery(Long supplierId)
    {
        SupplierPerson q = new SupplierPerson();
        q.setSupplierId(supplierId);
        return q;
    }

    private SupplierContact contactQuery(Long supplierId)
    {
        SupplierContact q = new SupplierContact();
        q.setSupplierId(supplierId);
        return q;
    }

    /** 本地沉淀命中：主要人员与联系方式在本地库均已有数据 => 无需再外调天眼查 818/819。 */
    private boolean localDetailSedimentHit(Long supplierId)
    {
        List<SupplierPerson> persons = personService.selectSupplierPersonList(personQuery(supplierId));
        if (persons == null || persons.isEmpty()) return false;
        List<SupplierContact> contacts = contactService.selectSupplierContactList(contactQuery(supplierId));
        return contacts != null && !contacts.isEmpty();
    }
}