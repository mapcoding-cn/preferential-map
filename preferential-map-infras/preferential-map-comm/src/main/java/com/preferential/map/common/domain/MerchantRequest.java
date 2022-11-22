/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-12 20:21
 **/

package com.preferential.map.common.domain;

import lombok.Data;

@Data
public class MerchantRequest extends BaseRequest {

    private String location;
    private Double level;
    private Integer merchantType;
    private String input;
    private Long merchantId;
}
