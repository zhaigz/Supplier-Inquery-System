package com.ruoyi.srm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/** srm_rel_rule */
public class RelRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 关系规则名（页面7 命中明细的"命中规则"取此值） */
    private String ruleName;

    /** 关系维度编码：SERVE_ALL/INVEST/BRANCH/HISTORICAL_SHAREHOLDER/HISTORICAL_SERVE_ALL/ALL/legal_person/invest/common_hcg/phone/email/address */
    private String relDimension;

    /** 关系维度中文名：董监高/股东/分支机构/历史股东/历史董监高/所有类型（默认）/法定代表人/对外投资/共同董监高/同电话/同邮箱/同注册地址 */
    private String relDimensionName;

    /** 识别类型（页面10 列），本地规则维度与接口维度分列 */
    private String detectType;

    /** 分析方向：双向 / 向上 / 向下；1160 只有单一方向入参（up/down 各 10 层，不可双向），双向规则在网关侧拆成两次调用、分别计数计费 */
    private String direction;

    /** 线索权重 1—5（页面10；页面7 连线标注） */
    private Integer weight;

    /** 字段映射标识（由页面11 维护，指向 srm_field_mapping.inner_key） */
    private String fieldMappingCode;

    /** 承载接口：1161/1156/1141/1123/1160；本地计算维度为 NULL */
    private Integer apiId;

    /** 接口侧关系类型枚举原值：1-同电话 / 2-同邮箱 / 3-同注册地址 / SERVE_ALL 等 */
    private String tycRelType;

    /** 向上穿透层数，1—10（1160 层次，为空默认 4） */
    private Integer upDepth;

    /** 向下穿透层数，1—10 */
    private Integer downDepth;

    /** 穿透比例下限（≥，默认 0） */
    private BigDecimal ratioMin;

    /** 穿透比例上限（≤，默认 1） */
    private BigDecimal ratioMax;

    /** 识别说明（页面10 第 6 列） */
    private String remarkText;

    /** 是否推荐规则：1 系统预置，"恢复推荐规则"只重置本类 */
    private String recommendFlag;

    /** 默认启用：本期只开 股东 + 法定代表人（纪要结论），其余为 0 */
    private String defaultOn;

    /** 启用（页面10 第 1 列） */
    private String enabled;

    /**  */
    private Integer sortNo;

    /**  */
    private String delFlag;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getRuleName() { return ruleName; }

    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getRelDimension() { return relDimension; }

    public void setRelDimension(String relDimension) { this.relDimension = relDimension; }

    public String getRelDimensionName() { return relDimensionName; }

    public void setRelDimensionName(String relDimensionName) { this.relDimensionName = relDimensionName; }

    public String getDetectType() { return detectType; }

    public void setDetectType(String detectType) { this.detectType = detectType; }

    public String getDirection() { return direction; }

    public void setDirection(String direction) { this.direction = direction; }

    public Integer getWeight() { return weight; }

    public void setWeight(Integer weight) { this.weight = weight; }

    public String getFieldMappingCode() { return fieldMappingCode; }

    public void setFieldMappingCode(String fieldMappingCode) { this.fieldMappingCode = fieldMappingCode; }

    public Integer getApiId() { return apiId; }

    public void setApiId(Integer apiId) { this.apiId = apiId; }

    public String getTycRelType() { return tycRelType; }

    public void setTycRelType(String tycRelType) { this.tycRelType = tycRelType; }

    public Integer getUpDepth() { return upDepth; }

    public void setUpDepth(Integer upDepth) { this.upDepth = upDepth; }

    public Integer getDownDepth() { return downDepth; }

    public void setDownDepth(Integer downDepth) { this.downDepth = downDepth; }

    public BigDecimal getRatioMin() { return ratioMin; }

    public void setRatioMin(BigDecimal ratioMin) { this.ratioMin = ratioMin; }

    public BigDecimal getRatioMax() { return ratioMax; }

    public void setRatioMax(BigDecimal ratioMax) { this.ratioMax = ratioMax; }

    public String getRemarkText() { return remarkText; }

    public void setRemarkText(String remarkText) { this.remarkText = remarkText; }

    public String getRecommendFlag() { return recommendFlag; }

    public void setRecommendFlag(String recommendFlag) { this.recommendFlag = recommendFlag; }

    public String getDefaultOn() { return defaultOn; }

    public void setDefaultOn(String defaultOn) { this.defaultOn = defaultOn; }

    public String getEnabled() { return enabled; }

    public void setEnabled(String enabled) { this.enabled = enabled; }

    public Integer getSortNo() { return sortNo; }

    public void setSortNo(Integer sortNo) { this.sortNo = sortNo; }

    public String getDelFlag() { return delFlag; }

    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }

}
