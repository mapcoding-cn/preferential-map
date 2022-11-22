package com.preferential.map.common.exception;

import lombok.ToString;

/**
 * 错误码
 *
 * @author baiyichao
 */
@ToString
public enum ErrorCode {
    SUCCESS(0, "请求成功"),

    EXCEPTION(500, "系统异常"),

    DB_ERROR(1000, "数据库异常");


    public int code;
    public String desc;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
