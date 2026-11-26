package com.ruoyi.srm.gateway;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.service.ISupplierService;

/**
 * 天眼查外调结果投影器公共骨架（六段流水线第 ⑥ 段"投影"）。
 *
 * <p>统一封装各同构投影器共用的三件事，避免 458/839/871/872/846/848/851/1002/1046 各写一份：
 * <ol>
 *   <li>主体键反解供应商：信用代码 → 企业名 → tyc 公司 id 三级回落；</li>
 *   <li>响应 result 节点与 items[]/total 的通用取法；</li>
 *   <li>日期/字符串的宽松解析：天眼查同名字段在不同接口里既可能是 epoch 毫秒(Long)，
 *       也可能是 "yyyy-MM-dd" / "yyyy-MM-dd HH:mm:ss" 字符串，还可能空串，统一收敛。</li>
 * </ol>
 *
 * <p>敏感列（身份证号/组织机构代码 id_no）：目标表注释要求"入库加密"，本批投影先按原值落库并留
 * TODO，加密与脱敏导出在接入统一加解密组件后集中处理，不改变字段覆盖度。
 */
public abstract class AbstractTycProjector implements TycProjector
{
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ISupplierService supplierService;

    /** 取响应体的 result 节点；body 为网关传入的完整响应体。 */
    protected JSONObject result(JSONObject body)
    {
        return body == null ? null : body.getJSONObject("result");
    }

    /** 取 result.items 数组（风险类明细分页接口通用），可能为 null。 */
    protected JSONArray items(JSONObject result)
    {
        return result == null ? null : result.getJSONArray("items");
    }

    /** 取 result.total（接口返回总数），缺失返回 null。 */
    protected Integer total(JSONObject result)
    {
        if (result == null)
        {
            return null;
        }
        Object v = result.get("total");
        return v == null ? null : Integer.valueOf(str(v));
    }

    /** 宽松取字符串：Number/Boolean 转字面量，空安全。 */
    protected String str(Object v)
    {
        return v == null ? null : String.valueOf(v);
    }

    /**
     * 宽松解析日期：支持 epoch 毫秒(Long/数字串)、"yyyy-MM-dd HH:mm:ss"、"yyyy-MM-dd"、"yyyy-MM"；
     * 空串/无法解析返回 null。
     */
    protected Date toDate(Object v)
    {
        if (v == null)
        {
            return null;
        }
        if (v instanceof Number)
        {
            long ms = ((Number) v).longValue();
            return ms <= 0 ? null : new Date(ms);
        }
        String s = String.valueOf(v).trim();
        if (StringUtils.isEmpty(s))
        {
            return null;
        }
        if (s.chars().allMatch(Character::isDigit))
        {
            try
            {
                return new Date(Long.parseLong(s));
            }
            catch (NumberFormatException ignored)
            {
                return null;
            }
        }
        String[] patterns = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM" };
        for (String p : patterns)
        {
            try
            {
                return new SimpleDateFormat(p).parse(s);
            }
            catch (Exception ignored)
            {
                // 换下一个格式
            }
        }
        return null;
    }

    /**
     * 主体键反解供应商：信用代码优先，其次企业名，最后 tyc 公司 id。查不到返回 null（投影方记 WARN 跳过）。
     */
    protected Supplier findSupplier(String subjectKey)
    {
        if (StringUtils.isEmpty(subjectKey))
        {
            return null;
        }
        Supplier byCode = supplierService.selectSupplierList(one("creditCode", subjectKey)).stream().findFirst().orElse(null);
        if (byCode != null)
        {
            return byCode;
        }
        Supplier byName = supplierService.selectSupplierList(one("supplierName", subjectKey)).stream().findFirst().orElse(null);
        if (byName != null)
        {
            return byName;
        }
        return supplierService.selectSupplierList(one("tycCompanyId", subjectKey)).stream().findFirst().orElse(null);
    }

    /** 构造只带单个查询条件的 Supplier（供 selectSupplierList 反查）。 */
    private Supplier one(String field, String value)
    {
        Supplier query = new Supplier();
        if ("creditCode".equals(field))
        {
            query.setCreditCode(value);
        }
        else if ("supplierName".equals(field))
        {
            query.setSupplierName(value);
        }
        else
        {
            query.setTycCompanyId(value);
        }
        return query;
    }

    /** 子类通用：把一次成功外调的 result.items 落库的占位说明（各实现自行覆写 project）。 */
    @Override
    public abstract void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject data, Long rawId);

    /** 供子类复用的取首行工具（避免 import List 冗余判断）。 */
    protected <T> T first(List<T> list)
    {
        return list == null || list.isEmpty() ? null : list.get(0);
    }
}
