package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_api_raw */
public class ApiRaw extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 对应一次调用 srm_api_call_log.id */
    private Long callLogId;

    /**  */
    private Integer apiId;

    /** 查询主体 */
    private String subjectKey;

    /** 请求参数（不含密钥/令牌，任何情况下不入库） */
    private String requestJson;

    /** 原始响应（HTTP 层原样），结构化表可由本字段重建 */
    private String responseJson;

    /** 接口业务返回码/错误描述 */
    private String respStatus;

    /**  */
    private Integer httpStatus;

    /** 响应耗时 */
    private Integer costMs;

    /** db / file（超大响应落对象存储或本地磁盘，path 记于 storage_path） */
    private String storageType;

    /**  */
    private String storagePath;

    /** 响应字节数，供清理策略使用 */
    private Integer byteSize;

    /**  */
    private Date fetchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getCallLogId() { return callLogId; }

    public void setCallLogId(Long callLogId) { this.callLogId = callLogId; }

    public Integer getApiId() { return apiId; }

    public void setApiId(Integer apiId) { this.apiId = apiId; }

    public String getSubjectKey() { return subjectKey; }

    public void setSubjectKey(String subjectKey) { this.subjectKey = subjectKey; }

    public String getRequestJson() { return requestJson; }

    public void setRequestJson(String requestJson) { this.requestJson = requestJson; }

    public String getResponseJson() { return responseJson; }

    public void setResponseJson(String responseJson) { this.responseJson = responseJson; }

    public String getRespStatus() { return respStatus; }

    public void setRespStatus(String respStatus) { this.respStatus = respStatus; }

    public Integer getHttpStatus() { return httpStatus; }

    public void setHttpStatus(Integer httpStatus) { this.httpStatus = httpStatus; }

    public Integer getCostMs() { return costMs; }

    public void setCostMs(Integer costMs) { this.costMs = costMs; }

    public String getStorageType() { return storageType; }

    public void setStorageType(String storageType) { this.storageType = storageType; }

    public String getStoragePath() { return storagePath; }

    public void setStoragePath(String storagePath) { this.storagePath = storagePath; }

    public Integer getByteSize() { return byteSize; }

    public void setByteSize(Integer byteSize) { this.byteSize = byteSize; }

    public Date getFetchedAt() { return fetchedAt; }

    public void setFetchedAt(Date fetchedAt) { this.fetchedAt = fetchedAt; }

}
