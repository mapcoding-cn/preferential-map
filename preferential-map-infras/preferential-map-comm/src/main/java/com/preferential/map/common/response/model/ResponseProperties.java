/*
 * Copyright 1998 - 2022 Tencent. All Rights Reserved.
 */

package com.preferential.map.common.response.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Swagger服务配置属性
 *
 * @author layzhang
 */
@Data
@Configuration
public class ResponseProperties {

    private final boolean enable = true;
    /**
     * 是否开启快速序列化模式
     */
    private final boolean fastMode = true;
    /**
     * 路径排除
     */
    private final List<String> excludes = new ArrayList<>();

}
