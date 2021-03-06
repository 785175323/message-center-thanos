package com.kakuiwong.messagecenterthanos.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: gaoyang
 * @Description:
 */
@Component
public class Redis {
    private static final StringRedisTemplate REDIS = ApplicationContextHolder.getContext().getBean(StringRedisTemplate.class);

    /**
     * Redis中是否存在指定键
     *
     * @param key 键
     * @return 是否存在指定键
     */
    public static Boolean hasKey(String key) {
        return REDIS.hasKey(key);
    }

    /**
     * 获取Key过期时间
     *
     * @param key  键
     * @param unit 时间单位
     * @return 过期时间
     */
    public static long getExpire(String key, TimeUnit unit) {
        Long expire = REDIS.getExpire(key, unit);

        return expire == null ? 0 : expire;
    }

    /**
     * 设置Key过期时间
     *
     * @param key  键
     * @param time 时间长度
     * @param unit 时间单位
     */
    public static void setExpire(String key, long time, TimeUnit unit) {
        if (!hasKey(key)) {
            return;
        }

        REDIS.expire(key, time, unit);
    }

    /**
     * 从Redis删除指定键
     *
     * @param key 键
     */
    public static void deleteKey(String key) {
        REDIS.delete(key);
    }

    /**
     * 从Redis读取指定键的值
     *
     * @param key 键
     * @return Value
     */
    public static String get(String key) {
        return REDIS.opsForValue().get(key);
    }

    /**
     * 以键值对方式保存数据到Redis
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, String value) {
        REDIS.opsForValue().set(key, value);
    }

    /**
     * 以键值对方式保存数据到Redis
     *
     * @param key   键
     * @param value 值
     * @param time  时间长度
     * @param unit  时间单位
     */
    public static void set(String key, String value, long time, TimeUnit unit) {
        REDIS.opsForValue().set(key, value, time, unit);
    }

    /**
     * 以键值对方式保存数据到Redis
     *
     * @param key   键
     * @param value 值
     * @return 是否保存
     */
    public static boolean setIfAbsent(String key, String value) {
        Boolean absent = REDIS.opsForValue().setIfAbsent(key, value);

        return absent == null ? false : absent;
    }

    /**
     * Redis中是否存在指定键
     *
     * @param key   键
     * @param field 字段名称
     * @return 是否存在指定键
     */
    public static Boolean hasKey(String key, String field) {
        return REDIS.opsForHash().hasKey(key, field);
    }

    /**
     * 从Redis读取指定键下的字段名称的值
     *
     * @param key   键
     * @param field 字段名称
     * @return Value
     */
    public static String get(String key, String field) {
        Object val = REDIS.opsForHash().get(key, field);

        return val == null ? null : val.toString();
    }

    /**
     * 从Redis读取指定键的值并反序列化为对象后返回
     *
     * @param key 键
     * @return Value
     */
    public static <T> T get(String key, Class<T> type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map val = REDIS.opsForHash().entries(key);
        return objectMapper.readValue(objectMapper.writeValueAsString(val), type);
    }

    /**
     * 从Redis读取指定键下的全部字段名
     *
     * @param key 键
     * @return Value
     */
    public static List<Object> getHashKeys(String key) {
        Set<Object> val = REDIS.opsForHash().keys(key);

        return new ArrayList<>(val);
    }

    /**
     * 以Hash方式保存数据到Redis
     *
     * @param key   键
     * @param field 字段名称
     * @param value 值
     */
    public static void set(String key, String field, Object value) {
        REDIS.opsForHash().put(key, field, value.toString());
    }

    /**
     * 以Hash方式保存数据到Redis
     *
     * @param key 键
     * @param map Map 对象
     */
    public static void set(String key, Map map) {
        Map<String, String> hashMap = new HashMap<>(32);
        for (Object k : map.keySet()) {
            Object v = map.get(k);
            hashMap.put(k.toString(), v == null ? null : v.toString());
        }

        REDIS.opsForHash().putAll(key, hashMap);
    }

    /**
     * 删除指定的Hash键
     *
     * @param key   键
     * @param field 字段名称
     */
    public static void deleteKey(String key, String field) {

        REDIS.opsForHash().delete(key, field);
    }

    /**
     * 是否指定键的成员
     *
     * @param key    键
     * @param member Set成员
     * @return 是否成员
     */
    public static Boolean isMember(String key, String member) {
        return REDIS.opsForSet().isMember(key, member);
    }

    /**
     * 从Redis读取指定键的全部成员
     *
     * @param key 键
     * @return Value
     */
    public static List<String> getMembers(String key) {
        Set<String> val = REDIS.opsForSet().members(key);

        return val == null ? null : new ArrayList<>(val);
    }

    /**
     * 以Set方式保存数据到Redis
     *
     * @param key   键
     * @param value 值
     */
    public static void add(String key, String value) {
        REDIS.opsForSet().add(key, value);
    }

    /**
     * 删除指定的Set成员
     *
     * @param key   键
     * @param value 值
     * @return Set成员数
     */
    public static Long remove(String key, String value) {
        return REDIS.opsForSet().remove(key, value);
    }
}
