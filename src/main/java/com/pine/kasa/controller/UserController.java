package com.pine.kasa.controller;

import com.pine.kasa.common.Constant;
import com.pine.kasa.common.ServiceResult;
import com.pine.kasa.common.SessionUser;
import com.pine.kasa.model.entity.primary.User;
import com.pine.kasa.model.dto.UserDTO;
import com.pine.kasa.service.UserService;
import com.pine.kasa.utils.AssertUtils;
import com.pine.kasa.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @date: 2019-07-06 18:32.
 * @author: pine
 */
@Slf4j
@RestController
@RequestMapping(value = "/user", name = "用户")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param
     * @return
     */
    @PostMapping(value = "/register", name = "用户注册")
    public ServiceResult register(UserDTO userDTO) {
        AssertUtils.notNull(userDTO, "注册参数不能为空!");
        ValidationUtils.validate(userDTO);

        userService.create(userDTO);

        return ServiceResult.success("注册成功!");
    }

    /**
     * 用户登录
     *
     * @param mobile
     * @param password
     * @param request
     * @return com.pine.kasa.common.ServiceResult
     * @author: pine
     * @date: 2019-08-09 16:03
     */
    @PostMapping(value = "/login", name = "用户登录")
    public ServiceResult login(String mobile, String password, HttpServletRequest request) {
        AssertUtils.notNull(mobile, "用户名不能为空!");
        AssertUtils.notNull(password, "密码不能为空!");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(mobile, password);
        User user = userService.getUser(mobile);
        AssertUtils.notNull(user, "帐号不存在");
        try {
            subject.login(token);
            token.setRememberMe(true);
            request.setAttribute("msg", "登录成功!");
            SessionUser sessionUser = new SessionUser();
            sessionUser.setUserId(user.getId());
            sessionUser.setNickname(user.getNickname());
            sessionUser.setMobile(user.getMobile());

            subject.getSession().setAttribute(Constant.SESSION_USER, sessionUser);
            return ServiceResult.success("登录成功!");

        } catch (IncorrectCredentialsException e) {
            request.setAttribute("msg", "用户名或密码错误");
            return ServiceResult.success("用户名或密码错误");
        } catch (ExcessiveAttemptsException e) {
            request.setAttribute("msg", "登录失败次数过多");
            return ServiceResult.success("登录失败次数过多");
        } catch (LockedAccountException e) {
            request.setAttribute("msg", "帐号已被锁定");
            return ServiceResult.success("帐号已被锁定!");
        } catch (DisabledAccountException e) {
            request.setAttribute("msg", "帐号已被禁用");
            return ServiceResult.success("帐号已被禁用");
        } catch (ExpiredCredentialsException e) {
            request.setAttribute("msg", "帐号已过期");
            return ServiceResult.success("帐号已过期");
        } catch (UnknownAccountException e) {
            request.setAttribute("msg", "帐号不存在");
            return ServiceResult.success("帐号不存在");
        } catch (UnauthorizedException e) {
            request.setAttribute("msg", "您没有得到相应的授权！");
            return ServiceResult.success("您没有得到相应的授权!");
        } catch (AuthenticationException e) {
            request.setAttribute("msg", "用户名或密码错误");
            return ServiceResult.success("用户名或密码错误!");
        } catch (Exception e) {
            request.setAttribute("msg", "未知异常！" + e.getMessage());
            log.error("登陆失败,异常:", e);
            return ServiceResult.success("未知异常！" + e.getMessage());
        }
    }

}
