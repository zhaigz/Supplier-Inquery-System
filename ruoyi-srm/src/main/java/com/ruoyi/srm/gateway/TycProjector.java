package com.ruoyi.srm.gateway;

import com.alibaba.fastjson2.JSONObject;

/**
 * 外调结果投影器（TycGateway 六段流水线第 ⑥ 段"投影"扩展点）。
 *
 * <p>投影规则来源：srm_field_mapping（status=0 有效行）+ API接口文档 §2.4 落库三件套。
 * 各页面按自身结构化表实现，网关只负责按 apiId 分发，不硬编码任何业务字段。
 */
public interface TycProjector
{
    /** 该投影器是否处理此 apiId。 */
    boolean supports(int apiId);

    /**
     * 把一次成功外调写入结构化表，必须带 source_api_id / raw_id / fetched_at 三件套。
     *
     * @param apiId      外调编号
     * @param subjectKey 主体键
     * @param params     入参
     * @param data       响应体
     * @param rawId      srm_api_raw.id
     */
    void project(int apiId, String subjectKey, java.util.Map<String, Object> params, JSONObject data, Long rawId);
}
