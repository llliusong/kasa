package com.pine.kasa.service;


/**
 * Created by pine on 2018-03-22 18:04:48
 */
public interface RoleUserService  {

    /**
     * 根据userId获取当前用户权限
     *
     * @return
     */
    String getRolesByUserId(Integer userId);

    /**
     * 根据userId修改权限状态
     *
     * @param userId
     * @param status
     */
    void modifyRoleUserStatus(Integer userId, Integer status);

}