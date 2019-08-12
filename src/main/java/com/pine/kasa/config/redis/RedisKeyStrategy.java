package com.pine.kasa.config.redis;


/**
 * reids key 生成策略
 *
 * @Author: pine
 * @CreateDate: 2019-01-22 11:47
 */
public class RedisKeyStrategy {

    public static final String SHIRO_RESOURCES_KEY = "shiro:cache:com.pine.kasa.config.shiro.MyShiroRealm.authorizationCache";

    public static final String DEFAULT_RETRY_LIMIT_CACHE_KEY_PREFIX = "shiro:cache:retrylimit:";

}
