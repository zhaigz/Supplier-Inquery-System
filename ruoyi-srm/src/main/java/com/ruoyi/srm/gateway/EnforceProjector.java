package com.ruoyi.srm.gateway;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.domain.RiskEnforce;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.service.IRiskEnforceService;

/**
 * 被执行人投影器：839（ENFORCE · 当前）与 871（ENFORCE_HIST · 历史）同构，落 srm_risk_enforce，
 * 靠 hist_flag 分列（0 当前 / 1 历史）。
 *
 * <p>字段映射（xlsx items[]）：caseCode→case_no、pname→person_name、execCourtName→court、
 * partyCardNum→id_no（敏感，TODO 入库加密）、caseCreateTime→create_date、execMoney→target_amount、
 * result.total→total_count。
 *
 * <p>重建范围仅限本次编号对应 hist_flag 的存量行，另一 hist_flag（另一编号）的行不受影响。
 */
@Component
public class EnforceProjector extends AbstractTycProjector
{
    @Autowired
    private IRiskEnforceService enforceService;

    @Override
    public boolean supports(int apiId)
    {
        return apiId == SrmApiId.ENFORCE || apiId == SrmApiId.ENFORCE_HIST;
    }

    @Override
    public void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject body, Long rawId)
    {
        JSONObject result = result(body);
        if (result == null)
        {
            log.warn("被执行人投影跳过：无 result, apiId={} subjectKey={}", apiId, subjectKey);
            return;
        }
        Supplier supplier = findSupplier(subjectKey);
        if (supplier == null)
        {
            log.warn("被执行人投影跳过：主体键未匹配到供应商, subjectKey={}", subjectKey);
            return;
        }
        String histFlag = apiId == SrmApiId.ENFORCE_HIST ? "1" : "0";
        Integer totalCount = total(result);
        Date now = new Date();

        // 只重建本 hist_flag 的存量行
        RiskEnforce probe = new RiskEnforce();
        probe.setSupplierId(supplier.getId());
        List<RiskEnforce> old = enforceService.selectRiskEnforceList(probe);
        if (old != null)
        {
            for (RiskEnforce row : old)
            {
                if (histFlag.equals(row.getHistFlag()))
                {
                    enforceService.deleteRiskEnforceById(row.getId());
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
                RiskEnforce row = new RiskEnforce();
                row.setSupplierId(supplier.getId());
                row.setHistFlag(histFlag);
                row.setCaseNo(item.getString("caseCode"));
                row.setPersonName(item.getString("pname"));
                row.setCourt(item.getString("execCourtName"));
                row.setIdNo(item.getString("partyCardNum")); // TODO 敏感列入库加密
                row.setCreateDate(toDate(item.get("caseCreateTime")));
                row.setTargetAmount(str(item.get("execMoney")));
                row.setTotalCount(totalCount);
                row.setRawId(rawId);
                row.setFetchedAt(now);
                row.setCreateTime(now);
                enforceService.insertRiskEnforce(row);
                count++;
            }
        }
        log.info("被执行人投影完成 apiId={} supplierId={} histFlag={} 登记 {} 条", apiId, supplier.getId(), histFlag, count);
    }
}
