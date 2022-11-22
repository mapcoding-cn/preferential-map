package com.preferential.map.common.syslog.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 日志注解
 *
 * @author baiyichao
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    /**
     * 描述
     */
    String description() default "";

    /**
     * -1不输出结果,其余输出结果
     */
    int logResult() default 0;
}
