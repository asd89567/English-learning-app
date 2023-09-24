package com.bocky.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-23-上午 04:23
 * 　@Description:
 */
@Data
@TableName("word")
@AllArgsConstructor
public class Word {
    @TableId(type = IdType.AUTO)
    @JsonProperty("word_id")
    public int wordId;
    @JsonProperty("word")
    public String word;
    @JsonProperty("meaning")
    public String meaning;
    @JsonProperty("user_id")
    public Integer userId;
}
