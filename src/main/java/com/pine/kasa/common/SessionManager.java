package com.pine.kasa.common;

import com.pine.kasa.config.shiro.ShiroUtil;
import com.pine.kasa.exception.LoginException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * session工具类
 *
 * @author pine
 */
public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class.getCanonicalName());

    public SessionManager(HttpServletRequest request) {
        parseCookieSession(request);
    }

    public static void setSession(SessionUser sessionUser, HttpServletRequest request, HttpServletResponse response) {
        try {
            String sessionKey = AES.Encrypt(sessionUser.getUserId() + "_" + sessionUser.getMobile() + "", Constant.AES_KEY);
            Cookie cookie = new Cookie(Constant.COOKIE_NAME, sessionKey);

            cookie.setPath("/");
            String host = request.getHeader("host");
            if (host.contains(":")) {
                host = host.split(":")[0];
            }
            cookie.setDomain(host);
//            cookie.setDomain(SysConstant.DOMAIN);
            cookie.setMaxAge(7200);//7200秒
            response.addCookie(cookie);
            // TODO: 2018/9/10 如果session过期，从cookie取出重放session
            request.getSession().setAttribute(sessionKey, sessionUser);

            logger.info("session设置成功!");
        } catch (Exception e) {
            logger.error("[免登陆]设置session 失败!", e);
        }
    }

    /**
     * 获取放在存储在session里面的相关信息
     */
    public static SessionUser getSessionUser(HttpServletRequest request) {
        SessionUser session = (SessionUser) request.getSession().getAttribute(Constant.SESSION_USER);
        if (session == null) {
            parseCookieSession(request);
            return (SessionUser) request.getSession().getAttribute(Constant.SESSION_USER);
        }
        return session;
    }

    public static SessionUser getSessionUser(Integer userId, Integer companyId) {
        Session session = ShiroUtil.getSession(userId, companyId);
        if (session == null) {
            return null;
        }
        return (SessionUser)session.getAttribute(Constant.SESSION_USER);
    }

    /**
     * 获取当前登录的用户，若用户未登录，则返回未登录 json
     *
     * @return
     */
    public static SessionUser currentLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object principal = subject.getPrincipals().getPrimaryPrincipal();
            if (principal instanceof SessionUser) {
                return (SessionUser) principal;
            }
        }
        return null;
    }

    /**
     * 根据cookie获取session中的user
     *
     * @return
     */
    public static SessionUser getUser() {
        Subject subject = SecurityUtils.getSubject();
        SessionUser sessionUser = null;
        if (subject.isAuthenticated()) {
            sessionUser = (SessionUser) subject.getSession().getAttribute(Constant.SESSION_USER);
        }

        if (sessionUser == null || sessionUser.getUserId() == null) {
            throw new LoginException("请登录!");
        }
        return sessionUser;
    }

    /**
     * 是否登录
     *
     * @author pine
     * @date 2017/12/26 下午2:16
     */
    public static boolean hasLogin() {
        return SecurityUtils.getSubject().isAuthenticated();
    }


    public static Long getUserId() {
        return getUser().getUserId();
    }

    public static String getMobile() {
        return getUser().getMobile();
    }

    public static String getNickname() {
        return getUser().getNickname();
    }

    public static Integer getRoleId() {
        return getUser().getRoleId();
    }

    /**
     * 通过解析cookie 放置session
     *
     * @param request
     * @return
     */
    public static boolean parseCookieSession(HttpServletRequest request) {
        boolean flag = false;
        Cookie[] cookies = request.getCookies();
        String en_str = "";
        if (cookies != null)
            for (Cookie obj : cookies) {
                if (obj.getName().equals(Constant.COOKIE_NAME)) {
                    en_str = obj.getValue();
                    break;
                }
            }
        if (StringUtils.isNotBlank(en_str)) {
            logger.debug("-------cookie valuess--------" + en_str);
            logger.debug("进入cookie 解析");
            try {
                en_str = java.net.URLDecoder.decode(en_str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            logger.debug("-------cookie valuexxx--------" + en_str);
            en_str = AES.Decrypt(en_str, Constant.AES_KEY);
            if (StringUtils.isBlank(en_str)) {
                logger.warn("sessionUser decrypt error:" + en_str);
                return false;
            }
            logger.debug("-------cookie value--------" + en_str);
            String[] arr = en_str.split(",", -1);
            if (arr.length >= 3) {
                SessionUser sessionUser = new SessionUser();
                sessionUser.setUserId(Long.valueOf(arr[0]));
                sessionUser.setMobile(String.valueOf(arr[1]));
                sessionUser.setNickname(arr[2]);
                sessionUser.setRoleId(1);

                request.getSession(true).setAttribute(Constant.SESSION_USER, sessionUser);
                flag = sessionUser.getUserId() > 0;
            }
        }
        return flag;
    }

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    /**
     * 获取HttpSession
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession(false);
    }

    /**
     * 获取真实路径
     *
     * @return
     */
    public static String getRealPath() {
        return getRequest().getServletContext().getRealPath("/");
    }

    /**
     * 获取ip
     *
     * @return
     */
    public static String getIp() {
        if (getRequest() != null) {
            return getRequest().getRemoteAddr();
        }
        return null;
    }

    /**
     * 获取session值
     *
     * @param name
     * @return
     */
    public static Object getSessionAttribute(String name) {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getSession().getAttribute(name);
    }

    /**
     * 设置session的值
     *
     * @param name
     * @param value
     */
    public static void setSessionAttribute(String name, Object value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().setAttribute(name, value);
        }
    }

    /**
     * 删除session值
     *
     * @param name
     */
    public static void removeSessionAttribute(String name) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().removeAttribute(name);
        }
    }
}
