package com.preferential.map.common.syslog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.preferential.map.common.syslog.model.SysLog;
import com.preferential.map.common.syslog.model.SystemLog;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 统一日志处理切面
 *
 * @author: rangobai
 * @date 2021/11/11
 */

@Slf4j
@Aspect
public class AspectSystemLog {


    /**
     * 获取请求参数
     */
    private static Map<String, Object> getFieldsName(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<>(32);
        for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }

    @Pointcut("@annotation(com.preferential.map.common.syslog.model.SystemLog)")
    public void systemLog() {

    }

    @Before("systemLog()")
    public void before(JoinPoint joinPoint) {
        Map<String, Object> fieldsName = getFieldsName(joinPoint);
        String requestInfo = StringEscapeUtils.unescapeJson(
                JSON.toJSONString(fieldsName, SerializerFeature.WriteMapNullValue));
        log.info("请求入参:{}", requestInfo);

    }

    /**
     * 监听日志
     *
     * @param joinPoint
     */
    @Around("systemLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行请求
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = null;
        Exception error = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            error = e;
            throw e;
        } finally {
            stopWatch.stop();
            //打印结果
            log(joinPoint, stopWatch, result, error);
        }

        return result;
    }


    /**
     * 打印日志
     *
     * @param joinPoint
     * @param stopWatch
     * @param result
     */
    private void log(ProceedingJoinPoint joinPoint, StopWatch stopWatch, Object result, Exception error) {
        //获取当前请求对象
        SysLog systemLog = new SysLog();

        //执行路径
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        systemLog.setMethod(method);
        //耗时
        systemLog.setTime(stopWatch.getTotalTimeMillis());
        //IP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            systemLog.setIp(request.getRemoteHost());
        } else {
            systemLog.setIp("unknown");
        }
        //error
        systemLog.setError(error);
        log.info("请求记录:{}", systemLog);

        //记录结果
        SystemLog annotation = method.getAnnotation(SystemLog.class);
        if (annotation.logResult() != -1) {
            systemLog.setResult(result);
            log.info("请求出参:{}", StringEscapeUtils.unescapeJson(JSON.toJSONString(result)));
        }
    }
}