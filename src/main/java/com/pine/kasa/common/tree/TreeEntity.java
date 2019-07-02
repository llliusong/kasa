package com.pine.kasa.common.tree;

/**
 * ========================================
 *
 * @Description: ${description}
 * @Author: pine
 * @CreateDate: 2018-04-19 19:50
 * ========================================
 */

import java.util.List;

/**
 * 树形数据实体接口
 * @param <E>
 * @author jianda
 * @date 2017年5月26日
 */
public interface TreeEntity<E> {
    /**
     * 当前菜单id
     */
    Long getId();

    /**
     * 当前菜单父id
     */
    Long getPid();

    /**
     * 当前菜单id
     */
    void setChildList(List<E> childList);
}

