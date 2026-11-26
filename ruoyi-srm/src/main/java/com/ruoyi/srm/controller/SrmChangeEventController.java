package com.ruoyi.srm.controller;

import java.text.SimpleDateFormat;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.ChangeEvent;
import com.ruoyi.srm.domain.DataSnapshot;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.dto.ChangeEventExportVo;
import com.ruoyi.srm.service.IChangeEventService;
import com.ruoyi.srm.service.IDataSnapshotService;
import com.ruoyi.srm.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面8「变更监控清单」接口，共 5 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 *
 * <p>TODO[天眼查接入] 需求差异（本类当前无外调，清单口径）：本页在 134 条清单中标注“无外调”。
 * 但《供应商查询系统需求及建设说明书 V2.1》要求变更监控扫描比对以下 5 个编号；因“轮询/扫描”接口
 * 不在本轮 134 条清单内，按“不增删条目”约束未在此类补调用。接入前需先拍板并同步更新
 * api_manifest_v1.0.json / 接口清单_全量.md，再经 TycGateway.call 落点：
 * <ul><li>821（SHAREHOLDER · 企业股东）</li></ul>
 * <ul><li>458（FORMER_NAME · 企业曾用名）</li></ul>
 * <ul><li>848（ADMIN_PUNISH_ABNORMAL · 经营异常）</li></ul>
 * <ul><li>846（ADMIN_PUNISH_ILLEGAL · 严重违法）</li></ul>
 * <ul><li>1002（JUDICIAL_SUMMARY · 司法风险）</li></ul>
 */
@RestController
@RequestMapping("/srm/change-event")
public class SrmChangeEventController extends BaseController
{
    @Autowired private IChangeEventService changeEventService;
    @Autowired private IDataSnapshotService dataSnapshotService;
    @Autowired private ISupplierService supplierService;

    /**
     * 变更清单 13 列
     *
     * <p>页面 8「变更监控清单」；权限 srm:changeEvent:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:changeEvent:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChangeEvent query)
    {
        startPage(); startOrderBy();
        return getDataTable(changeEventService.selectChangeEventList(query == null ? new ChangeEvent() : query));
    }

    /**
     * 事件详情+前后快照
     *
     * <p>页面 8「变更监控清单」；权限 srm:changeEvent:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:changeEvent:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        ChangeEvent event = requireEvent(id);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("event", event);
        data.put("prevSnapshot", snapshot(event.getPrevSnapshotId()));
        data.put("currSnapshot", snapshot(event.getCurrSnapshotId()));
        return success(data);
    }

    /**
     * 处置（改状态）
     *
     * <p>页面 8「变更监控清单」；权限 srm:changeEvent:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:changeEvent:edit')")
    @Log(title="变更事件", businessType=BusinessType.UPDATE)
    @SrmAudit(module="变更监控", action="处置", object="{p:id}", detail="变更事件处置改状态")
    @PutMapping("/handle")
    public AjaxResult handle(@RequestBody Map<String,Object> body)
    {
        Long id = longOf(body == null ? null : body.get("id"));
        ChangeEvent event = requireEvent(id);
        ChangeEvent patch = new ChangeEvent();
        patch.setId(id);
        patch.setHandleStatus(str(body == null ? null : body.get("handleStatus")));
        if (StringUtils.isEmpty(patch.getHandleStatus())) patch.setHandleStatus("已处置");
        patch.setHandleBy(getUsername());
        patch.setHandleAt(new Date());
        patch.setUpdateBy(getUsername());
        patch.setUpdateTime(new Date());
        changeEventService.updateChangeEvent(patch);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("id", event.getId());
        data.put("handleStatus", patch.getHandleStatus());
        data.put("handleBy", patch.getHandleBy());
        data.put("handleAt", patch.getHandleAt());
        return success(data);
    }

    /**
     * 维度/企业统计+成本
     *
     * <p>页面 8「变更监控清单」；权限 srm:changeEvent:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:changeEvent:list')")
    @GetMapping("/stat")
    public AjaxResult stat(ChangeEvent query)
    {
        ChangeEvent q = query == null ? new ChangeEvent() : query;
        List<ChangeEvent> rows = changeEventService.selectChangeEventList(q);
        Map<String,Integer> byDimension = new LinkedHashMap<>();
        Map<String,Integer> byHandle = new LinkedHashMap<>();
        Map<String,Integer> bySupplier = new LinkedHashMap<>();
        for (ChangeEvent e : rows == null ? new ArrayList<ChangeEvent>() : rows)
        {
            byDimension.put(nvl(e.getDimension(), "未分类"), byDimension.getOrDefault(nvl(e.getDimension(), "未分类"), 0) + 1);
            byHandle.put(nvl(e.getHandleStatus(), "待处置"), byHandle.getOrDefault(nvl(e.getHandleStatus(), "待处置"), 0) + 1);
            bySupplier.put(nvl(e.getSupplierName(), "未知企业"), bySupplier.getOrDefault(nvl(e.getSupplierName(), "未知企业"), 0) + 1);
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("total", rows == null ? 0 : rows.size());
        data.put("byDimension", byDimension);
        data.put("byHandleStatus", byHandle);
        data.put("bySupplier", bySupplier);
        data.put("cost", 0);
        return success(data);
    }

    /**
     * 导出变更清单
     *
     * <p>页面 8「变更监控清单」；权限 srm:changeEvent:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:changeEvent:export')")
    @Log(title="变更清单", businessType=BusinessType.EXPORT)
    @SrmAudit(module="变更监控", action="导出", object="变更清单", detail="导出变更清单")
    @PostMapping("/export")
    public void export(@RequestBody(required=false) ChangeEvent query, HttpServletResponse response)
    {
        List<ChangeEvent> rows = changeEventService.selectChangeEventList(query == null ? new ChangeEvent() : query);
        List<ChangeEventExportVo> out = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ChangeEvent e : rows == null ? new ArrayList<ChangeEvent>() : rows)
        {
            ChangeEventExportVo v = new ChangeEventExportVo();
            Supplier supplier = e.getSupplierId() == null ? null : supplierService.selectSupplierById(e.getSupplierId());
            v.setSupplierCode(supplier == null ? null : supplier.getSupplierCode());
            v.setSupplierName(e.getSupplierName());
            v.setCreditCode(e.getCreditCode());
            v.setLegalPerson(e.getLegalPerson());
            v.setIndustry(supplier == null ? null : supplier.getIndustry());
            v.setMonitorStatus(e.getMonitorStatus());
            v.setChangeAt(e.getChangeAt() == null ? null : df.format(e.getChangeAt()));
            v.setChangeItem(e.getChangeItem());
            v.setDimension(e.getDimension());
            v.setLevelTag(e.getLevelTag());
            v.setBeforeValue(e.getBeforeValue());
            v.setAfterValue(e.getAfterValue());
            v.setHandleStatus(e.getHandleStatus());
            out.add(v);
        }
        new ExcelUtil<ChangeEventExportVo>(ChangeEventExportVo.class).exportExcel(response, out, "变更监控清单");
    }

    private DataSnapshot snapshot(Long id)
    {
        if (id == null) return null;
        return dataSnapshotService.selectDataSnapshotById(id);
    }

    private ChangeEvent requireEvent(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        ChangeEvent event = changeEventService.selectChangeEventById(id);
        if (event == null) throw new ServiceException("变更事件不存在", SrmErrorCode.CODE_60001);
        return event;
    }

    private String nvl(String v, String def) { return StringUtils.isEmpty(v) ? def : v; }
    private Long longOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).longValue(); return Long.valueOf(String.valueOf(v)); }
    private String str(Object v) { return v == null ? null : String.valueOf(v); }
}