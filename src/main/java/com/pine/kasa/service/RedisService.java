package com.pine.kasa.service;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: ${description}
 * @Author: pine
 * @CreateDate: 2018-08-20 14:32
 */
public interface RedisService {

    /**
     * 检查key是否存在
     *
     * @param key
     * @return
     */
    boolean existsKey(String key);

    /**
     * 设置key
     *
     * @param key
     * @param value
     * @return
     */
    void set(String key, Object value);

    /**
     * 设置key
     *
     * @param key
     * @param value
     * @param expires 单位秒
     * @return
     */
    void set(String key, Object value, long expires);

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    void expireKeyAt(String key, Date date);


    /**
     * 获取key
     *
     * @return
     */
    String get(String key);

    /**
     * 获取key
     *
     * @param key
     * @param zlass
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> zlass);

    /**
     * 删除key
     *
     * @param key
     */
    boolean deleteKey(String key);

    /**
     * 删除多个key
     *
     * @param keys
     */
    void deleteKey(String... keys);

    /**
     * 删除Key的集合
     *
     * @param keys
     */
    void deleteKey(Collection<String> keys);

    /**
     * 模糊删除key，比如删除user:开头的
     *
     * @param key
     */
    void fuzzyDeleteKey(String key);


    void deleteKeys(String key);

    Set<String> getkeys(String key);


    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    void renameKey(String oldKey, String newKey);

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    boolean renameKeyNotExist(String oldKey, String newKey);

    /**
     * 设置key的生命周期
     *
     * @param key
     * @param time
     * @param timeUnit TimeUnit时间颗粒度
     */
    void expireKey(String key, long time, TimeUnit timeUnit);

    /**
     * 查询key的生命周期
     *
     * @param key
     * @return
     */
    long getKeyExpire(String key);

    /**
     * 将key设置为永久有效
     *
     * @param key
     */
    void persistKey(String key);

    void leftPush(String key, Object value);

    Long size(String key);

    Object rightPop(String key);

    <T> T rightPop(String key, Class<T> cls);

    boolean setIfAbsent(String key, String value);

    /**
     * 获取指定前缀的keys合集
     *
     * @return
     */
    Set<String> keys(String keyStartWith);
}
