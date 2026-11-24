package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_data_snapshot */
public class DataSnapshot extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    private Long supplierId;

    /**  */
    private String creditCode;

    /** 快照对象类型：industry_info/shareholder/person/former_name/risk_illegal/risk_abnormal/judicial/equity/backstage */
    private String snapshotType;

    /** 快照键：企业 + 关系口径/深度 或 穿透方向/层数/比例（页面5/6 快照键口径） */
    private String snapshotKey;

    /** 参与比对字段的规范化摘要，相等即无变更（空值不参与比对） */
    private String snapshotHash;

    /** 参与比对的字段子集（只存字段映射表 monitor_flag=1 的字段） */
    private String payloadJson;

    /** 本快照由哪些接口取回 */
    private String apiIds;

    /**  */
    private Long rawId;

    /** 采集时间 */
    private Date capturedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getCreditCode() { return creditCode; }

    public void setCreditCode(String creditCode) { this.creditCode = creditCode; }

    public String getSnapshotType() { return snapshotType; }

    public void setSnapshotType(String snapshotType) { this.snapshotType = snapshotType; }

    public String getSnapshotKey() { return snapshotKey; }

    public void setSnapshotKey(String snapshotKey) { this.snapshotKey = snapshotKey; }

    public String getSnapshotHash() { return snapshotHash; }

    public void setSnapshotHash(String snapshotHash) { this.snapshotHash = snapshotHash; }

    public String getPayloadJson() { return payloadJson; }

    public void setPayloadJson(String payloadJson) { this.payloadJson = payloadJson; }

    public String getApiIds() { return apiIds; }

    public void setApiIds(String apiIds) { this.apiIds = apiIds; }

    public Long getRawId() { return rawId; }

    public void setRawId(Long rawId) { this.rawId = rawId; }

    public Date getCapturedAt() { return capturedAt; }

    public void setCapturedAt(Date capturedAt) { this.capturedAt = capturedAt; }

}
