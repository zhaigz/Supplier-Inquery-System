package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_import_row */
public class ImportRow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long batchId;

    /** 行号（页面14 表 14-1 列） */
    private Integer rowNo;

    /** 企业名称 */
    private String supplierName;

    /** 统一社会信用代码 */
    private String creditCode;

    /** 法人 */
    private String legalPerson;

    /** 重点监控：是 / 否 */
    private String monitorFlag;

    /** 校验结果：可导入 / 重复，跳过 / 缺少名称 */
    private String checkResult;

    /** 重复到的既有主档 */
    private Long dupOfSupplierId;

    /** 导入后指向主档 */
    private Long supplierId;

    /**  */
    private String errMsg;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getBatchId() { return batchId; }

    public void setBatchId(Long batchId) { this.batchId = batchId; }

    public Integer getRowNo() { return rowNo; }

    public void setRowNo(Integer rowNo) { this.rowNo = rowNo; }

    public String getSupplierName() { return supplierName; }

    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getCreditCode() { return creditCode; }

    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }

    public String getLegalPerson() { return legalPerson; }

    public void setLegalPerson(String legalPerson) { this.legalPerson = legalPerson; }

    public String getMonitorFlag() { return monitorFlag; }

    public void setMonitorFlag(String monitorFlag) { this.monitorFlag = monitorFlag; }

    public String getCheckResult() { return checkResult; }

    public void setCheckResult(String checkResult) { this.checkResult = checkResult; }

    public Long getDupOfSupplierId() { return dupOfSupplierId; }

    public void setDupOfSupplierId(Long dupOfSupplierId) { this.dupOfSupplierId = dupOfSupplierId; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getErrMsg() { return errMsg; }

    public void setErrMsg(String errMsg) { this.errMsg = errMsg; }

}
