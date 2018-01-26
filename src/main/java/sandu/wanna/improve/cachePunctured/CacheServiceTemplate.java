/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package sandu.wanna.improve.cachePunctured;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.RedisCache;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Gonjan
 * @version $Id: CacheServiceTemplate.java, v 0.1 2018年01月24日 23:54 Gonjan Exp $
 */
public class CacheServiceTemplate<T> {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    private static final Logger logger = LoggerFactory.getLogger(CacheServiceTemplate.class);

    /**
     * 并发处理的缓存查询模板方法
     * @param queryKey 查询键值
     * @param expire 缓存过期时间
     * @param unit 时间单位
     * @param typeReference 传入泛型类型的类对象
     * @param cacheLoadable 业务回调类
     * @param <T>
     * @return
     */
    public <T> T queryByCache(String queryKey, long expire, TimeUnit unit,
                              TypeReference<T> typeReference, CacheLoadable<T> cacheLoadable) {
        T result;
        readWriteLock.readLock().lock();//添加读锁
        try {
            //缓存中查找
            String goodsStockJsonStr = RedisCache.get(queryKey);
            //缓存中查找成功
            if (!StringUtils.isEmpty(goodsStockJsonStr) && !"null".equals(goodsStockJsonStr)) {
                logger.info("=====query from cache=====");
                result = JSONObject.parseObject(goodsStockJsonStr, typeReference);
            } else {
                //若缓存读取失败，则需要去数据库中查询
                readWriteLock.readLock().unlock();//释放读锁
                readWriteLock.writeLock().lock();//添加写锁
                try {
                    goodsStockJsonStr = RedisCache.get(queryKey);
                    if (!StringUtils.isEmpty(goodsStockJsonStr) && !"null".equals(goodsStockJsonStr)) {
                        logger.info("=====query from cache=====");
                        return JSONObject.parseObject(goodsStockJsonStr, typeReference);
                    }
                    logger.info("=====query from DB=====");
                    //这里调用业务传入的回调方法，真正处理业务的地方只有这一行
                    result = cacheLoadable.load();
                    RedisCache.set(queryKey, JSONArray.toJSONString(result));
                } finally {
                    readWriteLock.writeLock().unlock();
                    readWriteLock.readLock().lock();
                }
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        return result;
    }
}