package com.pine.kasa.enums.user;

/**
 * @author: pine
 * @date: 2019-08-08 18:07.
 * @description:
 */
public enum  UserSexEnum {

    /**
     * 未知
     */
    UNKNOWN(0),

    /**
     * 男
     */
    MAN(1),

    /**
     * 女
     */
    WOMAN(2);

    private Integer sex;


    UserSexEnum(Integer sex) {
        this.sex = sex;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
