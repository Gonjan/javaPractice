/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

import general.model.GoodsStock;
import general.service.GoodsStockService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Gonjan
 * @version $Id: TestGoodsStockService.java, v 0.1 2018年01月26日 15:24 Gonjan Exp $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
public class TestGoodsStockService {
    private static final Logger logger = LoggerFactory.getLogger(TestGoodsStockService.class);
    @Autowired
    private GoodsStockService goodsStockService;

    private CountDownLatch countDownLatch = new CountDownLatch(10);

    private static final Integer GOODS_ID = new Integer(1);

    @Test
    public void testSelectByPrimaryKey() {
        Integer id = new Integer("1");
        Assert.assertTrue(goodsStockService.queryGoodsStock(GOODS_ID) != null);

    }

    /**
     * 10个线程并发调用服务
     */
    @Test
    public void testMultiThreadQuery() throws Exception{
        for(int i = 0; i < 10; i++) {
            new Thread(new QueryTask()).start();
            countDownLatch.countDown(); //启动线程达到10个时，10个线程同时执行查询
        }
        Thread.sleep(500000);
    }

    private class QueryTask implements Runnable {
        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GoodsStock goodsStock = goodsStockService.queryByTemplate(GOODS_ID);
        }
    }

}