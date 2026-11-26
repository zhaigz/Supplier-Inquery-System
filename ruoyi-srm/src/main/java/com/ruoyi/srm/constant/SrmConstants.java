package com.ruoyi.srm.constant;

/** SRM 字符串常量。 */
public final class SrmConstants
{
    public static final String CALL_RESULT_SUCCESS = "成功";
    public static final String CALL_RESULT_FAIL = "失败";
    public static final String CALL_RESULT_BLOCKED = "被拦截";
    public static final String CACHE_ENV_PROD = "prod";
    public static final String CACHE_ENV_TEST = "test";

    /** 通用布尔字符。 */
    public static final String YES = "1";
    public static final String NO = "0";

    /** 逻辑删除：0 存在 / 2 已删除（与 DDL 一致）。 */
    public static final String DEL_FLAG_EXIST = "0";
    public static final String DEL_FLAG_DELETED = "2";

    /** 异步任务状态五值（API文档 §2.5）。 */
    public static final String TASK_STATUS_QUEUED = "QUEUED";
    public static final String TASK_STATUS_RUNNING = "RUNNING";
    public static final String TASK_STATUS_SUCCESS = "SUCCESS";
    public static final String TASK_STATUS_FAIL = "FAIL";
    public static final String TASK_STATUS_CANCELED = "CANCELED";

    /** 任务计费标记。 */
    public static final String BILL_FLAG_UNBILLED = "0";
    public static final String BILL_FLAG_BILLED = "1";

    /** 任务类型 / 业务场景。 */
    public static final String TASK_TYPE_EQUITY = "equity";
    public static final String TASK_TYPE_BACKSTAGE = "backstage";
    public static final String TASK_TYPE_PROJECT = "project";
    /** 监控扫描任务（缺口③：SrmMonitorScheduler.runPolicy 每次 cron 触发落一条，留痕采集与变更事件） */
    public static final String TASK_TYPE_MONITOR = "monitor";
    public static final String BIZ_SCENE_PAGE5 = "page5";
    public static final String BIZ_SCENE_PAGE6 = "page6";
    public static final String BIZ_SCENE_PAGE7 = "page7";
    /** 监控扫描归档场景（页面15 监控策略配置触发的采集任务） */
    public static final String BIZ_SCENE_PAGE15 = "page15";

    /** 触发器场景编码（srm_trigger_rule.rule_code）。 */
    public static final String SCENE_SUPPLIER_SAVE = "supplier_save";
    public static final String SCENE_SINGLE_ENRICH = "single_enrich";
    public static final String SCENE_MONITOR_SCAN = "monitor_scan";
    public static final String SCENE_PROJECT_ANALYSIS = "project_analysis";
    public static final String SCENE_REPEAT_QUERY = "repeat_query";

    /** 建档来源（srm_supplier.data_source）。 */
    public static final String DATA_SOURCE_MANUAL = "人工录入";
    public static final String DATA_SOURCE_QUERY_MISS = "查询补全";
    public static final String DATA_SOURCE_IMPORT = "批量导入";
    public static final String DATA_SOURCE_BUILTIN = "初始内置";

    private SrmConstants() { }
}