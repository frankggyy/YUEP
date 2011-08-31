/*
 * $Id: LoginAction.java, 2010-4-27 下午02:18:44 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.main.mainframe.processing.DefaultStartClientProcessor;

/**
 * <p>
 * Title: LoginAction
 * </p>
 * <p>
 * Description:登录的Action，执行点击登录按钮的动作
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午02:18:44
 * modified [who date description]
 * check [who date description]
 */
public class LoginAction extends AbstractAction {

    private static final long serialVersionUID = -3217685158655265419L;
    private StartObj startObj;
    public LoginAction(StartObj startObj) {
        this.startObj = startObj;
    }
    /**
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        Runnable runnable = new Runnable() {
            public void run() {
                startObj.getLoginController().collectData();
                DefaultStartClientProcessor<StartObj> startClientProcessor = new DefaultStartClientProcessor<StartObj>();
                startClientProcessor.process(startObj);
            }
        };
        new Thread(runnable).start();
    }
}
