/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-12 20:21
 **/

package com.preferential.map.common.domain;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty
    String jsCode;
    @NotEmpty
    String reqId;

}
