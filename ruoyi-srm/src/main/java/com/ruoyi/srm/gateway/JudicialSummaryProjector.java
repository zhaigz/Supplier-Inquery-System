package com.ruoyi.srm.gateway;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.domain.RiskJudicialSummary;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.service.IRiskJudicialSummaryService;

/**
 * 1002（JUDICIAL_SUMMARY · 司法风险组合接口）投影器，落 srm_risk_judicial_summary。
 *
 * <p>组合接口一次返回 7 个维度数组，本投影按维度各写一行：dimension(中文维度名) +
 * item_count(该数组长度) + detail_json(该数组原样序列化留存)。明细字段不在库表固化，
 * 由页面按需解释，故只保证覆盖度而非结构化拆列。
 *
 * <p>result 数组 → 维度名（xlsx 1002）：lawSuitList→法律诉讼、ktAnnouncementList→开庭公告、
 * zhixingList→被执行人、courtAnnouncementList→法院公告、courtRegisterList→立案信息、
 * sendAnnouncementList→送达公告、dishonestList→失信人。表无来源列，按 supplier_id 整体重建。
 */
@Component
public class JudicialSummaryProjector extends AbstractTycProjector
{
    /** 数组键 → 中文维度名，顺序与接口文档一致，便于一眼核对。 */
    private static final String[][] DIMENSIONS = {
        { "lawSuitList", "法律诉讼" },
        { "ktAnnouncementList", "开庭公告" },
        { "zhixingList", "被执行人" },
        { "courtAnnouncementList", "法院公告" },
        { "courtRegisterList", "立案信息" },
        { "sendAnnouncementList", "送达公告" },
        { "dishonestList", "失信人" },
    };

    @Autowired
    private IRiskJudicialSummaryService judicialSummaryService;

    @Override
    public boolean supports(int apiId)
    {
        return apiId == SrmApiId.JUDICIAL_SUMMARY;
    }

    @Override
    public void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject body, Long rawId)
    {
        JSONObject result = result(body);
        if (result == null)
        {
            log.warn("1002 投影跳过：无 result, subjectKey={}", subjectKey);
            return;
        }
        Supplier supplier = findSupplier(subjectKey);
        if (supplier == null)
        {
            log.warn("1002 投影跳过：主体键未匹配到供应商, subjectKey={}", subjectKey);
            return;
        }
        Date now = new Date();

        RiskJudicialSummary probe = new RiskJudicialSummary();
        probe.setSupplierId(supplier.getId());
        List<RiskJudicialSummary> old = judicialSummaryService.selectRiskJudicialSummaryList(probe);
        if (old != null)
        {
            for (RiskJudicialSummary row : old)
            {
                judicialSummaryService.deleteRiskJudicialSummaryById(row.getId());
            }
        }

        int dimCount = 0;
        for (String[] dim : DIMENSIONS)
        {
            JSONArray arr = result.getJSONArray(dim[0]);
            int size = arr == null ? 0 : arr.size();
            RiskJudicialSummary row = new RiskJudicialSummary();
            row.setSupplierId(supplier.getId());
            row.setDimension(dim[1]);
            row.setItemCount(size);
            row.setDetailJson(arr == null ? "[]" : arr.toJSONString());
            row.setRawId(rawId);
            row.setFetchedAt(now);
            row.setCreateTime(now);
            judicialSummaryService.insertRiskJudicialSummary(row);
            dimCount++;
        }
        log.info("1002 投影完成 supplierId={} subjectKey={} 登记 {} 个维度", supplier.getId(), subjectKey, dimCount);
    }
}
