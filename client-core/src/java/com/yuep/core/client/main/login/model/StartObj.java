/*
 * $Id: StartObj.java, 2010-4-27 下午02:19:50 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.model;

import com.yuep.core.client.main.login.LoginController;
import com.yuep.core.client.main.login.LoginSplash;

/**
 * <p>
 * Title: StartObj
 * </p>
 * <p>
 * Description:启动过程中传递的参数对象
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午02:19:50
 * modified [who date description]
 * check [who date description]
 */
public class StartObj {
    
    private LoginSplash loginSplash;
    
    private LoginController loginController;
    
    private LoginInfo loginInfo;
    
    /**
     * @return the loginSplash
     */
    public LoginSplash getLoginSplash() {
        return loginSplash;
    }

    /**
     * @param loginSplash
     *            the loginSplash to set
     */
    public void setLoginSplash(LoginSplash loginSplash) {
        this.loginSplash = loginSplash;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * @return the loginInfo
     */
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    /**
     * @param loginInfo the loginInfo to set
     */
    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
