/**
 * @description:
 * @author: rangobai
 * @date: 2022-01-24 1:30 下午
 **/

package com.preferential.map.core.domain;

import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

    private Long userId;
    private String openId;
    private String wechatName;
    private Integer superUser;
    /**
     * 持证类型
     */
    private Integer credentialType;
    /**
     * 用户配置的商家区域
     */
    private JSONArray merchantArea;
}
