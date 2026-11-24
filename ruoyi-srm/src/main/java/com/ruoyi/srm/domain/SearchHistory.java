package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_search_history */
public class SearchHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 查询人（按人展示，非全库共享） */
    private Long userId;

    /** 检索标识类型：公司名称/公司id/注册号/社会统一信用代码 */
    private String subjectType;

    /** 检索值 */
    private String subjectKey;

    /** 命中主档（未命中为空） */
    private Long supplierId;

    /** 匹配方式：模糊（包含匹配）/ 精确 */
    private String matchMode;

    /** 检索时间（页面2 最近查询 = 企业标识 + 检索时间） */
    private Date searchedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getSubjectType() { return subjectType; }

    public void setSubjectType(String subjectType) { this.subjectType = subjectType; }

    public String getSubjectKey() { return subjectKey; }

    public void setSubjectKey(String subjectKey) { this.subjectKey = subjectKey; }

    public Long getSupplierId() { return supplierId; }

    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getMatchMode() { return matchMode; }

    public void setMatchMode(String matchMode) { this.matchMode = matchMode; }

    public Date getSearchedAt() { return searchedAt; }

    public void setSearchedAt(Date searchedAt) { this.searchedAt = searchedAt; }

}
