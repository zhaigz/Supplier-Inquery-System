package com.ruoyi.srm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_supplier */
public class Supplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 供应商编码（系统内部生成，页面1 列表列） */
    private String supplierCode;

    /** 企业名称（本地主档权威值，页面3 主档 13 项） */
    private String supplierName;

    /** 统一社会信用代码（缓存与比对的业务主键，可空由补全回填） */
    private String creditCode;

    /** 注册号（818 注册号，检索标识类型之一） */
    private String regNo;

    /** 天眼查公司id（818 企业id，作为外部主体稳定标识） */
    private String tycCompanyId;

    /** 法定代表人 */
    private String legalPerson;

    /** 注册资本（保留原文含币种的文本形态，页面3） */
    private String regCapital;

    /** 注册资本数值（万元，仅用于排序/统计，不参与关系匹配） */
    private BigDecimal regCapitalNum;

    /** 成立日期（818 成立日期 / 819 注册日期） */
    private Date establishDate;

    /** 经营状态（818 企业状态；注销/吊销用于图谱角标） */
    private String bizStatus;

    /** 所属行业（818 行业 / 国民经济行业分类大类名称） */
    private String industry;

    /** 注册地址（规范化后值，页面11 要求电话/地址/人员需规范化） */
    private String regAddress;

    /** 联系电话（规范化后主值） */
    private String phone;

    /** 电子邮箱 */
    private String email;

    /** 省（818 省） */
    private String province;

    /** 市（818 市） */
    private String city;

    /** 区（818 区） */
    private String district;

    /** 行政区划代码（818） */
    private String areaCode;

    /** 重点监控：1 重点监控 / 0 普通供应商（页面1 监控状态、页面15 监控范围） */
    private String monitorFlag;

    /** 建档来源：初始内置/批量导入/人工录入/查询补全；补全只递增版本，不改写本列（页面3 主档 13 项之数据来源） */
    private String dataSource;

    /** 主档数据版本号，每次回填递增（页面2 查询时间、数据来源与版本） */
    private Integer dataVersion;

    /** 最近一次工商补全成功时间 */
    private Date lastEnrichAt;

    /** 最近更新（页面3 主档 13 项之最近更新） */
    private Date lastUpdateAt;

    /** 首次入库批次号（页面14 批量导入来源追溯） */
    private String firstImportBatch;

    /** 若依状态：0 正常 / 1 停用（停用不参与分析） */
    private String status;

    /** 删除标志：0 存在 / 2 已删除 */
    private String delFlag;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getSupplierCode() { return supplierCode; }

    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }

    public String getSupplierName() { return supplierName; }

    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getCreditCode() { return creditCode; }

    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }

    public String getRegNo() { return regNo; }

    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getTycCompanyId() { return tycCompanyId; }

    public void setTycCompanyId(String tycCompanyId) { this.tycCompanyId = tycCompanyId; }

    public String getLegalPerson() { return legalPerson; }

    public void setLegalPerson(String legalPerson) { this.legalPerson = legalPerson; }

    public String getRegCapital() { return regCapital; }

    public void setRegCapital(String regCapital) { this.regCapital = regCapital; }

    public BigDecimal getRegCapitalNum() { return regCapitalNum; }

    public void setRegCapitalNum(BigDecimal regCapitalNum) { this.regCapitalNum = regCapitalNum; }

    public Date getEstablishDate() { return establishDate; }

    public void setEstablishDate(Date establishDate) { this.establishDate = establishDate; }

    public String getBizStatus() { return bizStatus; }

    public void setBizStatus(String bizStatus) { this.bizStatus = bizStatus; }

    public String getIndustry() { return industry; }

    public void setIndustry(String industry) { this.industry = industry; }

    public String getRegAddress() { return regAddress; }

    public void setRegAddress(String regAddress) { this.regAddress = regAddress; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getProvince() { return province; }

    public void setProvince(String province) { this.province = province; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district; }

    public String getAreaCode() { return areaCode; }

    public void setAreaCode(String areaCode) { this.areaCode = areaCode; }

    public String getMonitorFlag() { return monitorFlag; }

    public void setMonitorFlag(String monitorFlag) { this.monitorFlag = monitorFlag; }

    public String getDataSource() { return dataSource; }

    public void setDataSource(String dataSource) { this.dataSource = dataSource; }

    public Integer getDataVersion() { return dataVersion; }

    public void setDataVersion(Integer dataVersion) { this.dataVersion = dataVersion; }

    public Date getLastEnrichAt() { return lastEnrichAt; }

    public void setLastEnrichAt(Date lastEnrichAt) { this.lastEnrichAt = lastEnrichAt; }

    public Date getLastUpdateAt() { return lastUpdateAt; }

    public void setLastUpdateAt(Date lastUpdateAt) { this.lastUpdateAt = lastUpdateAt; }

    public String getFirstImportBatch() { return firstImportBatch; }

    public void setFirstImportBatch(String firstImportBatch) { this.firstImportBatch = firstImportBatch; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getDelFlag() { return delFlag; }

    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }

}
