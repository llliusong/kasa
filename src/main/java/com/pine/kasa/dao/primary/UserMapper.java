package com.pine.kasa.dao.primary;

import com.pine.kasa.model.entity.primary.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    User getUserByMobile(String mobile);

    User getUserByUserId(Integer id);
}