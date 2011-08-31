/*
 * $Id: OpenUserMaintenanceAction.java, 2011-4-25 下午12:53:20 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.UserMaintenanceController;
import com.yuep.nms.core.client.smmanager.model.UserMaintenanceModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.UserMaintenanceView;

/**
 * <p>
 * Title: OpenUserMaintenanceAction
 * </p>
 * <p>
 * Description:打开用户维护信息的界面
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:53:20
 * modified [who date description]
 * check [who date description]
 */
public class OpenUserMaintenanceAction extends AbstractXAction {

    private static final long serialVersionUID = 4451084331953373795L;

    /**
     * @param isMultiple
     * @param actionId
     */
    public OpenUserMaintenanceAction(String actionId) {
        super(actionId);
        String text = ClientCoreContext.getString("Button.add");
        int vk = XGuiUtils.getMnemonicKey('a');
        text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
        putValue(NAME, text);
        putValue(MNEMONIC_KEY, vk);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        UserMaintenanceController userManagerController = module.getController(UserMaintenanceModel.class,
                UserMaintenanceView.class, UserMaintenanceController.class);
        userManagerController.initView("");
        userManagerController.initData();
        ClientCoreContext.getWindowManager().openAsFrame(userManagerController);
    }

}
