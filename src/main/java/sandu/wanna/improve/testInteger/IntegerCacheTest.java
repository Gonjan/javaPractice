/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package sandu.wanna.improve.testInteger;

import java.lang.reflect.Field;

/**
 *
 * @author Gonjan
 * @version $Id: IntegerCacheTest.java, v 0.1 2018年01月28日 21:32 Gonjan Exp $
 */
public class IntegerCacheTest {
    public static void main(String[] args) {
        //使用缓存IntegerCache
        Integer a = Integer.valueOf(1);
        Integer b = Integer.valueOf(2);

        //需要装箱操作，调用valueOf方法
        //Integer a = 1;
        //Integer b = 2;

        //直接创建对象，不使用Integer中的IntegerCache
        //Integer a = new Integer(1);
        //Integer b = new Integer(2);2

        System.out.println("a = " + a + "   b = " + b);
        swap(a,b);
        System.out.println("a = " + a + "   b = " + b);
        Integer num = 1;
        System.out.println(num);
    }

    /**
     * swap a and b
     * a 和 b 都是IntegerCache中的值，
     * 通过Integer的源码可看出，通过valueOf进行创建对象的方式，
     * 值在-128~127之间的值会使用IntegerCache的缓存对象。
     * @param a
     * @param b
     */
    public static void swap(Integer a, Integer b) {
        try {
            //获取Integer中的value字段
            Field field = Integer.class.getDeclaredField("value");
            //去除value字段的private属性
            field.setAccessible(true);
            int temp = a.intValue();
            //调用field的se方法，将b对象中的value字段的值传入a对象value字段的值
            field.set(a,b);
            field.set(b,temp);
            //以上两句实现两个对象value值的调换，这样，由于使用缓存中的对象，导致
            //缓存中相应位置的值发生变换，从未出现Integer num = 1； 实则num对象为缓存数组中
            //1 + 128 位置的值为2 ，出现num value的值为2
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}