package com.pine.kasa.service.impl;

import com.pine.kasa.dao.primary.TestMapper;
import com.pine.kasa.model.entity.primary.Test;
import com.pine.kasa.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @date: 2019-07-02 17:05.
 * @author: pine
 */
@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestMapper testMapper;

    @Override
    public List<Test> findAll() {
        return testMapper.selectAll();
    }

    @Override
    public Test getTest(Integer id) {
        return testMapper.selectByPrimaryKey(id);
    }
}
