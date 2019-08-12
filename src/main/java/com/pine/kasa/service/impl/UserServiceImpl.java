package com.pine.kasa.service.impl;

import com.pine.kasa.dao.primary.UserMapper;
import com.pine.kasa.entity.primary.RoleUser;
import com.pine.kasa.entity.primary.User;
import com.pine.kasa.pojo.dto.UserDTO;
import com.pine.kasa.service.UserService;
import com.pine.kasa.utils.AssertUtils;
import com.pine.kasa.utils.CyptoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: pine
 * @date: 2019-08-08 16:58.
 * @description: 用户实现接口
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUser(String mobile) {
        return userMapper.getUserByMobile(mobile);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserDTO dto) {
        User user = userMapper.getUserByMobile(dto.getMobile());
        AssertUtils.isTrue(user == null, "该用户已存在!");
        user = new User();
        BeanUtils.copyProperties(dto,user);
        // 密码加密，不可逆
        user.setPassword(CyptoUtils.encryptPassword(dto.getPassword()));
        userMapper.insertSelective(user);
    }


    private void endowRole(Integer userId) {
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(userId);
    }
}
