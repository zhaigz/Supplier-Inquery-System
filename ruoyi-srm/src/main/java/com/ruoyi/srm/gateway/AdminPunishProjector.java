package com.ruoyi.srm.gateway;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.domain.RiskAdminPunish;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.service.IRiskAdminPunishService;

/**
 * 行政处罚投影器：846（ADMIN_PUNISH_ILLEGAL · 严重违法）与 848（ADMIN_PUNISH_ABNORMAL · 经营异常）
 * 同构，落 srm_risk_admin_punish，靠 risk_type 区分（illegal / abnormal）。
 *
 * <p>字段映射（xlsx items[]）：putReason→list_reason、putDepartment→list_dept、putDate→list_date、
 * removeReason→remove_reason、removeDepartment→remove_dept、removeDate→remove_date、total→total_count。
 * putDate 在 846 为 epoch 毫秒、848 为 yyyy-MM-dd 字符串，由 toDate 统一收敛。
 *
 * <p>重建范围仅限本次编号对应 risk_type 的存量行，另一 risk_type 不受影响。
 */
@Component
public class AdminPunishProjector extends AbstractTycProjector
{
    @Autowired
    private IRiskAdminPunishService punishService;

    @Override
    public boolean supports(int apiId)
    {
        return apiId == SrmApiId.ADMIN_PUNISH_ILLEGAL || apiId == SrmApiId.ADMIN_PUNISH_ABNORMAL;
    }

    @Override
    public void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject body, Long rawId)
    {
        JSONObject result = result(body);
        if (result == null)
        {
            log.warn("行政处罚投影跳过：无 result, apiId={} subjectKey={}", apiId, subjectKey);
            return;
        }
        Supplier supplier = findSupplier(subjectKey);
        if (supplier == null)
        {
            log.warn("行政处罚投影跳过：主体键未匹配到供应商, subjectKey={}", subjectKey);
            return;
        }
        String riskType = apiId == SrmApiId.ADMIN_PUNISH_ILLEGAL ? "illegal" : "abnormal";
        Integer totalCount = total(result);
        Date now = new Date();

        RiskAdminPunish probe = new RiskAdminPunish();
        probe.setSupplierId(supplier.getId());
        List<RiskAdminPunish> old = punishService.selectRiskAdminPunishList(probe);
        if (old != null)
        {
            for (RiskAdminPunish row : old)
            {
                if (riskType.equals(row.getRiskType()))
                {
                    punishService.deleteRiskAdminPunishById(row.getId());
                }
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
                RiskAdminPunish row = new RiskAdminPunish();
                row.setSupplierId(supplier.getId());
                row.setRiskType(riskType);
                row.setListReason(item.getString("putReason"));
                row.setListDept(item.getString("putDepartment"));
                row.setListDate(toDate(item.get("putDate")));
                row.setRemoveReason(item.getString("removeReason"));
                row.setRemoveDept(item.getString("removeDepartment"));
                row.setRemoveDate(toDate(item.get("removeDate")));
                row.setTotalCount(totalCount);
                row.setRawId(rawId);
                row.setFetchedAt(now);
                row.setCreateTime(now);
                punishService.insertRiskAdminPunish(row);
                count++;
            }
        }
        log.info("行政处罚投影完成 apiId={} supplierId={} riskType={} 登记 {} 条", apiId, supplier.getId(), riskType, count);
    }
}
