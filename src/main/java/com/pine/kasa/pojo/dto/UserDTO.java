package com.pine.kasa.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author: pine
 * @date: 2019-08-08 18:02.
 * @description:
 */
@Data
public class UserDTO {
    private String mobile;
    private String password;

    private String nickname;

    /**
     *生日
     */
    private Date birthday;

    private Integer sex;

    private String email;
}
