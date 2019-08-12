package com.pine.kasa.controller;

import com.pine.kasa.common.ServiceResult;
import com.pine.kasa.common.SessionManager;
import com.pine.kasa.common.SessionUser;
import com.pine.kasa.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @date: 2019-07-02 17:04.
 * @author: pine
 */
@RestController
@RequestMapping(value = "/test", name = "审核")
public class TestController extends BaseController {

    @Resource
    private TestService testService;

    /**
     * all
     *
     * @param
     * @return
     */
    @GetMapping(value = "/all", name = "all")
    public ServiceResult all() {
        return ServiceResult.success(testService.findAll());
    }

    /**
     * pc端获取未处理列表
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/one", name = "one")
    public ServiceResult one(Integer id) {
        return ServiceResult.success(testService.getTest(id));
    }

    /**
     * pc端获取未处理列表
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/test", name = "one")
    public ServiceResult test(Integer id) {
        SessionUser user = SessionManager.getUser();

        return ServiceResult.success(user);
    }

}
