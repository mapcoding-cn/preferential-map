package com.preferential.map.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preferential.map.dal.domain.Merchant;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author bycwh
 * @description 针对表【merchant】的数据库操作Mapper
 * @createDate 2022-11-12 22:38:48
 * @Entity com.preferential.map.dal.domain.Merchant
 */
public interface MerchantMapper extends BaseMapper<Merchant> {

    List<Merchant> queryByMerchantLocationAndMerchantType(@Param("box") String box,
            @Param("merchantType") Integer merchantType, @Param("credentialType") Integer credentialType,
            @Param("area") List<Integer> area);

    List<Merchant> queryByMerchantName(@Param("merchantName") String merchantName);

}




