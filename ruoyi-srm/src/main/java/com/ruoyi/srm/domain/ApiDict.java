package com.ruoyi.srm.domain;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_api_dict */
public class ApiDict extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 接口编号：818/819/821/846/848/851/871/872/839/1002/1046/1047/1123/1141/1156/1160/1161/1168/458 */
    private Integer apiId;

    /** 接口名称 */
    private String apiName;

    /** 数据分类：工商信息/司法风险/经营风险/关系发现/组合接口/特色接口/增值服务 */
    private String apiCategory;

    /** 详细 URL */
    private String docUrl;

    /** 单价（元/次），逐字取自接口清单 */
    private BigDecimal unitPrice;

    /** 计价单位（次/家） */
    private String priceUnit;

    /** 是否分页接口：1 分页每页 20 条 / 0 非分页 */
    private String isPageQuery;

    /** 每页条数上限，非分页为 0 */
    private Integer pageSizeMax;

    /** 计费口径：per_call 每次计 / per_page 每页计 / async_poll 二次拉取才计 / unknown 待实测 */
    private String billingMode;

    /** 是否异步任务型接口（1161/1141 需 Init-Poll） */
    private String asyncFlag;

    /** 是否在呈现清单（接口表 是否呈现=是 的 19 项） */
    private String showFlag;

    /** 0 可用 / 1 未购买或停用（停用接口前端不出现调用入口） */
    private String status;

    /** 本接口默认缓存有效期（秒），页面16 缓存有效期可全局覆盖 */
    private Integer cacheTtlSec;

    /** 接口深度上限：1160=10、1161=10（区间外返回无数据）、4=默认层数 */
    private Integer depthMax;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Integer getApiId() { return apiId; }

    public void setApiId(Integer apiId) { this.apiId = apiId; }

    public String getApiName() { return apiName; }

    public void setApiName(String apiName) { this.apiName = apiName; }

    public String getApiCategory() { return apiCategory; }

    public void setApiCategory(String apiCategory) { this.apiCategory = apiCategory; }

    public String getDocUrl() { return docUrl; }

    public void setDocUrl(String docUrl) { this.docUrl = docUrl; }

    public BigDecimal getUnitPrice() { return unitPrice; }

    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public String getPriceUnit() { return priceUnit; }

    public void setPriceUnit(String priceUnit) { this.priceUnit = priceUnit; }

    public String getIsPageQuery() { return isPageQuery; }

    public void setIsPageQuery(String isPageQuery) { this.isPageQuery = isPageQuery; }

    public Integer getPageSizeMax() { return pageSizeMax; }

    public void setPageSizeMax(Integer pageSizeMax) { this.pageSizeMax = pageSizeMax; }

    public String getBillingMode() { return billingMode; }

    public void setBillingMode(String billingMode) { this.billingMode = billingMode; }

    public String getAsyncFlag() { return asyncFlag; }

    public void setAsyncFlag(String asyncFlag) { this.asyncFlag = asyncFlag; }

    public String getShowFlag() { return showFlag; }

    public void setShowFlag(String showFlag) { this.showFlag = showFlag; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Integer getCacheTtlSec() { return cacheTtlSec; }

    public void setCacheTtlSec(Integer cacheTtlSec) { this.cacheTtlSec = cacheTtlSec; }

    public Integer getDepthMax() { return depthMax; }

    public void setDepthMax(Integer depthMax) { this.depthMax = depthMax; }

}
