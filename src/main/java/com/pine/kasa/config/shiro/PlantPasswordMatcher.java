package com.pine.kasa.config.shiro;

import com.pine.kasa.config.redis.RedisKeyStrategy;
import com.pine.kasa.service.RedisService;
import com.pine.kasa.utils.CyptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: pine
 * @date: 2019-08-08 23:33.
 * @description: 密码验证方式
 */
@Slf4j
public class PlantPasswordMatcher extends HashedCredentialsMatcher {


    @Autowired
    private RedisService redisService;

    private String getRedisKickOutKey(String username) {
        return RedisKeyStrategy.DEFAULT_RETRY_LIMIT_CACHE_KEY_PREFIX + username;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //得到密码
        String password = new String((char[])token.getCredentials());
        //获取用户名
        String username = usernamePasswordToken.getUsername();
        AtomicInteger retryCount = redisService.get(getRedisKickOutKey(username),AtomicInteger.class);
        //获取用户登录次数
        if (retryCount == null) {
            //如果没有登陆过，登陆次数+1，存入缓存
            retryCount = new AtomicInteger(0);
        }
        if (retryCount.incrementAndGet() > 5) {
            // 设置状态锁定用户
//            log.info("用户："+username+"已被锁定");
//            throw new LockedAccountException();
            throw new ExcessiveAttemptsException();
        }
        //判断账号是否正确
//        boolean matches = super.doCredentialsMatch(token, info);
        boolean matches = false;
        if(!password.equals(info.getCredentials().toString())){
            matches = CyptoUtils.checkPassword(password, info.getCredentials().toString());
        }
        if (matches) {
            // clear retry count
            redisService.deleteKey(getRedisKickOutKey(username));
        }else {
            redisService.set(getRedisKickOutKey(username), retryCount);
        }
        return matches;
    }

    /**
     * 解锁用户
     *
     * @param username
     * @return void
     * @author: pine
     * @date: 2019-08-09 00:05
     */
    public void unlockAccount(String username) {
        // 设置状态解锁
    }

    public static void main(String[] args) {
        AtomicInteger retryCount = new AtomicInteger(0);
        System.out.println(retryCount);

        System.out.println(retryCount.incrementAndGet());
    }

//    @Override
//    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
////        logger.debug("输入密码：{}，库存密码：{}", token.getCredentials(), info.getCredentials());
//        //得到密码
//        String password = new String((char[])token.getCredentials());
//        //得到用户名
//        String username = (String)token.getPrincipal();
//        log.debug("{}输入密码：{}，库存密码：{}", username, info.getCredentials());
//
//        // retry count + 1
//        AtomicInteger retryCount = passwordRetryCache.get(username);
//        if (retryCount == null) {
//            retryCount = new AtomicInteger(0);
//            passwordRetryCache.put(username, retryCount);
//        }
//        if (retryCount.incrementAndGet() > 3) {
//            // if retry count > 5 throw
//            throw new ExcessiveAttemptsException();
//        }
//
//        boolean matches = true;
//        if(!password.equals(info.getCredentials().toString())){
//            // 使用自定义的检查密码
//            matches = CyptoUtils.checkPassword(password, info.getCredentials().toString());
//        }
//        //boolean matches = CyptoUtils.checkPassword(password.toString(), info.getCredentials().toString());
//        if (matches) {
//            // clear retry count
//            passwordRetryCache.remove(username);
//        }
//        return matches;
//    }
//
//
//    public PlantPasswordMatcher() {
//    }

}
