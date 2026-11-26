package com.ruoyi.srm.gateway;

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
import com.ruoyi.srm.domain.SupplierFormerName;
import com.ruoyi.srm.service.ISupplierFormerNameService;

/**
 * 458（FORMER_NAME · 企业曾用名）投影器，落 srm_supplier_former_name。
 *
 * <p>响应结构（xlsx 458）：result.historyNameInfoList[]（name/beginTime/endTime，yyyy-MM）为主，
 * result.historyNames[]（仅名称）作兜底。表无 source_api_id 列，按 supplier_id 整体重建。
 */
@Component
public class FormerNameProjector extends AbstractTycProjector
{
    @Autowired
    private ISupplierFormerNameService formerNameService;

    @Override
    public boolean supports(int apiId)
    {
        return apiId == SrmApiId.FORMER_NAME;
    }

    @Override
    public void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject body, Long rawId)
    {
        JSONObject result = result(body);
        if (result == null)
        {
            log.warn("458 投影跳过：响应无 result 节点, subjectKey={}", subjectKey);
            return;
        }
        Supplier supplier = findSupplier(subjectKey);
        if (supplier == null)
        {
            log.warn("458 投影跳过：主体键未匹配到供应商, subjectKey={}", subjectKey);
            return;
        }

        Date now = new Date();
        SupplierFormerName probe = new SupplierFormerName();
        probe.setSupplierId(supplier.getId());
        List<SupplierFormerName> old = formerNameService.selectSupplierFormerNameList(probe);
        if (old != null)
        {
            for (SupplierFormerName row : old)
            {
                formerNameService.deleteSupplierFormerNameById(row.getId());
            }
        }

        int count = 0;
        JSONArray info = result.getJSONArray("historyNameInfoList");
        if (info != null)
        {
            for (int i = 0; i < info.size(); i++)
            {
                JSONObject item = info.getJSONObject(i);
                if (item == null || StringUtils.isBlank(item.getString("name")))
                {
                    continue;
                }
                SupplierFormerName row = new SupplierFormerName();
                row.setSupplierId(supplier.getId());
                row.setFormerName(item.getString("name"));
                row.setStartTime(item.getString("beginTime"));
                row.setEndTime(item.getString("endTime"));
                row.setRawId(rawId);
                row.setFetchedAt(now);
                row.setCreateTime(now);
                formerNameService.insertSupplierFormerName(row);
                count++;
            }
        }
        // historyNameInfoList 缺失时回落到仅含名称的 historyNames[]
        if (count == 0)
        {
            JSONArray plain = result.getJSONArray("historyNames");
            if (plain != null)
            {
                for (int i = 0; i < plain.size(); i++)
                {
                    String name = plain.getString(i);
                    if (StringUtils.isBlank(name))
                    {
                        continue;
                    }
                    SupplierFormerName row = new SupplierFormerName();
                    row.setSupplierId(supplier.getId());
                    row.setFormerName(name);
                    row.setRawId(rawId);
                    row.setFetchedAt(now);
                    row.setCreateTime(now);
                    formerNameService.insertSupplierFormerName(row);
                    count++;
                }
            }
        }
        log.info("458 投影完成 supplierId={} subjectKey={} 登记 {} 条", supplier.getId(), subjectKey, count);
    }
}
