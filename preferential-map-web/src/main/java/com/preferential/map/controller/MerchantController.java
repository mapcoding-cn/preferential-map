/**
 * @description:电子眼入口
 * @author: rangobai
 * @date: 2021-11-16 4:51 下午
 **/

package com.preferential.map.controller;

import com.preferential.map.common.domain.MerchantRequest;
import com.preferential.map.common.syslog.model.SystemLog;
import com.preferential.map.core.domain.MerchantDTO;
import com.preferential.map.core.domain.MerchantInfoDTO;
import com.preferential.map.core.service.MerchantQueryService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/user/v1")
@RestController
public class MerchantController {

    @Resource
    MerchantQueryService merchantQueryService;

    @SystemLog
    @RequestMapping(path = "/query-merchant-by-location")
    public List<MerchantDTO> queryByLocation(MerchantRequest request) {
        return merchantQueryService.queryByLocation(request);
    }

    @SystemLog
    @RequestMapping(path = "/query-merchant-by-name")
    public List<MerchantDTO> queryByName(MerchantRequest request) {
        return merchantQueryService.queryByName(request);
    }

    @SystemLog
    @RequestMapping(path = "/query-merchant-by-id")
    public MerchantInfoDTO queryById(MerchantRequest request) {
        return merchantQueryService.queryById(request);
    }


}
