package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_import_batch */
public class ImportBatch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 导入批次号（系统生成，页面14 原文未列，按留痕需要补） */
    private String batchNo;

    /** seed 初始内置库 / paste 文本粘贴 / file 文件上传 */
    private String sourceType;

    /** csv 逗号分隔 / tsv 制表符分隔 */
    private String separator;

    /** 首行处理：1 含字段名 / 0 不含字段名 */
    private String hasHeader;

    /** 预览记录（页面14 指标卡） */
    private Integer totalRows;

    /** 可导入 */
    private Integer okRows;

    /** 重复记录 */
    private Integer dupRows;

    /** 无效记录 */
    private Integer invalidRows;

    /** 待确认 / 已导入 / 部分导入 / 已取消 */
    private String importStatus;

    /** 重复识别口径：信用代码 / 企业名称（含同批次内重复） */
    private String dupRule;

    /** 原始文件存档路径（导入留痕，可重放） */
    private String filePath;

    /**  */
    private Date confirmTime;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getBatchNo() { return batchNo; }

    public void setBatchNo(String batchNo) { this.batchNo = batchNo; }

    public String getSourceType() { return sourceType; }

    public void setSourceType(String sourceType) { this.sourceType = sourceType; }

    public String getSeparator() { return separator; }

    public void setSeparator(String separator) { this.separator = separator; }

    public String getHasHeader() { return hasHeader; }

    public void setHasHeader(String hasHeader) { this.hasHeader = hasHeader; }

    public Integer getTotalRows() { return totalRows; }

    public void setTotalRows(Integer totalRows) { this.totalRows = totalRows; }

    public Integer getOkRows() { return okRows; }

    public void setOkRows(Integer okRows) { this.okRows = okRows; }

    public Integer getDupRows() { return dupRows; }

    public void setDupRows(Integer dupRows) { this.dupRows = dupRows; }

    public Integer getInvalidRows() { return invalidRows; }

    public void setInvalidRows(Integer invalidRows) { this.invalidRows = invalidRows; }

    public String getImportStatus() { return importStatus; }

    public void setImportStatus(String importStatus) { this.importStatus = importStatus; }

    public String getDupRule() { return dupRule; }

    public void setDupRule(String dupRule) { this.dupRule = dupRule; }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Date getConfirmTime() { return confirmTime; }

    public void setConfirmTime(Date confirmTime) { this.confirmTime = confirmTime; }

}
