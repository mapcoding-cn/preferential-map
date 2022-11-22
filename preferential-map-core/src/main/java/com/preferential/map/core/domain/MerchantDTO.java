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
public class MerchantDTO {

    private Long merchantId;
    private String merchantName;
    private String merchantLocation;
    private Integer merchantType;


}
