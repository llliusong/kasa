package com.pine.kasa.service.impl;


import com.pine.kasa.dao.primary.RoleMapper;
import com.pine.kasa.dao.primary.RoleUserMapper;
import com.pine.kasa.entity.primary.Role;
import com.pine.kasa.service.RoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
@Service
public class RoleUserServiceImpl  implements RoleUserService {

    private static final Logger logger = LoggerFactory.getLogger(RoleUserServiceImpl.class);

    @Resource
    private RoleUserMapper roleUserMapper;

    @Resource
    private RoleMapper roleMapper;

    /**
     * 根据userId获取当前用户权限
     *
     * @param userId
     * @return
     */
    @Override
    public String getRolesByUserId(Integer userId) {
        try {
            List<Role> roleEntityListInDB = roleMapper.findRoleListByUserId(userId);
            if (roleEntityListInDB == null || roleEntityListInDB.size() == 0) {
                return null;
            }
            StringBuffer roleIds = new StringBuffer();
            for (Role entity : roleEntityListInDB) {
                roleIds.append(entity.getId()).append(",");
            }
            return roleIds.substring(0, roleIds.length() - 1);
        } catch (Exception e) {
            logger.error("获取权限失败,当前账号:" + userId + "异常:", e);
            return null;
        }
    }

    /**
     * 根据userId修改权限状态
     *
     * @param userId
     * @param status
     */
    @Override
    public void modifyRoleUserStatus(Integer userId, Integer status) {
        roleUserMapper.modifyRoleUserStatus(userId, status);
    }

}