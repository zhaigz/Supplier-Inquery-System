package com.ruoyi.srm.gateway;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.domain.RiskTaxOwed;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.service.IRiskTaxOwedService;

/**
 * 851（TAX_OWED · 欠税公告）投影器，落 srm_risk_tax_owed。
 *
 * <p>字段映射（xlsx items[]）：taxIdNumber→taxpayer_no、name→taxpayer_name、
 * personIdNumber→id_no(敏感)、taxpayerType→taxpayer_type、regType→reg_type、
 * taxCategory→tax_type(欠税税种)、type→tax_category(税务类型)、ownTaxAmount→owed_amount、
 * ownTaxBalance→owed_balance、newOwnTaxBalance→new_owed_balance、publishDate→publish_time、
 * location→biz_address、department→tax_authority、legalpersonName→legal_name、
 * personIdName→legal_id_type；result.total→total_count。表无来源列，按 supplier_id 整体重建。
 *
 * <p>注意 xlsx 里 taxCategory 语义为"欠税税种"、type 语义为"税务类型"，与列名顺序相反，按注释对齐落库。
 */
@Component
public class TaxOwedProjector extends AbstractTycProjector
{
    @Autowired
    private IRiskTaxOwedService taxOwedService;

    @Override
    public boolean supports(int apiId)
    {
        return apiId == SrmApiId.TAX_OWED;
    }

    @Override
    public void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject body, Long rawId)
    {
        JSONObject result = result(body);
        if (result == null)
        {
            log.warn("851 投影跳过：无 result, subjectKey={}", subjectKey);
            return;
        }
        Supplier supplier = findSupplier(subjectKey);
        if (supplier == null)
        {
            log.warn("851 投影跳过：主体键未匹配到供应商, subjectKey={}", subjectKey);
            return;
        }
        Integer totalCount = total(result);
        Date now = new Date();

        RiskTaxOwed probe = new RiskTaxOwed();
        probe.setSupplierId(supplier.getId());
        List<RiskTaxOwed> old = taxOwedService.selectRiskTaxOwedList(probe);
        if (old != null)
        {
            for (RiskTaxOwed row : old)
            {
                taxOwedService.deleteRiskTaxOwedById(row.getId());
            }
        }

        JSONArray items = items(result);
        int count = 0;
        if (items != null)
        {
            for (int i = 0; i < items.size(); i++)
            {
                JSONObject item = items.getJSONObject(i);
                if (item == null)
                {
                    continue;
                }
                RiskTaxOwed row = new RiskTaxOwed();
                row.setSupplierId(supplier.getId());
                row.setTaxpayerNo(item.getString("taxIdNumber"));
                row.setTaxpayerName(item.getString("name"));
                row.setIdNo(item.getString("personIdNumber")); // TODO 敏感列入库加密
                row.setTaxpayerType(item.getString("taxpayerType"));
                row.setRegType(item.getString("regType"));
                row.setTaxType(item.getString("taxCategory")); // 欠税税种
                row.setTaxCategory(item.getString("type")); // 税务类型
                row.setOwedAmount(str(item.get("ownTaxAmount")));
                row.setOwedBalance(str(item.get("ownTaxBalance")));
                row.setNewOwedBalance(str(item.get("newOwnTaxBalance")));
                row.setPublishTime(toDate(item.get("publishDate")));
                row.setBizAddress(item.getString("location"));
                row.setTaxAuthority(item.getString("department"));
                row.setLegalName(item.getString("legalpersonName"));
                row.setLegalIdType(item.getString("personIdName"));
                row.setTotalCount(totalCount);
                row.setRawId(rawId);
                row.setFetchedAt(now);
                row.setCreateTime(now);
                taxOwedService.insertRiskTaxOwed(row);
                count++;
            }
        }
        log.info("851 投影完成 supplierId={} subjectKey={} 登记 {} 条", supplier.getId(), subjectKey, count);
    }
}
