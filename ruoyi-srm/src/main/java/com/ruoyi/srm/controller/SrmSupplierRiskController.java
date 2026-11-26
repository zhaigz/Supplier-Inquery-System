package com.ruoyi.srm.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.RiskAdminPunish;
import com.ruoyi.srm.domain.RiskDishonest;
import com.ruoyi.srm.domain.RiskEnforce;
import com.ruoyi.srm.domain.RiskJudicialSummary;
import com.ruoyi.srm.domain.RiskTaxOwed;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.dto.RiskExportVo;
import com.ruoyi.srm.gateway.CallContext;
import com.ruoyi.srm.gateway.TycGateway;
import com.ruoyi.srm.gateway.TycResult;
import com.ruoyi.srm.service.IRiskAdminPunishService;
import com.ruoyi.srm.service.IRiskDishonestService;
import com.ruoyi.srm.service.IRiskEnforceService;
import com.ruoyi.srm.service.IRiskJudicialSummaryService;
import com.ruoyi.srm.service.IRiskTaxOwedService;
import com.ruoyi.srm.service.ISupplierService;
import com.ruoyi.srm.util.SrmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面4「司法风险 / 经营风险」接口，共 8 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 *
 * <p>TODO[天眼查接入] 本类调用的天眼查编号（共 7 个）。接入时只需在 application.yml 的 
 * <code>srm.tyc.apis.&lt;编号&gt;</code> 补 path/method，并把 TYC_TOKEN 注入环境变量，代码无需改动：
 * <ul><li>839（ENFORCE · 被执行人）</li></ul>
 * <ul><li>846（ADMIN_PUNISH_ILLEGAL · 严重违法）</li></ul>
 * <ul><li>848（ADMIN_PUNISH_ABNORMAL · 经营异常）</li></ul>
 * <ul><li>851（TAX_OWED · 欠税公告）</li></ul>
 * <ul><li>871（ENFORCE_HIST · 历史被执行人）</li></ul>
 * <ul><li>872（DISHONEST · 历史失信人）</li></ul>
 * <ul><li>1002（JUDICIAL_SUMMARY · 司法风险）</li></ul>
 */
@RestController
@RequestMapping("/srm/supplier/risk")
public class SrmSupplierRiskController extends BaseController
{
    @Autowired private ISupplierService supplierService;
    @Autowired private IRiskJudicialSummaryService summaryService;
    @Autowired private IRiskEnforceService enforceService;
    @Autowired private IRiskDishonestService dishonestService;
    @Autowired private IRiskAdminPunishService punishService;
    @Autowired private IRiskTaxOwedService taxOwedService;
    @Autowired private TycGateway tycGateway;

    /**
     * 七维度汇总卡（统一本地优先 + 来源标识透出 样板）
     *
     * <p>页面 4「司法风险 / 经营风险」；权限 srm:risk:query；外调 天眼查 1002（JUDICIAL_SUMMARY）
     *
     * <p>先查本地 srm_risk_judicial_summary，命中直接回放并标 {@code source=local}；本地为空才
     * 自动付费外调 1002（JudicialSummaryProjector 回写），标 {@code source=tyc} + 回传 {@code unitConsumed}。
     * 维度明细由顶层结构改为嵌套在 {@code data.dimensions}（dimension 中文名 → 行对象）。
     */
    @PreAuthorize("@ss.hasPermi('srm:risk:query')")
    @GetMapping("/summary/{supplierId}")
    public AjaxResult summary(@PathVariable Long supplierId,
                              @RequestParam(required=false, defaultValue="false") boolean refresh)
    {
        requireSupplier(supplierId);
        Map<String,Object> data = new LinkedHashMap<>();
        // ① 本地优先
        List<RiskJudicialSummary> local = summaryRows(supplierId);
        if (!local.isEmpty())
        {
            data.put("source", "local");
            data.put("dataSource", "本地库（历史天眼查 1002 回写沉淀）");
            data.put("cacheHit", false);
            data.put("unitConsumed", BigDecimal.ZERO);
        }
        else
        {
            // ② 本地为空 => 自动付费兜底外调 1002（组合接口一次拉 7 维度）
            autoPayCall(data, supplierId, refresh, SrmApiId.JUDICIAL_SUMMARY);
            local = summaryRows(supplierId);
        }
        Map<String,Object> dimensions = new LinkedHashMap<>();
        for (RiskJudicialSummary row : local) dimensions.put(row.getDimension(), row);
        data.put("dimensions", dimensions);
        return success(data);
    }

    /**
     * 被执行/历史被执行（统一本地优先 + 来源标识透出 样板）
     *
     * <p>页面 4「司法风险 / 经营风险」；权限 srm:risk:list；外调 天眼查 839（ENFORCE）、871（ENFORCE_HIST）
     *
     * <p>先查本地 srm_risk_enforce（按 histFlag 过滤），命中回放标 {@code source=local}；本地为空才按
     * histFlag 选编号自动付费兜底（null→839+871，0→839，1→871），标 {@code source=tyc} + 合计 {@code unitConsumed}。
     */
    @PreAuthorize("@ss.hasPermi('srm:risk:list')")
    @GetMapping("/enforce")
    public AjaxResult enforce(@RequestParam(required=false) Long supplierId,
                              @RequestParam(required=false) String histFlag,
                              @RequestParam(required=false, defaultValue="false") boolean refresh)
    {
        if (supplierId == null) throw new ServiceException("supplierId不能为空", SrmErrorCode.CODE_60008);
        requireSupplier(supplierId);
        Map<String,Object> data = new LinkedHashMap<>();
        RiskEnforce q = new RiskEnforce();
        q.setSupplierId(supplierId);
        q.setHistFlag(histFlag);
        List<RiskEnforce> local = nvl(enforceService.selectRiskEnforceList(q));
        if (!local.isEmpty())
        {
            data.put("source", "local");
            data.put("dataSource", "本地库（历史天眼查 839/871 回写沉淀）");
            data.put("cacheHit", false);
            data.put("unitConsumed", BigDecimal.ZERO);
            data.put("rows", local);
            data.put("total", local.size());
            return success(data);
        }
        // 本地为空 => 按 histFlag 选编号自动付费兜底：TYC-CALL 839（ENFORCE）/871（ENFORCE_HIST）
        int[] apiIds = "0".equals(histFlag) ? new int[] { SrmApiId.ENFORCE }
                : "1".equals(histFlag) ? new int[] { SrmApiId.ENFORCE_HIST }
                : new int[] { SrmApiId.ENFORCE, SrmApiId.ENFORCE_HIST };
        autoPayCall(data, supplierId, refresh, apiIds);
        List<RiskEnforce> after = nvl(enforceService.selectRiskEnforceList(q));
        data.put("rows", after);
        data.put("total", after.size());
        return success(data);
    }

    /**
     * 失信人明细（统一本地优先 + 来源标识透出 样板）
     *
     * <p>页面 4「司法风险 / 经营风险」；权限 srm:risk:list；外调 天眼查 872（DISHONEST）
     *
     * <p>先查本地 srm_risk_dishonest，命中回放标 {@code source=local}；本地为空才自动付费外调 872。
     */
    @PreAuthorize("@ss.hasPermi('srm:risk:list')")
    @GetMapping("/dishonest")
    public AjaxResult dishonest(@RequestParam(required=false) Long supplierId,
                               @RequestParam(required=false, defaultValue="false") boolean refresh)
    {
        if (supplierId == null) throw new ServiceException("supplierId不能为空", SrmErrorCode.CODE_60008);
        requireSupplier(supplierId);
        Map<String,Object> data = new LinkedHashMap<>();
        RiskDishonest q = new RiskDishonest();
        q.setSupplierId(supplierId);
        List<RiskDishonest> local = nvl(dishonestService.selectRiskDishonestList(q));
        if (!local.isEmpty())
        {
            data.put("source", "local");
            data.put("dataSource", "本地库（历史天眼查 872 回写沉淀）");
            data.put("cacheHit", false);
            data.put("unitConsumed", BigDecimal.ZERO);
            data.put("rows", local);
            data.put("total", local.size());
            return success(data);
        }
        // 本地为空 => 自动付费兜底：TYC-CALL 872（DISHONEST · 历史失信人）0.10 元/次
        autoPayCall(data, supplierId, refresh, SrmApiId.DISHONEST);
        List<RiskDishonest> after = nvl(dishonestService.selectRiskDishonestList(q));
        data.put("rows", after);
        data.put("total", after.size());
        return success(data);
    }

    /**
     * 严重违法/经营异常（统一本地优先 + 来源标识透出 样板）
     *
     * <p>页面 4「司法风险 / 经营风险」；权限 srm:risk:list；外调 天眼查 846（ADMIN_PUNISH_ILLEGAL）、848（ADMIN_PUNISH_ABNORMAL）
     *
     * <p>先查本地 srm_risk_admin_punish（按 riskType 过滤），命中回放标 {@code source=local}；本地为空才按
     * riskType 选编号自动付费兜底（null→846+848，illegal→846，abnormal→848）。
     */
    @PreAuthorize("@ss.hasPermi('srm:risk:list')")
    @GetMapping("/punish")
    public AjaxResult punish(@RequestParam(required=false) Long supplierId,
                             @RequestParam(required=false) String riskType,
                             @RequestParam(required=false, defaultValue="false") boolean refresh)
    {
        if (supplierId == null) throw new ServiceException("supplierId不能为空", SrmErrorCode.CODE_60008);
        requireSupplier(supplierId);
        Map<String,Object> data = new LinkedHashMap<>();
        RiskAdminPunish q = new RiskAdminPunish();
        q.setSupplierId(supplierId);
        q.setRiskType(riskType);
        List<RiskAdminPunish> local = nvl(punishService.selectRiskAdminPunishList(q));
        if (!local.isEmpty())
        {
            data.put("source", "local");
            data.put("dataSource", "本地库（历史天眼查 846/848 回写沉淀）");
            data.put("cacheHit", false);
            data.put("unitConsumed", BigDecimal.ZERO);
            data.put("rows", local);
            data.put("total", local.size());
            return success(data);
        }
        // 本地为空 => 按 riskType 选编号自动付费兜底：TYC-CALL 846（严重违法）/848（经营异常）
        int[] apiIds = "illegal".equals(riskType) ? new int[] { SrmApiId.ADMIN_PUNISH_ILLEGAL }
                : "abnormal".equals(riskType) ? new int[] { SrmApiId.ADMIN_PUNISH_ABNORMAL }
                : new int[] { SrmApiId.ADMIN_PUNISH_ILLEGAL, SrmApiId.ADMIN_PUNISH_ABNORMAL };
        autoPayCall(data, supplierId, refresh, apiIds);
        List<RiskAdminPunish> after = nvl(punishService.selectRiskAdminPunishList(q));
        data.put("rows", after);
        data.put("total", after.size());
        return success(data);
    }

    /**
     * 欠税公告（统一本地优先 + 来源标识透出 样板）
     *
     * <p>页面 4「司法风险 / 经营风险」；权限 srm:risk:list；外调 天眼查 851（TAX_OWED）
     *
     * <p>先查本地 srm_risk_tax_owed，命中回放标 {@code source=local}；本地为空才自动付费外调 851。
     */
    @PreAuthorize("@ss.hasPermi('srm:risk:list')")
    @GetMapping("/tax")
    public AjaxResult tax(@RequestParam(required=false) Long supplierId,
                         @RequestParam(required=false, defaultValue="false") boolean refresh)
    {
        if (supplierId == null) throw new ServiceException("supplierId不能为空", SrmErrorCode.CODE_60008);
        requireSupplier(supplierId);
        Map<String,Object> data = new LinkedHashMap<>();
        RiskTaxOwed q = new RiskTaxOwed();
        q.setSupplierId(supplierId);
        List<RiskTaxOwed> local = nvl(taxOwedService.selectRiskTaxOwedList(q));
        if (!local.isEmpty())
        {
            data.put("source", "local");
            data.put("dataSource", "本地库（历史天眼查 851 回写沉淀）");
            data.put("cacheHit", false);
            data.put("unitConsumed", BigDecimal.ZERO);
            data.put("rows", local);
            data.put("total", local.size());
            return success(data);
        }
        // 本地为空 => 自动付费兜底：TYC-CALL 851（TAX_OWED · 欠税公告）0.15 元/次
        autoPayCall(data, supplierId, refresh, SrmApiId.TAX_OWED);
        List<RiskTaxOwed> after = nvl(taxOwedService.selectRiskTaxOwedList(q));
        data.put("rows", after);
        data.put("total", after.size());
        return success(data);
    }

    /**
     * 刷新全量风险（六段）
     *
     * <p>页面 4「司法风险 / 经营风险」；权限 srm:risk:edit；外调 天眼查 839（ENFORCE）、846（ADMIN_PUNISH_ILLEGAL）、848（ADMIN_PUNISH_ABNORMAL）、851（TAX_OWED）、871（ENFORCE_HIST）、872（DISHONEST）、1002（JUDICIAL_SUMMARY）
     */
    @PreAuthorize("@ss.hasPermi('srm:risk:edit')")
    @SrmAudit(module="司法风险", action="刷新", object="{p:supplierId}", detail="刷新全量风险六段")
    @PostMapping("/refresh")
    public AjaxResult refresh(@RequestBody Map<String,Object> body)
    {
        Long supplierId = longOf(body == null ? null : body.get("supplierId"));
        requireSupplier(supplierId);
        CallContext ctx = context(body);
        Map<String,Object> params = params(supplierId);
        List<Map<String,Object>> calls = new ArrayList<>();
        // TYC-CALL 外调天眼查 1002（JUDICIAL_SUMMARY · 司法风险（组合））1.50 元/次
        calls.add(call(SrmApiId.JUDICIAL_SUMMARY, supplierId, params, ctx));
        // TYC-CALL 外调天眼查 839（ENFORCE · 被执行人）0.15 元/次
        calls.add(call(SrmApiId.ENFORCE, supplierId, params, ctx));
        // TYC-CALL 外调天眼查 871（ENFORCE_HIST · 历史被执行人）0.10 元/次
        calls.add(call(SrmApiId.ENFORCE_HIST, supplierId, params, ctx));
        // TYC-CALL 外调天眼查 872（DISHONEST · 历史失信人）0.10 元/次
        calls.add(call(SrmApiId.DISHONEST, supplierId, params, ctx));
        // TYC-CALL 外调天眼查 846（ADMIN_PUNISH_ILLEGAL · 严重违法）0.15 元/次
        calls.add(call(SrmApiId.ADMIN_PUNISH_ILLEGAL, supplierId, params, ctx));
        // TYC-CALL 外调天眼查 848（ADMIN_PUNISH_ABNORMAL · 经营异常）0.15 元/次
        calls.add(call(SrmApiId.ADMIN_PUNISH_ABNORMAL, supplierId, params, ctx));
        // TYC-CALL 外调天眼查 851（TAX_OWED · 欠税公告）0.15 元/次
        calls.add(call(SrmApiId.TAX_OWED, supplierId, params, ctx));
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("supplierId", supplierId);
        data.put("calls", calls);
        return success(data);
    }

    /**
     * 接口元数据/分页上限
     *
     * <p>页面 4「司法风险 / 经营风险」；权限 srm:risk:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:risk:list')")
    @GetMapping("/meta")
    public AjaxResult meta()
    {
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("pageSizeMax", SrmUtils.RISK_PAGE_SIZE_MAX);
        data.put("dimensions", new String[] {"enforce", "dishonest", "illegal", "abnormal", "taxOwed", "judicialSummary"});
        data.put("refreshSegments", 7);
        return success(data);
    }

    /**
     * 导出风险清单
     *
     * <p>页面 4「司法风险 / 经营风险」；权限 srm:risk:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:risk:export')")
    @Log(title="风险清单", businessType=BusinessType.EXPORT)
    @SrmAudit(module="司法风险", action="导出", object="{p:supplierId}", detail="导出风险清单")
    @PostMapping("/export")
    public void export(@RequestBody(required=false) Map<String,Object> body, HttpServletResponse response)
    {
        Long supplierId = longOf(body == null ? null : body.get("supplierId"));
        if (supplierId == null) throw new ServiceException("supplierId不能为空", SrmErrorCode.CODE_60008);
        requireSupplier(supplierId);
        List<RiskExportVo> rows = new ArrayList<>();
        RiskEnforce qe = new RiskEnforce();
        qe.setSupplierId(supplierId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (RiskEnforce r : nvl(enforceService.selectRiskEnforceList(qe)))
        {
            RiskExportVo v = new RiskExportVo();
            v.setSupplierId(supplierId);
            v.setRiskCategory("1".equals(r.getHistFlag()) ? "历史被执行" : "被执行");
            v.setBizNo(r.getCaseNo());
            v.setOrg(r.getCourt());
            v.setPartyName(r.getPersonName());
            v.setAmount(r.getTargetAmount());
            v.setHappenedAt(r.getCreateDate() == null ? null : df.format(r.getCreateDate()));
            rows.add(v);
        }
        RiskDishonest qd = new RiskDishonest();
        qd.setSupplierId(supplierId);
        for (RiskDishonest r : nvl(dishonestService.selectRiskDishonestList(qd)))
        {
            RiskExportVo v = new RiskExportVo();
            v.setSupplierId(supplierId);
            v.setRiskCategory("失信人");
            v.setBizNo(r.getCaseNo());
            v.setOrg(r.getCourt());
            v.setPartyName(r.getPersonName());
            v.setAmount(r.getAmount());
            v.setHappenedAt(r.getPublishTime() == null ? null : df.format(r.getPublishTime()));
            v.setDetail(r.getPerformStatus());
            rows.add(v);
        }
        RiskAdminPunish qp = new RiskAdminPunish();
        qp.setSupplierId(supplierId);
        for (RiskAdminPunish r : nvl(punishService.selectRiskAdminPunishList(qp)))
        {
            RiskExportVo v = new RiskExportVo();
            v.setSupplierId(supplierId);
            v.setRiskCategory(r.getRiskType());
            v.setOrg(r.getListDept());
            v.setHappenedAt(r.getListDate() == null ? null : df.format(r.getListDate()));
            v.setDetail(r.getListReason());
            rows.add(v);
        }
        RiskTaxOwed qt = new RiskTaxOwed();
        qt.setSupplierId(supplierId);
        for (RiskTaxOwed r : nvl(taxOwedService.selectRiskTaxOwedList(qt)))
        {
            RiskExportVo v = new RiskExportVo();
            v.setSupplierId(supplierId);
            v.setRiskCategory("欠税公告");
            v.setOrg(r.getTaxAuthority());
            v.setPartyName(r.getTaxpayerName());
            v.setAmount(r.getOwedAmount());
            v.setHappenedAt(r.getPublishTime() == null ? null : df.format(r.getPublishTime()));
            rows.add(v);
        }
        new ExcelUtil<RiskExportVo>(RiskExportVo.class).exportExcel(response, rows, "风险清单");
    }

    private Map<String,Object> call(int apiId, Long supplierId, Map<String,Object> params, CallContext ctx)
    {
        // TODO[天眼查接入] 待接入天眼查 1002、839、871、872、846、848、851：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
        // TYC-CALL 外调天眼查：apiId 由 refresh 调用方传入，取值限定为下列 7 个风险类编号
        //          1002 司法风险（组合）/ 839 被执行人 / 871 历史被执行人 / 872 历史失信人 /
        //          846 严重违法 / 848 经营异常 / 851 欠税公告
        // TYC-CALL 外调天眼查 1002（JUDICIAL_SUMMARY · 司法风险（组合））
        // TYC-CALL 外调天眼查 839（ENFORCE · 被执行人）
        // TYC-CALL 外调天眼查 871（ENFORCE_HIST · 历史被执行人）
        // TYC-CALL 外调天眼查 872（DISHONEST · 历史失信人）
        // TYC-CALL 外调天眼查 846（ADMIN_PUNISH_ILLEGAL · 严重违法）
        // TYC-CALL 外调天眼查 848（ADMIN_PUNISH_ABNORMAL · 经营异常）
        // TYC-CALL 外调天眼查 851（TAX_OWED · 欠税公告）
        TycResult r = tycGateway.call(apiId, subjectKey(supplierId), params, ctx);
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("apiId", r.getApiId());
        m.put("cacheHit", r.isCacheHit());
        m.put("blocked", r.isBlocked());
        m.put("blockReason", r.getBlockReason());
        m.put("unitConsumed", r.getUnitConsumed());
        m.put("fetchedAt", r.getFetchedAt());
        return m;
    }

    /**
     * 付费兜底外调：按编号列表自动调用天眼查，并把每笔的成本/缓存/拦截标识聚合透出。
     *
     * <p>每笔外调由 TycGateway 落 srm_api_call_log（含 unit_consumed/cache_hit/call_result），
     * 本方法只做响应侧聚合：{@code data.source}（local/tyc/blocked）、{@code data.unitConsumed}
     * （本次批量扣费合计，命中原始缓存则为 0）、{@code data.cacheHit}/{@code data.blocked}/{@code data.blockReason}。
     * {@code force=refresh} 时绕过原始缓存强制重拉。
     */
    private void autoPayCall(Map<String,Object> data, Long supplierId, boolean force, int... apiIds)
    {
        BigDecimal consumed = BigDecimal.ZERO;
        boolean anyCacheHit = false;
        boolean anyBlocked = false;
        StringBuilder reasons = new StringBuilder();
        for (int apiId : apiIds)
        {
            TycResult r = callSubject(apiId, supplierId, force);
            if (r.getUnitConsumed() != null) consumed = consumed.add(r.getUnitConsumed());
            anyCacheHit |= r.isCacheHit();
            anyBlocked |= r.isBlocked();
            if (StringUtils.isNotEmpty(r.getBlockReason()))
            {
                if (reasons.length() > 0) reasons.append("; ");
                reasons.append(apiId).append(':').append(r.getBlockReason());
            }
        }
        data.put("source", anyBlocked ? "blocked" : "tyc");
        data.put("cacheHit", anyCacheHit);
        data.put("unitConsumed", consumed);
        data.put("blocked", anyBlocked);
        data.put("blockReason", reasons.length() == 0 ? null : reasons.toString());
    }

    private TycResult callSubject(int apiId, Long supplierId, boolean force)
    {
        CallContext ctx = new CallContext();
        ctx.setManual(true);
        ctx.setScene(com.ruoyi.srm.constant.SrmConstants.SCENE_MONITOR_SCAN);
        ctx.setForce(force);
        // TODO[天眼查接入] 待接入天眼查 1002、839、871、872、846、848、851：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
        // TYC-CALL 外调天眼查：apiId 由各 GET 本地沉淀为空时自动触发，取值限定为下列 7 个风险类编号
        //          1002 司法风险（组合）/ 839 被执行人 / 871 历史被执行人 / 872 历史失信人 /
        //          846 严重违法 / 848 经营异常 / 851 欠税公告
        return tycGateway.call(apiId, subjectKey(supplierId), params(supplierId), ctx);
    }

    private CallContext context(Map<String,Object> body)
    {
        CallContext ctx = new CallContext();
        ctx.setManual(true);
        ctx.setScene(com.ruoyi.srm.constant.SrmConstants.SCENE_MONITOR_SCAN);
        ctx.setForce(boolOf(body == null ? null : body.get("force")));
        return ctx;
    }

    private Map<String,Object> params(Long supplierId)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("keyword", subjectKey(supplierId));
        return params;
    }

    private String subjectKey(Long supplierId)
    {
        Supplier supplier = requireSupplier(supplierId);
        return StringUtils.isNotEmpty(supplier.getCreditCode()) ? supplier.getCreditCode() : supplier.getSupplierName();
    }

    private Supplier requireSupplier(Long supplierId)
    {
        if (supplierId == null) throw new ServiceException("supplierId不能为空", SrmErrorCode.CODE_60008);
        Supplier supplier = supplierService.selectSupplierById(supplierId);
        if (supplier == null) throw new ServiceException("供应商不存在", SrmErrorCode.CODE_60001);
        return supplier;
    }

    private List<RiskJudicialSummary> summaryRows(Long supplierId)
    {
        RiskJudicialSummary q = new RiskJudicialSummary();
        q.setSupplierId(supplierId);
        return nvl(summaryService.selectRiskJudicialSummaryList(q));
    }

    private <T> List<T> nvl(List<T> list) { return list == null ? new ArrayList<>() : list; }
    private Long longOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).longValue(); return Long.valueOf(String.valueOf(v)); }
    private boolean boolOf(Object v) { if (v == null) return false; if (v instanceof Boolean) return (Boolean) v; return Boolean.parseBoolean(String.valueOf(v)); }
}