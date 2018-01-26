/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package sandu.wanna.improve.cachePunctured;

import general.service.GoodsStockService;
import general.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import utils.SpringUtil;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Gonjan
 * @version $Id: Main.java, v 0.1 2018年01月24日 23:15 Gonjan Exp $
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private CountDownLatch countDownLatch = new CountDownLatch(10);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringUtil.initSpringContainer();
        GoodsStockService goodsStockService = SpringUtil.getBean(applicationContext,GoodsStockService.class);
        RedisTemplate<String,String> redisTemplate = SpringUtil.getBean(applicationContext,RedisTemplate.class);

    }

    private void testCache() {

    }




}