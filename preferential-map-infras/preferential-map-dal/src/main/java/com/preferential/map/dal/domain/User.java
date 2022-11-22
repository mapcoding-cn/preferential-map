package com.preferential.map.dal.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {

    /**
     *
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     *
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     *
     */
    @TableField(value = "wechat_name")
    private String wechatName;

    /**
     *
     */
    @TableField(value = "session_id")
    private String sessionId;
    /**
     *
     */
    @TableField(value = "super_user")
    private Integer superUser = 0;

    /**
     *
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}