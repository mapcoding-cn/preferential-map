/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-12 20:21
 **/

package com.preferential.map.common.domain;

import java.util.List;
import lombok.Data;

@Data
public class ConfigRequest extends BaseRequest {

    Integer credentialType;
    String  merchantArea;

}
