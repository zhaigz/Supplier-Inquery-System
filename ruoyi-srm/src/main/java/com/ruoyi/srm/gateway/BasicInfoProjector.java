package com.ruoyi.srm.gateway;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.constant.SrmApiId;
import com.ruoyi.srm.domain.Supplier;

/**
 * 818 / 819（BASIC_INFO / BASIC_INFO_PERSON · 企业基本信息）投影器——六段流水线第 ⑥ 段"投影"。
 *
 * <p>补全闭环的关键一环：页面1 {@code POST /srm/supplier/enrich}、页面3 {@code detail/enrich}、
 * 页面15 监控调度经网关发出 818/819 后，本投影把响应 result 里的工商要素逐字回填
 * {@code srm_supplier} 主档，使 {@code localSedimentHit} 得以命中、此后检索永久离线可用。
 *
 * <p>字段映射（响应键 → 主档列，均取自《天眼查API接口文档》818/819 result 节点）：
 * <ul>
 *   <li>creditCode → credit_code 统一社会信用代码</li>
 *   <li>regNumber → reg_no 注册号</li>
 *   <li>id → tyc_company_id 企业 id（外部主体稳定标识）</li>
 *   <li>legalPersonName → legal_person 法定代表人</li>
 *   <li>regCapital → reg_capital 注册资本原文（含币种），并抽取前导数字 → reg_capital_num（万元）</li>
 *   <li>regStatus → biz_status 经营状态</li>
 *   <li>industry → industry 所属行业</li>
 *   <li>regLocation → reg_address 注册地址（规范化折叠空白）</li>
 *   <li>phoneNumber → phone 联系电话（规范化去空格连字符）</li>
 *   <li>email → email、city → city、district → district、districtCode → area_code</li>
 *   <li>estiblishTime（epoch 毫秒）→ establish_date 成立日期</li>
 * </ul>
 *
 * <p>不回填项：supplier_name 为本地主档权威值（且常是检索主体键本身），保持不覆盖；
 * province 一列 818/819 仅提供 {@code base}（省份拼音简称，如 "gz"），非规范省名，
 * 直接写入会污染展示口径，故留空不映射。
 *
 * <p>写回策略：只把"响应非空且与当前值不同"的列纳入 patch（{@code updateSupplier} 为动态 SQL，
 * null 列不参与 SET），无任何差异时不递增版本、不落库；有差异时 data_version+1 并刷新
 * last_enrich_at / last_update_at。
 */
@Component
public class BasicInfoProjector extends AbstractTycProjector
{
    private static final Pattern CAPITAL_NUM = Pattern.compile("\\d+(\\.\\d+)?");

    @Override
    public boolean supports(int apiId)
    {
        return apiId == SrmApiId.BASIC_INFO || apiId == SrmApiId.BASIC_INFO_PERSON;
    }

    @Override
    public void project(int apiId, String subjectKey, Map<String, Object> params, JSONObject body, Long rawId)
    {
        JSONObject result = result(body);
        if (result == null)
        {
            log.warn("{} 投影跳过：响应无 result 节点, subjectKey={}", apiId, subjectKey);
            return;
        }
        Supplier supplier = findSupplier(subjectKey);
        if (supplier == null)
        {
            log.warn("{} 投影跳过：主体键未匹配到供应商, subjectKey={}", apiId, subjectKey);
            return;
        }

        Supplier patch = new Supplier();
        patch.setId(supplier.getId());
        boolean changed = false;

        changed |= assign(supplier.getCreditCode(), result.getString("creditCode"), patch::setCreditCode);
        changed |= assign(supplier.getRegNo(), result.getString("regNumber"), patch::setRegNo);
        changed |= assign(supplier.getTycCompanyId(), str(result.get("id")), patch::setTycCompanyId);
        changed |= assign(supplier.getLegalPerson(), result.getString("legalPersonName"), patch::setLegalPerson);
        changed |= assign(supplier.getRegCapital(), result.getString("regCapital"), patch::setRegCapital);
        changed |= assign(supplier.getBizStatus(), result.getString("regStatus"), patch::setBizStatus);
        changed |= assign(supplier.getIndustry(), result.getString("industry"), patch::setIndustry);
        changed |= assign(supplier.getRegAddress(), normalizeAddr(result.getString("regLocation")), patch::setRegAddress);
        changed |= assign(supplier.getPhone(), normalizePhone(result.getString("phoneNumber")), patch::setPhone);
        changed |= assign(supplier.getEmail(), trim(result.getString("email")), patch::setEmail);
        changed |= assign(supplier.getCity(), trim(result.getString("city")), patch::setCity);
        changed |= assign(supplier.getDistrict(), trim(result.getString("district")), patch::setDistrict);
        changed |= assign(supplier.getAreaCode(), trim(result.getString("districtCode")), patch::setAreaCode);

        BigDecimal capNum = parseCapitalNum(result.getString("regCapital"));
        if (capNum != null && (supplier.getRegCapitalNum() == null || supplier.getRegCapitalNum().compareTo(capNum) != 0))
        {
            patch.setRegCapitalNum(capNum);
            changed = true;
        }
        Date est = toDate(result.get("estiblishTime"));
        if (est != null && !sameDay(supplier.getEstablishDate(), est))
        {
            patch.setEstablishDate(est);
            changed = true;
        }

        if (!changed)
        {
            log.info("{} 投影：主档工商要素无变化，跳过回填 supplierId={} subjectKey={}", apiId, supplier.getId(), subjectKey);
            return;
        }

        Date now = new Date();
        patch.setDataVersion((supplier.getDataVersion() == null ? 0 : supplier.getDataVersion()) + 1);
        patch.setLastEnrichAt(now);
        patch.setLastUpdateAt(now);
        patch.setUpdateBy(currentUser());
        patch.setUpdateTime(now);
        supplierService.updateSupplier(patch);
        log.info("{} 投影回填完成 supplierId={} subjectKey={} data_version {}→{}",
                apiId, supplier.getId(), subjectKey, supplier.getDataVersion(), patch.getDataVersion());
    }

    /** 仅当来值非空且与现值不同才写入 patch，返回是否发生变更。 */
    private boolean assign(String current, String incoming, Consumer<String> setter)
    {
        if (StringUtils.isEmpty(incoming) || incoming.equals(current))
        {
            return false;
        }
        setter.accept(incoming);
        return true;
    }

    /** 注册资本数值：从 "157549.2153万人民币" 抽取前导数字（单位：万元）。 */
    private BigDecimal parseCapitalNum(String regCapital)
    {
        if (StringUtils.isEmpty(regCapital))
        {
            return null;
        }
        Matcher m = CAPITAL_NUM.matcher(regCapital);
        if (!m.find())
        {
            return null;
        }
        try
        {
            return new BigDecimal(m.group());
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /** 地址规范化（页面11）：折叠连续空白为单个空格。 */
    private String normalizeAddr(String raw)
    {
        String v = trim(raw);
        return v == null ? null : v.replaceAll("\\s+", " ");
    }

    /** 电话规范化（页面11）：去空格与连字符，保留数字及 +。 */
    private String normalizePhone(String raw)
    {
        String v = trim(raw);
        return v == null ? null : v.replaceAll("[\\s\\-]", "");
    }

    private String trim(String raw)
    {
        if (raw == null)
        {
            return null;
        }
        String v = raw.trim();
        return StringUtils.isEmpty(v) ? null : v;
    }

    /** 日期按天比较（establish_date 为 yyyy-MM-dd，忽略时分秒差异避免无谓的版本递增）。 */
    private boolean sameDay(Date a, Date b)
    {
        if (a == null || b == null)
        {
            return false;
        }
        java.util.Calendar ca = java.util.Calendar.getInstance();
        ca.setTime(a);
        java.util.Calendar cb = java.util.Calendar.getInstance();
        cb.setTime(b);
        return ca.get(java.util.Calendar.YEAR) == cb.get(java.util.Calendar.YEAR)
                && ca.get(java.util.Calendar.MONTH) == cb.get(java.util.Calendar.MONTH)
                && ca.get(java.util.Calendar.DAY_OF_MONTH) == cb.get(java.util.Calendar.DAY_OF_MONTH);
    }

    /** 监控调度线程无登录态，SecurityUtils 会抛异常；仅在鉴权存在时取用户名，否则回落 system。 */
    private String currentUser()
    {
        try
        {
            return SecurityUtils.getAuthentication() == null ? "system" : SecurityUtils.getUsername();
        }
        catch (Exception e)
        {
            return "system";
        }
    }
}
