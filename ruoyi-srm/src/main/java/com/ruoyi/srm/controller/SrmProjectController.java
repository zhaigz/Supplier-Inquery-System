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
import com.ruoyi.srm.domain.RelClue;
import com.ruoyi.srm.domain.Project;
import com.ruoyi.srm.domain.ProjectMember;
import com.ruoyi.srm.domain.ReportVersion;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.dto.ProjectExportVo;
import com.ruoyi.srm.gateway.CallContext;
import com.ruoyi.srm.gateway.TycGateway;
import com.ruoyi.srm.gateway.TycResult;
import com.ruoyi.srm.service.IAnalysisTaskService;
import com.ruoyi.srm.service.IGraphProjectionService;
import com.ruoyi.srm.service.IProjectMemberService;
import com.ruoyi.srm.service.IProjectService;
import com.ruoyi.srm.service.IRelClueService;
import com.ruoyi.srm.service.IReportVersionService;
import com.ruoyi.srm.service.ISupplierService;
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
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面7「项目供应商关系网」接口，共 13 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 *
 * <p>TODO[天眼查接入] 本类调用的天眼查编号（共 4 个）。接入时只需在 application.yml 的 
 * <code>srm.tyc.apis.&lt;编号&gt;</code> 补 path/method，并把 TYC_TOKEN 注入环境变量，代码无需改动：
 * <ul><li>1123（ACTUAL_CONTROLLER · 疑似实际控制人）</li></ul>
 * <ul><li>1156（SUSPECT_RELATION · 疑似关系发现）</li></ul>
 * <ul><li>1160（EQUITY_PENETRATION · 股权穿透（10层））</li></ul>
 * <ul><li>1161（BACKSTAGE_RELATION · 幕后关系（10层））</li></ul>
 */
@RestController
@RequestMapping("/srm/project")
public class SrmProjectController extends BaseController
{
    @Autowired private IProjectService projectService;
    @Autowired private IProjectMemberService projectMemberService;
    @Autowired private IAnalysisTaskService analysisTaskService;
    @Autowired private IRelClueService relClueService;
    @Autowired private IReportVersionService reportVersionService;
    @Autowired private ISupplierService supplierService;
    @Autowired private IGraphProjectionService graphProjectionService;
    @Autowired private TycGateway tycGateway;

    /**
     * 项目分页清单
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(Project query)
    {
        Project q = query == null ? new Project() : query;
        if (StringUtils.isEmpty(q.getDelFlag())) q.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
        startPage(); startOrderBy();
        return getDataTable(projectService.selectProjectList(q));
    }

    /**
     * 项目详情+成员+参数
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        Project project = requireProject(id);
        ProjectMember mq = new ProjectMember();
        mq.setProjectId(id);
        List<ProjectMember> members = projectMemberService.selectProjectMemberList(mq);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("project", project);
        data.put("members", members == null ? new ArrayList<>() : members);
        data.put("analysisParam", parseJson(project.getAnalysisParamJson()));
        return success(data);
    }

    /**
     * 新建项目
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:add')")
    @Log(title="项目", businessType=BusinessType.INSERT)
    @SrmAudit(module="项目关系网", action="新增", object="项目", detail="新建项目")
    @PostMapping
    public AjaxResult add(@RequestBody Project project)
    {
        if (project == null || StringUtils.isEmpty(project.getProjectName())) throw new ServiceException("projectName不能为空", SrmErrorCode.CODE_60008);
        project.setId(null);
        if (StringUtils.isEmpty(project.getProjectNo())) project.setProjectNo(nextProjectNo());
        if (StringUtils.isEmpty(project.getAnalysisStatus())) project.setAnalysisStatus("未分析");
        if (StringUtils.isEmpty(project.getStatus())) project.setStatus(SrmConstants.NO);
        project.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
        project.setSupplierCount(0);
        project.setCreateBy(getUsername());
        project.setCreateTime(new Date());
        projectService.insertProject(project);
        return success(project.getId());
    }

    /**
     * 修改项目与穿透参数
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:edit')")
    @Log(title="项目", businessType=BusinessType.UPDATE)
    @SrmAudit(module="项目关系网", action="修改", object="{p:id}", detail="修改项目与穿透参数")
    @PutMapping
    public AjaxResult edit(@RequestBody Project project)
    {
        if (project == null || project.getId() == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        requireProject(project.getId());
        project.setUpdateBy(getUsername());
        project.setUpdateTime(new Date());
        projectService.updateProject(project);
        return success(projectService.selectProjectById(project.getId()));
    }

    /**
     * 删除项目（有版本禁删）
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:remove；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:remove')")
    @Log(title="项目", businessType=BusinessType.DELETE)
    @SrmAudit(module="项目关系网", action="删除", object="{arg0}", detail="删除项目")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids)
    {
        Long[] values = parseIds(ids);
        if (values.length == 0) throw new ServiceException("ids不能为空", SrmErrorCode.CODE_60008);
        for (Long id : values)
        {
            Project project = requireProject(id);
            if (project.getLastReportVersionId() != null) throw new ServiceException("项目已有报告版本，禁止删除", SrmErrorCode.CODE_60010);
            Project patch = new Project();
            patch.setId(id);
            patch.setDelFlag(SrmConstants.DEL_FLAG_DELETED);
            patch.setUpdateBy(getUsername());
            patch.setUpdateTime(new Date());
            projectService.updateProject(patch);
        }
        return success();
    }

    /**
     * 加入供应商成员
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:add')")
    @SrmAudit(module="项目关系网", action="新增", object="{p:projectId}", detail="加入供应商成员")
    @PostMapping("/member")
    public AjaxResult addMember(@RequestBody Map<String,Object> body)
    {
        Long projectId = longOf(body == null ? null : body.get("projectId"));
        requireProject(projectId);
        List<?> supplierIds = body == null || body.get("supplierIds") == null ? new ArrayList<>() : (List<?>) body.get("supplierIds");
        if (supplierIds.isEmpty() && body != null && body.get("supplierId") != null) supplierIds = List.of(body.get("supplierId"));
        if (supplierIds.isEmpty()) throw new ServiceException("supplierIds不能为空", SrmErrorCode.CODE_60008);
        int added = 0;
        for (Object raw : supplierIds)
        {
            Long supplierId = longOf(raw);
            Supplier supplier = supplierService.selectSupplierById(supplierId);
            if (supplier == null) throw new ServiceException("供应商不存在: " + supplierId, SrmErrorCode.CODE_60001);
            ProjectMember q = new ProjectMember();
            q.setProjectId(projectId);
            q.setSupplierId(supplierId);
            List<ProjectMember> exists = projectMemberService.selectProjectMemberList(q);
            if (exists != null && !exists.isEmpty()) continue;
            ProjectMember member = new ProjectMember();
            member.setProjectId(projectId);
            member.setSupplierId(supplierId);
            member.setSupplierCode(supplier.getSupplierCode());
            member.setSupplierName(supplier.getSupplierName());
            member.setMonitorFlag(supplier.getMonitorFlag());
            member.setJoinTime(new Date());
            member.setCreateBy(getUsername());
            member.setCreateTime(new Date());
            projectMemberService.insertProjectMember(member);
            added++;
        }
        refreshMemberCount(projectId);
        return success(added);
    }

    /**
     * 移出成员
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:remove；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:remove')")
    @SrmAudit(module="项目关系网", action="移出", object="{arg0}", detail="移出成员")
    @DeleteMapping("/member/{ids}")
    public AjaxResult removeMember(@PathVariable String ids)
    {
        Long[] values = parseIds(ids);
        if (values.length == 0) throw new ServiceException("ids不能为空", SrmErrorCode.CODE_60008);
        projectMemberService.deleteProjectMemberByIds(values);
        return success();
    }

    /**
     * 执行关联分析
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:add；外调 天眼查 1123（ACTUAL_CONTROLLER）、1156（SUSPECT_RELATION）、1160（EQUITY_PENETRATION）、1161（BACKSTAGE_RELATION）（异步触发）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:add')")
    @SrmAudit(module="项目关系网", action="分析", object="{p:projectId}", detail="执行关联分析")
    @PostMapping("/analysis/init")
    public AjaxResult analysisInit(@RequestBody Map<String,Object> body)
    {
        Long projectId = longOf(body == null ? null : body.get("projectId"));
        Project project = requireProject(projectId);
        AnalysisTask task = new AnalysisTask();
        task.setTaskNo(nextTaskNo());
        task.setTaskType(SrmConstants.TASK_TYPE_PROJECT);
        task.setBizScene(SrmConstants.BIZ_SCENE_PAGE7);
        task.setProjectId(projectId);
        task.setTaskStatus(SrmConstants.TASK_STATUS_RUNNING);
        task.setProgressStep("RUNNING");
        task.setBillFlag(SrmConstants.BILL_FLAG_UNBILLED);
        task.setEnv(SrmConstants.CACHE_ENV_PROD);
        task.setCreateBy(getUsername());
        task.setCreateTime(new Date());
        analysisTaskService.insertAnalysisTask(task);

        CallContext ctx = new CallContext();
        ctx.setManual(true);
        ctx.setTaskId(task.getId());
        ctx.setScene(SrmConstants.SCENE_PROJECT_ANALYSIS);
        List<Map<String,Object>> calls = new ArrayList<>();
        List<Long> localHitIds = new ArrayList<>();
        List<Long> externalIds = new ArrayList<>();
        int clueCount = 0;
        ProjectMember mq = new ProjectMember();
        mq.setProjectId(projectId);
        List<ProjectMember> members = projectMemberService.selectProjectMemberList(mq);
        // 缺口④修复：解析项目归档的穿透参数 analysisParamJson（direction/upDepth/downDepth/minWeight/relScope/ruleVersionNo），
        // 按与 SrmEquityGraphController.equityParams 同款口径透传给 1160/1161，不再硬编码 depth=10。
        Map<String,Object> ap = project.getAnalysisParamJson() == null || project.getAnalysisParamJson().isEmpty()
                ? new LinkedHashMap<>() : toMap(parseJson(project.getAnalysisParamJson()));
        String direction = ap.get("direction") == null ? "both" : String.valueOf(ap.get("direction"));
        int upDepth = clamp(intOf(ap.get("upDepth"), 5), 1, 10);
        int downDepth = clamp(intOf(ap.get("downDepth"), 5), 1, 10);
        String depthFlag = "up".equalsIgnoreCase(direction) ? String.valueOf(upDepth)
                : "down".equalsIgnoreCase(direction) ? String.valueOf(downDepth)
                : String.valueOf(Math.max(upDepth, downDepth));
        // 比例下限：仅当项目参数显式配置 minPercent/minRatio（百分数 0-100）时生效；
        // minWeight 是关系强度分（1-5），不是持股比例，不参与 1160 比例口径，只随 1156 留痕。
        double minPercent = doubleOf(firstNonNull(ap.get("minPercent"), ap.get("minRatio")), 0D);
        double maxPercent = doubleOf(firstNonNull(ap.get("maxPercent"), ap.get("maxRatio")), 100D);
        String types = ap.get("relScope") == null || String.valueOf(ap.get("relScope")).isEmpty()
                ? "ALL" : String.valueOf(ap.get("relScope")).trim().toUpperCase();
        for (ProjectMember member : members == null ? new ArrayList<ProjectMember>() : members)
        {
            Supplier supplier = supplierService.selectSupplierById(member.getSupplierId());
            if (supplier == null) continue;
            String subjectKey = StringUtils.isNotEmpty(supplier.getCreditCode()) ? supplier.getCreditCode() : supplier.getSupplierName();
            // 本地优先（核心语义）：该成员在本地库已有成功沉淀的图谱结果时，直接回放本地数据，不重复付费外调天眼查。
            if (!localSedimentHit(subjectKey))
            {
                externalIds.add(member.getSupplierId());
            }
            else
            {
                localHitIds.add(member.getSupplierId());
                continue;
            }
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("keyword", subjectKey);
            // 1160 官方口径：dir（up/down）+ flag（层次，最大10）+ minPercent/maxPercent（比例0-1小数），与 equityParams 同款换算
            params.put("dir", "up".equalsIgnoreCase(direction) ? "up" : "down");
            params.put("flag", depthFlag);
            params.put("minPercent", minPercent / 100D);
            params.put("maxPercent", maxPercent / 100D);
            // 1156 分页口径：携项目归档的规则版本与权重下限留痕（非官方入参时由网关按 query 透传，不影响其它编号）
            params.put("ruleVersionNo", intOf(ap.get("ruleVersionNo"), 1));
            params.put("minWeight", intOf(ap.get("minWeight"), 1));
            // TODO[天眼查接入] 待接入天眼查 1156：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
            // TYC-CALL 外调天眼查 1156（SUSPECT_RELATION · 疑似关系发现）1.00 元/次；项目关系网读端点数据源，落 srm_rel_clue
            TycResult r1156 = tycGateway.call(SrmApiId.SUSPECT_RELATION, subjectKey, params, ctx);
            clueCount += graphProjectionService.projectSuspectRelation(task.getId(), projectId, subjectKey, r1156);
            calls.add(describe(r1156));
            // 1161 官方口径：types 关系口径（取项目 relScope）+ depth [1,10]（按 direction 取 up/down 层级，数值型，与页面 6 口径一致）
            Map<String,Object> backstageParams = new LinkedHashMap<>();
            backstageParams.put("keyword", subjectKey);
            backstageParams.put("types", types);
            backstageParams.put("depth", intOf(depthFlag, 10));
            // TODO[天眼查接入] 待接入天眼查 1161：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
            // TYC-CALL 外调天眼查 1161（BACKSTAGE_RELATION · 幕后关系（10层））4.00 元/次；并入本 task 图谱
            TycResult r1161 = tycGateway.call(SrmApiId.BACKSTAGE_RELATION, subjectKey, backstageParams, ctx);
            graphProjectionService.projectBackstage(task.getId(), subjectKey, r1161);
            calls.add(describe(r1161));
            // TODO[天眼查接入] 待接入天眼查 1123：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
            // TYC-CALL 外调天眼查 1123（ACTUAL_CONTROLLER · 疑似实际控制人）1.00 元/次；并入本 task 图谱
            TycResult r1123 = tycGateway.call(SrmApiId.ACTUAL_CONTROLLER, subjectKey, params, ctx);
            graphProjectionService.projectActualController(task.getId(), subjectKey, r1123);
            calls.add(describe(r1123));
            // TODO[天眼查接入] 待接入天眼查 1160：SrmApiId 常量已直连 TycGateway，接入时只需在 application.yml 的 srm.tyc.apis.<编号> 补 path/method 并注入 TYC_TOKEN，无需改代码
            // TYC-CALL 外调天眼查 1160（EQUITY_PENETRATION · 股权穿透（10层））4.00 元/次；并入本 task 图谱
            TycResult r1160 = tycGateway.call(SrmApiId.EQUITY_PENETRATION, subjectKey, params, ctx);
            graphProjectionService.projectEquity(task.getId(), subjectKey, r1160);
            calls.add(describe(r1160));
        }
        task.setTaskStatus(SrmConstants.TASK_STATUS_SUCCESS);
        task.setProgressStep("DONE");
        task.setResultCount(clueCount);
        task.setUnitConsumed(totalUnit(calls));
        task.setFinishTime(new Date());
        task.setUpdateTime(new Date());
        analysisTaskService.updateAnalysisTask(task);

        Project patch = new Project();
        patch.setId(projectId);
        patch.setAnalysisStatus("已完成");
        patch.setLastTaskId(task.getId());
        patch.setUpdateTime(new Date());
        projectService.updateProject(patch);

        Map<String,Object> data = new LinkedHashMap<>();
        data.put("taskNo", task.getTaskNo());
        data.put("taskId", task.getId());
        data.put("taskStatus", task.getTaskStatus());
        data.put("calls", calls);
        data.put("localHitSupplierIds", localHitIds);
        data.put("externalSupplierIds", externalIds);
        data.put("localHitCount", localHitIds.size());
        data.put("externalCallCount", calls.size());
        return success(data);
    }

    /**
     * 本地沉淀命中：srm_analysis_task 中该主体已存在成功且 result_count>0 的图谱任务 => 本地库已有天眼查回写数据。
     */
    private boolean localSedimentHit(String subjectKey)
    {
        if (StringUtils.isEmpty(subjectKey)) return false;
        for (String type : new String[] { SrmConstants.TASK_TYPE_EQUITY, SrmConstants.TASK_TYPE_BACKSTAGE })
        {
            AnalysisTask q = new AnalysisTask();
            q.setTaskType(type);
            q.setSubjectKey(subjectKey);
            q.setTaskStatus(SrmConstants.TASK_STATUS_SUCCESS);
            List<AnalysisTask> rows = analysisTaskService.selectAnalysisTaskList(q);
            if (rows != null)
            {
                for (AnalysisTask t : rows)
                {
                    if (t.getResultCount() != null && t.getResultCount() > 0) return true;
                }
            }
        }
        return false;
    }

    /**
     * 分析进度
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:query')")
    @GetMapping("/analysis/progress/{taskNo}")
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
     * 命中线索明细
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:list')")
    @GetMapping("/clue/list")
    public TableDataInfo clueList(RelClue query)
    {
        startPage(); startOrderBy();
        return getDataTable(relClueService.selectRelClueList(query == null ? new RelClue() : query));
    }

    /**
     * 关系网图谱回放
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:query')")
    @GetMapping("/graph/{taskNo}")
    public AjaxResult graph(@PathVariable String taskNo)
    {
        AnalysisTask task = requireTask(taskNo);
        RelClue q = new RelClue();
        q.setTaskId(task.getId());
        List<RelClue> clues = relClueService.selectRelClueList(q);
        List<Map<String,Object>> nodes = new ArrayList<>();
        List<Map<String,Object>> edges = new ArrayList<>();
        if (clues != null)
        {
            for (RelClue c : clues)
            {
                nodes.add(projectNode(c.getSupplierA(), c.getEntAName(), c.getEntATycId()));
                nodes.add(projectNode(c.getSupplierB(), c.getEntBName(), c.getEntBTycId()));
                Map<String,Object> edge = new LinkedHashMap<>();
                edge.put("from", c.getSupplierA());
                edge.put("to", c.getSupplierB());
                edge.put("relType", c.getClueType());
                edge.put("relPath", c.getRelPath());
                edge.put("weight", c.getWeight());
                edges.add(edge);
            }
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("taskNo", task.getTaskNo());
        data.put("source", "local");
        data.put("dataSource", "本地库（1156 回写沉淀的 srm_rel_clue）");
        data.put("nodes", nodes);
        data.put("edges", edges);
        return success(data);
    }

    /**
     * 保存报告版本
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:add')")
    @SrmAudit(module="项目关系网", action="保存", object="{p:projectId}", detail="保存报告版本")
    @PostMapping("/report/save")
    public AjaxResult reportSave(@RequestBody ReportVersion report)
    {
        if (report == null || report.getProjectId() == null) throw new ServiceException("projectId不能为空", SrmErrorCode.CODE_60008);
        Project project = requireProject(report.getProjectId());
        ReportVersion q = new ReportVersion();
        q.setProjectId(report.getProjectId());
        List<ReportVersion> versions = reportVersionService.selectReportVersionList(q);
        int next = 1;
        if (versions != null) for (ReportVersion v : versions) if (v.getVersionNo() != null && v.getVersionNo() >= next) next = v.getVersionNo() + 1;
        report.setId(null);
        report.setVersionNo(next);
        report.setAnalysisAt(new Date());
        report.setExportCount(0);
        report.setCreateBy(getUsername());
        report.setCreateTime(new Date());
        reportVersionService.insertReportVersion(report);
        Project patch = new Project();
        patch.setId(project.getId());
        patch.setLastReportVersionId(report.getId());
        patch.setUpdateTime(new Date());
        projectService.updateProject(patch);
        return success(report.getId());
    }

    /**
     * 导出报告
     *
     * <p>页面 7「项目供应商关系网」；权限 srm:project:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:project:export')")
    @Log(title="项目关系网", businessType=BusinessType.EXPORT)
    @SrmAudit(module="项目关系网", action="导出", object="{p:projectId}", detail="导出报告")
    @PostMapping("/export")
    public void export(@RequestBody(required=false) Map<String,Object> body, HttpServletResponse response)
    {
        Long projectId = longOf(body == null ? null : body.get("projectId"));
        requireProject(projectId);
        RelClue q = new RelClue();
        q.setProjectId(projectId);
        List<RelClue> clues = relClueService.selectRelClueList(q);
        List<ProjectExportVo> rows = new ArrayList<>();
        for (RelClue c : clues == null ? new ArrayList<RelClue>() : clues)
        {
            ProjectExportVo v = new ProjectExportVo();
            v.setSupplierA(c.getEntAName());
            v.setSupplierB(c.getEntBName());
            v.setClueType(c.getClueType());
            v.setRuleName(c.getRuleName());
            v.setWeight(c.getWeight());
            v.setRelPath(c.getRelPath());
            v.setDataSource(c.getDataSource());
            rows.add(v);
        }
        new ExcelUtil<ProjectExportVo>(ProjectExportVo.class).exportExcel(response, rows, "项目关系网");
    }

    private Map<String,Object> projectNode(Long supplierId, String name, String tycId)
    {
        Map<String,Object> node = new LinkedHashMap<>();
        node.put("id", supplierId);
        node.put("name", name);
        node.put("tycId", tycId);
        return node;
    }

    private Object parseJson(String json)
    {
        if (StringUtils.isEmpty(json)) return new LinkedHashMap<>();
        try { return com.alibaba.fastjson2.JSON.parseObject(json); } catch (Exception e) { return new LinkedHashMap<>(); }
    }

    private void refreshMemberCount(Long projectId)
    {
        ProjectMember q = new ProjectMember();
        q.setProjectId(projectId);
        List<ProjectMember> members = projectMemberService.selectProjectMemberList(q);
        Project patch = new Project();
        patch.setId(projectId);
        patch.setSupplierCount(members == null ? 0 : members.size());
        patch.setUpdateTime(new Date());
        projectService.updateProject(patch);
    }

    private Project requireProject(Long id)
    {
        if (id == null) throw new ServiceException("projectId不能为空", SrmErrorCode.CODE_60008);
        Project project = projectService.selectProjectById(id);
        if (project == null) throw new ServiceException("项目不存在", SrmErrorCode.CODE_60001);
        return project;
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

    private BigDecimal totalUnit(List<Map<String,Object>> calls)
    {
        BigDecimal total = BigDecimal.ZERO;
        for (Map<String,Object> c : calls)
        {
            Object u = c.get("unitConsumed");
            if (u instanceof BigDecimal) total = total.add((BigDecimal) u);
        }
        return total;
    }

    @SuppressWarnings("unchecked")
    private Map<String,Object> toMap(Object parsed)
    {
        return parsed instanceof Map ? (Map<String,Object>) parsed : new LinkedHashMap<>();
    }

    private Object firstNonNull(Object a, Object b) { return a != null ? a : b; }

    private int clamp(int v, int lo, int hi) { return Math.max(lo, Math.min(v, hi)); }

    private int intOf(Object v, int def)
    {
        if (v == null) return def;
        try { return Integer.parseInt(String.valueOf(v).trim()); } catch (Exception e) { return def; }
    }

    private double doubleOf(Object v, double def)
    {
        if (v == null) return def;
        try { return Double.parseDouble(String.valueOf(v).trim()); } catch (Exception e) { return def; }
    }

    private String nextProjectNo() { return "PJ" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); }
    private String nextTaskNo() { return "PR" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); }
    private Long[] parseIds(String ids) { List<Long> list = new ArrayList<>(); for (String x : ids.split(",")) { String v = x.trim(); if (v.matches("\\d+")) list.add(Long.valueOf(v)); } return list.toArray(new Long[0]); }
    private Long longOf(Object v) { if (v == null) return null; if (v instanceof Number) return ((Number) v).longValue(); return Long.valueOf(String.valueOf(v)); }
}