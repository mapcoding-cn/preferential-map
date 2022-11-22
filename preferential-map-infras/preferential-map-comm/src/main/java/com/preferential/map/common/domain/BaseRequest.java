/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-12 20:21
 **/

package com.preferential.map.common.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseRequest {

    @NotNull
    Long userId;
    @NotEmpty
    String openId;
    @NotEmpty
    String reqId;
}
