package com.preferential.map;

import java.util.TimeZone;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动入口
 *
 * @author: rangobai
 */
@SpringBootApplication
@EnableCaching
@ServletComponentScan
@MapperScan(basePackages = "com.preferential.map.**.mapper")
public class PmapApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(PmapApplication.class, args);
    }

}
