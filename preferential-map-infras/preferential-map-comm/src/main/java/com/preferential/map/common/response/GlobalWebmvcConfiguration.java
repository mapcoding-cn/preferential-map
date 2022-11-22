/**
 * @description:
 * @author: Rangobai
 * @date: 2022-02-23 1:29 下午
 **/

package com.preferential.map.common.response;

import static com.preferential.map.common.constant.Global.YYYY_MM_DD_HH_MM_SS_SSS;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.preferential.map.common.fastjson.DateSerializer;
import com.preferential.map.common.response.model.ResponseProperties;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 预处理 Http 请求消息
 *
 * @author rangobai
 */
public class GlobalWebmvcConfiguration implements WebMvcConfigurer {

    @Resource
    ResponseProperties responseProperties;


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (responseProperties.isFastMode()) {

            DateSerializer.init();
            GlobalHttpMessageConverter messageConverter = new GlobalHttpMessageConverter();
            FastJsonConfig fastJsonConfig = messageConverter.getFastJsonConfig();
            fastJsonConfig.setDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
            fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                    SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
            fastJsonConfig.setCharset(StandardCharsets.UTF_8);

            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            messageConverter.setSupportedMediaTypes(mediaTypes);

            //保持第一顺位
            converters.add(0, messageConverter);
        }
        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}
