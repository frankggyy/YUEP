/*
 * $Id: AbstractStartClientResponsibility.java, 2010-4-27 下午03:25:36 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.mainframe.processing.rsp;

import com.yuep.core.client.main.login.LoginSplash;
import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.main.process.rsp.AbstractResponsibility;

/**
 * <p>
 * Title: AbstractStartClientResponsibility
 * </p>
 * <p>
 * Description:抽象的客户端启动Resonsibility实现
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:25:36
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractStartClientResponsibility<T> extends AbstractResponsibility<T> {

    private LoginSplash loginSplash;

    /**
     * 设置启动步骤
     * @param t
     * @param progress
     */
    protected void setProgress(T t, int progress) {
        if (t instanceof StartObj) {
            StartObj obj = (StartObj) t;
            loginSplash = obj.getLoginSplash();
            loginSplash.updateProgress(progress);
            if (progress == 100) {
                loginSplash.dispose();
            }
        }
    }

    protected StartObj getStartObj(T t) {
        if (t instanceof StartObj)
            return (StartObj) t;
        return null;
    }
}
