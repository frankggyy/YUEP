/*
 * $Id: OpenMainFrameRsp.java, 2009-2-21 下午12:56:34 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.mainframe.processing.rsp;

import java.util.Locale;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.output.OutputManager;
import com.yuep.core.client.component.output.OutputManagerImpl;
import com.yuep.core.client.component.statusbar.view.DefaultStatusBarView;
import com.yuep.core.client.component.statusbar.view.StatusBarManager;
import com.yuep.core.client.component.statusbar.view.StatusBarManagerImpl;
import com.yuep.core.client.main.login.model.LoginInfo;
import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.main.mainframe.MainFrame;

/**
 * <p>
 * Title: OpenMainFrameRsp
 * </p>
 * <p>
 * Description:打开客户端主界面的默认实现,参考start-process.xml
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:25:36
 * modified [who date description]
 * check [who date description]
 */
public class DefaultOpenMainFrameRsp<T> extends AbstractStartClientResponsibility<T> {

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.main.process.rsp.Responsibility#execute(java.lang.Object)
     */
    @Override
    public boolean execute(T t) {
        Locale.setDefault(ClientCoreContext.getLocale());
        StatusBarManager statusBarManager = new StatusBarManagerImpl();
        LoginInfo loginInfo = ((StartObj)t).getLoginInfo();
        ((DefaultStatusBarView)statusBarManager.getStatusBarView()).setLoginInfo(loginInfo);
        ClientCoreContext.setStatusBarManager(statusBarManager);
        OutputManager outputManager = new OutputManagerImpl();
        ClientCoreContext.setOutputManager(outputManager);
//        ClientCoreContext.getMessageDispatcher().addMessageListener(OutputMessage.class, new OutputMessageListener());
        MainFrame mainFrame = new MainFrame();
        mainFrame.constructUi();
        ClientCoreContext.getMenuBarView().initMenu();
        ClientCoreContext.getToolBarView().initToolBar();
        mainFrame.setVisible(true);
        statusBarManager.getStatusBarView().refreshStatus();
        ClientCoreContext.setMainFrame(mainFrame);
        setProgress(t, 100);
        return true;
    }

}
