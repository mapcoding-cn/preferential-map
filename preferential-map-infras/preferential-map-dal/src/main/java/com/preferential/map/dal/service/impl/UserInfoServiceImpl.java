package com.preferential.map.dal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preferential.map.dal.domain.UserInfo;
import com.preferential.map.dal.service.UserInfoService;
import com.preferential.map.dal.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author bycwh
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2022-11-12 22:38:48
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




