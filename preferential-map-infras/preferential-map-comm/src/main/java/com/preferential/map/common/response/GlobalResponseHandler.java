package com.preferential.map.common.response;

import com.alibaba.fastjson.JSON;
import com.preferential.map.common.constant.Global;
import com.preferential.map.common.response.model.Response;
import com.preferential.map.common.response.model.ResponseProperties;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应处理
 *
 * @author: rangobai
 * @date 2021/11/11
 */

@Slf4j
@Configuration
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice {

    @javax.annotation.Resource
    ResponseProperties responseProperties;


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return responseProperties.isEnable();
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String path = request.getURI().getPath();
        if (body instanceof Resource) {
            return body;
        }

        boolean excludes = responseProperties.getExcludes().stream().anyMatch(it -> path.startsWith(it));
        if (excludes) {
            return body;
        }
        Response success = body instanceof Response ? (Response) body : Response.success(body);
        String result = success == null ? "" : JSON.toJSONString(success);
        return Base64.getEncoder().encodeToString(result.getBytes(StandardCharsets.UTF_8));

    }
}
