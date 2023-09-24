package com.bocky.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-18-下午 06:06
 * 　@Description:
 */
@TableName("photo")
@Data
public class photo {
    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("photo_Id")
    private Integer photoId;

    @JsonProperty("username")
    @TableField("username")
    private String username;
    @JsonProperty("photo")
    @TableField("photo")
    private String photo;
}
