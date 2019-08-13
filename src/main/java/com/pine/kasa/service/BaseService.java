package com.pine.kasa.service;


import java.util.List;

/**
 * Created by pine on  2018/3/21 下午2:23.
 */
public interface BaseService<T> {

    T selectByPrimaryKey(Object key);

    /**
     * 查询单个对象：如果多条记录则会抛出异常
     *
     * @param entity
     * @return
     */
    T selectByObject(T entity);


    int insertSelective(T entity);


    int updateSelective(T entity);

    void delete(T entity);

    List<T> findObjectForList();

    /**
     * 带条件查询所有
     *
     * @param entity
     * @return
     */
    List<T> findObjectForList(T entity);
}
