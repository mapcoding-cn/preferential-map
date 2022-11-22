/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-12 21:28
 **/

package com.preferential.map.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.preferential.map.client.WeChatClient;
import com.preferential.map.common.domain.BaseRequest;
import com.preferential.map.common.domain.ConfigRequest;
import com.preferential.map.common.domain.LoginRequest;
import com.preferential.map.common.exception.PmapException;
import com.preferential.map.core.domain.UserDTO;
import com.preferential.map.core.domain.UserDTO.UserDTOBuilder;
import com.preferential.map.core.domain.UserInfoDTO;
import com.preferential.map.core.domain.UserInfoDTO.UserInfoDTOBuilder;
import com.preferential.map.dal.domain.User;
import com.preferential.map.dal.domain.UserInfo;
import com.preferential.map.dal.mapper.UserMapper;
import com.preferential.map.dal.service.UserInfoService;
import com.preferential.map.dal.service.UserService;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class UserOperateService {

    public static final String MERCHANT_AREA = "merchant_area";
    @Resource
    UserService userService;

    @Resource
    UserMapper userMapper;

    @Resource
    UserInfoService userInfoService;

    @Resource
    WeChatClient weChatClient;

    /**
     * 默认配置
     */
    public static final JSONObject defaultConfig = JSON.parseObject("{\"merchant_area\":[0,1,2]}");

    /**
     * 查询用户配置
     *
     * @param request
     * @return
     */
    public UserInfoDTO queryUserConfig(BaseRequest request) {
        User user = validUser(request);

        UserInfoDTOBuilder builder = UserInfoDTO.builder().userId(user.getUserId()).openId(user.getOpenId())
                .superUser(user.getSuperUser()).wechatName(user.getWechatName());

        UserInfo userInfo = userInfoService.getById(request.getUserId());
        if (userInfo == null) {
            log.warn("找不到用户配置信息");
            return builder.build();
        }

        JSONObject config = userInfo.getConfig();
        //给默认全部开启
        config = config == null ? defaultConfig : config;
        JSONArray merchantArea = config.getJSONArray(MERCHANT_AREA);
        Integer credentialType = userInfo.getCredentialType();
        //默认优待证持证人
        credentialType = credentialType == null ? 1 : credentialType;

        return builder.merchantArea(merchantArea).credentialType(credentialType).build();

    }

    /**
     * 保存用户配置
     *
     * @param request
     */
    public void saveUserConfig(ConfigRequest request) {
        validUser(request);
        UserInfo userInfo = userInfoService.getById(request.getUserId());
        if (userInfo == null) {
            throw new PmapException("没有找到用户配置");
        }

        //持证类型
        Integer credentialType = request.getCredentialType();
        if (credentialType != null && credentialType > 0 && credentialType < 5) {
            userInfo.setCredentialType(credentialType);
        }

        //商家优待区域
        JSONObject config = userInfo.getConfig();
        config = config == null ? defaultConfig : config;
        String[] areaText = StringUtils.split(request.getMerchantArea(), ",");
        if (areaText != null && areaText.length > 0) {
            List<Integer> area = Arrays.stream(areaText).map(it -> Integer.valueOf(it)).collect(Collectors.toList());
            if (Arrays.asList(0, 1, 2).containsAll(area)) {
                config.put(MERCHANT_AREA, area);
                userInfo.setConfig(config);
            }
        }
        userInfoService.saveOrUpdate(userInfo);
    }

    /**
     * 校验并创建用户(如果没有)
     *
     * @param request
     */
    @Transactional
    public UserDTO loginUser(LoginRequest request) {
        String jsCode = request.getJsCode();
        JSONObject map = weChatClient.releaseJsCode(jsCode);
        String openId = map.getString("openid");
        String sessionKey = map.getString("session_key");
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(sessionKey)) {
            throw new PmapException("查不到合法的微信用户");
        }

        //查询用户
        List<User> users = userMapper.queryByOpenId(openId);
        User user;
        //新创建用户
        if (CollectionUtils.isEmpty(users)) {
            user = new User();
            user.setSuperUser(0);
            user.setOpenId(openId);
            user.setWechatName("微信用户");
            //保存一下以获取ID
            userService.save(user);
            user = userMapper.queryByOpenId(openId).get(0);
            Long userId = user.getUserId();
            //更新一下用户名
            user.setWechatName("使命" + (9999 + userId) + "号");

            //设置默认配置信息
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setCredentialType(1);
            userInfo.setConfig(defaultConfig);
            userInfoService.save(userInfo);

            log.info("新增用户id={},name={},openId={}", userId, user.getWechatName(), user.getOpenId());
        } else {
            user = users.get(0);
        }
        //更新一下session
        user.setSessionId(sessionKey);
        userService.saveOrUpdate(user);

        log.info("用户id={},name={},登录成功", user.getUserId(), user.getWechatName());
        UserDTOBuilder builder = UserDTO.builder();

        //加密一下
        String wordSession = sessionKey + user.getUserId() + user.getCreateTime();
        byte[] buffer = ConcurrentMessageDigest.digestMD5(wordSession.getBytes(StandardCharsets.UTF_8));
        String sessionId = (1078340 - user.getUserId()) + "|" + MD5Encoder.encode(buffer);
        return builder.superUser(user.getSuperUser()).userId(user.getUserId()).openId(user.getOpenId())
                .wechatName(user.getWechatName()).sessionId(sessionId).build();

    }

    /**
     * 验证用户合法性
     *
     * @param request
     * @return
     */
    private User validUser(BaseRequest request) {
        Long userId = request.getUserId();
        User user = userService.getById(userId);
        if (user == null) {
            throw new PmapException("找不到该用户");
        }
        return user;
    }

}
