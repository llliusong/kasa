package com.pine.kasa.config.shiro;

import com.pine.kasa.common.Constant;
import com.pine.kasa.common.SessionUser;
import com.pine.kasa.filter.SpringContextHolder;
import com.pine.kasa.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisSessionDAO;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ShiroUtil {

    private static RedisSessionDAO redisSessionDAO = SpringContextHolder.getBean(RedisSessionDAO.class);

    private static RoleService roleService = SpringContextHolder.getBean(RoleService.class);

    private ShiroUtil() {
    }

    /**
     * 获取指定用户名的Session
     *
     * @param username
     * @return
     */
    private static Session getSessionByUsername(String username) {
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        MyUserInfo user;
        Object attribute;
        for (Session session : sessions) {
            if (session == null) {
                continue;
            }
            attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }
            user = (MyUserInfo) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (user == null) {
                continue;
            }
            if (Objects.equals(user.getMobile(), username)) {
                return session;
            }
        }
        return null;
    }

    /**
     * 删除用户缓存信息
     *
     * @param username        用户名 username = mobile
     * @param isRemoveSession 是否删除session，删除后用户需重新登录
     */
    public static void kickOutUser(String username, boolean isRemoveSession) {
        Session session = getSessionByUsername(username);
        if (session == null) {
            return;
        }

        Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (attribute == null) {
            return;
        }

        MyUserInfo user = (MyUserInfo) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
        if (!username.equals(user.getMobile())) {
            return;
        }

        //删除session
        if (isRemoveSession) {
            redisSessionDAO.delete(session);
        }


        DefaultWebSecurityManager securityManager;

        try {
            securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        } catch (UnavailableSecurityManagerException exception) {
            securityManager = SpringContextHolder.getBean(DefaultWebSecurityManager.class);
            SecurityUtils.setSecurityManager(securityManager);
        }

        Authenticator authc = securityManager.getAuthenticator();
        //删除cache，在访问受限接口时会重新授权
        ((LogoutAware) authc).onLogout((SimplePrincipalCollection) attribute);
    }

    public static String getShiroUserName(Integer userId, Integer companyId) {
        return userId + "|" + companyId;
    }


    public static void updateUserRole(String userName, boolean isRemoveSession) {
        Session session = getSessionByUsername(userName);
        if (session == null) {
            return;
        }

        SessionUser sessionUser = (SessionUser) session.getAttribute(Constant.SESSION_USER);
        if (sessionUser == null) {
            return;
        }

        Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (attribute == null) {
            return;
        }

        MyUserInfo user = (MyUserInfo) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
        if (!userName.equals(user.getMobile())) {
            return;
        }

        if (isRemoveSession) {
            //删除session
            redisSessionDAO.delete(session);
        } else {
            //重新查出当前用户角色
//            List<Integer> roleId = roleService.getRoleIdByUserId(userName);
//            sessionUser.setRoleId(roleId.get(0));
//
//            user.setRoleId(null);
//            //重写入sessionDAO
//            redisSessionDAO.update(session);
        }

        DefaultWebSecurityManager securityManager;

        try {
            securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        } catch (UnavailableSecurityManagerException exception) {
            securityManager = SpringContextHolder.getBean(DefaultWebSecurityManager.class);
            SecurityUtils.setSecurityManager(securityManager);
        }

        Authenticator authc = securityManager.getAuthenticator();
        //删除cache，在访问受限接口时会重新授权
        ((LogoutAware) authc).onLogout((SimplePrincipalCollection) attribute);
    }

    /**
     * @return void    返回类型
     * @Title: clearAuth
     * @Description: 清空所有资源权限
     */
    public static void clearAuth() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm = (MyShiroRealm) rsm.getRealms().iterator().next();
        realm.clearAuthz();
    }

    public static Session getSession(Integer userId, Integer companyId) {
        return getSessionByUsername(getShiroUserName(userId, companyId));
    }

    /**
     * 批量删除userIds
     *
     * @param isRemoveSession
     */
    public static void kickOutUserList(List<String> mobiles, boolean isRemoveSession) {
        for (String mobile : mobiles) {
            kickOutUser(mobile, isRemoveSession);
        }
    }
}
