/**
 * @description:
 * @author: rangobai
 * @date: 2022-01-24 1:30 下午
 **/

package com.preferential.map.core.domain;

import com.alibaba.fastjson.JSONArray;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantInfoDTO {

    private Long merchantId;
    private String merchantName;
    private String merchantLocation;
    private Integer merchantType;
    private String merchantAddress;
    private String profile;
    private String description;
    private JSONArray images;
    private Integer merchantArea;
    private JSONArray credentialType;
    private String source;

}
