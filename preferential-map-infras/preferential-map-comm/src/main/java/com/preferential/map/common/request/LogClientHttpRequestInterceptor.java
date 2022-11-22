package com.preferential.map.common.request;

import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;

/**
 * http 请求拦截器
 *
 * @author baiyichao
 */
@Slf4j
public class LogClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        String traceKey = RestRequestListener.TRACE_ID;
        String traceId = MDC.get(traceKey);
        if (!StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }
        request.getHeaders().set(traceKey, traceId);

        log.info("发起请求:url={}", request.getURI());

        try {
            StopWatch stopWatch = new StopWatch("请求耗时");
            stopWatch.start();
            ClientHttpResponse execute = execution.execute(request, body);
            stopWatch.stop();

            log.info("结束请求: url={}, status={},cost={}ms", request.getURI(),
                    execute.getStatusCode().value(), stopWatch.getTotalTimeMillis());
            return execute;
        } catch (Exception e) {
            log.error("请求失败:url={} error=", request.getURI(), e);
            throw e;
        }

    }
}
