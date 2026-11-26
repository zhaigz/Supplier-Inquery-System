package com.ruoyi.srm.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.domain.GraphEdge;
import com.ruoyi.srm.domain.GraphNode;
import com.ruoyi.srm.domain.RelClue;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.gateway.TycResult;
import com.ruoyi.srm.service.IGraphEdgeService;
import com.ruoyi.srm.service.IGraphNodeService;
import com.ruoyi.srm.service.IGraphProjectionService;
import com.ruoyi.srm.service.IRelClueService;
import com.ruoyi.srm.service.ISupplierService;

/**
 * 图谱 / 项目关系投影实现（详见 {@link IGraphProjectionService} 设计说明）。
 *
 * <p>幂等：每次投影先按 taskId 载入已存在 nodeKey / edgeKey 集合，重复节点、重复连线只补不重复插。
 * 无权威字段映射配置（srm_field_mapping 未初始化），键名严格按《天眼查API接口》1160/1161/1123/1156
 * 真实响应对应，逐字取键，缺失键回落候选名，绝不臆造。
 *
 * @author ruoyi
 */
@Service
public class GraphProjectionServiceImpl implements IGraphProjectionService
{
    private static final Logger log = LoggerFactory.getLogger(GraphProjectionServiceImpl.class);

    @Autowired private IGraphNodeService graphNodeService;
    @Autowired private IGraphEdgeService graphEdgeService;
    @Autowired private IRelClueService relClueService;
    @Autowired private ISupplierService supplierService;

    // ============================================================ 1160 股权穿透

    @Override
    public int projectEquity(Long taskId, String subjectKey, TycResult result)
    {
        if (!usable(result)) return 0;
        // 1160 result 为数组，首元素是查询主体根节点，children 递归下钻
        JSONArray roots = result.getData().getJSONArray("result");
        if (roots == null || roots.isEmpty())
        {
            log.info("1160 投影跳过：result 数组为空, taskId={} subjectKey={}", taskId, subjectKey);
            return 0;
        }
        Set<String> nodeKeys = existingNodeKeys(taskId);
        Set<String> edgeKeys = existingEdgeKeys(taskId);
        Long rawId = result.getRawId();
        int[] written = {0, 0};
        for (int i = 0; i < roots.size(); i++)
        {
            walkEquity(taskId, subjectKey, roots.getJSONObject(i), null, 0, rawId, nodeKeys, edgeKeys, written);
        }
        log.info("1160 投影完成 taskId={} 节点累计写 {} 连线累计写 {}", taskId, written[0], written[1]);
        return written[0] + written[1];
    }

    /** 递归落 1160 子树节点，并为 child 生成 parent→child 连线（占比取 child.percent）。 */
    private void walkEquity(Long taskId, String subjectKey, JSONObject node, String parentNodeKey, int level,
                            Long rawId, Set<String> nodeKeys, Set<String> edgeKeys, int[] written)
    {
        if (node == null) return;
        String nodeKey = pick(node, "id");
        if (StringUtils.isEmpty(nodeKey)) return;
        if (nodeKeys.add(nodeKey))
        {
            GraphNode g = baseNode(taskId, rawId, SrmApiId.EQUITY_PENETRATION);
            g.setNodeKey(nodeKey);
            g.setNodeType(pick(node, "lable", "label"));
            g.setNodeName(pick(node, "name"));
            g.setCreditCode(pick(node, "creditCode"));
            g.setRegStatus(pick(node, "regStatus"));
            g.setLevelNo(level);
            g.setParentNodeKey(parentNodeKey);
            // open 为布尔原文字段，逐字保留到 canExtend（"true"/"false"）
            g.setCanExtend(pick(node, "open"));
            alignSupplier(g, subjectKey);
            graphNodeService.insertGraphNode(g);
            written[0]++;
        }
        JSONArray children = node.getJSONArray("children");
        if (children != null)
        {
            for (int i = 0; i < children.size(); i++)
            {
                JSONObject child = children.getJSONObject(i);
                if (child == null) continue;
                String childKey = pick(child, "id");
                if (StringUtils.isNotEmpty(childKey))
                {
                    addEdge(taskId, rawId, SrmApiId.EQUITY_PENETRATION, edgeKeys,
                            nodeKey, childKey, pick(child, "id"), "INVEST",
                            ratio(child, "percent"), pick(child, "percent"), null);
                    written[1]++;
                }
                walkEquity(taskId, subjectKey, child, nodeKey, level + 1, rawId, nodeKeys, edgeKeys, written);
            }
        }
    }

    // ============================================================ 1161 幕后关系

    @Override
    public int projectBackstage(Long taskId, String subjectKey, TycResult result)
    {
        if (!usable(result)) return 0;
        JSONObject root = result.getData().getJSONObject("result");
        if (root == null)
        {
            log.info("1161 投影跳过：无 result 节点, taskId={} subjectKey={}", taskId, subjectKey);
            return 0;
        }
        // isRelationship 是本次关系发现的整体标识，落到连线 foundFlag
        String isRelationship = pick(root, "isRelationship");
        Set<String> nodeKeys = existingNodeKeys(taskId);
        Set<String> edgeKeys = existingEdgeKeys(taskId);
        Long rawId = result.getRawId();
        int[] written = {0, 0};
        for (String key : root.keySet())
        {
            // 仅 p_x 分片是含 nodes/relationships 的子图，其它（ids/idsTo/isRelationship...）跳过
            Object frag = root.get(key);
            if (!(frag instanceof JSONObject)) continue;
            writeFragment(taskId, subjectKey, rawId, SrmApiId.BACKSTAGE_RELATION, (JSONObject) frag,
                    nodeKeys, edgeKeys, written, isRelationship);
        }
        log.info("1161 投影完成 taskId={} 节点累计写 {} 连线累计写 {}", taskId, written[0], written[1]);
        return written[0] + written[1];
    }

    // ============================================================ 1123 疑似实际控制人

    @Override
    public int projectActualController(Long taskId, String subjectKey, TycResult result)
    {
        if (!usable(result)) return 0;
        JSONObject root = result.getData().getJSONObject("result");
        if (root == null)
        {
            log.info("1123 投影跳过：无 result 节点, taskId={} subjectKey={}", taskId, subjectKey);
            return 0;
        }
        Set<String> nodeKeys = existingNodeKeys(taskId);
        Set<String> edgeKeys = existingEdgeKeys(taskId);
        Long rawId = result.getRawId();
        int[] written = {0, 0};
        JSONObject pathMap = root.getJSONObject("pathMap");
        Set<String> redKeys = new LinkedHashSet<>();
        if (pathMap != null)
        {
            for (String key : pathMap.keySet())
            {
                Object frag = pathMap.get(key);
                if (!(frag instanceof JSONObject)) continue;
                writeFragment(taskId, subjectKey, rawId, SrmApiId.ACTUAL_CONTROLLER, (JSONObject) frag,
                        nodeKeys, edgeKeys, written, null);
                collectRedNodes((JSONObject) frag, redKeys);
            }
        }
        // actualControllerList 的 hId 即疑似实际控制人，标红
        JSONArray controllers = root.getJSONArray("actualControllerList");
        if (controllers != null)
        {
            for (int i = 0; i < controllers.size(); i++)
            {
                JSONObject c = controllers.getJSONObject(i);
                String hId = c == null ? null : pick(c, "hId", "hid");
                if (StringUtils.isNotEmpty(hId)) redKeys.add(hId);
            }
        }
        if (!redKeys.isEmpty()) markRed(taskId, redKeys);
        log.info("1123 投影完成 taskId={} 节点累计写 {} 连线累计写 {} 标红 {}", taskId, written[0], written[1], redKeys.size());
        return written[0] + written[1];
    }

    /**
     * 落一个 {nodes[], relationships[]} 子图分片（1161 与 1123 结构一致）。
     * 节点 nodeKey 优先 idNew，其次 id；连线 1161 带关系 id 作 edgeKey，1123 无 id 用 from|to|type 兜底。
     */
    private void writeFragment(Long taskId, String subjectKey, Long rawId, int apiId, JSONObject frag,
                               Set<String> nodeKeys, Set<String> edgeKeys, int[] written, String foundFlag)
    {
        JSONArray nodes = frag.getJSONArray("nodes");
        if (nodes != null)
        {
            for (int i = 0; i < nodes.size(); i++)
            {
                JSONObject n = nodes.getJSONObject(i);
                if (n == null) continue;
                String nodeKey = firstNonEmpty(pick(n, "idNew"), pick(n, "id"));
                if (StringUtils.isEmpty(nodeKey) || !nodeKeys.add(nodeKey)) continue;
                JSONArray labels = n.getJSONArray("labels");
                String name = null, regStatus = pick(n, "regStatus");
                JSONObject props = n.getJSONObject("properties");
                if (props != null) name = pick(props, "name");
                GraphNode g = baseNode(taskId, rawId, apiId);
                g.setNodeKey(nodeKey);
                g.setNodeType(labels == null || labels.isEmpty() ? null : labels.getString(0));
                g.setNodeName(name);
                g.setRegStatus(regStatus);
                if (props != null) g.setAttrsJson(props.toJSONString());
                alignSupplier(g, subjectKey);
                graphNodeService.insertGraphNode(g);
                written[0]++;
            }
        }
        JSONArray rels = frag.getJSONArray("relationships");
        if (rels != null)
        {
            for (int i = 0; i < rels.size(); i++)
            {
                JSONObject r = rels.getJSONObject(i);
                if (r == null) continue;
                String from = firstNonEmpty(pick(r, "startNodeNew"), pick(r, "startNode"));
                String to = firstNonEmpty(pick(r, "endNodeNew"), pick(r, "endNode"));
                String type = pick(r, "type");
                String edgeKey = firstNonEmpty(pick(r, "id"), from + "|" + to + "|" + type);
                JSONObject props = r.getJSONObject("properties");
                BigDecimal ratio = props == null ? null : ratio(props, "percent");
                String ratioLabel = props == null ? null : pick(props, "percentStr");
                if (addEdge(taskId, rawId, apiId, edgeKeys, from, to, edgeKey, type, ratio, ratioLabel, foundFlag))
                {
                    written[1]++;
                }
            }
        }
    }

    /** 收集 1123 关系中 properties.isRed==1 的起始节点作为标红候选。 */
    private void collectRedNodes(JSONObject frag, Set<String> redKeys)
    {
        JSONArray rels = frag.getJSONArray("relationships");
        if (rels == null) return;
        for (int i = 0; i < rels.size(); i++)
        {
            JSONObject r = rels.getJSONObject(i);
            JSONObject props = r == null ? null : r.getJSONObject("properties");
            String isRed = props == null ? null : pick(props, "isRed");
            if ("1".equals(isRed))
            {
                String from = firstNonEmpty(pick(r, "startNodeNew"), pick(r, "startNode"));
                if (StringUtils.isNotEmpty(from)) redKeys.add(from);
            }
        }
    }

    private void markRed(Long taskId, Set<String> nodeKeys)
    {
        GraphNode q = new GraphNode();
        q.setTaskId(taskId);
        List<GraphNode> rows = graphNodeService.selectGraphNodeList(q);
        if (rows == null) return;
        for (GraphNode n : rows)
        {
            if (nodeKeys.contains(n.getNodeKey()) && !"1".equals(n.getRedFlag()))
            {
                GraphNode patch = new GraphNode();
                patch.setId(n.getId());
                patch.setRedFlag("1");
                graphNodeService.updateGraphNode(patch);
            }
        }
    }

    // ============================================================ 1156 疑似关系

    @Override
    public int projectSuspectRelation(Long taskId, Long projectId, String subjectKey, TycResult result)
    {
        if (!usable(result)) return 0;
        JSONObject root = result.getData().getJSONObject("result");
        JSONArray items = root == null ? null : root.getJSONArray("items");
        if (items == null || items.isEmpty())
        {
            log.info("1156 投影跳过：result.items 为空, taskId={} subjectKey={}", taskId, subjectKey);
            return 0;
        }
        Supplier self = findSupplier(subjectKey);
        Long rawId = result.getRawId();
        Date now = new Date();
        Set<String> seen = existingClueKeys(taskId);
        int count = 0;
        for (int i = 0; i < items.size(); i++)
        {
            JSONObject item = items.getJSONObject(i);
            if (item == null) continue;
            String entBName = pick(item, "name");
            String entBTycId = pick(item, "id");
            if (StringUtils.isEmpty(entBName) && StringUtils.isEmpty(entBTycId)) continue;
            String uk = StringUtils.isNotEmpty(entBTycId) ? entBTycId : entBName;
            if (!seen.add(uk)) continue;
            RelClue clue = new RelClue();
            clue.setTaskId(taskId);
            clue.setProjectId(projectId);
            // A 侧 = 被分析主体；B 侧 = 1156 命中的疑似关联企业
            clue.setSupplierA(self == null ? null : self.getId());
            clue.setEntAName(subjectKey);
            clue.setEntATycId(self == null ? null : self.getTycCompanyId());
            clue.setEntBName(entBName);
            clue.setEntBTycId(entBTycId);
            Supplier other = findSupplier(entBName);
            if (other != null) clue.setSupplierB(other.getId());
            JSONArray legal = item.getJSONArray("legalPerson");
            if (legal != null && !legal.isEmpty())
            {
                JSONObject lp = legal.getJSONObject(0);
                if (lp != null)
                {
                    clue.setLegalPersonB(pick(lp, "legalPersonName"));
                    clue.setLegalTypeB(pick(lp, "legalPersonType"));
                }
            }
            clue.setRegCapitalB(pick(item, "regCapital"));
            clue.setEstablishB(pick(item, "establishTime"));
            clue.setClueType(pick(item, "type"));
            clue.setClueDetail(pick(item, "details"));
            clue.setBookkeepingPhone(pick(item, "bkNumber"));
            clue.setDataSource(String.valueOf(SrmApiId.SUSPECT_RELATION));
            clue.setSourceApiId(SrmApiId.SUSPECT_RELATION);
            clue.setRawId(rawId);
            clue.setReviewStatus("待复核");
            clue.setAnalyzedAt(now);
            clue.setCreateTime(now);
            relClueService.insertRelClue(clue);
            count++;
        }
        log.info("1156 投影完成 taskId={} projectId={} 写入线索 {} 条", taskId, projectId, count);
        return count;
    }

    // ============================================================ 公共工具

    /** TycResult 是否可投影：非拦截、带响应体、且 error_code 为 0。 */
    private boolean usable(TycResult result)
    {
        if (result == null || result.isBlocked() || result.getData() == null) return false;
        Object code = result.getData().get("error_code");
        return code == null || "0".equals(String.valueOf(code));
    }

    private GraphNode baseNode(Long taskId, Long rawId, int apiId)
    {
        GraphNode g = new GraphNode();
        g.setTaskId(taskId);
        g.setSourceApiId(apiId);
        g.setRawId(rawId);
        g.setCreateTime(new Date());
        return g;
    }

    /** 节点与本地主档对齐：优先按 subjectKey（当前分析主体），节点企业名与主档名一致也命中。 */
    private void alignSupplier(GraphNode g, String subjectKey)
    {
        Supplier s = findSupplier(subjectKey);
        if (s != null && subjectKey != null && subjectKey.equals(g.getNodeName()))
        {
            g.setSupplierId(s.getId());
            g.setIsMonitor(s.getMonitorFlag());
        }
    }

    /** 新增连线，edgeKey（或 from|to|relType）已存在则跳过返回 false。 */
    private boolean addEdge(Long taskId, Long rawId, int apiId, Set<String> edgeKeys,
                            String from, String to, String edgeKey, String relType,
                            BigDecimal ratio, String ratioLabel, String foundFlag)
    {
        if (StringUtils.isEmpty(from) || StringUtils.isEmpty(to)) return false;
        String uk = StringUtils.isNotEmpty(edgeKey) ? edgeKey : from + "|" + to + "|" + relType;
        if (!edgeKeys.add(uk)) return false;
        GraphEdge e = new GraphEdge();
        e.setTaskId(taskId);
        e.setEdgeKey(uk);
        e.setFromNodeKey(from);
        e.setToNodeKey(to);
        e.setFromIdNew(from);
        e.setToIdNew(to);
        e.setRelType(relType);
        e.setRatio(ratio);
        e.setRatioLabel(ratioLabel);
        e.setFoundFlag(foundFlag);
        e.setSourceApiId(apiId);
        e.setRawId(rawId);
        e.setCreateTime(new Date());
        graphEdgeService.insertGraphEdge(e);
        return true;
    }

    private Set<String> existingNodeKeys(Long taskId)
    {
        GraphNode q = new GraphNode();
        q.setTaskId(taskId);
        return collect(graphNodeService.selectGraphNodeList(q), GraphNode::getNodeKey);
    }

    private Set<String> existingEdgeKeys(Long taskId)
    {
        GraphEdge q = new GraphEdge();
        q.setTaskId(taskId);
        return collect(graphEdgeService.selectGraphEdgeList(q), GraphEdge::getEdgeKey);
    }

    private Set<String> existingClueKeys(Long taskId)
    {
        RelClue q = new RelClue();
        q.setTaskId(taskId);
        Set<String> keys = new HashSet<>();
        List<RelClue> rows = relClueService.selectRelClueList(q);
        if (rows != null)
        {
            for (RelClue c : rows)
            {
                String k = StringUtils.isNotEmpty(c.getEntBTycId()) ? c.getEntBTycId() : c.getEntBName();
                if (StringUtils.isNotEmpty(k)) keys.add(k);
            }
        }
        return keys;
    }

    private <T> Set<String> collect(List<T> list, java.util.function.Function<T, String> f)
    {
        Set<String> keys = new HashSet<>();
        if (list != null) for (T t : list) { String v = f.apply(t); if (StringUtils.isNotEmpty(v)) keys.add(v); }
        return keys;
    }

    /** 主体键反解供应商：信用代码 → 企业名 → tyc 公司 id 三级回落（与 A 类投影器一致）。 */
    private Supplier findSupplier(String subjectKey)
    {
        if (StringUtils.isEmpty(subjectKey)) return null;
        Supplier byCode = querySupplier("creditCode", subjectKey);
        if (byCode != null) return byCode;
        Supplier byName = querySupplier("supplierName", subjectKey);
        if (byName != null) return byName;
        return querySupplier("tycCompanyId", subjectKey);
    }

    private Supplier querySupplier(String field, String value)
    {
        Supplier query = new Supplier();
        if ("creditCode".equals(field)) query.setCreditCode(value);
        else if ("supplierName".equals(field)) query.setSupplierName(value);
        else query.setTycCompanyId(value);
        List<Supplier> rows = supplierService.selectSupplierList(query);
        return rows == null || rows.isEmpty() ? null : rows.get(0);
    }

    /** 按候选键顺序取第一个非空字符串值。 */
    private String pick(JSONObject obj, String... keys)
    {
        if (obj == null) return null;
        for (String k : keys)
        {
            Object v = obj.get(k);
            if (v != null && StringUtils.isNotEmpty(String.valueOf(v))) return String.valueOf(v);
        }
        return null;
    }

    private String firstNonEmpty(String a, String b) { return StringUtils.isNotEmpty(a) ? a : b; }

    /** 占比解析：兼容 "1.000000000000" 小数、"55%"、"55"（>1 视为百分数归一为小数）。 */
    private BigDecimal ratio(JSONObject obj, String key)
    {
        String raw = pick(obj, key);
        if (StringUtils.isEmpty(raw)) return null;
        String s = raw.trim();
        boolean percent = s.endsWith("%");
        if (percent) s = s.substring(0, s.length() - 1);
        try
        {
            BigDecimal v = new BigDecimal(s.trim());
            if (percent || v.compareTo(BigDecimal.ONE) > 0) v = v.movePointLeft(2);
            return v;
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }
}
