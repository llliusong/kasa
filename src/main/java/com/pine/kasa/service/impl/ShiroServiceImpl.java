package com.pine.kasa.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Lists;
import com.pine.kasa.common.ServiceResult;
import com.pine.kasa.config.shiro.ShiroUtil;
import com.pine.kasa.dao.primary.ResourceMapper;
import com.pine.kasa.enums.RolePrimaryKeyEnum;
import com.pine.kasa.filter.SpringContextHolder;
import com.pine.kasa.service.RoleService;
import com.pine.kasa.service.ShiroService;
import com.pine.kasa.pojo.vo.permission.ResourceVO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {


    @Resource
    private ResourceMapper resourceMapper;

    @Resource
    private RoleService roleService;

    /**
     * 初始化权限
     */
    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        //DynamicDataSourceHolder.putDataSource(DynamicDataSourceHolder.DEFAULT_DS);
        List<ResourceVO> resourcesList = resourceMapper.findAllAnonResource();
        // 权限控制map.从数据库获取
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 权限控制map.
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/user/**", "anon");
        filterChainDefinitionMap.put("/role/insertBatch", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
        //这里要取出content-path
        for (ResourceVO resources : resourcesList) {
            if (StringUtil.isNotEmpty(resources.getValue())) {
                filterChainDefinitionMap.put(resources.getValue(), "anon");
            }
        }
        //拦截所有请求，自定义filter
        filterChainDefinitionMap.put("/**", "permission");
        return filterChainDefinitionMap;
    }

    @Override
    public Set<String> findPermissionUrlByUserId(Integer userId) {
        List<Integer> roleIds = roleService.getRoleIdByUserIdAndCompanyId(userId);
//        resourceMapper.findPermissionUrlByUserIdAndCompanyId(userId, companyId);
        return findPermissionUrlByRoleId(roleIds);
    }

    @Override
    public Set<String> findPermissionUrlByRoleId(List<Integer> roleIds) {
        //这里一个用户有多个角色，查询出所有的请求链接去重
        if (CollectionUtils.isEmpty(roleIds)) {
            //如果没有角色，默认普通用户
            roleIds = Lists.newArrayList();
            roleIds.add(RolePrimaryKeyEnum.NORMAL_USER.getPrimaryKey());
//            return new HashSet<>();
        }
        return resourceMapper.findPermissionUrlByRoleId(roleIds);
    }

    /**
     * 在对角色进行增删改操作时，需要调用此方法进行动态刷新
     */
    @Override
    public void updatePermission(Integer roleId, Integer companyId) {
        updatePermission();
        //根据角色id查用户名
        ShiroUtil.kickOutUser(1, 1, false);
    }

    private void updatePermission() {
        synchronized (this) {
            ShiroFilterFactoryBean shiroFilterFactoryBean = SpringContextHolder.getBean(ShiroFilterFactoryBean.class);
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }
    }

    @Override
    public ServiceResult getUserResource(String userName) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = SpringContextHolder.getBean(ShiroFilterFactoryBean.class);
        AbstractShiroFilter shiroFilter;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
        }
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
        return ServiceResult.success(manager.getChainNames());
    }
}
