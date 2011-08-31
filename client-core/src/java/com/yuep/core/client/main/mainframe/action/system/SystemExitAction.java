/*
 * $Id: SystemExitAction.java, 2010-4-27 下午02:03:21 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.mainframe.action.system;

import javax.swing.SwingUtilities;

import com.yuep.core.bootstrap.impl.ModuleContextImpl;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.GuiConstants;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.session.def.SessionMessage;

/**
 * <p>
 * Title: SystemExitAction
 * </p>
 * <p>
 * Description:系统退出的action
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午02:03:21
 * modified [who date description]
 * check [who date description]
 */
public class SystemExitAction extends XAbstractAction {
    private static final long serialVersionUID = 496026480131451967L;

    /**
     * @param actionId
     */
    public SystemExitAction(String actionId) {
        super(actionId);
    }

    /**
     * 窗口是否已经打开了
     */
    private static boolean windowHasOpened=false;
    
    private MessageReceiver messageReceiver;
    
    public void setMessageReceiver(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }
    
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        if(windowHasOpened)
            return null;
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String title = "common.mainframe.close.title";
                String message = "common.mainframe.close.message";
                int yesOrNo = DialogUtils.showYesNoOptionDialog(ClientCoreContext.getMainFrame(), message, title);
                windowHasOpened=true;
                if (yesOrNo == GuiConstants.RetValues.YES_OPTION) {
                    // 清理工作
                    try {
                        ClientCoreContext.disconnect();
                    } catch (Exception ex) {
                        DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(), ex);
                    }
                    
                    // 退出系统,会被容器捕获到，逐个 stop各个module
                    ModuleContextImpl.notifyExit();
                }else{
                    // 取消了，就再继续弹出提示确认窗
                    windowHasOpened=false;
                    // 取消后，还必须继续接收消息，需要重新注册
                    if(SystemExitAction.this.messageReceiver!=null){
                        ClientCoreContext.subscribeLocal(SessionMessage.NAME, SystemExitAction.this.messageReceiver);
                    }
                }
            }
        });
        return null;
    }

    /**
     * @see com.ycignp.veapo.client.framework.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
    }
}
