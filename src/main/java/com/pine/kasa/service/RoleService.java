package com.pine.kasa.service;

import java.util.List;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
public interface RoleService {

    List<Integer> getRoleIdByUserId(Integer userId);
}