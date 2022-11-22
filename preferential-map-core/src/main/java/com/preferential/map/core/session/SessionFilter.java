/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-18 18:40
 **/

package com.preferential.map.core.session;

import com.alibaba.fastjson.JSON;
import com.preferential.map.common.exception.ErrorCode;
import com.preferential.map.common.response.model.Response;
import com.preferential.map.dal.domain.User;
import com.preferential.map.dal.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.http.MediaType;

@WebFilter(urlPatterns = {"/user/*"})
public class SessionFilter implements Filter {

    @Resource
    UserService userService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String sessionId = request.getHeader("sessionId");
        if (StringUtils.isEmpty(sessionId)) {
            flush(response, "请求不合法");
            return;
        }
        String[] split = StringUtils.split(sessionId, "|");
        if (split.length != 2) {
            flush(response, "请求session不正确");
            return;
        }
        Long userId = Long.valueOf(split[0]);
        String realSession = split[1];
        User user = userService.getById(1078340 - userId);
        if (user == null) {
            flush(response, "找不到合法用户");
            return;
        }
        String wordSession = user.getSessionId() + user.getUserId() + user.getCreateTime();
        byte[] buffer = ConcurrentMessageDigest.digestMD5(wordSession.getBytes(StandardCharsets.UTF_8));
        String sessionDb = MD5Encoder.encode(buffer);
        if (!StringUtils.equals(sessionDb, realSession)) {
            flush(response, "请求session不合法");
            return;

        }
        chain.doFilter(req, response);
    }

    private void flush(ServletResponse response, String message) throws IOException {
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = response.getWriter();
        Response error = Response.error(ErrorCode.EXCEPTION.code, message);
        String x = JSON.toJSONString(error);
        String base64 = Base64.getEncoder().encodeToString(x.getBytes(StandardCharsets.UTF_8));
        writer.println(base64);
        response.flushBuffer();
        writer.close();
    }

}
