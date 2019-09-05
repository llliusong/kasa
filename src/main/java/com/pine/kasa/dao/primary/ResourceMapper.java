package com.pine.kasa.dao.primary;

import com.pine.kasa.model.entity.primary.Resources;
import com.pine.kasa.model.vo.permission.ResourceVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
public interface ResourceMapper extends Mapper<Resources> {

    /**
     * 获取不需要登录的url
     */
    List<String> getNoLoginUrls();

    /**
     * 根据条件查询资源列表
     *
     * @param roleIdList 角色列表
     * @author pine
     * @date 2018/09/14 下午2:15
     */
    List<Resources> findShiroResourcesByRoleIds(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 查找表中所有资源
     *
     * @return
     */
    List<Resources> findAllResource();

    /**
     * 查找表中所有游客资源
     *
     * @return
     */
    List<ResourceVO> findAllAnonResource();

    List<ResourceVO> findResourceByRoleId(Integer roleId);

    Set<String> findPermissionUrlByUserIdAndCompanyId(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    Set<String> findPermissionUrlByRoleId(@Param("roleIds") List<Integer> roleIds);

}