package com.pine.kasa.service.impl;

import com.github.pagehelper.PageInfo;
import com.pine.kasa.service.BaseService;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by pine on  2018/3/21 下午2:23.
 */
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {


    protected Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    protected Mapper<T> mapper;

    @Override
    public T selectByPrimaryKey(Object key) {
        try {
            return this.mapper.selectByPrimaryKey(key);
        } catch (Exception e) {
            logger.error("---find error---", e);
            throw new MyBatisSystemException(e);
        }
    }

    @Override
    public T selectByObject(T entity) {
        try {
            return this.mapper.selectOne(entity);
        } catch (Exception e) {
            logger.error("错误的查询,检查是否返回多个结果集!", e);
            throw new MyBatisSystemException(e);
        }
    }

    @Override
    public int insertSelective(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int updateSelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(T entity) {
        mapper.delete(entity);
    }

    @Override
    public List<T> findObjectForList() {
        return this.mapper.selectAll();
    }

    @Override
    public List<T> findObjectForList(T entity) {
        return this.mapper.select(entity);
    }


    public PageInfo getPageInfo(PageInfo from, PageInfo to) {
        to.setEndRow(from.getEndRow());
        to.setHasNextPage(from.isHasNextPage());
        to.setHasPreviousPage(from.isHasPreviousPage());
        to.setIsFirstPage(from.isIsFirstPage());
        to.setIsLastPage(from.isIsLastPage());
        to.setNavigatepageNums(from.getNavigatepageNums());
        to.setNextPage(from.getNextPage());
        to.setPageNum(from.getPageNum());
        to.setPages(from.getPages());
        to.setPrePage(from.getPrePage());
        to.setSize(from.getSize());
        to.setStartRow(from.getStartRow());
        to.setTotal(from.getTotal());
        return to;
    }
}
