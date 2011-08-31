/*
 * $Id: CheckLoginInfoRsp.java, 2009-2-21 下午01:03:59 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.mainframe.processing.rsp;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.main.login.LoginController;
import com.yuep.core.client.main.login.LoginSplash;
import com.yuep.core.client.main.login.model.LoginInfo;
import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: CheckLoginInfoRsp
 * </p>
 * <p>
 * Description:客户端登录检查的默认实现，包括对用户名密码等信息的校验,参考start-process.xml
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:25:36
 * modified [who date description]
 * check [who date description]
 */
public class DefaultCheckLoginInfoRsp<T> extends AbstractStartClientResponsibility<T> {

     /**
      * (non-Javadoc)
      * @see com.yuep.core.client.main.process.rsp.Responsibility#execute(java.lang.Object)
      */
    @Override
    public boolean execute(T t) {
        StartObj startObj = getStartObj(t);
        LoginController loginController = startObj.getLoginController();
        LoginInfo loginInfo = startObj.getLoginInfo();
        LoginSplash splash = new LoginSplash();
        startObj.setLoginSplash(splash);
        setProgress(t, 5);
        try {
            login(loginInfo);
        } catch (RuntimeException e) {
            splash.dispose();
            DialogUtils.showErrorExpandDialog(loginController.getClientView().getWindow(), e);
            return false;
        }
        splash.setAlwaysOnTop(true);
        loginController.getClientView().dispose();
        setProgress(t, 15);

        return true;
    }

    /**
     * @param server
     * @param user
     * @param password
     */
    protected void login(LoginInfo loginInfo) {
        ClientCoreContext.connect2Remote(loginInfo.getServer(), Integer.valueOf(loginInfo.getPort()));
    }

}
