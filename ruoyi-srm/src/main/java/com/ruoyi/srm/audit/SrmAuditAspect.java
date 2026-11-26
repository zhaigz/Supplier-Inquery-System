package com.ruoyi.srm.audit;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.srm.annotation.SrmAudit;

/**
 * 审计切面：所有标注 {@link SrmAudit} 的写接口在执行后追加一条 srm_audit_log。
 *
 * <p>放入 ruoyi-srm 模块内自管（本轮决策 4），不依赖 ruoyi-framework 的 sys_oper_log 切面实现。
 * trace_id 与 srm_api_call_log 通过 {@link SrmAuditService} 生成并回填到 ThreadLocal，供网关复用。
 */
@Aspect
@Component
public class SrmAuditAspect
{
    private static final Logger log = LoggerFactory.getLogger(SrmAuditAspect.class);

    /** 当前线程链路号，供同一请求内的 TycGateway 复用。 */
    private static final ThreadLocal<String> TRACE = new ThreadLocal<>();

    @Autowired
    private SrmAuditService auditService;

    public static String currentTraceId()
    {
        return TRACE.get();
    }

    public static void setTraceId(String traceId)
    {
        TRACE.set(traceId);
    }

    public static void clearTraceId()
    {
        TRACE.remove();
    }

    @Around("@annotation(com.ruoyi.srm.annotation.SrmAudit)")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        String traceId = auditService.newTraceId();
        setTraceId(traceId);
        Object result;
        try
        {
            result = point.proceed();
        }
        catch (Throwable t)
        {
            write(point, traceId, t, null);
            throw t;
        }
        finally
        {
            // 保留 traceId 到请求结束由 Filter 清理；此处不清，便于 Controller 返回后仍可审计
        }
        write(point, traceId, null, result);
        return result;
    }

    private void write(ProceedingJoinPoint point, String traceId, Throwable error, Object result)
    {
        try
        {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            SrmAudit audit = method.getAnnotation(SrmAudit.class);
            if (audit == null)
            {
                audit = point.getTarget().getClass()
                        .getMethod(method.getName(), method.getParameterTypes())
                        .getAnnotation(SrmAudit.class);
            }
            if (audit == null)
            {
                return;
            }
            Object[] args = point.getArgs();
            String object = resolve(audit.object(), method, args, result);
            if (StringUtils.isEmpty(object))
            {
                object = method.getName();
            }
            String detail = resolve(audit.detail(), method, args, result);
            if (error != null)
            {
                detail = "异常: " + error.getMessage();
            }
            auditService.record(new AuditRecord(audit.module(), audit.action(), object, detail)
                    .setScopeType(audit.scope()), traceId);
        }
        catch (Exception e)
        {
            log.error("审计落库失败 method={}", point.getSignature().toShortString(), e);
        }
    }

    /**
     * 极简表达式求值：{argN} 取实参；{p:xxx} 取首个非空实参的 xxx getter；{} 取实参 JSON。
     */
    private String resolve(String expression, Method method, Object[] args, Object result)
    {
        if (StringUtils.isEmpty(expression))
        {
            return null;
        }
        String value = expression;
        for (int i = 0; i < args.length; i++)
        {
            value = value.replace("{arg" + i + "}", String.valueOf(args[i]));
        }
        int pIdx = value.indexOf("{p:");
        if (pIdx >= 0)
        {
            int end = value.indexOf('}', pIdx);
            if (end > pIdx)
            {
                String prop = value.substring(pIdx + 3, end);
                Object propValue = readProperty(args, prop);
                value = value.substring(0, pIdx) + (propValue == null ? "" : String.valueOf(propValue))
                        + value.substring(end + 1);
            }
        }
        value = value.replace("{}", args.length == 0 ? "" : JSON.toJSONString(args.length == 1 ? args[0] : Arrays.asList(args)));
        if (result != null && value.contains("{result}"))
        {
            value = value.replace("{result}", String.valueOf(result));
        }
        if (value.contains("{error}"))
        {
            value = value.replace("{error}", "");
        }
        return value;
    }

    private Object readProperty(Object[] args, String prop)
    {
        for (Object arg : args)
        {
            if (arg == null)
            {
                continue;
            }
            try
            {
                String getter = "get" + Character.toUpperCase(prop.charAt(0)) + prop.substring(1);
                return arg.getClass().getMethod(getter).invoke(arg);
            }
            catch (Exception ignored)
            {
                // 尝试下一个实参
            }
        }
        return null;
    }
}
