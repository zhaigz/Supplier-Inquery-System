package com.ruoyi.srm.constant;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 天眼查外调编号常量（唯一外调出口 {@code TycGateway.call(...)} 的实参白名单）。
 *
 * <p>取值来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json 中 134 条接口"外调"列去重后的编号集合，
 * 共 16 个。该集合与对账脚本 tools/check_srm_controllers.py 的外调编号对账同源，不得增删。
 *
 * <p>srm_api_dict 另有 3 条停用记录（1047/1168/1141），134 条接口未引用，故不在此定义为调用实参，
 * 避免把"清单外"的编号写成可达调用。
 *
 * <p>编号 / 接口名 / 文档 URL 三方同源于《天眼查API接口.xlsx》，用于：
 * <ul>
 *   <li>调用点注释：{@code // TYC-CALL 外调天眼查 1160（EQUITY_PENETRATION · 股权穿透（10层））}；</li>
 *   <li>运行期留痕：{@link #name(int)} / {@link #docUrl(int)} 随 srm_api_call_log 与日志一并可读。</li>
 * </ul>
 */
public final class SrmApiId
{
    /** 企业曾用名（增值服务，0.10 元/次） */
    public static final int FORMER_NAME = 458;
    /** 企业基本信息（含企业联系方式）（工商信息，0.20 元/次） */
    public static final int BASIC_INFO = 818;
    /** 企业基本信息（含主要人员）（工商信息，0.25 元/次） */
    public static final int BASIC_INFO_PERSON = 819;
    /** 企业股东（工商信息，分页查询每页 20 条，0.15 元/次） */
    public static final int SHAREHOLDER = 821;
    /** 被执行人（司法风险，分页查询每页 20 条，0.15 元/次） */
    public static final int ENFORCE = 839;
    /** 严重违法（经营风险，分页查询每页 20 条，0.15 元/次） */
    public static final int ADMIN_PUNISH_ILLEGAL = 846;
    /** 经营异常（经营风险，分页查询每页 20 条，0.15 元/次） */
    public static final int ADMIN_PUNISH_ABNORMAL = 848;
    /** 欠税公告（经营风险，分页查询每页 20 条，0.15 元/次） */
    public static final int TAX_OWED = 851;
    /** 历史被执行人（司法风险，分页查询每页 20 条，0.10 元/次） */
    public static final int ENFORCE_HIST = 871;
    /** 历史失信人（司法风险，分页查询每页 20 条，0.10 元/次） */
    public static final int DISHONEST = 872;
    /** 司法风险（组合接口：开庭公告/被执行人/法院公告/立案信息/失信人/法律诉讼/送达公告，1.50 元/次） */
    public static final int JUDICIAL_SUMMARY = 1002;
    /** 企业联系方式（工商信息，0.10 元/次） */
    public static final int CONTACT = 1046;
    /** 疑似实际控制人（关系发现，1.00 元/次） */
    public static final int ACTUAL_CONTROLLER = 1123;
    /** 疑似关系发现（关系发现，分页查询每页 20 条，1.00 元/次） */
    public static final int SUSPECT_RELATION = 1156;
    /** 股权穿透（10 层）（特色接口，4.00 元/次） */
    public static final int EQUITY_PENETRATION = 1160;
    /** 幕后关系（10 层）（特色接口，4.00 元/次） */
    public static final int BACKSTAGE_RELATION = 1161;

    /**
     * 134 条接口引用到的全部外调编号，顺序固定，供对账脚本逐字核对。
     */
    public static final int[] IN_SCOPE =
    {
        458, 818, 819, 821, 839, 846, 848, 851, 871, 872, 1002, 1046, 1123, 1156, 1160, 1161
    };

    /** 编号 -> 天眼查接口中文名（来源：天眼查API接口.xlsx）。 */
    private static final Map<Integer, String> NAMES;

    /** 编号 -> 天眼查接口分类（来源：天眼查API接口.xlsx）。 */
    private static final Map<Integer, String> CATEGORIES;

    /** 编号 -> 天眼查在线文档 URL（来源：天眼查API接口.xlsx）。 */
    private static final Map<Integer, String> DOC_URLS;

    static
    {
        Map<Integer, String> names = new LinkedHashMap<>();
        names.put(FORMER_NAME, "企业曾用名");
        names.put(BASIC_INFO, "企业基本信息（含企业联系方式）");
        names.put(BASIC_INFO_PERSON, "企业基本信息（含主要人员）");
        names.put(SHAREHOLDER, "企业股东");
        names.put(ENFORCE, "被执行人");
        names.put(ADMIN_PUNISH_ILLEGAL, "严重违法");
        names.put(ADMIN_PUNISH_ABNORMAL, "经营异常");
        names.put(TAX_OWED, "欠税公告");
        names.put(ENFORCE_HIST, "历史被执行人");
        names.put(DISHONEST, "历史失信人");
        names.put(JUDICIAL_SUMMARY, "司法风险");
        names.put(CONTACT, "企业联系方式");
        names.put(ACTUAL_CONTROLLER, "疑似实际控制人");
        names.put(SUSPECT_RELATION, "疑似关系发现");
        names.put(EQUITY_PENETRATION, "股权穿透（10层）");
        names.put(BACKSTAGE_RELATION, "幕后关系（10层）");
        NAMES = Collections.unmodifiableMap(names);

        Map<Integer, String> categories = new LinkedHashMap<>();
        categories.put(FORMER_NAME, "增值服务");
        categories.put(BASIC_INFO, "工商信息");
        categories.put(BASIC_INFO_PERSON, "工商信息");
        categories.put(SHAREHOLDER, "工商信息");
        categories.put(ENFORCE, "司法风险");
        categories.put(ADMIN_PUNISH_ILLEGAL, "经营风险");
        categories.put(ADMIN_PUNISH_ABNORMAL, "经营风险");
        categories.put(TAX_OWED, "经营风险");
        categories.put(ENFORCE_HIST, "司法风险");
        categories.put(DISHONEST, "司法风险");
        categories.put(JUDICIAL_SUMMARY, "组合接口");
        categories.put(CONTACT, "工商信息");
        categories.put(ACTUAL_CONTROLLER, "关系发现");
        categories.put(SUSPECT_RELATION, "关系发现");
        categories.put(EQUITY_PENETRATION, "特色接口");
        categories.put(BACKSTAGE_RELATION, "特色接口");
        CATEGORIES = Collections.unmodifiableMap(categories);

        Map<Integer, String> urls = new LinkedHashMap<>();
        for (Integer id : names.keySet())
        {
            urls.put(id, "https://www.tianyancha.com/data/api/" + id);
        }
        DOC_URLS = Collections.unmodifiableMap(urls);
    }

    private SrmApiId() { }

    /**
     * 判断编号是否属于本轮 134 条接口引用的外调集合。
     */
    public static boolean inScope(int apiId)
    {
        for (int id : IN_SCOPE)
        {
            if (id == apiId)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 天眼查接口中文名，未知编号返回"未知外调接口"。
     */
    public static String name(int apiId)
    {
        String name = NAMES.get(apiId);
        return name == null ? "未知外调接口" : name;
    }

    /**
     * 天眼查接口分类，未知编号返回""。
     */
    public static String category(int apiId)
    {
        String category = CATEGORIES.get(apiId);
        return category == null ? "" : category;
    }

    /**
     * 天眼查在线文档 URL，未知编号返回""。
     */
    public static String docUrl(int apiId)
    {
        String url = DOC_URLS.get(apiId);
        return url == null ? "" : url;
    }

    /**
     * 统一的运行期/日志标签，形如 {@code 1160（EQUITY_PENETRATION · 股权穿透（10层））}。
     */
    public static String label(int apiId)
    {
        String symbol = symbolOf(apiId);
        return apiId + "（" + symbol + " · " + name(apiId) + "）";
    }

    /**
     * 常量名反查（仅用于日志与注释渲染）。
     */
    public static String symbolOf(int apiId)
    {
        String symbol = SYMBOLS.get(apiId);
        return symbol == null ? "UNKNOWN" : symbol;
    }

    /** 编号 -> 常量名（与上方常量声明逐字一致）。 */
    private static final Map<Integer, String> SYMBOLS;

    static
    {
        Map<Integer, String> symbols = new LinkedHashMap<>();
        symbols.put(FORMER_NAME, "FORMER_NAME");
        symbols.put(BASIC_INFO, "BASIC_INFO");
        symbols.put(BASIC_INFO_PERSON, "BASIC_INFO_PERSON");
        symbols.put(SHAREHOLDER, "SHAREHOLDER");
        symbols.put(ENFORCE, "ENFORCE");
        symbols.put(ADMIN_PUNISH_ILLEGAL, "ADMIN_PUNISH_ILLEGAL");
        symbols.put(ADMIN_PUNISH_ABNORMAL, "ADMIN_PUNISH_ABNORMAL");
        symbols.put(TAX_OWED, "TAX_OWED");
        symbols.put(ENFORCE_HIST, "ENFORCE_HIST");
        symbols.put(DISHONEST, "DISHONEST");
        symbols.put(JUDICIAL_SUMMARY, "JUDICIAL_SUMMARY");
        symbols.put(CONTACT, "CONTACT");
        symbols.put(ACTUAL_CONTROLLER, "ACTUAL_CONTROLLER");
        symbols.put(SUSPECT_RELATION, "SUSPECT_RELATION");
        symbols.put(EQUITY_PENETRATION, "EQUITY_PENETRATION");
        symbols.put(BACKSTAGE_RELATION, "BACKSTAGE_RELATION");
        SYMBOLS = Collections.unmodifiableMap(symbols);
    }
}
