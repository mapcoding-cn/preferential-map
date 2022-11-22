/*
 * Copyright 1998 - 2022 Tencent. All Rights Reserved.
 */

package com.preferential.map.common.request;

import com.google.common.base.Charsets;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Rest服务自动配置
 *
 * @author layzhang
 */
@Configuration
public class RestAutoConfiguration {

    /**
     * Rest模版
     *
     * @return Rest模版
     */
    @Bean
    public RestTemplate restTemplate() {

        HttpClient httpClient = HttpClientBuilder.create().disableCookieManagement().setMaxConnPerRoute(2000)
                .setMaxConnTotal(4000).disableRedirectHandling().build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);


        RestTemplate restTemplate = new RestTemplate(requestFactory);
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charsets.UTF_8);
                break;
            }
        }

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(new LogClientHttpRequestInterceptor());

        return restTemplate;
    }


    @Bean
    public RestRequestListener restRequestListener() {
        return new RestRequestListener();
    }

}
