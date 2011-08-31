/*
 * $Id: DeactiveUserAction.java, 2011-4-20 下午04:22:37 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.action;

import java.awt.event.KeyEvent;
import java.util.List;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smcore.def.SmCoreClientService;
import com.yuep.nms.core.client.smmanager.controller.UserListController;
import com.yuep.nms.core.client.smmanager.model.UserListModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.UserListView;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: DeactiveUserAction
 * </p>
 * <p>
 * Description:禁用用户
 * </p>
 * 
 * @author luwei
 * created 2011-4-20 下午04:22:37
 * modified [who date description]
 * check [who date description]
 */
public class DeactiveUserAction extends XAbstractAction {
    private static final long serialVersionUID = 1773054535880416367L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    public DeactiveUserAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        String text = ClientCoreContext.getString("Button.inactive");
        int vk = XGuiUtils.getMnemonicKey('I');
        text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
        putValue(NAME, text);
        putValue(MNEMONIC_KEY, vk);
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);
        UserListController userListController = module.getController(UserListModel.class, UserListView.class,
                UserListController.class);
        UserListView userListView = (UserListView) userListController.getClientView();
        List<User> userList = userListView.getSelectedData();
        SmCoreClientService smCoreClientService = ClientCoreContext.getLocalService("smCoreClientService",
                SmCoreClientService.class);
        for (User user : userList) {
            if (user.getUserName().equals(smCoreClientService.getCurrentUser())) {
                DialogUtils.showInfoDialog(userListView.getWindow(), "UserInfo.can'tInactiveSelf");
                continue;
            }

            smManagerService.deactiveUser(user.getUserName());
        }
        List<User> allUsers = smManagerService.getAllUsers();
        return allUsers.toArray(new User[allUsers.size()]);
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        if (null == objs)
            return;
        List<Object> newArrayList = YuepObjectUtils.newArrayList(objs);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        UserListController userListController = module.getController(UserListModel.class, UserListView.class,
                UserListController.class);
        userListController.getClientView().updateTable(newArrayList);
    }

}
