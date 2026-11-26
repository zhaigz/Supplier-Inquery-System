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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.GraphEdge;
import com.ruoyi.srm.domain.GraphNode;
import com.ruoyi.srm.domain.RelClue;
import com.ruoyi.srm.domain.ReportVersion;
import com.ruoyi.srm.domain.dto.ReportClueExportVo;
import com.ruoyi.srm.service.IGraphEdgeService;
import com.ruoyi.srm.service.IGraphNodeService;
import com.ruoyi.srm.service.IRelClueService;
import com.ruoyi.srm.service.IReportVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面13「分析报告版本」接口，共 7 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/report-version")
public class SrmReportVersionController extends BaseController
{
    @Autowired private IReportVersionService reportVersionService;
    @Autowired private IRelClueService relClueService;
    @Autowired private IGraphNodeService graphNodeService;
    @Autowired private IGraphEdgeService graphEdgeService;

    /**
     * 版本清单
     *
     * <p>页面 13「分析报告版本」；权限 srm:reportVersion:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:reportVersion:list')")
    @GetMapping("/list")
    public TableDataInfo list(ReportVersion query)
    {
        startPage(); startOrderBy();
        return getDataTable(reportVersionService.selectReportVersionList(query == null ? new ReportVersion() : query));
    }

    /**
     * 版本详情
     *
     * <p>页面 13「分析报告版本」；权限 srm:reportVersion:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:reportVersion:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(requireVersion(id));
    }

    /**
     * 版本内线索（只读快照）
     *
     * <p>页面 13「分析报告版本」；权限 srm:reportVersion:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:reportVersion:list')")
    @GetMapping("/clue/{id}")
    public TableDataInfo clue(@PathVariable Long id)
    {
        requireVersion(id);
        RelClue q = new RelClue();
        q.setReportVersionId(id);
        startPage(); startOrderBy();
        return getDataTable(relClueService.selectRelClueList(q));
    }

    /**
     * 图谱回放
     *
     * <p>页面 13「分析报告版本」；权限 srm:reportVersion:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:reportVersion:query')")
    @GetMapping("/graph/{id}")
    public AjaxResult graph(@PathVariable Long id)
    {
        ReportVersion version = requireVersion(id);
        RelClue q = new RelClue();
        q.setReportVersionId(id);
        List<RelClue> clues = relClueService.selectRelClueList(q);
        List<Map<String,Object>> nodes = new ArrayList<>();
        List<Map<String,Object>> edges = new ArrayList<>();
        for (RelClue c : clues == null ? new ArrayList<RelClue>() : clues)
        {
            nodes.add(node(c.getSupplierA(), c.getEntAName(), c.getEntATycId()));
            nodes.add(node(c.getSupplierB(), c.getEntBName(), c.getEntBTycId()));
            Map<String,Object> edge = new LinkedHashMap<>();
            edge.put("from", c.getSupplierA());
            edge.put("to", c.getSupplierB());
            edge.put("relType", c.getClueType());
            edge.put("relPath", c.getRelPath());
            edge.put("weight", c.getWeight());
            edges.add(edge);
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("version", version);
        data.put("nodes", nodes);
        data.put("edges", edges);
        return success(data);
    }

    /**
     * 锁定/解锁
     *
     * <p>页面 13「分析报告版本」；权限 srm:reportVersion:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:reportVersion:edit')")
    @Log(title="报告版本", businessType=BusinessType.UPDATE)
    @SrmAudit(module="报告版本", action="锁定", object="{p:id}", detail="锁定/解锁版本")
    @PutMapping("/lock/{id}")
    public AjaxResult lock(@PathVariable Long id)
    {
        ReportVersion version = requireVersion(id);
        ReportVersion patch = new ReportVersion();
        patch.setId(id);
        patch.setIsLocked("1".equals(version.getIsLocked()) ? "0" : "1");
        patch.setUpdateBy(getUsername());
        patch.setUpdateTime(new Date());
        reportVersionService.updateReportVersion(patch);
        return success(patch.getIsLocked());
    }

    /**
     * 导出快照
     *
     * <p>页面 13「分析报告版本」；权限 srm:reportVersion:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:reportVersion:export')")
    @Log(title="报告版本", businessType=BusinessType.EXPORT)
    @SrmAudit(module="报告版本", action="导出", object="{p:id}", detail="导出快照")
    @PostMapping("/export/{id}")
    public void export(@PathVariable Long id, HttpServletResponse response)
    {
        ReportVersion version = requireVersion(id);
        RelClue q = new RelClue();
        q.setReportVersionId(id);
        List<RelClue> clues = relClueService.selectRelClueList(q);
        List<ReportClueExportVo> rows = new ArrayList<>();
        for (RelClue c : clues == null ? new ArrayList<RelClue>() : clues)
        {
            ReportClueExportVo v = new ReportClueExportVo();
            v.setEntAName(c.getEntAName());
            v.setEntBName(c.getEntBName());
            v.setClueType(c.getClueType());
            v.setRuleName(c.getRuleName());
            v.setWeight(c.getWeight());
            v.setRelPath(c.getRelPath());
            v.setReviewStatus(c.getReviewStatus());
            rows.add(v);
        }
        ReportVersion patch = new ReportVersion();
        patch.setId(id);
        patch.setExportCount(version.getExportCount() == null ? 1 : version.getExportCount() + 1);
        patch.setUpdateTime(new Date());
        reportVersionService.updateReportVersion(patch);
        new ExcelUtil<ReportClueExportVo>(ReportClueExportVo.class).exportExcel(response, rows, "报告版本快照");
    }

    /**
     * 两版本差异
     *
     * <p>页面 13「分析报告版本」；权限 srm:reportVersion:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:reportVersion:query')")
    @GetMapping("/diff/{idA}/{idB}")
    public AjaxResult diff(@PathVariable Long idA, @PathVariable Long idB)
    {
        ReportVersion a = requireVersion(idA);
        ReportVersion b = requireVersion(idB);
        RelClue qa = new RelClue();
        qa.setReportVersionId(idA);
        RelClue qb = new RelClue();
        qb.setReportVersionId(idB);
        List<RelClue> listA = relClueService.selectRelClueList(qa);
        List<RelClue> listB = relClueService.selectRelClueList(qb);
        Map<String,RelClue> mapA = keyBy(listA);
        Map<String,RelClue> mapB = keyBy(listB);
        List<RelClue> added = new ArrayList<>();
        List<RelClue> removed = new ArrayList<>();
        for (String k : mapB.keySet()) if (!mapA.containsKey(k)) added.add(mapB.get(k));
        for (String k : mapA.keySet()) if (!mapB.containsKey(k)) removed.add(mapA.get(k));
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("versionA", a);
        data.put("versionB", b);
        data.put("added", added);
        data.put("removed", removed);
        data.put("addedCount", added.size());
        data.put("removedCount", removed.size());
        return success(data);
    }

    private Map<String,RelClue> keyBy(List<RelClue> rows)
    {
        Map<String,RelClue> map = new LinkedHashMap<>();
        for (RelClue c : rows == null ? new ArrayList<RelClue>() : rows)
        {
            map.put(String.valueOf(c.getSupplierA()) + "->" + String.valueOf(c.getSupplierB()) + "#" + String.valueOf(c.getClueType()), c);
        }
        return map;
    }

    private Map<String,Object> node(Long id, String name, String tycId)
    {
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("name", name);
        m.put("tycId", tycId);
        return m;
    }

    private ReportVersion requireVersion(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        ReportVersion version = reportVersionService.selectReportVersionById(id);
        if (version == null) throw new ServiceException("版本不存在", SrmErrorCode.CODE_60001);
        return version;
    }
}