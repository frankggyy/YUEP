/*
 * $Id: DefaultOpenLoginViewRsp.java, 2010-9-28 下午06:29:20 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.processing.rsp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.main.login.LoginController;
import com.yuep.core.client.main.login.LoginModel;
import com.yuep.core.client.main.login.LoginView;
import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.main.process.rsp.AbstractResponsibility;

/**
 * <p>
 * Title: DefaultOpenLoginViewRsp
 * </p>
 * <p>
 * Description:默认打开登录窗口的Responsibility（职责块）
 * </p>
 * 
 * @author aaron
 * created 2010-9-28 下午06:29:20
 * modified [who date description]
 * check [who date description]
 */
public class DefaultOpenLoginViewRsp<T> extends AbstractResponsibility<T> {
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.main.process.rsp.Responsibility#execute(java.lang.Object)
     */
    @Override
    public boolean execute(T t) {
        LoginController controller = new LoginController(LoginView.class, LoginModel.class);
        ((StartObj)t).setLoginController(controller);
        ClientCoreContext.getWindowManager().openAsFrame(controller);
        controller.initData(t);
        JFrame frame = (JFrame)controller.getClientView().getWindow();
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosed(WindowEvent arg0) {
                System.exit(0);
            }
            
        });
        frame.pack();
        System.err.println("Open login frame end time = " + System.currentTimeMillis());
        return true;
    }

}
