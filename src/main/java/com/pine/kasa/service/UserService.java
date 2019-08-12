package com.pine.kasa.service;

import com.pine.kasa.entity.primary.User;
import com.pine.kasa.pojo.dto.UserDTO;

/**
 * @author: pine
 * @date: 2019-08-08 16:57.
 * @description: 用户服务层
 */
public interface UserService {

    User getUser(String mobile);

    void create(UserDTO dto);
}
