package com.ruoyi.srm.gateway;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.SupplierContact;
import com.ruoyi.srm.service.ISupplierContactService;

/**
 * 1046（CONTACT · 企业联系方式）投影器——六段流水线第 ⑥ 段"投影"打样实现。
 *
 * <p>响应结构来源：《天眼查API接口.xlsx》1046 响应列，result 节点字段：
 * <ul>
 *   <li>phoneNumber 主用电话 → phone 行，is_primary=1</li>
 *   <li>allCalls[]（phoneNumber/numberType/source/tag）→ phone 行，
 *       phone_source=source（号码来源）、phone_label=tag（标签）</li>
 *   <li>email → email 行（空串不落）</li>
 *   <li>websiteList → url 行（多个网址按分号/逗号拆分）</li>
 *   <li>regLocation → address 行（注册地址）</li>
 * </ul>
 *
 * <p>落库策略：srm_supplier_contact 唯一键为 (supplier_id, contact_type, contact_value)。
 * 先删除该供应商本来源（source_api_id=1046）的旧行再整体重建，保证翻页/换结果后不残留；
 * 818 落的同值行属于其它来源，不受影响，靠代码内去重避免与唯一键冲突。
 *
 * <p>TODO srm_field_mapping 未配置 1046 映射行，暂按本类硬编码映射；配置表就绪后切换为
 * 通用 FieldMappingProjector（IFieldMappingService 按 src_field→dst_field 驱动）。
 */
@Component
public class ContactProjector extends AbstractTycProjector
{
    @Autowired
    private ISupplierContactService contactService;

    @Override
    public boolean supports(int apiId)
    {
        return apiId == SrmApiId.CONTACT;
    }

    @Override
    public void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject body, Long rawId)
    {
        // 网关传入的是完整响应体，业务节点在 result 下
        JSONObject result = result(body);
        if (result == null)
        {
            log.warn("1046 投影跳过：响应无 result 节点, subjectKey={}", subjectKey);
            return;
        }
        Supplier supplier = findSupplier(subjectKey);
        if (supplier == null)
        {
            log.warn("1046 投影跳过：主体键未匹配到供应商, subjectKey={}", subjectKey);
            return;
        }

        // 按 类型+规范化值 去重收集：主用电话优先登记，allCalls 与主档同值时不重复
        Map<String, SupplierContact> rows = new LinkedHashMap<>();
        collect(rows, supplier.getId(), "phone", result.getString("phoneNumber"),
                null, null, "1", rawId);
        JSONArray allCalls = result.getJSONArray("allCalls");
        if (allCalls != null)
        {
            for (int i = 0; i < allCalls.size(); i++)
            {
                JSONObject call = allCalls.getJSONObject(i);
                if (call == null)
                {
                    continue;
                }
                collect(rows, supplier.getId(), "phone", call.getString("phoneNumber"),
                        call.getString("source"), call.getString("tag"), "0", rawId);
            }
        }
        collect(rows, supplier.getId(), "email", result.getString("email"), null, null, "1", rawId);
        collect(rows, supplier.getId(), "address", result.getString("regLocation"), null, null, "1", rawId);
        String websiteList = result.getString("websiteList");
        if (StringUtils.isNotEmpty(websiteList))
        {
            // 网址可能多个，按分号/逗号拆分逐个登记，第一条为主用
            Set<String> primaryDone = new LinkedHashSet<>();
            for (String url : websiteList.split("[;；,，]\\s*"))
            {
                if (StringUtils.isEmpty(url))
                {
                    continue;
                }
                String isPrimary = primaryDone.add("url") ? "1" : "0";
                collect(rows, supplier.getId(), "url", url, null, null, isPrimary, rawId);
            }
        }
        if (rows.isEmpty())
        {
            log.info("1046 投影：响应无可登记联系值, subjectKey={}", subjectKey);
            return;
        }

        // 重建本来源旧行：先删 1046 的存量，再插新结果
        SupplierContact old = new SupplierContact();
        old.setSupplierId(supplier.getId());
        old.setSourceApiId(SrmApiId.CONTACT);
        List<SupplierContact> oldRows = contactService.selectSupplierContactList(old);
        if (oldRows != null)
        {
            for (SupplierContact row : oldRows)
            {
                contactService.deleteSupplierContactById(row.getId());
            }
        }
        Date now = new Date();
        for (SupplierContact row : rows.values())
        {
            // 与其它来源（如 818）同值撞唯一键的行直接跳过，保留原来源行
            if (existsSameValue(row))
            {
                continue;
            }
            row.setCreateTime(now);
            contactService.insertSupplierContact(row);
        }
        log.info("1046 投影完成 supplierId={} subjectKey={} 登记 {} 条", supplier.getId(), subjectKey, rows.size());
    }

    /** 规范化后放入去重容器；空值不登记。同键后来者不覆盖先到者（主用电话先收集，优先级天然更高）。 */
    private void collect(Map<String, SupplierContact> rows, Long supplierId, String type, String rawValue,
                         String phoneSource, String phoneLabel, String isPrimary, Long rawId)
    {
        String value = normalize(type, rawValue);
        if (StringUtils.isEmpty(value))
        {
            return;
        }
        String uk = type + "|" + value;
        if (rows.containsKey(uk))
        {
            return;
        }
        SupplierContact row = new SupplierContact();
        row.setSupplierId(supplierId);
        row.setContactType(type);
        row.setContactValue(value);
        row.setRawValue(StringUtils.substring(rawValue, 0, 300));
        row.setPhoneSource(phoneSource);
        row.setPhoneLabel(phoneLabel);
        row.setIsPrimary(isPrimary);
        row.setSourceApiId(SrmApiId.CONTACT);
        row.setRawId(rawId);
        row.setFetchedAt(new Date());
        rows.put(uk, row);
    }

    /** 规范化（页面11 规则）：去首尾空白；电话去空格与连字符保留数字/+；地址折叠连续空白。 */
    private String normalize(String type, String rawValue)
    {
        if (StringUtils.isBlank(rawValue))
        {
            return null;
        }
        String v = rawValue.trim();
        if ("phone".equals(type))
        {
            v = v.replaceAll("[\\s\\-]", "");
        }
        else
        {
            v = v.replaceAll("\\s+", " ");
        }
        return StringUtils.isEmpty(v) ? null : StringUtils.substring(v, 0, 200);
    }

    /** 唯一键冲突检查：同供应商+类型+值已被其它来源（818 等）登记时不重复插入。 */
    private boolean existsSameValue(SupplierContact row)
    {
        SupplierContact query = new SupplierContact();
        query.setSupplierId(row.getSupplierId());
        query.setContactType(row.getContactType());
        query.setContactValue(row.getContactValue());
        List<SupplierContact> exists = contactService.selectSupplierContactList(query);
        return exists != null && !exists.isEmpty();
    }
}
