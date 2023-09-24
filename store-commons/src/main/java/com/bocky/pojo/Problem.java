package com.bocky.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-23-上午 03:08
 * 　@Description:
 */
@Data
@AllArgsConstructor
@TableName("problem")
public class Problem {
    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("problem_id")
    public Integer problemId;
    @JsonProperty("problem_content")
    public String problemContent;
    @JsonProperty("problem_answer")
    public String problemAnswer;
    @JsonProperty("problem_type")
    public String problemType;
    @JsonProperty("user_id")
    public Integer userId;

    public Problem(String problem_content, String problem_answer, String problem_type, Integer user_id) {
        this.problemContent = problem_content;
        this.problemAnswer = problem_answer;
        this.problemType = problem_type;
        this.userId = user_id;
    }

    ;
}
