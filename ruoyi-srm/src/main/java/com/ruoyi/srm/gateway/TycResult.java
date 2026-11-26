package com.ruoyi.srm.gateway;

import com.alibaba.fastjson2.JSONObject;

/**
 * TycGateway 返回体。来源：API接口文档 §3.2 建议签名。
 */
public class TycResult
{
    private int apiId;
    private boolean cacheHit;
    private Long rawId;
    private Long cacheKeyId;
    private java.math.BigDecimal unitConsumed = java.math.BigDecimal.ZERO;
    private boolean blocked;
    private Integer blockCode;
    private String blockReason;
    private JSONObject data;
    private java.util.Date fetchedAt;
    private java.util.Date cacheExpireAt;

    public int getApiId()
    {
        return apiId;
    }

    public void setApiId(int apiId)
    {
        this.apiId = apiId;
    }

    public boolean isCacheHit()
    {
        return cacheHit;
    }

    public void setCacheHit(boolean cacheHit)
    {
        this.cacheHit = cacheHit;
    }

    public Long getRawId()
    {
        return rawId;
    }

    public void setRawId(Long rawId)
    {
        this.rawId = rawId;
    }

    public Long getCacheKeyId()
    {
        return cacheKeyId;
    }

    public void setCacheKeyId(Long cacheKeyId)
    {
        this.cacheKeyId = cacheKeyId;
    }

    public java.math.BigDecimal getUnitConsumed()
    {
        return unitConsumed;
    }

    public void setUnitConsumed(java.math.BigDecimal unitConsumed)
    {
        this.unitConsumed = unitConsumed;
    }

    public boolean isBlocked()
    {
        return blocked;
    }

    public void setBlocked(boolean blocked)
    {
        this.blocked = blocked;
    }

    public Integer getBlockCode()
    {
        return blockCode;
    }

    public void setBlockCode(Integer blockCode)
    {
        this.blockCode = blockCode;
    }

    public String getBlockReason()
    {
        return blockReason;
    }

    public void setBlockReason(String blockReason)
    {
        this.blockReason = blockReason;
    }

    public JSONObject getData()
    {
        return data;
    }

    public void setData(JSONObject data)
    {
        this.data = data;
    }

    public java.util.Date getFetchedAt()
    {
        return fetchedAt;
    }

    public void setFetchedAt(java.util.Date fetchedAt)
    {
        this.fetchedAt = fetchedAt;
    }

    public java.util.Date getCacheExpireAt()
    {
        return cacheExpireAt;
    }

    public void setCacheExpireAt(java.util.Date cacheExpireAt)
    {
        this.cacheExpireAt = cacheExpireAt;
    }
}
