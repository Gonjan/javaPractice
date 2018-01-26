/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package general.service;

import general.model.UserInfo;


/**
 *
 * @author Gonjan
 * @version $Id: UserInfoService.java, v 0.1 2018年01月24日 22:38 Gonjan Exp $
 */
public interface UserInfoService {
    UserInfo selectByPrimaryKey(Integer id);
}