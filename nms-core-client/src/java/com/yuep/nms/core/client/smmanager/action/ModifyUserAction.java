/*
 * $Id: ModifyUserAction.java, 2011-4-21 上午11:48:24 luwei Exp $
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
import java.util.List;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.UserListController;
import com.yuep.nms.core.client.smmanager.controller.UserMaintenanceController;
import com.yuep.nms.core.client.smmanager.model.UserListModel;
import com.yuep.nms.core.client.smmanager.model.UserMaintenanceModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.UserListView;
import com.yuep.nms.core.client.smmanager.view.UserMaintenanceView;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: ModifyUserAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author luwei
 * created 2011-4-21 上午11:48:24
 * modified [who date description]
 * check [who date description]
 */
public class ModifyUserAction extends XAbstractAction {

    private static final long serialVersionUID = -3003395119144927432L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    public ModifyUserAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        String text = ClientCoreContext.getString("Button.modify");
        int vk = XGuiUtils.getMnemonicKey('m');
        text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
        putValue(NAME, text);
        putValue(MNEMONIC_KEY, vk);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);
        UserMaintenanceController userManagerController = module.getController(UserMaintenanceModel.class,
                UserMaintenanceView.class, UserMaintenanceController.class);
        UserListController userListController = module.getController(UserListModel.class, UserListView.class,
                UserListController.class);
        UserListView userListView = (UserListView) userListController.getClientView();
        List<User> users = userListView.getSelectedData();
        Object[] objects = new Object[3];
        objects[0] = smManagerService.getUser(users.get(0).getUserName());
        // objects[1]存放isView的标志，为true时，表示为查看；
        objects[1] = false;
        // objects[2]存放isModify的标示，为true时，表示修改；
        objects[2] = true;
        userManagerController.initView();
        userManagerController.initData(objects);

        ClientCoreContext.getWindowManager().openAsFrame(userManagerController);
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        // TODO Auto-generated method stub
        return new Object[] {};
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        // TODO Auto-generated method stub

    }

}
