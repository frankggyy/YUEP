/*
 * $Id: TopoMainFrameRsp.java, 2011-3-14 ÏÂÎç03:46:09 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.topo;

import java.util.Locale;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.output.OutputManager;
import com.yuep.core.client.component.output.OutputManagerImpl;
import com.yuep.core.client.component.statusbar.view.DefaultStatusBarView;
import com.yuep.core.client.component.statusbar.view.StatusBarManager;
import com.yuep.core.client.component.statusbar.view.StatusBarManagerImpl;
import com.yuep.core.client.main.login.model.LoginInfo;
import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.main.mainframe.processing.rsp.DefaultOpenMainFrameRsp;
import com.yuep.nms.biz.client.topo.view.TopoMainFrame;

/**
 * <p>
 * Title: TopoMainFrameRsp
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2011-3-14 ÏÂÎç03:46:09
 * modified [who date description]
 * check [who date description]
 */
public class TopoMainFrameRsp<T> extends DefaultOpenMainFrameRsp<T> {

    @Override
    public boolean execute(T t) {
        Locale.setDefault(ClientCoreContext.getLocale());
        StatusBarManager statusBarManager = new StatusBarManagerImpl();
        LoginInfo loginInfo = ((StartObj)t).getLoginInfo();
        ((DefaultStatusBarView)statusBarManager.getStatusBarView()).setLoginInfo(loginInfo);
        ClientCoreContext.setStatusBarManager(statusBarManager);
        OutputManager outputManager = new OutputManagerImpl();
        ClientCoreContext.setOutputManager(outputManager);
        TopoMainFrame mainFrame = new TopoMainFrame();
        mainFrame.constructUi();
        mainFrame.initData();
        mainFrame.initListener();
        ClientCoreContext.getMenuBarView().initMenu();
        ClientCoreContext.getToolBarView().initToolBar();
        mainFrame.setVisible(true);
        statusBarManager.getStatusBarView().refreshStatus();
        ClientCoreContext.setMainFrame(mainFrame);
        setProgress(t, 100);
        return true;
    }
    
}
