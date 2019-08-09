package com.pine.kasa.service;

import com.pine.kasa.entity.primary.Resources;

import java.util.List;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
public interface ResourcesService {

    /**
     * 获取不需要登录的url
     */
    List<String> getNoLoginUrls();

    /**
     * 根据条件查询资源列表
     *
     * @param roleIdList 角色列表
     * @author pine
     * @date 2017/3/22 下午2:15
     */
    List<Resources> findResourceEntityByRoleIds(List<Integer> roleIdList);

    /**
     * 查找表中所有资源
     *
     * @return
     */
    List<Resources> findAllResource();

}