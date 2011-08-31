/*
 * $Id: SaveSelectRoleAction.java, 2011-4-19 下午02:12:53 luwei Exp $
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
import java.util.ArrayList;
import java.util.List;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.core.client.smmanager.controller.SelectRoleController;
import com.yuep.nms.core.client.smmanager.controller.UserInfoController;
import com.yuep.nms.core.client.smmanager.model.SelectRoleModel;
import com.yuep.nms.core.client.smmanager.model.UserInfoModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.SelectRoleView;
import com.yuep.nms.core.client.smmanager.view.UserInfoView;
import com.yuep.nms.core.common.smcore.model.Role;

/**
 * <p>
 * Title: SaveSelectRoleAction
 * </p>
 * <p>
 * Description:保存为用户分配的角色
 * </p>
 * 
 * @author luwei
 * created 2011-4-19 下午02:12:53
 * modified [who date description]
 * check [who date description]
 */
public class SaveSelectRoleAction extends XAbstractAction {
    private static final long serialVersionUID = -2868667900102680725L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    public SaveSelectRoleAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        // TODO Auto-generated constructor stub
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        SelectRoleController selectRoleController = module.getController(SelectRoleModel.class, SelectRoleView.class,
                SelectRoleController.class);
        UserInfoController userController = module.getController(UserInfoModel.class, UserInfoView.class,
                UserInfoController.class);
        selectRoleController.collectData();
        List<Role> roles = selectRoleController.getDatas();
        UserInfoView userInfoView = (UserInfoView) userController.getClientView();
        List<Role> allRoles = userInfoView.getAllRoles();
        List<Role> tempRoles = new ArrayList<Role>();
        for (Role selectedRole : roles) {
            for (Role existRole : allRoles) {
                if (selectedRole.getRoleName().equals(existRole.getRoleName())) {
                    tempRoles.add(selectedRole);
                    continue;
                }
            }

        }
        roles.removeAll(tempRoles);
        allRoles.addAll(roles);
        userInfoView.updateUserRoleTable(allRoles);
        selectRoleController.getClientView().getWindow().dispose();
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        // TODO Auto-generated method stub
        return new Object[]{};
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        // TODO Auto-generated method stub

    }

}
