package com.preferential.map.dal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preferential.map.dal.domain.CommentInfo;
import com.preferential.map.dal.service.CommentInfoService;
import com.preferential.map.dal.mapper.CommentInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author bycwh
* @description 针对表【comment_info】的数据库操作Service实现
* @createDate 2022-11-13 19:36:01
*/
@Service
public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoMapper, CommentInfo>
    implements CommentInfoService{

}




