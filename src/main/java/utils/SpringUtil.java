/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;

/**
 *
 * @author Gonjan
 * @version $Id: SpringUtil.java, v 0.1 2018年01月24日 23:16 Gonjan Exp $
 */
public class SpringUtil {
    private static Logger logger = LoggerFactory.getLogger(SpringUtil.class);

    private static String springXml = "spring/spring-*.xml";

    public static ApplicationContext initSpringContainer() {
        ApplicationContext context = new ClassPathXmlApplicationContext(springXml);
        if(context == null) {
            logger.error("Spring容器初始化失败");
        } else {
            if(logger.isInfoEnabled()) {
                logger.info("Spring容器初始化成功，加载的bean:");
            }
            String[] beanNames = context.getBeanDefinitionNames();
            for(String beanName:beanNames) {
                System.out.println(beanName);
            }
        }
        return context;
    }

    public static <T> T getBean(ApplicationContext context, Class<T> clazz) {
        String beanName = clazz.getSimpleName();
        beanName = beanName.substring(0,1).toLowerCase(Locale.ENGLISH) + beanName.substring(1);
        T t = context.getBean(beanName, clazz);
        return t;
    }

}