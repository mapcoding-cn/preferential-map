package com.preferential.map.common.response;

import com.preferential.map.common.exception.ErrorCode;
import com.preferential.map.common.exception.PmapException;
import com.preferential.map.common.request.RestRequestListener;
import com.preferential.map.common.response.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author: rangobai
 * @date 2021/11/11
 */

@Slf4j
@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理基础异常
     *
     * @param e 抛出的异常
     * @return 返回给前端的错误信息提示
     * @description 统一的异常处理方法
     */
    @ExceptionHandler
    public Response handleException(Exception e) {

        String taskId = MDC.get(RestRequestListener.TASK_ID);
        String from = MDC.get(RestRequestListener.FROM);

        if (e instanceof PmapException) {
            PmapException ex = (PmapException) e;
            log.warn("已知异常,from:[{}],task_id:[{}],[{}][{}]:{}", from, taskId, ex.getErrorCode().code,
                    ex.getErrorCode().desc, e.getMessage(), e);
            return Response.error(ex.getErrorCode().code, ex.getErrorCode().desc + ":" + e.getMessage(), null);
        } else {
            log.error("未知异常:from:[{}],task_id:[{}],{}", from, taskId, e.getMessage(), e);
            return Response.error(ErrorCode.EXCEPTION.code, ErrorCode.EXCEPTION.desc + ":" + e.getMessage(), null);
        }
    }


}
