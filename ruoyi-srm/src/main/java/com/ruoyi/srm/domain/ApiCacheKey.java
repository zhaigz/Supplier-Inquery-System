package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_api_cache_key */
public class ApiCacheKey extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 缓存键 md5(api_id + 标准化主体标识 + 参数指纹)，页面5/6/7 有方向/层数/比例/口径入参，只按信用代码缓存会错命中 */
    private String cacheKey;

    /** 接口编号 */
    private Integer apiId;

    /** 统一社会信用代码（页面5/6 逐字要求按信用代码缓存，本字段保留该口径） */
    private String creditCode;

    /** 标准化主体标识：信用代码优先，缺失时用规范化企业名或 tyc 公司id */
    private String subjectKey;

    /** 参数指纹原文（穿透方向/层数/比例上下限/关系口径/深度/源与目标主体） */
    private String paramJson;

    /** 结果指向 srm_api_raw.id */
    private Long rawId;

    /** 命中次数（缓存节省量统计，页面16 缓存命中不重复计量） */
    private Integer hitCount;

    /** 过期时间 = fetched_at + 有效时长 */
    private Date expireAt;

    /**  */
    private Date fetchedAt;

    /** prod/test：测试额度与正式额度的结果分桶，避免测试数据污染正式结论 */
    private String env;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCacheKey() { return cacheKey; }

    public void setCacheKey(String cacheKey) { this.cacheKey = cacheKey; }

    public Integer getApiId() { return apiId; }

    public void setApiId(Integer apiId) { this.apiId = apiId; }

    public String getCreditCode() { return creditCode; }

    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }

    public String getSubjectKey() { return subjectKey; }

    public void setSubjectKey(String subjectKey) { this.subjectKey = subjectKey; }

    public String getParamJson() { return paramJson; }

    public void setParamJson(String paramJson) { this.paramJson = paramJson; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Integer getHitCount() { return hitCount; }

    public void setHitCount(Integer hitCount) { this.hitCount = hitCount; }

    public Date getExpireAt() { return expireAt; }

    public void setExpireAt(Date expireAt) { this.expireAt = expireAt; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

    public String getEnv() { return env; }

    public void setEnv(String env) { this.env = env; }

}
