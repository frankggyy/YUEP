/*
 * $Id: LoginModel.java, 2010-4-27 下午02:44:01 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login;

import java.util.Properties;

import com.yuep.base.resource.FileLoader;
import com.yuep.core.client.main.login.model.LoginInfo;
import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.mvc.AbstractClientModel;

/**
 * <p>
 * Title: LoginModel
 * </p>
 * <p>
 * Description:登录界面的Model
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午02:44:01
 * modified [who date description]
 * check [who date description]
 */
public class LoginModel extends AbstractClientModel<StartObj> {
    private StartObj startObj;

    private void readLastLoginInfo() {
        Properties prop = FileLoader.getProperties(LoginInfo.loginInfoFile);
        if (prop != null) {
            startObj.setLoginInfo(new LoginInfo());
            boList.add(startObj);
            startObj.getLoginInfo().setUser(prop.getProperty(LoginInfo.USERNAME));
            startObj.getLoginInfo().setServer(prop.getProperty(LoginInfo.SERVER_IP));
            startObj.getLoginInfo().setPort(prop.getProperty(LoginInfo.PORT));
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.module.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        boList.clear();
        if (objects != null && objects.length == 1) {
            startObj = (StartObj) objects[0];
            readLastLoginInfo();
        }
    }
}
