package com.preferential.map.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preferential.map.dal.domain.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author bycwh
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2022-11-12 22:38:48
 * @Entity com.preferential.map.dal.domain.User
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> queryByOpenId(@Param("openId") String openId);

}




