package com.pine.kasa.common;

import lombok.Data;

/**
 * 项目中的常量定义类
 */
@Data
public class Constant {

    //cookie 加密相关
    public static final String AES_KEY = "alsdjsowSksa@#!$";
    public static final String COOKIE_NAME = "lol_v6";// cookie 名称

    public static final String SESSION_USER = "session_user";

    //web domain
    public static final String DOMAIN = "forwe.store";
    //小程序地址
    public static final String URL="";

    public static final String DEFAULT_AVATAR = "https://media.forwe.store/community/avatar/avatar.png";

    public static final String DEFAULT_IMAGE = "https://media.forwe.store/community/image/portal_default.jpg";

    public static final int middle = 50;
    public static final int small = 25;


}
