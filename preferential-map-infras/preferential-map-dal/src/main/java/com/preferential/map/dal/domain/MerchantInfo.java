package com.preferential.map.dal.domain;

import com.alibaba.fastjson.JSONArray;
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
 * @TableName merchant_info
 */
@TableName(value = "merchant_info")
@Data
public class MerchantInfo implements Serializable {

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
    @TableField(value = "merchant_area")
    private Integer merchantArea;

    /**
     *
     */
    @TableField(value = "credential_type")
    private JSONArray credentialType;

    /**
     *
     */
    @TableField(value = "config")
    private JSONObject config;

    /**
     *
     */
    @TableField(value = "source")
    private String source;

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