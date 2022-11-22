package com.preferential.map.dal.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName comment_info
 */
@TableName(value = "comment_info")
@Data
public class CommentInfo implements Serializable {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @TableField(value = "merchant_id")
    private Long merchantId;

    /**
     *
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     *
     */
    @TableField(value = "comment")
    private String comment;

    /**
     *
     */
    @TableField(value = "image")
    private String image;

    /**
     *
     */
    @TableField(value = "score")
    private Integer score;

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