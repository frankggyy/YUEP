/*
 * $Id: SmCoreClientService.java, 2011-3-29 上午11:16:37 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smcore.def;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SmCoreClientService
 * </p>
 * <p>
 * Description: sm core客户端对其他模块提供的本地接口
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 上午11:16:37
 * modified [who date description]
 * check [who date description]
 */
public interface SmCoreClientService {

    /**
     * client登陆后初始化SM Core Client,比如加载权限等
     * @param currentUserName
     */
    public void initAfterLogin(String currentUserName);
    
    /**
     * 得到当前登录的用户
     * @return
     */
    public String getCurrentUser();
    
    /**
     * 当前登录客户端的session id
     * @return
     */
    public Long getCurrentSessionId();
    
    /**
     * 当前登录用户对moNamings是否有actionid的权限
     * @param actionId
     * @param moNamings
     * @return
     */
    public boolean check(String actionId,MoNaming ... moNamings);
    
    /**
     * 得到当前登录用户的个性化信息
     * @param propKey
     * @return
     */
    public String getUserCustom(String propKey);
    
    /**
     * 设置当前登录用户的个性化信息
     * @param propKey
     * @param value
     */
    public void setUserCustom(String propKey,String value);
    
    /**
     * 注册一个监听用户个性化信息的listener
     * @param listener
     */
    public void registerUserCustomChangeListener(UserCustomChangeListener listener);
    
    /**
     * 注销一个监听用户个性化信息的listener
     * @param listener
     */
    public void unregisterUserCustomChangeListener(UserCustomChangeListener listener);

}
