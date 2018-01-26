/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package general.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import general.model.GoodsStock;
import general.model.mapper.GoodsStockMapper;
import general.service.GoodsStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sandu.wanna.improve.cachePunctured.CacheLoadable;
import sandu.wanna.improve.cachePunctured.CacheServiceTemplate;
import utils.RedisCache;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Gonjan
 * @version $Id: GoodsStockServiceImpl.java, v 0.1 2018年01月25日 20:00 Gonjan Exp $
 */
@Service("goodsStockService")
public class GoodsStockServiceImpl implements GoodsStockService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsStockServiceImpl.class);

    @Autowired
    private GoodsStockMapper goodsStockMapper;

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    CacheServiceTemplate<GoodsStock> cacheServiceTemplate = new CacheServiceTemplate();

    @Override
    /**
     * 根据商品id(也是主键)查询商品库存记录
     */
    public GoodsStock queryGoodsStock(Integer id) {
        GoodsStock result;
        readWriteLock.readLock().lock();//添加读锁
        try {
            //缓存中查找
            String goodsStockJsonStr = RedisCache.get(id);
            //缓存中查找成功
            if (!StringUtils.isEmpty(goodsStockJsonStr) && !"null".equals(goodsStockJsonStr)) {
                logger.info("=====query from cache=====");
                result = JSONObject.parseObject(goodsStockJsonStr, GoodsStock.class);
            } else {
                //若缓存读取失败，则需要去数据库中查询
                readWriteLock.readLock().unlock();//释放读锁
                readWriteLock.writeLock().lock();//添加写锁
                try {
                    goodsStockJsonStr = RedisCache.get(id);
                    if (!StringUtils.isEmpty(goodsStockJsonStr) && !"null".equals(goodsStockJsonStr)) {
                        logger.info("=====query from cache=====");
                        return JSONObject.parseObject(goodsStockJsonStr, GoodsStock.class);
                    }
                    logger.info("=====query from DB=====");
                    result = goodsStockMapper.selectByPrimaryKey(id);
                    //查询结果写入缓存
                    RedisCache.set(id, JSONArray.toJSONString(result));
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


    /**
     *
     * @param id
     * @return
     */
    @Override
    public GoodsStock queryByTemplate(Integer id) {
        return cacheServiceTemplate.queryByCache(String.valueOf(id), 0, null,
                new TypeReference<GoodsStock>() {}, new CacheLoadable<GoodsStock>() {
                    @Override
                    public GoodsStock load() {
                        return goodsStockMapper.selectByPrimaryKey(id);
                    }
                });
    }
}