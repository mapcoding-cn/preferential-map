/**
 * 统一异常处理
 *
 * @author: Rangobai
 * @date: 2022-02-15 4:07 下午
 **/

package com.preferential.map.common.exception;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PmapException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode = ErrorCode.EXCEPTION;

    public PmapException() {
        super();
    }

    public PmapException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public PmapException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public PmapException(String message) {
        super(message);
    }

    public PmapException(Throwable cause) {
        super(cause);
    }
}
