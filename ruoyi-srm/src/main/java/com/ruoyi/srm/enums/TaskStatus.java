package com.ruoyi.srm.enums;

/**
 * SRM 异步任务状态。来源：srm_analysis_task.task_status DDL 注释。
 */
public enum TaskStatus
{
    QUEUED,
    RUNNING,
    SUCCESS,
    FAIL,
    CANCELED
}
