/**
 * @description:
 * @author: rangobai
 * @date: 2022-01-24 1:30 下午
 **/

package com.preferential.map.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;
    private String openId;
    private String wechatName;
    private Integer superUser;
    private String sessionId;

}
