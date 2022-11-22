package com.preferential.map.dal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preferential.map.dal.domain.Merchant;
import com.preferential.map.dal.service.MerchantService;
import com.preferential.map.dal.mapper.MerchantMapper;
import org.springframework.stereotype.Service;

/**
* @author bycwh
* @description 针对表【merchant】的数据库操作Service实现
* @createDate 2022-11-12 22:38:48
*/
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant>
    implements MerchantService{

}




