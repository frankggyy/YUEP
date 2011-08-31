/*
 * $Id: LoginController.java, 2010-4-27 下午02:28:04 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login;

import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.resource.FileLoader;
import com.yuep.core.client.main.login.model.LoginInfo;
import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.mvc.AbstractClientController;

/**
 * <p>
 * Title: LoginController
 * </p>
 * <p>
 * Description:登录界面的Controller
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午02:28:04
 * modified [who date description]
 * check [who date description]
 */
public class LoginController extends AbstractClientController<StartObj, LoginView,LoginModel>{

    /**
     * @param viewClass
     * @param modelClass
     */
    public LoginController(Class<LoginView> viewClass, Class<LoginModel> modelClass) {
        super(viewClass, modelClass);
    }

    /**
     * (non-Javadoc)
     * @see com.yotc.opview.framework.client.component.module.AbstractClientController#collectData()
     */
    @Override
    public void collectData() {
        // 获取数据
        super.collectData();
        List<StartObj> collectData = clientModel.getModelDatas();
        if(CollectionUtils.isEmpty(collectData)){
            return;
        }
        LoginInfo submitData = collectData.get(0).getLoginInfo();
        if(submitData != null){
            // 存入system.properties
            Properties prop = new Properties();
            prop.setProperty(LoginInfo.SERVER_IP, submitData.getServer());
            prop.setProperty(LoginInfo.PORT, submitData.getPort());
            prop.setProperty(LoginInfo.USERNAME, submitData.getUser());
            FileLoader.storeProperties(prop, LoginInfo.loginInfoFile);
        }
    }    
}
