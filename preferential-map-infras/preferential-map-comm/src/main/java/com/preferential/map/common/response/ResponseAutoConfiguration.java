/*
 * Copyright 1998 - 2022 Tencent. All Rights Reserved.
 */

package com.preferential.map.common.response;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 统一返回模型
 *
 * @author rangobai
 */
@Configuration
public class ResponseAutoConfiguration {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public GlobalResponseHandler globalResponseHandler() {
        return new GlobalResponseHandler();
    }

    @Bean
    public GlobalWebmvcConfiguration globalWebmvcConfiguration() {
        return new GlobalWebmvcConfiguration();
    }

}
