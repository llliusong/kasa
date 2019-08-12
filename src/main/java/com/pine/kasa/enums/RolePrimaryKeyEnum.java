package com.pine.kasa.enums;

import java.util.List;

/**
 * 角色类型
 *
 * @Author: pine
 * @CreateDate: 2019-01-17 14:24
 */
public enum RolePrimaryKeyEnum {
    /**
     * 访客
     */
    ANON(1),

    /**
     * 普通用户
     */
    NORMAL_USER(2);

    RolePrimaryKeyEnum(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public static boolean whetherNormalUser(List<Integer> roleIds) {
        if (roleIds.contains(NORMAL_USER.getPrimaryKey())) {
            return true;
        } else {
            return false;
        }
    }

    private Integer primaryKey;

    public Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

}
