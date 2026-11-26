package com.ruoyi.srm.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.constant.SrmConstants;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.dto.EnrichDto;
import com.ruoyi.srm.domain.dto.MonitorDto;
import com.ruoyi.srm.domain.dto.SupplierCreateDto;
import com.ruoyi.srm.domain.dto.SupplierUpdateDto;
import com.ruoyi.srm.gateway.CallContext;
import com.ruoyi.srm.gateway.TycGateway;
import com.ruoyi.srm.gateway.TycResult;
import com.ruoyi.srm.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面1「供应商列表」接口，共 8 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 *
 * <p>TODO[天眼查接入] 本类调用的天眼查编号（共 1 个）。接入时只需在 application.yml 的 
 * <code>srm.tyc.apis.&lt;编号&gt;</code> 补 path/method，并把 TYC_TOKEN 注入环境变量，代码无需改动：
 * <ul><li>818（BASIC_INFO · 企业基本信息（含企业联系方式））</li></ul>
 */
@RestController
@RequestMapping("/srm/supplier")
public class SrmSupplierController extends BaseController
{
    @Autowired private ISupplierService supplierService;
    @Autowired private TycGateway tycGateway;

    /**
     * 分页列表（八列）
     *
     * <p>页面 1「供应商列表」；权限 srm:supplier:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(Supplier query)
    {
        if (StringUtils.isEmpty(query.getDelFlag())) query.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
        startPage(); startOrderBy();
        return getDataTable(supplierService.selectSupplierList(query));
    }

    /**
     * 编辑回显（主档+行业+曾用名）
     *
     * <p>页面 1「供应商列表」；权限 srm:supplier:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplier:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        Supplier supplier = supplierService.selectSupplierById(id);
        if (supplier == null) throw new ServiceException("供应商不存在", SrmErrorCode.CODE_60001);
        Map<String,Object> data = new LinkedHashMap<>(); data.put("supplier", supplier); data.put("industry", new LinkedHashMap<>()); data.put("formerNames", Collections.emptyList());
        return success(data);
    }

    /**
     * 新增主档（本地）
     *
     * <p>页面 1「供应商列表」；权限 srm:supplier:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplier:add')")
    @Log(title="供应商主档", businessType=BusinessType.INSERT)
    @SrmAudit(module="供应商主档", action="新增", object="供应商主档", detail="新增供应商")
    @PostMapping
    public AjaxResult add(@RequestBody SupplierCreateDto dto)
    {
        require(!StringUtils.isEmpty(dto.getSupplierName()), "supplierName不能为空");
        require(dto.getSupplierName().length() <= 200, "supplierName长度不能超过200");
        ensureCreditUnique(dto.getCreditCode(), null);
        Supplier s = new Supplier(); copy(dto,s);
        String scene = StringUtils.isEmpty(dto.getEntryScene()) ? "manual" : dto.getEntryScene();
        s.setSupplierCode(nextSupplierCode());
        s.setDataSource("query_miss".equalsIgnoreCase(scene) ? SrmConstants.DATA_SOURCE_QUERY_MISS : SrmConstants.DATA_SOURCE_MANUAL);
        s.setDataVersion(1); s.setMonitorFlag(StringUtils.isEmpty(dto.getMonitorFlag()) ? SrmConstants.NO : dto.getMonitorFlag()); s.setStatus(StringUtils.isEmpty(dto.getStatus()) ? SrmConstants.NO : dto.getStatus()); s.setDelFlag(SrmConstants.DEL_FLAG_EXIST); s.setCreateBy(getUsername()); s.setCreateTime(new Date());
        supplierService.insertSupplier(s); return success(s.getId());
    }

    /**
     * 修改主档
     *
     * <p>页面 1「供应商列表」；权限 srm:supplier:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplier:edit')")
    @Log(title="供应商主档", businessType=BusinessType.UPDATE)
    @SrmAudit(module="供应商主档", action="修改", object="{p:id}", detail="修改供应商")
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierUpdateDto dto)
    {
        require(dto.getId()!=null, "id不能为空"); Supplier old=supplierService.selectSupplierById(dto.getId()); if(old==null) throw new ServiceException("供应商不存在",SrmErrorCode.CODE_60001);
        ensureCreditUnique(dto.getCreditCode(), dto.getId()); Supplier s=new Supplier(); s.setId(dto.getId()); copy(dto,s); s.setUpdateBy(getUsername()); s.setUpdateTime(new Date()); supplierService.updateSupplier(s); return success();
    }

    /**
     * 勾选/取消重点监控
     *
     * <p>页面 1「供应商列表」；权限 srm:supplier:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplier:edit')")
    @Log(title="供应商监控", businessType=BusinessType.UPDATE)
    @SrmAudit(module="供应商主档", action="启停", object="重点监控", detail="更新重点监控标志")
    @PutMapping("/monitor")
    public AjaxResult monitor(@RequestBody MonitorDto dto)
    {
        require(dto.getIds()!=null && !dto.getIds().isEmpty(), "ids不能为空"); require("0".equals(dto.getMonitorFlag())||"1".equals(dto.getMonitorFlag()), "monitorFlag只能为0或1");
        return success(supplierService.updateSupplierMonitorFlag(dto.getIds().toArray(new Long[0]), dto.getMonitorFlag()));
    }

    /**
     * 批量逻辑删除
     *
     * <p>页面 1「供应商列表」；权限 srm:supplier:remove；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplier:remove')")
    @Log(title="供应商主档", businessType=BusinessType.DELETE)
    @SrmAudit(module="供应商主档", action="移出", object="{arg0}", detail="批量逻辑删除")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids)
    {
        Long[] values=parseIds(ids); if(values.length==0) throw new ServiceException("ids不能为空",SrmErrorCode.CODE_60008);
        Supplier patch=new Supplier(); patch.setDelFlag(SrmConstants.DEL_FLAG_DELETED); patch.setUpdateBy(getUsername()); patch.setUpdateTime(new Date()); for(Long id:values){patch.setId(id); supplierService.updateSupplier(patch);} return success();
    }

    /**
     * 工商补全（按需一次 818）
     *
     * <p>页面 1「供应商列表」；权限 srm:supplier:edit；外调 天眼查 818（BASIC_INFO）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplier:edit')")
    @Log(title="供应商工商补全", businessType=BusinessType.UPDATE)
    @SrmAudit(module="供应商主档", action="补全", object="{p:supplierId}", detail="工商补全")
    @RepeatSubmit
    @PostMapping("/enrich")
    public AjaxResult enrich(@RequestBody EnrichDto dto)
    {
        require(dto.getSupplierId()!=null,"supplierId不能为空"); Supplier supplier=supplierService.selectSupplierById(dto.getSupplierId()); if(supplier==null) throw new ServiceException("供应商不存在",SrmErrorCode.CODE_60001);
        // 本地优先（核心语义）：主档工商信息已在本地库沉淀完整时直接回放，不重复付费外调天眼查。
        if(!dto.isForce()&&localSedimentHit(supplier))
        {
            Map<String,Object> replay=new LinkedHashMap<>();
            replay.put("localHit",true); replay.put("dataSource","本地库（历史天眼查回写沉淀）");
            replay.put("unitConsumed",java.math.BigDecimal.ZERO); replay.put("cacheHit",false);
            replay.put("fetchedAt",supplier.getLastUpdateAt()); replay.put("emptyReason",null);
            replay.put("supplier",supplier);
            return success(replay);
        }
        String subjectKey=!StringUtils.isEmpty(supplier.getCreditCode())?supplier.getCreditCode():supplier.getSupplierName(); Map<String,Object> params=new LinkedHashMap<>(); params.put("keyword",subjectKey);
        CallContext ctx=new CallContext(); ctx.setManual(true); ctx.setScene(SrmConstants.SCENE_SINGLE_ENRICH); ctx.setForce(dto.isForce());
        // TODO[天眼查接入] 待接入天眼查 818：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
        // TYC-CALL 外调天眼查 818（BASIC_INFO · 企业基本信息（含企业联系方式））0.20 元/次，列表"工商补全"按需触发
        TycResult r=tycGateway.call(SrmApiId.BASIC_INFO,subjectKey,params,ctx);
        // 818 成功后 BasicInfoProjector 已在网关第⑥段同步回填主档，重新查回以返回补全后的最新值（闭环）
        Supplier enriched=supplierService.selectSupplierById(dto.getSupplierId());
        Map<String,Object> data=new LinkedHashMap<>(); data.put("cacheHit",r.isCacheHit()); data.put("unitConsumed",r.getUnitConsumed()); data.put("emptyReason",r.getData()==null?"无可用外部数据":null); data.put("fetchedAt",r.getFetchedAt()); data.put("cacheExpireAt",r.getCacheExpireAt()); data.put("supplier",enriched!=null?enriched:supplier); if(r.isBlocked()) data.put("blockReason",r.getBlockReason()); return success(data);
    }

    /**
     * 导出当前筛选结果
     *
     * <p>页面 1「供应商列表」；权限 srm:supplier:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplier:export')")
    @Log(title="供应商列表", businessType=BusinessType.EXPORT)
    @SrmAudit(module="供应商主档", action="导出", object="供应商列表", detail="导出当前筛选结果")
    @PostMapping("/export")
    public void export(@RequestBody(required=false) Supplier query, HttpServletResponse response)
    {
        if(query==null) query=new Supplier(); if(StringUtils.isEmpty(query.getDelFlag())) query.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
        List<Supplier> rows=supplierService.selectSupplierList(query); new ExcelUtil<Supplier>(Supplier.class).exportExcel(response,rows,"供应商列表");
    }

    private void copy(SupplierCreateDto d,Supplier s){s.setSupplierName(d.getSupplierName());s.setCreditCode(d.getCreditCode());s.setRegNo(d.getRegNo());s.setLegalPerson(d.getLegalPerson());s.setRegCapital(d.getRegCapital());s.setRegCapitalNum(d.getRegCapitalNum());s.setEstablishDate(d.getEstablishDate());s.setBizStatus(d.getBizStatus());s.setIndustry(d.getIndustry());s.setRegAddress(d.getRegAddress());s.setPhone(d.getPhone());s.setEmail(d.getEmail());s.setProvince(d.getProvince());s.setCity(d.getCity());s.setDistrict(d.getDistrict());s.setMonitorFlag(d.getMonitorFlag());s.setStatus(d.getStatus());s.setRemark(d.getRemark());}
    private void ensureCreditUnique(String creditCode,Long id){if(StringUtils.isEmpty(creditCode))return; Supplier q=new Supplier();q.setCreditCode(creditCode);List<Supplier> list=supplierService.selectSupplierList(q);if(list!=null)for(Supplier s:list)if(id==null||!id.equals(s.getId()))throw new ServiceException("统一社会信用代码已存在",SrmErrorCode.CODE_60009);}
    private String nextSupplierCode(){String day=new SimpleDateFormat("yyyyMMdd").format(new Date()); Supplier q=new Supplier();q.setSupplierCode("SUP"+day);List<Supplier> list=supplierService.selectSupplierList(q);int n=list==null?0:list.size()+1;return String.format("SUP%s%04d",day,n);}
    private Long[] parseIds(String ids){return Arrays.stream(ids.split(",")).map(String::trim).filter(x->x.matches("\\d+")).map(Long::valueOf).toArray(Long[]::new);}
    private void require(boolean ok,String msg){if(!ok)throw new ServiceException(msg,SrmErrorCode.CODE_60008);}
    /** 本地沉淀命中：信用代码/法人/注册资本/联系电话等工商要素已在本地库落库完整 => 无需再外调天眼查 818。 */
    private boolean localSedimentHit(Supplier s)
    {
        return !StringUtils.isEmpty(s.getCreditCode())&&!StringUtils.isEmpty(s.getLegalPerson())
                &&!StringUtils.isEmpty(s.getRegCapital())&&!StringUtils.isEmpty(s.getPhone())
                &&!StringUtils.isEmpty(s.getRegAddress());
    }
}
