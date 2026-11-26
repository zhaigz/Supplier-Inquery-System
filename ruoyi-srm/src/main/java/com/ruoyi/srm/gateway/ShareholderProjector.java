package com.ruoyi.srm.gateway;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.SupplierShareholder;
import com.ruoyi.srm.service.ISupplierShareholderService;

/**
 * 821（SHAREHOLDER · 企业股东）投影器，落 srm_supplier_shareholder，走 TycGateway 六段流水线第 ⑥ 段自动投影。
 *
 * <p>与图谱三路不同：股东是 supplier 维度永久沉淀（表有 source_api_id 列），与 458/1046 同属 A 类样板，
 * 故实现为 {@link TycProjector} 由网关按 apiId 分发，无需 controller 手工接线。
 *
 * <p>落库策略：先删除该供应商 source_api_id=821 的旧行再整体重建（同 ContactProjector），
 * 保证翻页/换结果后不残留。
 *
 * <p>字段映射来源：《天眼查API接口》821 真实响应（以文档为准），容器 result.items[]，每条股东一个对象：
 * <ul>
 *   <li>name 股东名 → shareholder_name；alias 简称 → short_name；id 对应表id → tyc_ref_id；
 *       hcgid 人员hcgid → hcgid；type 股东类型（1 公司/2 人/3 其它）→ shareholder_type</li>
 *   <li>capital[0] 认缴：amomon 认缴额 → sub_amount、time 认缴时间 → sub_date、paymet 认缴方式 → sub_way、
 *       percent（形如 "99.5%"）→ ratio 与 hold_ratio</li>
 *   <li>capitalActl[0] 实缴：amomon → paid_amount、time → paid_date、paymet → paid_way</li>
 *   <li>ftShareholding 首次持股日期（epoch 毫秒）→ first_hold_date（格式化为 yyyy-MM-dd）</li>
 *   <li>cgid/logo 无对应结构化列，不落库；hold_num、is_hk_enterprise 本响应无来源字段，保持为空</li>
 * </ul>
 *
 * @author ruoyi
 */
@Component
public class ShareholderProjector extends AbstractTycProjector
{
    @Autowired
    private ISupplierShareholderService shareholderService;

    @Override
    public boolean supports(int apiId)
    {
        return apiId == SrmApiId.SHAREHOLDER;
    }

    @Override
    public void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject body, Long rawId)
    {
        // 821 响应容器：result.items[]
        JSONArray items = resolveItems(body);
        if (items == null || items.isEmpty())
        {
            log.warn("821 投影跳过：result.items 为空, subjectKey={}", subjectKey);
            return;
        }
        Supplier supplier = findSupplier(subjectKey);
        if (supplier == null)
        {
            log.warn("821 投影跳过：主体键未匹配到供应商, subjectKey={}", subjectKey);
            return;
        }

        // 重建本来源旧行（仅 821），不动其它来源
        SupplierShareholder old = new SupplierShareholder();
        old.setSupplierId(supplier.getId());
        old.setSourceApiId(SrmApiId.SHAREHOLDER);
        List<SupplierShareholder> oldRows = shareholderService.selectSupplierShareholderList(old);
        if (oldRows != null)
        {
            for (SupplierShareholder row : oldRows)
            {
                shareholderService.deleteSupplierShareholderById(row.getId());
            }
        }

        Date now = new Date();
        String dataFrom = str(params == null ? null : params.get("dataFrom"));
        int count = 0;
        for (int i = 0; i < items.size(); i++)
        {
            JSONObject item = items.getJSONObject(i);
            if (item == null || StringUtils.isEmpty(item.getString("name"))) continue;
            // capital[] 认缴、capitalActl[] 实缴：取首条，percent 形如 "99.5%"
            JSONObject should = firstElement(item, "capital");
            JSONObject actual = firstElement(item, "capitalActl");
            BigDecimal ratio = ratio(should, "percent");
            SupplierShareholder row = new SupplierShareholder();
            row.setSupplierId(supplier.getId());
            row.setShareholderName(item.getString("name"));
            row.setShareholderType(str(item.get("type")));
            row.setShortName(item.getString("alias"));
            row.setTycRefId(str(item.get("id")));
            row.setHcgid(item.getString("hcgid"));
            row.setRatio(ratio);
            row.setHoldRatio(ratio);
            row.setSubAmount(should == null ? null : should.getString("amomon"));
            row.setSubDate(should == null ? null : should.getString("time"));
            row.setSubWay(should == null ? null : should.getString("paymet"));
            row.setPaidAmount(actual == null ? null : actual.getString("amomon"));
            row.setPaidDate(actual == null ? null : actual.getString("time"));
            row.setPaidWay(actual == null ? null : actual.getString("paymet"));
            // ftShareholding 首次持股日期：epoch 毫秒 → yyyy-MM-dd
            Date firstHold = toDate(item.get("ftShareholding"));
            row.setFirstHoldDate(firstHold == null ? null : new SimpleDateFormat("yyyy-MM-dd").format(firstHold));
            row.setDataFrom(StringUtils.isNotEmpty(dataFrom) ? dataFrom : "1");
            row.setSourceApiId(SrmApiId.SHAREHOLDER);
            row.setRawId(rawId);
            row.setFetchedAt(now);
            row.setCreateTime(now);
            shareholderService.insertSupplierShareholder(row);
            count++;
        }
        log.info("821 投影完成 supplierId={} subjectKey={} 登记股东 {} 条", supplier.getId(), subjectKey, count);
    }

    /** 取 result.items 数组；兼容 result 直接为数组与 result.items 两种容器。 */
    private JSONArray resolveItems(JSONObject body)
    {
        if (body == null) return null;
        Object resNode = body.get("result");
        if (resNode instanceof JSONArray) return (JSONArray) resNode;
        if (resNode instanceof JSONObject) return ((JSONObject) resNode).getJSONArray("items");
        return null;
    }

    /** 取对象下名为 key 的数组首元素（capital / capitalActl），缺失返回 null。 */
    private JSONObject firstElement(JSONObject obj, String key)
    {
        JSONArray arr = obj == null ? null : obj.getJSONArray(key);
        return arr == null || arr.isEmpty() ? null : arr.getJSONObject(0);
    }

    /** 占比解析：兼容 "55%"、"55.000000"、0.55；>1 或带百分号视为百分数归一为小数。 */
    private BigDecimal ratio(JSONObject obj, String key)
    {
        String raw = obj == null ? null : obj.getString(key);
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
