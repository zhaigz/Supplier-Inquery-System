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
 * 页面5「股权穿透图谱」接口，共 6 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 *
 * <p>TODO[天眼查接入] 本类调用的天眼查编号（共 3 个）。接入时只需在 application.yml 的 
 * <code>srm.tyc.apis.&lt;编号&gt;</code> 补 path/method，并把 TYC_TOKEN 注入环境变量，代码无需改动：
 * <ul><li>821（SHAREHOLDER · 企业股东）</li></ul>
 * <ul><li>1123（ACTUAL_CONTROLLER · 疑似实际控制人）</li></ul>
 * <ul><li>1160（EQUITY_PENETRATION · 股权穿透（10层））</li></ul>
 */
@RestController
@RequestMapping("/srm/graph/equity")
public class SrmEquityGraphController extends BaseController
{
    @Autowired private ISupplierService supplierService;
    @Autowired private IAnalysisTaskService analysisTaskService;
    @Autowired private IGraphNodeService graphNodeService;
    @Autowired private IGraphEdgeService graphEdgeService;
    @Autowired private IGraphProjectionService graphProjectionService;
    @Autowired private TycGateway tycGateway;

    /**
     * 发起穿透任务
     *
     * <p>页面 5「股权穿透图谱」；权限 srm:graphEquity:add；外调 天眼查 821（SHAREHOLDER）、1123（ACTUAL_CONTROLLER）、1160（EQUITY_PENETRATION）（异步触发）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphEquity:add')")
    @SrmAudit(module="股权穿透", action="发起", object="{p:supplierId}", detail="发起穿透任务")
    @PostMapping("/init")
    public AjaxResult init(@RequestBody Map<String,Object> body)
    {
        Long supplierId = longOf(body == null ? null : body.get("supplierId"));
        Supplier supplier = requireSupplier(supplierId);
        String subjectKey = subjectKey(supplier);
        // 本地优先（核心语义）：本系统已在本地库沉淀过该主体的穿透结果时，直接回放本地数据，不重复付费外调天眼查。
        if (!boolOf(body == null ? null : body.get("force")))
        {
            AnalysisTask local = findLocalTask(SrmConstants.TASK_TYPE_EQUITY, SrmConstants.BIZ_SCENE_PAGE5, subjectKey);
            if (local != null) return success(localReplay(local));
        }
        AnalysisTask task = new AnalysisTask();
        task.setTaskNo(nextTaskNo());
        task.setTaskType(SrmConstants.TASK_TYPE_EQUITY);
        task.setBizScene(SrmConstants.BIZ_SCENE_PAGE5);
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
        Map<String,Object> params = equityParams(body);
        params.put("keyword", subjectKey);
        List<Map<String,Object>> calls = new ArrayList<>();
        int graphRows = 0;
        // TYC-CALL 外调天眼查 1160（EQUITY_PENETRATION · 股权穿透（10层））4.00 元/次，发起穿透任务必调；成功后落 graph_node/graph_edge
        TycResult r1160 = tycGateway.call(SrmApiId.EQUITY_PENETRATION, subjectKey, params, ctx);
        graphRows += graphProjectionService.projectEquity(task.getId(), subjectKey, r1160);
        calls.add(describe(r1160));
        // TYC-CALL 外调天眼查 821（SHAREHOLDER · 企业股东）0.15 元/次，分页每页 20 条；ShareholderProjector 流水线自动回写 supplier_shareholder
        calls.add(describe(tycGateway.call(SrmApiId.SHAREHOLDER, subjectKey, params, ctx)));
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
     * 轮询进度（不计费）
     *
     * <p>页面 5「股权穿透图谱」；权限 srm:graphEquity:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphEquity:query')")
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
     * 取图谱结果
     *
     * <p>页面 5「股权穿透图谱」；权限 srm:graphEquity:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphEquity:query')")
    @GetMapping("/result/{taskNo}")
    public AjaxResult result(@PathVariable String taskNo)
    {
        AnalysisTask task = requireTask(taskNo);
        GraphNode nq = new GraphNode();
        nq.setTaskId(task.getId());
        GraphEdge eq = new GraphEdge();
        eq.setTaskId(task.getId());
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("taskNo", task.getTaskNo());
        data.put("taskStatus", task.getTaskStatus());
        data.put("source", "local");
        data.put("dataSource", "本地库（天眼查回写沉淀的 task 快照）");
        data.put("nodes", graphNodeService.selectGraphNodeList(nq));
        data.put("edges", graphEdgeService.selectGraphEdgeList(eq));
        return success(data);
    }

    /**
     * 节点续穿
     *
     * <p>页面 5「股权穿透图谱」；权限 srm:graphEquity:edit；外调 天眼查 1160（EQUITY_PENETRATION）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphEquity:edit')")
    @SrmAudit(module="股权穿透", action="续穿", object="{p:nodeId}", detail="节点续穿")
    @PostMapping("/expand")
    public AjaxResult expand(@RequestBody Map<String,Object> body)
    {
        String nodeKey = str(body == null ? null : body.get("nodeKey"));
        if (StringUtils.isEmpty(nodeKey)) throw new ServiceException("nodeKey不能为空", SrmErrorCode.CODE_60008);
        Long taskId = body != null && body.get("taskId") != null ? longOf(body.get("taskId")) : null;
        Map<String,Object> params = equityParams(body);
        params.put("keyword", nodeKey);
        String dir = String.valueOf(params.get("dir"));

        // 本地优先（核心语义，与 /init 一致）：先查本地库是否已沉淀该节点的图谱数据，命中则直接合并/回放，不外调天眼查。
        if (!boolOf(body == null ? null : body.get("force")))
        {
            AnalysisTask localTask = findLocalTaskForNode(taskId, nodeKey);
            if (localTask != null && (taskId == null || !localTask.getId().equals(taskId)))
            {
                int merged = taskId == null ? 0 : mergeLocalTaskGraph(taskId, localTask.getId());
                return success(localExpandReplay(nodeKey, merged, "本地库（合并历史穿透快照）"));
            }
            if (taskId != null && hasLocalEdgesInTask(taskId, nodeKey, dir))
            {
                return success(localExpandReplay(nodeKey, 0, "本地库（当前任务快照已沉淀）"));
            }
        }

        CallContext ctx = new CallContext();
        ctx.setManual(true);
        if (taskId != null) ctx.setTaskId(taskId);
        ctx.setScene(SrmConstants.SCENE_PROJECT_ANALYSIS);
        // TODO[天眼查接入] 待接入天眼查 1160：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
        // TYC-CALL 外调天眼查 1160（EQUITY_PENETRATION · 股权穿透（10层））4.00 元/次，节点续穿
        TycResult r = tycGateway.call(SrmApiId.EQUITY_PENETRATION, nodeKey, params, ctx);
        // 续穿结果并入发起时的 task 图谱（body 传了 taskId 才落库）
        int expandRows = ctx.getTaskId() == null ? 0 : graphProjectionService.projectEquity(ctx.getTaskId(), nodeKey, r);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("nodeKey", nodeKey);
        data.put("expandCount", expandRows);
        data.put("localHit", false);
        data.put("source", "tyc");
        data.put("result", describe(r));
        return success(data);
    }

    /**
     * 节点详情
     *
     * <p>页面 5「股权穿透图谱」；权限 srm:graphEquity:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphEquity:query')")
    @GetMapping("/node/{nodeId}")
    public AjaxResult node(@PathVariable Long nodeId)
    {
        GraphNode node = graphNodeService.selectGraphNodeById(nodeId);
        if (node == null) throw new ServiceException("节点不存在", SrmErrorCode.CODE_60001);
        GraphNode nq = new GraphNode();
        nq.setTaskId(node.getTaskId());
        GraphEdge eq = new GraphEdge();
        eq.setTaskId(node.getTaskId());
        List<GraphNode> allNodes = graphNodeService.selectGraphNodeList(nq);
        List<GraphEdge> allEdges = graphEdgeService.selectGraphEdgeList(eq);
        List<GraphEdge> related = new ArrayList<>();
        if (allEdges != null)
        {
            for (GraphEdge e : allEdges)
            {
                if (node.getNodeKey() != null && (node.getNodeKey().equals(e.getFromNodeKey()) || node.getNodeKey().equals(e.getToNodeKey()))) related.add(e);
            }
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("node", node);
        data.put("nodes", allNodes);
        data.put("edges", related);
        return success(data);
    }

    /**
     * 导出节点/连线
     *
     * <p>页面 5「股权穿透图谱」；权限 srm:graphEquity:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:graphEquity:export')")
    @Log(title="股权穿透", businessType=BusinessType.EXPORT)
    @SrmAudit(module="股权穿透", action="导出", object="{p:taskNo}", detail="导出节点/连线")
    @PostMapping("/export")
    public void export(@RequestBody(required=false) Map<String,Object> body, HttpServletResponse response)
    {
        String taskNo = str(body == null ? null : body.get("taskNo"));
        AnalysisTask task = requireTask(taskNo);
        GraphNode nq = new GraphNode();
        nq.setTaskId(task.getId());
        List<GraphExportVo> rows = new ArrayList<>();
        List<GraphNode> nodes = graphNodeService.selectGraphNodeList(nq);
        for (GraphNode n : nodes == null ? new ArrayList<GraphNode>() : nodes)
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
            rows.add(v);
        }
        GraphEdge eq = new GraphEdge();
        eq.setTaskId(task.getId());
        List<GraphEdge> edges = graphEdgeService.selectGraphEdgeList(eq);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (GraphEdge e : edges == null ? new ArrayList<GraphEdge>() : edges)
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
        new ExcelUtil<GraphExportVo>(GraphExportVo.class).exportExcel(response, rows, "股权穿透图谱");
    }

    /**
     * 按《天眼查API接口文档》1160 股权穿透（10层）口径组装入参：
     * dir（up/down，默认 down）、flag（层次，最大 10，默认 10，兼容前端 depth 字段）、
     * minPercent（股权穿透比例下限，默认 0）、maxPercent（股权穿透比例上限，默认 1）。
     * 比例口径按百分数入参（0-100），出参换算为文档示例的小数口径（0-1）。
     */
    private Map<String,Object> equityParams(Map<String,Object> body)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        String dir = str(body == null ? null : body.get("dir"));
        params.put("dir", "up".equalsIgnoreCase(dir) ? "up" : "down");
        int flag = intOf(body == null ? null : body.get("flag"), intOf(body == null ? null : body.get("depth"), 10));
        params.put("flag", Math.max(1, Math.min(flag, 10)));
        double min = doubleOf(body == null ? null : firstNonNull(body.get("minPercent"), body.get("minRatio")), 0D);
        double max = doubleOf(body == null ? null : firstNonNull(body.get("maxPercent"), body.get("maxRatio")), 100D);
        params.put("minPercent", min / 100D);
        params.put("maxPercent", max / 100D);
        return params;
    }

    private Object firstNonNull(Object a, Object b) { return a != null ? a : b; }

    private double doubleOf(Object v, double def)
    {
        if (v == null) return def;
        try { return Double.parseDouble(String.valueOf(v)); } catch (Exception e) { return def; }
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

    /** 查找以该节点（nodeKey / creditCode / nodeName）为主体的本地历史穿透任务 */
    private AnalysisTask findLocalTaskForNode(Long taskId, String nodeKey)
    {
        AnalysisTask found = findLocalTask(SrmConstants.TASK_TYPE_EQUITY, SrmConstants.BIZ_SCENE_PAGE5, nodeKey);
        if (found != null) return found;
        if (taskId == null) return null;
        GraphNode nq = new GraphNode();
        nq.setTaskId(taskId);
        List<GraphNode> nodes = graphNodeService.selectGraphNodeList(nq);
        if (nodes == null) return null;
        for (GraphNode n : nodes)
        {
            if (!nodeKey.equals(n.getNodeKey())) continue;
            if (StringUtils.isNotEmpty(n.getCreditCode()))
            {
                found = findLocalTask(SrmConstants.TASK_TYPE_EQUITY, SrmConstants.BIZ_SCENE_PAGE5, n.getCreditCode());
                if (found != null) return found;
            }
            if (StringUtils.isNotEmpty(n.getNodeName()))
            {
                found = findLocalTask(SrmConstants.TASK_TYPE_EQUITY, SrmConstants.BIZ_SCENE_PAGE5, n.getNodeName());
                if (found != null) return found;
            }
        }
        return null;
    }

    /** 将本地历史任务的节点与边去重合并入当前 task */
    private int mergeLocalTaskGraph(Long targetTaskId, Long sourceTaskId)
    {
        GraphNode tnq = new GraphNode();
        tnq.setTaskId(targetTaskId);
        java.util.Set<String> existingNodes = new java.util.HashSet<>();
        List<GraphNode> targetNodes = graphNodeService.selectGraphNodeList(tnq);
        if (targetNodes != null)
        {
            for (GraphNode n : targetNodes) if (n.getNodeKey() != null) existingNodes.add(n.getNodeKey());
        }
        GraphEdge teq = new GraphEdge();
        teq.setTaskId(targetTaskId);
        java.util.Set<String> existingEdges = new java.util.HashSet<>();
        List<GraphEdge> targetEdges = graphEdgeService.selectGraphEdgeList(teq);
        if (targetEdges != null)
        {
            for (GraphEdge e : targetEdges) existingEdges.add(edgeSig(e));
        }
        int added = 0;
        GraphNode snq = new GraphNode();
        snq.setTaskId(sourceTaskId);
        List<GraphNode> sourceNodes = graphNodeService.selectGraphNodeList(snq);
        if (sourceNodes != null)
        {
            for (GraphNode n : sourceNodes)
            {
                if (n.getNodeKey() == null || !existingNodes.add(n.getNodeKey())) continue;
                n.setId(null);
                n.setTaskId(targetTaskId);
                graphNodeService.insertGraphNode(n);
                added++;
            }
        }
        GraphEdge seq = new GraphEdge();
        seq.setTaskId(sourceTaskId);
        List<GraphEdge> sourceEdges = graphEdgeService.selectGraphEdgeList(seq);
        if (sourceEdges != null)
        {
            for (GraphEdge e : sourceEdges)
            {
                if (!existingEdges.add(edgeSig(e))) continue;
                e.setId(null);
                e.setTaskId(targetTaskId);
                graphEdgeService.insertGraphEdge(e);
                added++;
            }
        }
        return added;
    }

    private String edgeSig(GraphEdge e)
    {
        return String.valueOf(e.getFromNodeKey()) + "|" + String.valueOf(e.getToNodeKey()) + "|" + String.valueOf(e.getRelType());
    }

    /** 当前 task 快照中是否已沉淀该节点沿穿透方向的 INVEST 边 */
    private boolean hasLocalEdgesInTask(Long taskId, String nodeKey, String dir)
    {
        GraphEdge eq = new GraphEdge();
        eq.setTaskId(taskId);
        List<GraphEdge> edges = graphEdgeService.selectGraphEdgeList(eq);
        if (edges == null) return false;
        boolean up = "up".equalsIgnoreCase(dir);
        for (GraphEdge e : edges)
        {
            if (!"INVEST".equalsIgnoreCase(e.getRelType())) continue;
            if (up && nodeKey.equals(e.getToNodeKey())) return true;
            if (!up && nodeKey.equals(e.getFromNodeKey())) return true;
        }
        return false;
    }

    /** 续穿本地命中响应：localHit=true、不外调、unitConsumed=0 */
    private Map<String,Object> localExpandReplay(String nodeKey, int expandCount, String dataSource)
    {
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("nodeKey", nodeKey);
        data.put("expandCount", expandCount);
        data.put("localHit", true);
        data.put("source", "local");
        data.put("dataSource", dataSource);
        data.put("unitConsumed", java.math.BigDecimal.ZERO);
        return data;
    }

    private String nextTaskNo()
    {
        return "EQ" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    private String str(Object v) { return v == null ? null : String.valueOf(v); }
    private boolean boolOf(Object v) { return v != null && ("true".equalsIgnoreCase(String.valueOf(v)) || "1".equals(String.valueOf(v))); }
    private Long longOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).longValue(); return Long.valueOf(String.valueOf(v)); }
    private Integer intOf(Object v, int def) { if (v == null) return def; if (v instanceof Number) return ((Number) v).intValue(); try { return Integer.valueOf(String.valueOf(v)); } catch (Exception e) { return def; } }
}