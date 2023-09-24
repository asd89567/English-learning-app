package com.bocky.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@TableName("user")
@Data
public class User implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("user_id")
    private Integer userId;
    @Length(min = 6)
    private String  userName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String  password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String  userPhonenumber;
}
