package com.pine.kasa.service.impl;

import com.pine.kasa.dao.primary.ResourceMapper;
import com.pine.kasa.entity.primary.Resources;
import com.pine.kasa.service.ResourcesService;
import com.pine.kasa.utils.AssertUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
@Service
public class ResourcesServiceImpl implements ResourcesService {

	@Resource
	private ResourceMapper resourceMapper;

	/**
	 * 获取不需要登录的url
	 */
	@Override
	public List<String> getNoLoginUrls() {
		return resourceMapper.getNoLoginUrls();
	}

	@Override
	public List<Resources> findResourceEntityByRoleIds(List<Integer> roleIdList) {
		AssertUtils.isTrue(!CollectionUtils.isEmpty(roleIdList), "角色Id列表不能为空!");

		return resourceMapper.findShiroResourcesByRoleIds(roleIdList);
	}

	@Override
	public List<Resources> findAllResource(){
		return resourceMapper.findAllResource();
	}

	/**
	 * 根据权限查找菜单树
	 *
	 * @param role
	 * @return
	 */
//	@Override
//	public List<ElementMenuVO> queryAllMenuList(Integer role) {
//
//		List<ElementMenuVO> rootMenu = resourceMapper.queryAllMenuList(role);
//		// 最后的结果
//
//		List<ElementMenuVO> menuList = TreeParser.getTreeList(0, rootMenu);
//
//		return menuList;
//	}
}