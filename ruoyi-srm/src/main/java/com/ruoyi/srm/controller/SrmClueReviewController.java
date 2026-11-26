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
import com.ruoyi.srm.domain.ClueReview;
import com.ruoyi.srm.domain.RelClue;
import com.ruoyi.srm.service.IClueReviewService;
import com.ruoyi.srm.service.IRelClueService;
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
 * 页面12「人工复核」接口，共 6 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/clue-review")
public class SrmClueReviewController extends BaseController
{
    @Autowired private IClueReviewService clueReviewService;
    @Autowired private IRelClueService relClueService;

    /**
     * 待复核线索
     *
     * <p>页面 12「人工复核」；权限 srm:clueReview:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:clueReview:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClueReview query)
    {
        startPage(); startOrderBy();
        return getDataTable(clueReviewService.selectClueReviewList(query == null ? new ClueReview() : query));
    }

    /**
     * 命中依据回放
     *
     * <p>页面 12「人工复核」；权限 srm:clueReview:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:clueReview:query')")
    @GetMapping("/evidence/{clueId}")
    public AjaxResult evidence(@PathVariable Long clueId)
    {
        RelClue clue = relClueService.selectRelClueById(clueId);
        if (clue == null) throw new ServiceException("线索不存在", SrmErrorCode.CODE_60001);
        ClueReview q = new ClueReview();
        q.setClueId(clueId);
        List<ClueReview> reviews = clueReviewService.selectClueReviewList(q);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("clue", clue);
        data.put("reviews", reviews == null ? new ArrayList<>() : reviews);
        data.put("evidenceRef", clue.getRelPath());
        return success(data);
    }

    /**
     * 提交复核结论
     *
     * <p>页面 12「人工复核」；权限 srm:clueReview:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:clueReview:add')")
    @Log(title="线索复核", businessType=BusinessType.INSERT)
    @SrmAudit(module="人工复核", action="新增", object="{p:clueId}", detail="提交复核结论")
    @PostMapping
    public AjaxResult add(@RequestBody ClueReview review)
    {
        if (review == null || review.getClueId() == null) throw new ServiceException("clueId不能为空", SrmErrorCode.CODE_60008);
        if (relClueService.selectRelClueById(review.getClueId()) == null) throw new ServiceException("线索不存在", SrmErrorCode.CODE_60001);
        review.setId(null);
        review.setReviewerId(getUserId());
        review.setReviewerName(getUsername());
        review.setReviewedAt(new Date());
        if (StringUtils.isEmpty(review.getReviewStatus())) review.setReviewStatus("已复核");
        review.setCreateBy(getUsername());
        review.setCreateTime(new Date());
        clueReviewService.insertClueReview(review);
        return success(review.getId());
    }

    /**
     * 改判（追加）
     *
     * <p>页面 12「人工复核」；权限 srm:clueReview:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:clueReview:edit')")
    @Log(title="线索复核", businessType=BusinessType.UPDATE)
    @SrmAudit(module="人工复核", action="改判", object="{p:id}", detail="改判并追加记录")
    @PutMapping
    public AjaxResult edit(@RequestBody ClueReview review)
    {
        if (review == null || review.getId() == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        ClueReview old = requireReview(review.getId());
        ClueReview appended = new ClueReview();
        appended.setClueId(old.getClueId());
        appended.setProjectId(old.getProjectId());
        appended.setReportVersionId(old.getReportVersionId());
        appended.setReviewStatus(StringUtils.isEmpty(review.getReviewStatus()) ? old.getReviewStatus() : review.getReviewStatus());
        appended.setConclusion(review.getConclusion());
        appended.setReviewNote(review.getReviewNote());
        appended.setEvidenceRef(review.getEvidenceRef());
        appended.setReviewerId(getUserId());
        appended.setReviewerName(getUsername());
        appended.setReviewedAt(new Date());
        appended.setCreateBy(getUsername());
        appended.setCreateTime(new Date());
        clueReviewService.insertClueReview(appended);
        return success(appended.getId());
    }

    /**
     * 复核进度统计
     *
     * <p>页面 12「人工复核」；权限 srm:clueReview:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:clueReview:list')")
    @GetMapping("/stat")
    public AjaxResult stat(ClueReview query)
    {
        List<ClueReview> rows = clueReviewService.selectClueReviewList(query == null ? new ClueReview() : query);
        Map<String,Integer> byStatus = new LinkedHashMap<>();
        for (ClueReview r : rows == null ? new ArrayList<ClueReview>() : rows)
        {
            String key = StringUtils.isEmpty(r.getReviewStatus()) ? "待复核" : r.getReviewStatus();
            byStatus.put(key, byStatus.getOrDefault(key, 0) + 1);
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("total", rows == null ? 0 : rows.size());
        data.put("byStatus", byStatus);
        data.put("reviewed", byStatus.getOrDefault("已复核", 0));
        data.put("pending", byStatus.getOrDefault("待复核", 0));
        return success(data);
    }

    /**
     * 导出复核清单
     *
     * <p>页面 12「人工复核」；权限 srm:clueReview:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:clueReview:export')")
    @Log(title="复核清单", businessType=BusinessType.EXPORT)
    @SrmAudit(module="人工复核", action="导出", object="复核清单", detail="导出复核清单")
    @PostMapping("/export")
    public void export(@RequestBody(required=false) ClueReview query, HttpServletResponse response)
    {
        List<ClueReview> rows = clueReviewService.selectClueReviewList(query == null ? new ClueReview() : query);
        new ExcelUtil<ClueReview>(ClueReview.class).exportExcel(response, rows == null ? new ArrayList<>() : rows, "复核清单");
    }

    private ClueReview requireReview(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        ClueReview review = clueReviewService.selectClueReviewById(id);
        if (review == null) throw new ServiceException("复核记录不存在", SrmErrorCode.CODE_60001);
        return review;
    }
}