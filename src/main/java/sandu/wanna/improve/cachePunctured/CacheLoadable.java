/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package sandu.wanna.improve.cachePunctured;

/**
 *
 * @author Gonjan
 * @version $Id: CacheLoadable.java, v 0.1 2018年01月26日 22:53 Gonjan Exp $
 */
public interface CacheLoadable<T> {
    T load();
}