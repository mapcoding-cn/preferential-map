package com.preferential.map.dal.mapper;
import com.alibaba.fastjson.JSONArray;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preferential.map.dal.domain.MerchantInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author bycwh
 * @description 针对表【merchant_info】的数据库操作Mapper
 * @createDate 2022-11-12 22:38:48
 * @Entity com.preferential.map.dal.domain.MerchantInfo
 */
public interface MerchantInfoMapper extends BaseMapper<MerchantInfo> {

    List<MerchantInfo> queryByMerchantId(@Param("merchantId") Long merchantId);


}




