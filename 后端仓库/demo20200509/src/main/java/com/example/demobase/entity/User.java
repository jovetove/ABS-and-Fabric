package com.example.demobase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;


/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /*** 用户ID*/
    //@NotNull(message = "用户id不能为空")
    @TableId(type= IdType.AUTO)
    private Integer id;

    /** 用户名*/
    // @NotBlank(message = "用户名不能为空")
    @Length(max = 20, message = "用户名不能超过20个字符")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "用户昵称限制：最多20字符，包含文字、字母和数字")
    private String name;

    /** 密码*/
    @NotBlank
    @Length(max = 20, message = "用户名不能超过20个字符")
    private String password;

    /** 邮箱*/
    @NotBlank(message = "联系邮箱不能为空")
    @Email(message = "邮箱格式不对")
    private String email;

    /** 年龄*/
    private Integer age;

    /**激活状态 0 未激活 1 已激活*/
    private Boolean activeStatus;
    
    private Date timestamp;


}