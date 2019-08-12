package com.pine.kasa.config.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: pine
 * @CreateDate: 2018-09-13 15:48
 */
@Data
public class MyUserInfo implements Serializable {
    private Integer userId;

    private String mobile;//手机号
    private String username;//手机号

    private Integer roleId;
}
