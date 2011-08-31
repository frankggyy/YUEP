/*
 * $Id: AddPermissionBundleAction.java, 2011-5-10 下午05:13:47 WangRui Exp $
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
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.AddPermissionBundleController;
import com.yuep.nms.core.client.smmanager.model.ModifyPermissionBundleModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.AddPermissionBundleView;

/**
 * <p>
 * Title: AddPermissionBundleAction
 * </p>
 * <p>
 * Description:打开添加权限集的action
 * </p>
 * 
 * @author WangRui
 * created 2011-5-10 下午05:13:47
 * modified [who date description]
 * check [who date description]
 */
public class AddPermissionBundleAction extends AbstractXAction {
    private static final long serialVersionUID = 5593895520929371597L;
 
    public AddPermissionBundleAction(boolean isMultiple, String actionId) {
        super(isMultiple, actionId);
        String text = ClientCoreContext.getString("PermissionGroup.button.update");
        int vk = XGuiUtils.getMnemonicKey('u');
        text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
        putValue(NAME, text);
        putValue(MNEMONIC_KEY, vk);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        AddPermissionBundleController addController = module.getController(ModifyPermissionBundleModel.class, AddPermissionBundleView.class, AddPermissionBundleController.class);
        WindowManager windowManager = ClientCoreContext.getWindowManager();
        windowManager.openAsDialog(addController); 
        addController.initData();
    }

}
