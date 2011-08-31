/*
 * $Id: AuthenticationService.java, 2011-3-24 上午11:17:52 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.base.def.annotation.LogType;

/**
 * <p>
 * Title: AuthenticationService
 * </p>
 * <p>
 * Description:认证接口
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:17:52
 * modified [who date description]
 * check [who date description]
 */
public interface AuthenticationService {

    /**
     * 登录
     * @param user 用户名
     * @param password 密码
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY,scopeFilter=false)
    public void login(String user,String password);

    /**
     * 注销
     * @param sessionId 会话id
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY,scopeFilter=false)
    public void logout(long sessionId);
    
}
