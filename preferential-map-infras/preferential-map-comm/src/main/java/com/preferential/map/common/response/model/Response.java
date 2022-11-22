package com.preferential.map.common.response.model;

import com.preferential.map.common.exception.ErrorCode;
import com.preferential.map.common.request.RestRequestListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import org.slf4j.MDC;

/**
 * 通用返回类
 *
 * @author: rangobai
 * @date 2021/11/11
 */
@Data
public class Response implements Serializable {

    private String msg;
    private int code;
    private Object data;
    private String time;
    private String traceId;

    private Response() {
    }

    private Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    private Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.traceId = MDC.get(RestRequestListener.TRACE_ID);
    }

    public static Response success() {
        return new Response(ErrorCode.SUCCESS.code, ErrorCode.SUCCESS.desc);
    }

    public static Response success(Object data) {
        return new Response(ErrorCode.SUCCESS.code, ErrorCode.SUCCESS.desc, data);
    }

    public static Response success(Object data, String msg) {
        return new Response(ErrorCode.SUCCESS.code, msg, data);
    }

    public static Response error() {
        return new Response(ErrorCode.EXCEPTION.code, ErrorCode.EXCEPTION.desc);
    }

    public static Response error(ErrorCode errorCode) {
        return new Response(errorCode.code, errorCode.desc);
    }

    public static Response error(ErrorCode errorCode, Object data) {
        return new Response(errorCode.code, errorCode.desc, data);
    }

    public static Response error(int code, String msg) {
        return new Response(code, msg);
    }

    public static Response error(int code, String msg, Object data) {
        return new Response(code, msg, data);
    }

}