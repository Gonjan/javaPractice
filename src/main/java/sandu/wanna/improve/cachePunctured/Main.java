/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package sandu.wanna.improve.cachePunctured;

import general.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import utils.SpringUtil;

/**
 *
 * @author Gonjan
 * @version $Id: Main.java, v 0.1 2018年01月24日 23:15 Gonjan Exp $
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    private UserInfoService userInfoService;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringUtil.initSpringContainer();
        UserInfoService userInfoService = SpringUtil.getBean(applicationContext,UserInfoService.class);
        if(logger.isInfoEnabled()) {
            logger.info(userInfoService.selectByPrimaryKey(1).getUserAge());
        }
    }
}