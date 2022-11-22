package com.preferential.map.common.request;

import java.util.UUID;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

@WebListener
@Slf4j
public class RestRequestListener implements ServletRequestListener {

    public static final String TRACE_ID = "traceId";
    public static final String FROM = "from";
    public static final String TASK_ID = "task_id";

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        MDC.clear();
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        String trace = UUID.randomUUID().toString().replace("-", "");
        if (servletRequest instanceof HttpServletRequest) {
            String header = ((HttpServletRequest) servletRequest).getHeader(TRACE_ID);
            if (StringUtils.isNotEmpty(header)) {
                trace = header;
            }

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String from = request.getParameter(FROM);
            String taskId = request.getParameter(TASK_ID);
            MDC.put(TASK_ID, taskId);
            MDC.put(FROM, from);
        }

        MDC.put(TRACE_ID, trace);
    }
}
