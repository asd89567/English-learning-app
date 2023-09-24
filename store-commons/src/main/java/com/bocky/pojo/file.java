package com.bocky.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import retrofit2.http.DELETE;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-22-下午 05:47
 * 　@Description:
 */
@TableName("file")
@Data
@AllArgsConstructor
public class file {
    public static final Long serialVersionUID = 1L;

    @TableId(value = "file_id",type = IdType.AUTO)
    @JsonProperty("file_id")
    public Integer fileId;

    @TableField("file_name")
    @JsonProperty("file_name")
    public String fileName;

    @TableField("file_loc")
    @JsonProperty("file_loc")
    public String fileloc;

    @TableField("user_id")
    @JsonProperty("user_id")
    public Integer userId;


    public file(String fileloc, Integer userId) {
        this.fileloc = fileloc;
        this.userId = userId;
    }
}
