package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_clue_review */
public class ClueReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long clueId;

    /**  */
    private Long projectId;

    /** 项目/版本（页面12 清单列） */
    private Long reportVersionId;

    /** 复核状态：待复核 / 已确认有关联 / 排除线索 / 需补充材料 */
    private String reviewStatus;

    /** 复核结论（必填） */
    private String conclusion;

    /** 复核说明：排除线索与需补充材料时不得为空 */
    private String reviewNote;

    /** 证据依据（多行文本 / 附件索引，附件本体走文件存储） */
    private String evidenceRef;

    /** 复核人 */
    private Long reviewerId;

    /**  */
    private String reviewerName;

    /**  */
    private Date reviewedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getClueId() { return clueId; }

    public void setClueId(Long clueId) { this.clueId = clueId; }

    public Long getProjectId() { return projectId; }

    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getReportVersionId() { return reportVersionId; }

    public void setReportVersionId(Long reportVersionId) { this.reportVersionId = reportVersionId; }

    public String getReviewStatus() { return reviewStatus; }

    public void setReviewStatus(String reviewStatus) { this.reviewStatus = reviewStatus; }

    public String getConclusion() { return conclusion; }

    public void setConclusion(String conclusion) { this.conclusion = conclusion; }

    public String getReviewNote() { return reviewNote; }

    public void setReviewNote(String reviewNote) { this.reviewNote = reviewNote; }

    public String getEvidenceRef() { return evidenceRef; }

    public void setEvidenceRef(String evidenceRef) { this.evidenceRef = evidenceRef; }

    public Long getReviewerId() { return reviewerId; }

    public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerName() { return reviewerName; }

    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public Date getReviewedAt() { return reviewedAt; }

    public void setReviewedAt(Date reviewedAt) { this.reviewedAt = reviewedAt; }

}
