/*
 * $Id: UserProfileService.java, 2011-3-24 上午11:18:54 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import com.yuep.nms.core.common.smcore.model.UserCustom;

/**
 * <p>
 * Title: UserProfileService
 * </p>
 * <p>
 * Description:用户个性化管理接口
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:18:54
 * modified [who date description]
 * check [who date description]
 */
public interface UserProfileService {

    /**
     * 获取指定用户的个性化信息
     * @param userName 用户名
     * @return 该用户的个性化信息
     */
    public UserCustom getUserCustom(String userName);
    
    /**
     * 设置指定用户的某个个性化信息
     * @param userName 用户名
     * @param key 个性化信息的属性
     * @param value 个性化信息的值
     */
    public void setUserCustom(String userName,String key,String value);
    
    /**
     * 删除指定用户个性化信息的item
     * @param userName 用户名
     * @param key 个性化信息的属性
     */
    public void deleteUserCustomItem(String userName,String key);

}
