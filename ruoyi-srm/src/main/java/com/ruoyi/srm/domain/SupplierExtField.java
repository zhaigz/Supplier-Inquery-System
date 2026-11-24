package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_supplier_ext_field */
public class SupplierExtField extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /** 内部字段标识（页面11 内部稳定标识，按内部字段标识规范维护） */
    private String fieldCode;

    /** 自定义字段值（页面11 样例：银行账户标识（自定义）） */
    private String fieldValue;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getFieldCode() { return fieldCode; }

    public void setFieldCode(String fieldCode) { this.fieldCode = fieldCode; }

    public String getFieldValue() { return fieldValue; }

    public void setFieldValue(String fieldValue) { this.fieldValue = fieldValue; }

}
