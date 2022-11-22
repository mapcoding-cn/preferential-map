package com.preferential.map.dal.domain;

import com.alibaba.fastjson.JSONObject;
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
 * @TableName user_info
 */
@TableName(value = "user_info")
@Data
public class UserInfo implements Serializable {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 现役（0）优待证持证人（1）军属（2）医护（3）
     */
    @TableField(value = "credential_type")
    private Integer credentialType = 1;

    /**
     * 默认全部开启
     * merchant_area=[0,1,2]
     * 全国（0）省内（1）户籍地（2）
     */
    @TableField(value = "config")
    private JSONObject config = new JSONObject();

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