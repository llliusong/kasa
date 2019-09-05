package com.pine.kasa.config.shiro;

import com.pine.kasa.dao.primary.UserMapper;
import com.pine.kasa.model.entity.primary.User;
import com.pine.kasa.service.RoleService;
import com.pine.kasa.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {


    @Autowired
    private ShiroService shiroService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    @Resource
    private UserMapper userMapper;

    /**
     * 1. 在   @Controller 上加入  @RequiresRoles("admin")
     * 2.手工调用subject.hasRole(“admin”) 或 subject.isPermitted(“admin”)
     * 3.页面标签调用<shiro:hasPermission name="admin"></shiro:hasPermission>
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        MyUserInfo userInfo = (MyUserInfo) principalCollection.getPrimaryPrincipal();

        System.out.println("进行授权操作==========");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据用户ID查询角色（role），放入到Authorization里。
        /**Map<String, Object> map = new HashMap<String, Object>();
         map.put("user_id", userId);
         List<SysRole> roleList = sysRoleService.selectByMap(map);
         Set<String> roleSet = new HashSet<String>();
         for(SysRole role : roleList){
         roleSet.add(role.getType());
         }*/

        // 从新根据当前登录用户获取角色然后获取用户资源
        Set<String> permissionList = shiroService.findPermissionUrlByUserId(userInfo.getUserId());

//        Role role = roleMapper.getRoleById(roleId);
//        info.addRole(role.getName());
//        Set<String> roleSet = new HashSet<String>();
//        roleSet.add("100002");

//        info.setRoles(roleSet);

        //根据用户ID查询权限（permission），放入到Authorization里。
    /*List<SysPermission> permissionList = sysPermissionService.selectByMap(map);
    Set<String> permissionSet = new HashSet<String>();
    for(SysPermission Permission : permissionList){
        permissionSet.add(Permission.getName());
    }*/
        info.setStringPermissions(permissionList);
        return info;
    }

    /**
     * 认证回调函数，登录信息和用户验证信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //toke强转
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        String username = usernamePasswordToken.getUsername();

        MyUserInfo userInfo = new MyUserInfo();//username = mobile

        try {
            userInfo.setMobile(username);

            if (null != username) {
                //根据用户名查询密码，由安全管理器负责对比查询出的数据库中的密码和页面输入的密码是否一致
                User user = userMapper.getUserByMobile(username);
                userInfo.setUserId(user.getId());

                if (user != null) {
                    userInfo.setUserId(user.getId());
                    userInfo.setUsername(user.getNickname());

                    //最后的比对需要交给安全管理器,三个参数进行初步的简单认证信息对象的包装,由安全管理器进行包装运行
                    return new SimpleAuthenticationInfo(userInfo, user.getPassword(), getName());
                } else {
                    throw new UnknownAccountException(username + "账号不存在");
                }
            }
        } catch (Exception e) {
            throw new UnknownAccountException(username + "系统错误,登录失败!");
        }


        return null;
    }


    /**
     * 根据userId 清除当前session存在的用户的权限缓存
     *
     * @param userIds 已经修改了权限的userId
     */
    public void clearUserAuthByUserId(List<Integer> userIds) {
        if (null == userIds || userIds.size() == 0) return;
        //获取所有session
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session : sessions) {
            //获取session登录信息。
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (null != obj && obj instanceof SimplePrincipalCollection) {
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if (null != obj && obj instanceof MyUserInfo) {
                    MyUserInfo user = (MyUserInfo) obj;
                    System.out.println("user:" + user);
                    //比较用户ID，符合即加入集合
                    if (null != user && userIds.contains(user.getUserId())) {
                        list.add(spc);
                    }
                }
            }
        }
        RealmSecurityManager securityManager =
                (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm = (MyShiroRealm) securityManager.getRealms().iterator().next();
        for (SimplePrincipalCollection simplePrincipalCollection : list) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }


    public void clearAuthz() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

}
