package com.pine.kasa.service;

import com.pine.kasa.model.entity.primary.Test;

import java.util.List;

/**
 * @date: 2019-07-02 17:04.
 * @author: pine
 */
public interface TestService {

    List<Test> findAll();

    Test getTest(Integer id);

}
