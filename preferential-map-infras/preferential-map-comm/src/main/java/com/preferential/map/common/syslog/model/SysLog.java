package com.preferential.map.common.syslog.model;

import java.lang.reflect.Method;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

/**
 * 日志类
 *
 * @author: rangobai
 * @date 2021/11/11
 */

@Data
public class SysLog {

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    /**
     * 请求类型
     */
    private Method method;
    /**
     * 消耗时间
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 请求返回的结果
     */
    private Object result;

    /**
     * 是否有错误
     */
    private Exception error;


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Class<?> declaringClass = method.getDeclaringClass();
        String resultStr = res.append(declaringClass.getSimpleName()).append("|")
                .append(method.getName()).append("|")
                .append(time).append(" ms|")
                .append(ip).append("|")
                .append(error == null ? SUCCESS : ERROR).append("|")
                .append(error == null ? StringUtils.EMPTY : error.getMessage())
                .toString();
        return StringEscapeUtils.unescapeJson(resultStr);
    }
}