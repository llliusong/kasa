package com.pine.kasa.controller;

import com.pine.kasa.common.ServiceResult;
import com.pine.kasa.common.SessionManager;
import com.pine.kasa.common.SessionUser;
import com.pine.kasa.config.redis.RedisKeyStrategy;
import com.pine.kasa.config.shiro.ShiroUtil;
import com.pine.kasa.dao.primary.UserMapper;
import com.pine.kasa.entity.primary.User;
import com.pine.kasa.service.RedisService;
import com.pine.kasa.service.ShiroService;
import com.pine.kasa.service.TestService;
import com.pine.kasa.service.UserService;
import com.pine.kasa.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @date: 2019-07-02 17:04.
 * @author: pine
 */
@Slf4j
@RestController
@RequestMapping(value = "/sync", name = "同步模块")
public class SyncController extends BaseController {

    @Resource
    private TestService testService;

    @Resource
    private RedisService redisService;

    @Resource
    private ShiroService shiroService;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

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

    /**
     * 同步shiro中的资源
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/shiroResource")
    public ServiceResult shiro(Integer roleId, String mobile) {
        if (roleId == null && mobile == null) {
            return ServiceResult.failure("参数不能为空!");
        }
        if (roleId != null) {
            if (roleId == 0) {
                //清除所有授权缓存
                redisService.deleteKeys(RedisKeyStrategy.SHIRO_RESOURCES_KEY);
            }
            shiroService.updatePermission(roleId, mobile);
        }
        return ServiceResult.success("成功");
    }

    /**
     * 清除用户缓存，重新获取资源
     *
     * @param userId
     * @param isRemoveSession true需要重新登录，false不需要重新登录仅清除资源
     * @return
     */
    @RequestMapping("/shiroUser")
    public ServiceResult shiroUser(Integer userId, @RequestParam(defaultValue = "false") Boolean isRemoveSession) {
        AssertUtils.notNull(userId, "userId不能为空!");
        User user = userService.getUser(userId);
        AssertUtils.notNull(user, "没有找到该用户!");
        ShiroUtil.kickOutUser(user.getMobile(), isRemoveSession);

        return ServiceResult.success("成功");
    }

    /**
     * 获取用户当前可访问资源
     *
     * @param userId
     * @return com.pine.kasa.common.ServiceResult
     * @author: pine
     * @date: 2019-08-12 14:03
     */
    @RequestMapping("/getUserResource")
    public ServiceResult getUserResource(Integer userId) {
        AssertUtils.notNull(userId, "userId不能为空!");
        User user = userService.getUser(userId);
        AssertUtils.notNull(user, "没有找到该用户!");

        return shiroService.getUserResource(user.getMobile());
    }

    @RequestMapping("/get2")
    @Transactional
    public ServiceResult get2(Integer userId) {
        SessionUser sessionUser = SessionManager.getSessionUser(userId, 1);
        User user = userMapper.getUserByUserId(1);
        User user2 = userMapper.getUserByUserId(1);
        User user3 = userMapper.getUserByUserId(1);
        User user4 = userMapper.getUserByUserId(1);
        User user5 = userMapper.getUserByUserId(1);
        log.info("=============================================================");

        User UserCompany1 = userMapper.selectByPrimaryKey(1);
        User UserCompany2 = userMapper.selectByPrimaryKey(1);
        User UserCompany3 = userMapper.selectByPrimaryKey(1);
        log.info("=============================================================");

        return ServiceResult.success(sessionUser);
    }
}
