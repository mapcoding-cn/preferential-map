/**
 * @description:电子眼入口
 * @author: rangobai
 * @date: 2021-11-16 4:51 下午
 **/

package com.preferential.map.controller;

import com.preferential.map.common.domain.LoginRequest;
import com.preferential.map.common.syslog.model.SystemLog;
import com.preferential.map.core.domain.UserDTO;
import com.preferential.map.core.service.UserOperateService;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Resource
    UserOperateService userOperateService;

    @SystemLog
    @GetMapping(path = "/pmap-login-x")
    public UserDTO login(@Validated LoginRequest request) {
        return userOperateService.loginUser(request);
    }


}
