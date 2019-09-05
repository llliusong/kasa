package com.pine.kasa.dao.primary;


import com.pine.kasa.model.entity.primary.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
public interface RoleMapper extends Mapper<Role> {

    /**
     * 根据userId获取当前用户权限
     *
     * @return
     */
    List<Role> findRoleListByUserId(Integer userId);


    Role getRoleById(Integer roleId);

    List<Integer> getRoleIdByUserId(@Param("userId") Integer userId);
}