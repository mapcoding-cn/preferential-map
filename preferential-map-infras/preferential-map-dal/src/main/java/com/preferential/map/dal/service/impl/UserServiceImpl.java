package com.preferential.map.dal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preferential.map.dal.domain.User;
import com.preferential.map.dal.service.UserService;
import com.preferential.map.dal.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author bycwh
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-11-12 22:38:48
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




