package com.pine.kasa.service.impl;


import com.pine.kasa.dao.primary.RoleResourceMapper;
import com.pine.kasa.service.RoleResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
@Service
public class RoleResourceServiceImpl implements RoleResourceService {

    @Resource
    private RoleResourceMapper roleResourceMapper;


//	@Override
//	@Transactional
//	public void insertBatch(RolePermissionParam param) {
//		RoleResource or = new RoleResource();
//		or.setRoleId(param.getRoleId());
//		roleResourceMapper.delete(or);
//
//		List<String> resources = CommonUtil.removeArrayRepeat(param.getResourceArray().split(","));
//
//		List<RoleResource> paramList = new ArrayList<>();
//		RoleResource rrr;
//		for (String rid : resources) {
//			rrr = new RoleResource();
//			rrr.setRoleId(param.getRoleId());
//			rrr.setResourceId(Integer.valueOf(rid));
//			paramList.add(rrr);
//		}
//		roleResourceMapper.insertBatch(paramList);
//	}
}