package com.ruoyi.srm.gateway;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.domain.RiskDishonest;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.service.IRiskDishonestService;

/**
 * 872（DISHONEST · 历史失信人）投影器，落 srm_risk_dishonest。
 *
 * <p>字段映射（xlsx items[]）：iname→person_name、cardnum→id_no(敏感)、casecode→case_no、
 * courtname→court、amountInvolved→amount、publishdate→publish_time、regdate→filing_date、
 * disrupttypename→conduct_type、performance→perform_status、duty→obligation、
 * gistid→basis_no、gistunit→basis_org、areaname→province_area、type→dishonest_type、
 * result.total→total_count。表无来源列，按 supplier_id 整体重建。
 */
@Component
public class DishonestProjector extends AbstractTycProjector
{
    @Autowired
    private IRiskDishonestService dishonestService;

    @Override
    public boolean supports(int apiId)
    {
        return apiId == SrmApiId.DISHONEST;
    }

    @Override
    public void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject body, Long rawId)
    {
        JSONObject result = result(body);
        if (result == null)
        {
            log.warn("872 投影跳过：无 result, subjectKey={}", subjectKey);
            return;
        }
        Supplier supplier = findSupplier(subjectKey);
        if (supplier == null)
        {
            log.warn("872 投影跳过：主体键未匹配到供应商, subjectKey={}", subjectKey);
            return;
        }
        Integer totalCount = total(result);
        Date now = new Date();

        RiskDishonest probe = new RiskDishonest();
        probe.setSupplierId(supplier.getId());
        List<RiskDishonest> old = dishonestService.selectRiskDishonestList(probe);
        if (old != null)
        {
            for (RiskDishonest row : old)
            {
                dishonestService.deleteRiskDishonestById(row.getId());
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
                RiskDishonest row = new RiskDishonest();
                row.setSupplierId(supplier.getId());
                row.setPersonName(item.getString("iname"));
                row.setIdNo(item.getString("cardnum")); // TODO 敏感列入库加密
                row.setCaseNo(item.getString("casecode"));
                row.setCourt(item.getString("courtname"));
                row.setAmount(str(item.get("amountInvolved")));
                row.setPublishTime(toDate(item.get("publishdate")));
                row.setFilingDate(toDate(item.get("regdate")));
                row.setConductType(item.getString("disrupttypename"));
                row.setPerformStatus(item.getString("performance"));
                row.setObligation(item.getString("duty"));
                row.setBasisNo(item.getString("gistid"));
                row.setBasisOrg(item.getString("gistunit"));
                row.setProvinceArea(item.getString("areaname"));
                row.setDishonestType(item.getString("type"));
                row.setTotalCount(totalCount);
                row.setRawId(rawId);
                row.setFetchedAt(now);
                row.setCreateTime(now);
                dishonestService.insertRiskDishonest(row);
                count++;
            }
        }
        log.info("872 投影完成 supplierId={} subjectKey={} 登记 {} 条", supplier.getId(), subjectKey, count);
    }
}
