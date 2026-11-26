package com.ruoyi.srm.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import com.alibaba.fastjson2.JSON;

/**
 * SRM 通用工具：缓存键、参数指纹、证件号脱敏、分页上限。
 * 规则来源：供应商查询系统_API接口文档_V1.0.md §2.4 / §2.1。
 */
public final class SrmUtils
{
    /** 分页上限（§2.1 全局 pageSize 上限 100）。 */
    public static final int PAGE_SIZE_MAX = 100;

    /** 风险页分页上限（§4.4 每页 ≤20）。 */
    public static final int RISK_PAGE_SIZE_MAX = 20;

    /** 关系发现分页上限（1156 默认 10 最大 20）。 */
    public static final int RELATION_PAGE_SIZE_MAX = 20;

    private SrmUtils() { }

    /**
     * 稳定序列化：按 key 排序，保证同一参数集合得到同一 fingerprint。
     */
    public static String canonicalJson(Map<String, Object> params)
    {
        if (params == null || params.isEmpty())
        {
            return "{}";
        }
        Map<String, Object> sorted = new LinkedHashMap<>();
        params.keySet().stream().sorted().forEach(k -> sorted.put(k, params.get(k)));
        return JSON.toJSONString(sorted);
    }

    /**
     * 缓存键：md5(api_id + subject_key + param_json)。§2.4 唯一口径。
     */
    public static String cacheKey(int apiId, String subjectKey, Map<String, Object> params)
    {
        return md5(apiId + "" + (subjectKey == null ? "" : subjectKey) + canonicalJson(params));
    }

    /**
     * 参数指纹，用于 srm_api_call_log.param_digest。
     */
    public static String paramDigest(Map<String, Object> params)
    {
        return md5(canonicalJson(params));
    }

    /** MD5 十六进制小写。 */
    public static String md5(String text)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest((text == null ? "" : text).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes)
            {
                sb.append(Character.forDigit((b >> 4) & 0xf, 16));
                sb.append(Character.forDigit(b & 0xf, 16));
            }
            return sb.toString();
        }
        catch (Exception e)
        {
            throw new IllegalStateException("MD5 unavailable", e);
        }
    }

    /**
     * 证件号脱敏：保留前 3 后 4，中间等长替换为 *（§2.4）。
     */
    public static String maskIdNo(String value)
    {
        if (value == null)
        {
            return null;
        }
        String v = value.trim();
        if (v.length() <= 7)
        {
            return repeat('*', v.length());
        }
        return v.substring(0, 3) + repeat('*', v.length() - 7) + v.substring(v.length() - 4);
    }

    /**
     * 分页上限收敛。请求缺失取默认值；超上限不静默截断为上限值以外，直接封顶（§2.1 / §4.4）。
     */
    public static int capPageSize(Integer pageSize, int max)
    {
        if (pageSize == null || pageSize < 1)
        {
            return 10;
        }
        return Math.min(pageSize, max);
    }

    private static String repeat(char c, int n)
    {
        StringBuilder sb = new StringBuilder(Math.max(n, 0));
        for (int i = 0; i < n; i++)
        {
            sb.append(c);
        }
        return sb.toString();
    }
}
