/*
 * Copyright 1998 - 2022 Tencent. All Rights Reserved.
 */

package com.preferential.map.common.syslog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger服务自动配置
 *
 * @author layzhang
 */
@Configuration
public class AspectSystemLogAutoConfiguration {


    @Bean
    public AspectSystemLog systemLogAspect() {
        return new AspectSystemLog();
    }
}
