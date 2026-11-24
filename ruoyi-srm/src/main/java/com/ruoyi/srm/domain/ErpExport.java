package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_erp_export */
public class ErpExport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 导出批次号 */
    private String exportNo;

    /** file 文件交换 / api 接口推送（以 ERP 侧能力为准） */
    private String exportMode;

    /** 目标系统 */
    private String targetSystem;

    /** 本次输出范围说明（最终合格供应商结果） */
    private String scopeDesc;

    /** 输出供应商数 */
    private Integer supplierCount;

    /** 待执行 / 成功 / 部分失败 / 失败 */
    private String exportStatus;

    /**  */
    private String filePath;

    /**  */
    private String errMsg;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getExportNo() { return exportNo; }

    public void setExportNo(String exportNo) { this.exportNo = exportNo; }

    public String getExportMode() { return exportMode; }

    public void setExportMode(String exportMode) { this.exportMode = exportMode; }

    public String getTargetSystem() { return targetSystem; }

    public void setTargetSystem(String targetSystem) { this.targetSystem = targetSystem; }

    public String getScopeDesc() { return scopeDesc; }

    public void setScopeDesc(String scopeDesc) { this.scopeDesc = scopeDesc; }

    public Integer getSupplierCount() { return supplierCount; }

    public void setSupplierCount(Integer supplierCount) { this.supplierCount = supplierCount; }

    public String getExportStatus() { return exportStatus; }

    public void setExportStatus(String exportStatus) { this.exportStatus = exportStatus; }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getErrMsg() { return errMsg; }

    public void setErrMsg(String errMsg) { this.errMsg = errMsg; }

}
