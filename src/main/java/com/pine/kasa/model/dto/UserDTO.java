package com.pine.kasa.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author: pine
 * @date: 2019-08-08 18:02.
 * @description:
 */
@Data
public class UserDTO {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$", message = "手机格式错误")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String nickname;

    /**
     *生日
     */
    private Date birthday;

    private Integer sex;

    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式错误")
    private String email;
}
