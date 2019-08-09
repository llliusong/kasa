package com.pine.kasa.service.impl;

import com.google.common.collect.Lists;
import com.pine.kasa.dao.primary.RoleMapper;
import com.pine.kasa.dao.primary.RoleUserMapper;
import com.pine.kasa.entity.primary.RoleUser;
import com.pine.kasa.enums.RolePrimaryKeyEnum;
import com.pine.kasa.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;


    @Resource
    private RoleUserMapper roleUserMapper;

    @Override
    public List<Integer> getRoleIdByUserIdAndCompanyId(Integer userId) {
        List<Integer> roleIdByUserIdAndCompanyId = roleMapper.getRoleIdByUserIdAndCompanyId(userId);
        if (CollectionUtils.isEmpty(roleIdByUserIdAndCompanyId)) {
            roleIdByUserIdAndCompanyId = Lists.newArrayList();
            roleIdByUserIdAndCompanyId.add(RolePrimaryKeyEnum.NORMAL_USER.getPrimaryKey());
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(userId);
            roleUser.setRoleId(RolePrimaryKeyEnum.NORMAL_USER.getPrimaryKey());
            roleUserMapper.insertSelective(roleUser);
        }
        return roleIdByUserIdAndCompanyId;
    }

    @Override
    public Integer judgeManager(List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return 0;
        }
        if (RolePrimaryKeyEnum.whetherAdmin(roleIds)) {
            return 1;
        }
        return 0;
    }



}