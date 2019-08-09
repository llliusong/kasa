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
     * 子管理员
     */
    ADMIN(2),

    /**
     * 普通用户
     */
    NORMAL_USER(3),

    /**
     * 版块管理员
     */
    TOPIC_ADMIN(4),

    /**
     * 主管理员
     */
    SUPPER_MANAGER(5);

    RolePrimaryKeyEnum(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public static boolean whetherAdminAndTopicAdmin(List<Integer> roleIds) {
        if (roleIds.contains(ADMIN.getPrimaryKey()) || roleIds.contains(TOPIC_ADMIN.getPrimaryKey())||roleIds.contains(SUPPER_MANAGER.getPrimaryKey())) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean whetherAdmin(List<Integer> roleIds) {
        if (roleIds.contains(ADMIN.getPrimaryKey())||roleIds.contains(SUPPER_MANAGER.getPrimaryKey())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean whetherTopicAdmin(List<Integer> roleIds) {
        if (roleIds.contains(TOPIC_ADMIN.getPrimaryKey())) {
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
