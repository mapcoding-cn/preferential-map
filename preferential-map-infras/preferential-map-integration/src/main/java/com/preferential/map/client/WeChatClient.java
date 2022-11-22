/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-16 23:42
 **/

package com.preferential.map.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.preferential.map.common.exception.PmapException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeChatClient {

    @Value("${wechat.login.appid}")
    String appId;

    @Value("${wechat.login.secret}")
    String appSecret;

    @Value("${wechat.login.url}")
    String url;

    @Resource
    RestTemplate restTemplate;
    private String template = "%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";


    public JSONObject releaseJsCode(String jsCode) {
        String request = String.format(template, url, appId, appSecret, jsCode);
        ResponseEntity<String> result = restTemplate.getForEntity(request, String.class);
        if (result.getStatusCodeValue() != 200) {
            throw new PmapException("获取用户签名失败");
        }
        String resultBody = result.getBody();
//        String resultBody = "{\"errcode\":0,\"session_key\":\"12321341234123525\",\"openid\":\"12341234235234523\"}";
        log.info("请求微信返回:{}", resultBody);

        JSONObject map = JSON.parseObject(resultBody);
        if (map.containsKey("errcode") && map.getInteger("errcode") != 0) {
            throw new PmapException("请求用户签名失败");
        }
        return map;
    }

}
