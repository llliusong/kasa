package com.pine.kasa.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.pine.kasa.service.RedisService;
import com.pine.kasa.utils.jackson.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: ${description}
 * @Author: pine
 * @CreateDate: 2018-08-20 14:32
 */
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 默认过期时间24小时，单位：秒
     */
    public final static long DEFAULT_EXPIRES = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRES = -1;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ValueOperations<String, String> valueOperations;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    private ListOperations<String, Object> listOperations;

    @Autowired
    private SetOperations<String, Object> setOperations;

    @Autowired
    ZSetOperations<String, Object> zSetOperations;


    @Override
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void set(String key, Object value) {
        valueOperations.set(key, toJson(value));
    }

    @Override
    public void set(String key, Object value, long expires) {
        valueOperations.set(key, toJson(value));
        if (expires != NOT_EXPIRES) {
            redisTemplate.expire(key, expires, TimeUnit.SECONDS);
        }
    }

    @Override
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    @Override
    public String get(String key) {
        return valueOperations.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> zlass) {
        String value = valueOperations.get(key);
        return fromJson(value, zlass);
    }


    @Override
    public boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    @Override
    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    @Override
    public void fuzzyDeleteKey(String key) {
        Set<String> keys = redisTemplate.keys(key + "*");
        redisTemplate.delete(keys);
    }

    /**
     * scan删除
     *
     * @param key
     */
    @Override
    public void deleteKeys(String key) {
        Set<String> keys = getkeys(key);
        redisTemplate.delete(keys);
    }

    /**
     * 根前缀获取全部key
     *
     * @param key 为前缀
     * @return
     */
    @Override
    public Set<String> getkeys(String key) {
        Set<String> set = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions().match(key + "*").count(Integer.MAX_VALUE).build();
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        Cursor c = redisConnection.scan(options);
        while (c.hasNext()) {
            set.add(new String((byte[]) c.next(), Charset.forName("utf-8")));
        }
        return set;
    }

    @Override
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    @Override
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    @Override
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    @Override
    public long getKeyExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

    /**
     * 将value转化为JSON字符串
     *
     * @param value
     * @return
     */
    private String toJson(Object value) {
        if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Boolean ||
                value instanceof String) {
            return String.valueOf(value);
        }
        return JSONUtils.toJSONString(value);
    }

    /**
     * 将JSON字符串转化为Object对象
     *
     * @param Json
     * @param zlass
     * @param <T>
     * @return
     */
    private <T> T fromJson(String Json, Class<T> zlass) {
        if (StringUtils.isBlank(Json)) {
            return null;
        }
        return JsonUtil.string2Obj(Json, zlass);
    }

    @Override
    public void leftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Long size(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public <T> T rightPop(String key, Class<T> cls) {
        Object object = rightPop(key);
        return fromJson(toJson(object), cls);
    }

    @Override
    public boolean setIfAbsent(String key, String value) {
        return valueOperations.setIfAbsent(key, value);
    }

    @Override
    public Set<String> keys(String keyStartWith) {
        return redisTemplate.keys(keyStartWith + "*");
    }

}
