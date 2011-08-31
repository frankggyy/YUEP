/*
 * $Id: DeleteUserAction.java, 2011-4-20 下午12:52:50 luwei Exp $
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

import javax.swing.JOptionPane;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.UserListController;
import com.yuep.nms.core.client.smmanager.model.UserListModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.UserListView;
import com.yuep.nms.core.common.smcore.exception.SmException;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: DeleteUserAction
 * </p>
 * <p>
 * Description:删除用户
 * </p>
 * 
 * @author luwei
 * created 2011-4-20 下午12:52:50
 * modified [who date description]
 * check [who date description]
 */
public class DeleteUserAction extends XAbstractAction {

    private static final long serialVersionUID = 1152370137508156540L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    public DeleteUserAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        String text = ClientCoreContext.getString("Button.delete");
        int vk = XGuiUtils.getMnemonicKey('d');
        text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
        putValue(NAME, text);
        putValue(MNEMONIC_KEY, vk);
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        int optionCode = DialogUtils.showConfirmDialog(null, "smmanager.confirm.continue", "smmanager.confirm.title",
                JOptionPane.OK_CANCEL_OPTION);
        if (JOptionPane.OK_OPTION != optionCode)
            return null;
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);
        UserListController userListController = module.getController(UserListModel.class, UserListView.class,
                UserListController.class);
        UserListView userListView = (UserListView) userListController.getClientView();
        List<User> users = userListView.getSelectedData();
        try {
            for (User user : users) {
                if (user.getUserName().equals("admin")) {
                    DialogUtils.showInfoDialog(userListView.getWindow(), "UserInfo.superUserCan'tDelete");
                    continue;
                }
                smManagerService.deleteUser(user);
            }
        } catch (Exception e) {
            if (e instanceof SmException) {
                if (((SmException) e).getErrorCode() == SmException.USER_IN_SESSION) {
                    DialogUtils.showErrorExpandDialog(userListView.getWindow(), e);
                }
            }
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
