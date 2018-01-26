/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package general.service;

import general.model.GoodsStock;

/**
 *
 * @author Gonjan
 * @version $Id: GoodsStockService.java, v 0.1 2018年01月25日 19:59 Gonjan Exp $
 */
public interface GoodsStockService {
    GoodsStock queryGoodsStock(Integer id);
    GoodsStock queryByTemplate(Integer id);
}