package com.ruoyi.srm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务操作审计注解。写接口在 Controller 方法上标注，由 {@code SrmAuditAspect} 落 srm_audit_log。
 *
 * <p>五列必填（audit_time/module_name/action_name/object_name/detail）由切面兜底，任一为空时用占位值补齐，
 * 保证 APPEND-ONLY 审计不因业务参数缺失而断链。规则来源：API接口文档 §2.1。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SrmAudit
{
    /** 模块，对应 DDL 注释枚举：供应商主档/项目管理/… */
    String module();

    /** 动作：新增 / 修改 / 导入 / 导出 / 启停 / 移出 … */
    String action();

    /** 记录范围，对应 srm_audit_log.scope_type。 */
    String scope() default "";

    /**
     * 对象表达式：支持 {arg0} / {arg1} 形式引用方法实参，也支持 {p:名称} 取 Body 对象的 getter。
     * 留空时用方法名。
     */
    String object() default "";

    /** 详情表达式，写法同 object。 */
    String detail() default "";

    /** 是否在返回前计算 detail（否则只记动作说明）。 */
    boolean logResult() default false;
}
