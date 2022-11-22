package com.preferential.map.dal.domain;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.locationtech.jts.geom.Point;

/**
 * 
 * @TableName merchant
 */
@TableName(value ="merchant")
@Data
public class Merchant implements Serializable {
    /**
     * 
     */
    @TableId(value = "merchant_id", type = IdType.AUTO)
    private Long merchantId;

    /**
     * 
     */
    @TableField(value = "merchant_name")
    private String merchantName;

    /**
     * 
     */
    @TableField(value = "merchant_address")
    private String merchantAddress;

    /**
     * 全部（0）、景区（1）、出行（2）、酒店驿站（3）、商超（4）、美食（5）、医药（6）、生活服务（7）
     */
    @TableField(value = "merchant_type")
    private Integer merchantType;

    /**
     * 
     */
    @TableField(value = "profile")
    private String profile;

    /**
     * 
     */
    @TableField(value = "images")
    private JSONArray images;

    /**
     * 
     */
    @TableField(value = "merchant_location")
    private Point merchantLocation;

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