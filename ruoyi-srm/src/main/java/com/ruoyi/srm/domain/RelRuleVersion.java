package com.ruoyi.srm.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_rel_rule_version */
public class RelRuleVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 规则版本号，连续不重复（页面7 审计留痕之"规则版本"） */
    private Integer versionNo;

    /** 新增/编辑/启停/删除/恢复推荐 */
    private String changeType;

    /** 变更后全量启用规则快照（页面13 规则快照从此引用） */
    private String snapshotJson;

    /** 变更前后差异，供配置调整留痕 */
    private String diffJson;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Integer getVersionNo() { return versionNo; }

    public void setVersionNo(Integer versionNo) { this.versionNo = versionNo; }

    public String getChangeType() { return changeType; }

    public void setChangeType(String changeType) { this.changeType = changeType; }

    public String getSnapshotJson() { return snapshotJson; }

    public void setSnapshotJson(String snapshotJson) { this.snapshotJson = snapshotJson; }

    public String getDiffJson() { return diffJson; }

    public void setDiffJson(String diffJson) { this.diffJson = diffJson; }

}
