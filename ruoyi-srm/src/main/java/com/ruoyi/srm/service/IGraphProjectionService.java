package com.ruoyi.srm.service;

import com.ruoyi.srm.gateway.TycResult;

/**
 * 图谱 / 项目关系投影服务（C 类：task 维度快照落库）。
 *
 * <p>与 A 类（风险 / 联系方式）的区别：A 类结构化表按 {@code supplier_id} 永久沉淀，由 TycGateway
 * 六段流水线第 ⑥ 段的 {@link com.ruoyi.srm.gateway.TycProjector} 自动投影；而股权穿透 / 幕后关系 /
 * 疑似关系产出的是 {@code srm_graph_node}/{@code srm_graph_edge}/{@code srm_rel_clue}，这些表按
 * {@code task_id} 版本快照存储，投影时需要 {@code taskId}（乃至 {@code projectId}），而 TycProjector
 * 契约拿不到 taskId。故本服务不复用流水线，改由各图谱 controller 在 {@code tycGateway.call(...)} 之后，
 * 携 {@code TycResult}（含完整响应体 data 与 rawId）显式调用，写入后回填 {@code srm_analysis_task.result_count}。
 *
 * <p>字段映射严格对齐《天眼查API接口》1160/1161/1123/1156 真实响应结构，不臆造键名。所有方法幂等：
 * 同一 task 重复投影时按 nodeKey / edgeKey / 线索业务键去重，不产生重复行。
 *
 * @author ruoyi
 */
public interface IGraphProjectionService
{
    /**
     * 1160（EQUITY_PENETRATION · 股权穿透）投影：result[] 递归子树落 graph_node，父子链 + percent 落 graph_edge。
     *
     * @return 本次写入的节点 + 连线行数（用于回填 resultCount）
     */
    int projectEquity(Long taskId, String subjectKey, TycResult result);

    /**
     * 1161（BACKSTAGE_RELATION · 幕后关系）投影：result 下 p_x 分片的 nodes/relationships 落 graph_node/graph_edge，
     * 按关系 id 作 edgeKey 去重。
     *
     * @return 本次写入的节点 + 连线行数
     */
    int projectBackstage(Long taskId, String subjectKey, TycResult result);

    /**
     * 1123（ACTUAL_CONTROLLER · 疑似实际控制人）投影：result.pathMap 各分片落 graph_node/graph_edge，
     * 按 isRed / actualControllerList 标红控制人节点。被股权穿透、幕后关系、项目分析三路复用。
     *
     * @return 本次写入的节点 + 连线行数
     */
    int projectActualController(Long taskId, String subjectKey, TycResult result);

    /**
     * 1156（SUSPECT_RELATION · 疑似关系发现）投影：result.items[] 每条疑似关联企业落 srm_rel_clue。
     *
     * @return 本次写入的线索行数
     */
    int projectSuspectRelation(Long taskId, Long projectId, String subjectKey, TycResult result);
}
