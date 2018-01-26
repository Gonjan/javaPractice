/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
 * @author Gonjan
 * @version $Id: RedisCache.java, v 0.1 2018年01月25日 16:52 Gonjan Exp $
 */
public class RedisCache {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static RedisTemplate<String,String> redisTemplateStatic;

    private static ValueOperations<String,String> valueOperations;

    /**
     * @PostConstruct注解作用：是Java EE 5引入的注解，Spring允许开发者在受管Bean中使用它。
     * 当DI容器实例化当前受管Bean时
     * ，@PostConstruct注解的方法会被自动触发，从而完成一些初始化工作，示例代码如下。
     */
    @PostConstruct
    void init() {
        redisTemplateStatic = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    public static void set(String key, String value) {
         valueOperations.set(key,value);
    }

    public static void set(Integer key, String value) {
        String intKey = String.valueOf(key);
        set(intKey,value);
    }

    public static String get(String key) {
        return valueOperations.get(key);
    }

    public static String get(Integer key) {
        String intKey = String.valueOf(key);
        return get(intKey);
    }

}