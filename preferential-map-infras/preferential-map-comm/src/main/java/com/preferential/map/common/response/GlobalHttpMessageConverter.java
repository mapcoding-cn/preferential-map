package com.preferential.map.common.response;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;


/**
 * 全局通用的序列化工具
 *
 * @author baiyichao
 */
@Slf4j
public class GlobalHttpMessageConverter extends FastJsonHttpMessageConverter {

    @Override
    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        //特殊处理下返回String类型
        if (o instanceof String && APPLICATION_JSON.equals(contentType)) {
            try {
                o = JSON.parse((String) o);
            } catch (Exception e) {
                log.info("不是json类型,按照字符串反序列化:{}", o);
            }
            super.write(o, contentType, outputMessage);
        } else {
            super.write(o, type, contentType, outputMessage);
        }
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        super.writeInternal(object, outputMessage);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        try {
            return super.read(type, contextClass, inputMessage);
        } catch (IOException | HttpMessageNotReadableException e) {
            InputStream inputStream = inputMessage.getBody();
            String requestStr;
            try (final Reader reader = new InputStreamReader(inputStream)) {
                requestStr = CharStreams.toString(reader);
            }
            log.error("request error -> {}", requestStr);
            throw e;
        }
    }

}
