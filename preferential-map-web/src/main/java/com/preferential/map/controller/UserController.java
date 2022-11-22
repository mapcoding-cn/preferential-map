/**
 * @description:电子眼入口
 * @author: rangobai
 * @date: 2021-11-16 4:51 下午
 **/

package com.preferential.map.controller;

import com.preferential.map.common.domain.BaseRequest;
import com.preferential.map.common.domain.ConfigRequest;
import com.preferential.map.common.syslog.model.SystemLog;
import com.preferential.map.core.domain.UserInfoDTO;
import com.preferential.map.core.service.UserOperateService;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/user/v1")
@RestController
public class UserController {

    @Resource
    UserOperateService userOperateService;

    @SystemLog
    @RequestMapping(path = "/query-user-info")
    public UserInfoDTO query(@Validated BaseRequest request) {
        return userOperateService.queryUserConfig(request);

    }

    @SystemLog
    @RequestMapping(path = "/save-user-info")
    public String save(@Validated ConfigRequest request) {
        userOperateService.saveUserConfig(request);
        return "success";
    }


}
