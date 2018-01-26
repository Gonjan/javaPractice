/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package general.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import general.model.UserInfo;
import general.model.mapper.UserInfoMapper;
import general.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utils.RedisCache;

/**
 *
 * @author Gonjan
 * @version $Id: UserInfoServiceImpl.java, v 0.1 2018年01月24日 22:42 Gonjan Exp $
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 根据id查询user信息，首先尝试从缓存取，缓存有命中再从DB中取
     * @param id
     * @return
     */
    @Override
    public UserInfo selectByPrimaryKey(Integer id) {
        UserInfo result;
        String userJsonStr = RedisCache.get(id);

        //缓存中查找
        if(!StringUtils.isEmpty(userJsonStr) && !"null".equals(userJsonStr)) {
            logger.info("=====query from cache=====");
            return JSONObject.parseObject(userJsonStr,UserInfo.class);
        }
        //没有命中缓存，数据库中查找，并将结果写入缓存
        logger.info("=====query from DB=====");
        result = userInfoMapper.selectByPrimaryKey(id);
        RedisCache.set(id, JSONArray.toJSONString(result));
        return result;
    }
}