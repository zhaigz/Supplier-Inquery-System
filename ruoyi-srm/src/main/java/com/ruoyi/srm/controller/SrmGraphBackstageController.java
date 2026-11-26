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
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.constant.SrmConstants;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.AnalysisTask;
import com.ruoyi.srm.domain.GraphEdge;
import com.ruoyi.srm.domain.GraphNode;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.dto.GraphExportVo;
import com.ruoyi.srm.gateway.CallContext;
import com.ruoyi.srm.gateway.TycGateway;
import com.ruoyi.srm.gateway.TycResult;
import com.ruoyi.srm.service.IAnalysisTaskService;
import com.ruoyi.srm.service.IGraphEdgeService;
import com.ruoyi.srm.service.IGraphNodeService;
import com.ruoyi.srm.service.IGraphProjectionService;
import com.ruoyi.srm.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面6「幕后关系图谱」接口，共 6 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 *
 * <p>TODO[天眼查接入] 本类调用的天眼查编号（共 2 个）。接入时只需在 application.yml 的 
 * <code>srm.tyc.apis.&lt;编号&gt;</code> 补 path/method，并把 TYC_TOKEN 注入环境变量，代码无需改动：
 * <ul><li>1123（ACTUAL_CONTROLLER · 疑似实际控制人）</li></ul>
 * <ul><li>1161（BACKSTAGE_RELATION · 幕后关系（10层））</li></ul>
 */
@RestController
@RequestMapping("/srm/graph/backstage")
public class SrmGraphBackstageController extends BaseController
{
    @Autowired private ISupplierService supplierService;
    @Autowired private IAnalysisTaskService analysisTaskService;
    @Autowired private IGraphNodeService graphNodeService;
    @Autowired private IGraphEdgeService graphEdgeService;
    @Autowired private IGraphProjectionService graphProjectionService;
    @Autowired private TycGateway tycGateway;

    /**
     * 发起幕后关系任务
     *
     * <p>页面 6「幕后关系图谱」；权限 srm:graphBackstage:add；外调 天眼查 1123（ACTUAL_CONTROLLER）、1161（BACKSTAGE_RELATION）（异步触发）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphBackstage:add')")
    @SrmAudit(module="幕后关系", action="发起", object="{p:supplierId}", detail="发起幕后关系任务")
    @PostMapping("/init")
    public AjaxResult init(@RequestBody Map<String,Object> body)
    {
        Long supplierId = longOf(body == null ? null : body.get("supplierId"));
        Supplier supplier = requireSupplier(supplierId);
        String subjectKey = subjectKey(supplier);
        // 本地优先（核心语义）：本地库已沉淀该主体幕后关系结果时直接回放，不重复付费外调天眼查。
        if (!boolOf(body == null ? null : body.get("force")))
        {
            AnalysisTask local = findLocalTask(SrmConstants.TASK_TYPE_BACKSTAGE, SrmConstants.BIZ_SCENE_PAGE6, subjectKey);
            if (local != null) return success(localReplay(local));
        }
        AnalysisTask task = new AnalysisTask();
        task.setTaskNo(nextTaskNo());
        task.setTaskType(SrmConstants.TASK_TYPE_BACKSTAGE);
        task.setBizScene(SrmConstants.BIZ_SCENE_PAGE6);
        task.setSubjectKey(subjectKey);
        task.setTaskStatus(SrmConstants.TASK_STATUS_QUEUED);
        task.setProgressStep("QUEUED");
        task.setBillFlag(SrmConstants.BILL_FLAG_UNBILLED);
        task.setEnv(SrmConstants.CACHE_ENV_PROD);
        task.setCreateBy(getUsername());
        task.setCreateTime(new Date());
        analysisTaskService.insertAnalysisTask(task);

        CallContext ctx = new CallContext();
        ctx.setManual(true);
        ctx.setTaskId(task.getId());
        ctx.setScene(SrmConstants.SCENE_PROJECT_ANALYSIS);
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("keyword", subjectKey);
        // 按《天眼查API接口文档》1161 幕后关系（10层）口径补齐透传：
        // types 关系口径（SERVE_ALL/INVEST/BRANCH/HISTORICAL_SHAREHOLDER/HISTORICAL_SERVE_ALL/ALL，默认 ALL）；
        // depth 深度 [1,10]；idFrom/nameFrom 源公司（起止主体中必输其一）；idTo/nameTo 目标公司（可选）。
        String types = str(body == null ? null : body.get("types"));
        params.put("types", StringUtils.isEmpty(types) ? "ALL" : types.trim().toUpperCase());
        params.put("depth", Math.max(1, Math.min(intOf(body == null ? null : body.get("depth"), 10), 10)));
        if (StringUtils.isNotEmpty(supplier.getTycCompanyId())) params.put("idFrom", supplier.getTycCompanyId());
        params.put("nameFrom", supplier.getSupplierName());
        Long targetId = longOf(body == null ? null : firstNonNull(body.get("targetSupplierId"), body.get("supplierIdTo")));
        if (targetId != null)
        {
            Supplier target = supplierService.selectSupplierById(targetId);
            if (target == null) throw new ServiceException("目标供应商不存在: " + targetId, SrmErrorCode.CODE_60001);
            if (StringUtils.isNotEmpty(target.getTycCompanyId())) params.put("idTo", target.getTycCompanyId());
            params.put("nameTo", target.getSupplierName());
        }
        List<Map<String,Object>> calls = new ArrayList<>();
        int graphRows = 0;
        // TYC-CALL 外调天眼查 1161（BACKSTAGE_RELATION · 幕后关系（10层））4.00 元/次，发起幕后关系任务必调；成功后落 graph_node/graph_edge
        TycResult r1161 = tycGateway.call(SrmApiId.BACKSTAGE_RELATION, subjectKey, params, ctx);
        graphRows += graphProjectionService.projectBackstage(task.getId(), subjectKey, r1161);
        calls.add(describe(r1161));
        // TYC-CALL 外调天眼查 1123（ACTUAL_CONTROLLER · 疑似实际控制人）1.00 元/次；控人链路并入同一 task 图谱
        TycResult r1123 = tycGateway.call(SrmApiId.ACTUAL_CONTROLLER, subjectKey, params, ctx);
        graphRows += graphProjectionService.projectActualController(task.getId(), subjectKey, r1123);
        calls.add(describe(r1123));

        task.setTaskStatus(SrmConstants.TASK_STATUS_SUCCESS);
        task.setProgressStep("DONE");
        task.setResultCount(graphRows);
        task.setUnitConsumed(totalUnit(calls));
        task.setBillFlag(totalUnit(calls).signum() > 0 ? SrmConstants.BILL_FLAG_BILLED : SrmConstants.BILL_FLAG_UNBILLED);
        task.setFinishTime(new Date());
        task.setUpdateTime(new Date());
        analysisTaskService.updateAnalysisTask(task);

        Map<String,Object> data = new LinkedHashMap<>();
        data.put("taskNo", task.getTaskNo());
        data.put("taskId", task.getId());
        data.put("taskStatus", task.getTaskStatus());
        data.put("source", "tyc");
        data.put("resultCount", task.getResultCount());
        data.put("calls", calls);
        return success(data);
    }

    /**
     * 轮询进度
     *
     * <p>页面 6「幕后关系图谱」；权限 srm:graphBackstage:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphBackstage:query')")
    @GetMapping("/progress/{taskNo}")
    public AjaxResult progress(@PathVariable String taskNo)
    {
        AnalysisTask task = requireTask(taskNo);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("taskNo", task.getTaskNo());
        data.put("taskStatus", task.getTaskStatus());
        data.put("progressStep", task.getProgressStep());
        data.put("resultCount", task.getResultCount());
        data.put("errCode", task.getErrCode());
        data.put("errMsg", task.getErrMsg());
        data.put("finishTime", task.getFinishTime());
        return success(data);
    }

    /**
     * 取节点/连线/共同指向
     *
     * <p>页面 6「幕后关系图谱」；权限 srm:graphBackstage:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphBackstage:query')")
    @GetMapping("/result/{taskNo}")
    public AjaxResult result(@PathVariable String taskNo)
    {
        AnalysisTask task = requireTask(taskNo);
        GraphNode nq = new GraphNode();
        nq.setTaskId(task.getId());
        GraphEdge eq = new GraphEdge();
        eq.setTaskId(task.getId());
        List<GraphNode> nodes = graphNodeService.selectGraphNodeList(nq);
        List<GraphEdge> edges = graphEdgeService.selectGraphEdgeList(eq);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("taskNo", task.getTaskNo());
        data.put("taskStatus", task.getTaskStatus());
        data.put("source", "local");
        data.put("dataSource", "本地库（天眼查回写沉淀的 task 快照）");
        data.put("nodes", nodes);
        data.put("edges", edges);
        data.put("sharedTargets", sharedTargets(nodes, edges));
        return success(data);
    }

    /**
     * 与本地人员/股东对齐
     *
     * <p>页面 6「幕后关系图谱」；权限 srm:graphBackstage:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphBackstage:query')")
    @GetMapping("/align/{taskNo}")
    public AjaxResult align(@PathVariable String taskNo)
    {
        AnalysisTask task = requireTask(taskNo);
        GraphNode nq = new GraphNode();
        nq.setTaskId(task.getId());
        List<GraphNode> nodes = graphNodeService.selectGraphNodeList(nq);
        List<Map<String,Object>> aligned = new ArrayList<>();
        for (GraphNode node : nodes == null ? new ArrayList<GraphNode>() : nodes)
        {
            Map<String,Object> m = new LinkedHashMap<>();
            m.put("nodeKey", node.getNodeKey());
            m.put("nodeName", node.getNodeName());
            m.put("supplierId", node.getSupplierId());
            m.put("matched", node.getSupplierId() != null);
            aligned.add(m);
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("taskNo", task.getTaskNo());
        data.put("source", "local");
        data.put("aligned", aligned);
        return success(data);
    }

    /**
     * 历史任务清单
     *
     * <p>页面 6「幕后关系图谱」；权限 srm:graphBackstage:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphBackstage:list')")
    @GetMapping("/task/list")
    public TableDataInfo taskList(AnalysisTask query)
    {
        AnalysisTask q = query == null ? new AnalysisTask() : query;
        q.setTaskType(SrmConstants.TASK_TYPE_BACKSTAGE);
        startPage();
        return getDataTable(analysisTaskService.selectAnalysisTaskList(q));
    }

    /**
     * 导出幕后关系
     *
     * <p>页面 6「幕后关系图谱」；权限 srm:graphBackstage:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphBackstage:export')")
    @Log(title="幕后关系", businessType=BusinessType.EXPORT)
    @SrmAudit(module="幕后关系", action="导出", object="{p:taskNo}", detail="导出幕后关系")
    @PostMapping("/export")
    public void export(@RequestBody(required=false) Map<String,Object> body, HttpServletResponse response)
    {
        String taskNo = str(body == null ? null : body.get("taskNo"));
        AnalysisTask task = requireTask(taskNo);
        List<GraphExportVo> rows = new ArrayList<>();
        GraphNode nq = new GraphNode();
        nq.setTaskId(task.getId());
        for (GraphNode n : nvlNodes(graphNodeService.selectGraphNodeList(nq)))
        {
            GraphExportVo v = new GraphExportVo();
            v.setTaskNo(task.getTaskNo());
            v.setItemType("节点");
            v.setNodeKey(n.getNodeKey());
            v.setNodeType(n.getNodeType());
            v.setNodeName(n.getNodeName());
            v.setCreditCode(n.getCreditCode());
            v.setLevelNo(n.getLevelNo());
            v.setRegStatus(n.getRegStatus());
            v.setAlignResult(n.getSupplierId() == null ? "未对齐" : "已对齐");
            rows.add(v);
        }
        GraphEdge eq = new GraphEdge();
        eq.setTaskId(task.getId());
        for (GraphEdge e : nvlEdges(graphEdgeService.selectGraphEdgeList(eq)))
        {
            GraphExportVo v = new GraphExportVo();
            v.setTaskNo(task.getTaskNo());
            v.setItemType("连线");
            v.setFromNodeKey(e.getFromNodeKey());
            v.setToNodeKey(e.getToNodeKey());
            v.setRelType(e.getRelType());
            v.setRatio(e.getRatio() == null ? null : e.getRatio().toPlainString());
            v.setRatioLabel(e.getRatioLabel());
            rows.add(v);
        }
        new ExcelUtil<GraphExportVo>(GraphExportVo.class).exportExcel(response, rows, "幕后关系图谱");
    }

    private List<Map<String,Object>> sharedTargets(List<GraphNode> nodes, List<GraphEdge> edges)
    {
        Map<String,Integer> counter = new LinkedHashMap<>();
        for (GraphEdge e : edges == null ? new ArrayList<GraphEdge>() : edges)
        {
            if (e.getToNodeKey() != null) counter.put(e.getToNodeKey(), counter.getOrDefault(e.getToNodeKey(), 0) + 1);
        }
        List<Map<String,Object>> out = new ArrayList<>();
        for (Map.Entry<String,Integer> en : counter.entrySet())
        {
            if (en.getValue() > 1)
            {
                Map<String,Object> m = new LinkedHashMap<>();
                m.put("nodeKey", en.getKey());
                m.put("inDegree", en.getValue());
                out.add(m);
            }
        }
        return out;
    }

    /**
     * 本地命中判定：同一主体、同一场景、任务成功且已落图谱结果 => 本地库已有沉淀，直接回放。
     */
    private AnalysisTask findLocalTask(String taskType, String bizScene, String subjectKey)
    {
        AnalysisTask q = new AnalysisTask();
        q.setTaskType(taskType);
        q.setBizScene(bizScene);
        q.setSubjectKey(subjectKey);
        q.setTaskStatus(SrmConstants.TASK_STATUS_SUCCESS);
        List<AnalysisTask> rows = analysisTaskService.selectAnalysisTaskList(q);
        if (rows == null) return null;
        for (AnalysisTask t : rows)
        {
            if (t.getResultCount() != null && t.getResultCount() > 0) return t;
        }
        return null;
    }

    /** 本地回放响应：localHit=true、不外调、unitConsumed=0。 */
    private Map<String,Object> localReplay(AnalysisTask local)
    {
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("taskNo", local.getTaskNo());
        data.put("taskId", local.getId());
        data.put("taskStatus", local.getTaskStatus());
        data.put("localHit", true);
        data.put("dataSource", "本地库（历史天眼查回写沉淀）");
        data.put("unitConsumed", java.math.BigDecimal.ZERO);
        data.put("calls", new ArrayList<>());
        return data;
    }

    private AnalysisTask requireTask(String taskNo)
    {
        if (StringUtils.isEmpty(taskNo)) throw new ServiceException("taskNo不能为空", SrmErrorCode.CODE_60008);
        AnalysisTask q = new AnalysisTask();
        q.setTaskNo(taskNo);
        List<AnalysisTask> rows = analysisTaskService.selectAnalysisTaskList(q);
        if (rows == null || rows.isEmpty()) throw new ServiceException("任务不存在", SrmErrorCode.CODE_60001);
        return rows.get(0);
    }

    private Supplier requireSupplier(Long supplierId)
    {
        if (supplierId == null) throw new ServiceException("supplierId不能为空", SrmErrorCode.CODE_60008);
        Supplier supplier = supplierService.selectSupplierById(supplierId);
        if (supplier == null) throw new ServiceException("供应商不存在", SrmErrorCode.CODE_60001);
        return supplier;
    }

    private String subjectKey(Supplier supplier)
    {
        return StringUtils.isNotEmpty(supplier.getCreditCode()) ? supplier.getCreditCode() : supplier.getSupplierName();
    }

    private Map<String,Object> describe(TycResult r)
    {
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("apiId", r.getApiId());
        m.put("cacheHit", r.isCacheHit());
        m.put("blocked", r.isBlocked());
        m.put("blockReason", r.getBlockReason());
        m.put("unitConsumed", r.getUnitConsumed());
        m.put("fetchedAt", r.getFetchedAt());
        return m;
    }

    private java.math.BigDecimal totalUnit(List<Map<String,Object>> calls)
    {
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        for (Map<String,Object> c : calls)
        {
            Object u = c.get("unitConsumed");
            if (u instanceof java.math.BigDecimal) total = total.add((java.math.BigDecimal) u);
        }
        return total;
    }

    private Object firstNonNull(Object a, Object b) { return a != null ? a : b; }

    private String nextTaskNo()
    {
        return "BS" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    private List<GraphNode> nvlNodes(List<GraphNode> list) { return list == null ? new ArrayList<>() : list; }
    private List<GraphEdge> nvlEdges(List<GraphEdge> list) { return list == null ? new ArrayList<>() : list; }
    private String str(Object v) { return v == null ? null : String.valueOf(v); }
    private boolean boolOf(Object v) { return v != null && ("true".equalsIgnoreCase(String.valueOf(v)) || "1".equals(String.valueOf(v))); }
    private Long longOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).longValue(); return Long.valueOf(String.valueOf(v)); }
    private Integer intOf(Object v, int def) { if (v == null) return def; if (v instanceof Number) return ((Number) v).intValue(); try { return Integer.valueOf(String.valueOf(v)); } catch (Exception e) { return def; } }
}