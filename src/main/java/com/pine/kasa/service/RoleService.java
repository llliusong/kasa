package com.pine.kasa.service;

import java.util.List;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
public interface RoleService {

    List<Integer> getRoleIdByUserIdAndCompanyId(Integer userId);


    /**
     * 判断是否为社区的管理员，假如是主和子管理员返回1 其他的返回0
     */
    Integer judgeManager(List<Integer> roleIds);

}