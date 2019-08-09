package com.pine.kasa.dao.primary;

import com.pine.kasa.entity.primary.RoleUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
public interface RoleUserMapper extends Mapper<RoleUser> {

    /**
     * 根据userId修改权限状态
     *
     * @param userId
     * @param status
     */
    void modifyRoleUserStatus(@Param("userId") Integer userId, @Param("status") Integer status);


    /**
     * 批量插入数据
     *
     * @param roleUsers
     */
    void insertList(@Param("list") List<RoleUser> roleUsers);

}