/*
 * $Id: SaveUserAction.java, 2011-4-19 下午02:12:53 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.action;

import java.util.ArrayList;
import java.util.List;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.client.smmanager.controller.UserInfoController;
import com.yuep.nms.core.client.smmanager.controller.UserListController;
import com.yuep.nms.core.client.smmanager.controller.UserMaintenanceController;
import com.yuep.nms.core.client.smmanager.model.UserInfoModel;
import com.yuep.nms.core.client.smmanager.model.UserListModel;
import com.yuep.nms.core.client.smmanager.model.UserMaintenanceModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.UserInfoView;
import com.yuep.nms.core.client.smmanager.view.UserListView;
import com.yuep.nms.core.client.smmanager.view.UserMaintenanceView;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;
/**
 * <p>
 * Title: SaveUserAction
 * </p>
 * <p>
 * Description:保存用户
 * </p>
 * 
 * @author luwei
 * created 2011-4-19 下午02:12:53
 * modified [who date description]
 * check [who date description]
 */
public class SaveUserAction extends XAbstractAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = 51336053179077109L;
    private Boolean isModify = false;
    private Boolean isView = false;

    public SaveUserAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        UserMaintenanceController userManagerController = module.getController(UserMaintenanceModel.class,
                UserMaintenanceView.class, UserMaintenanceController.class);
        UserInfoController userController = module.getController(UserInfoModel.class, UserInfoView.class,
                UserInfoController.class);
        UserInfoView userInfoView = (UserInfoView) userController.getClientView();
        if (userInfoView.checkValid() == false) {
            return null;
        }
        userManagerController.collectData();
        List<Object> objects = userManagerController.getDatas();
        List<Object> userInfoObjects = (List<Object>) objects.get(0);
        List<Object> scopeObjects = (List<Object>) objects.get(1);
        List<MoNaming> namings = new ArrayList<MoNaming>();
        for (Object object : scopeObjects) {
            namings.add((MoNaming) object);
        }
        User user = (User) userInfoObjects.get(0);
        user.setMgmtScope(namings);
        List<User> allUsers = smManagerService.getAllUsers();
        isModify = (Boolean) userInfoObjects.get(2);
        isView = (Boolean) userInfoObjects.get(1);
        if (isView != null && isView == true) {
            userInfoView.getWindow().dispose();
            return null;

        }
        if (isModify != null && isModify == false) {
            for (User dbUser : allUsers) {
                if (user.getUserName().equalsIgnoreCase(dbUser.getUserName())) {
                    DialogUtils.showInfoDialog(userController.getClientView().getWindow(), "User.alreadyExist");
                    return null;
                }
            }
            smManagerService.addUser(user);
        } else {
            smManagerService.updateUser(user);
        }
        List<User> newUsers = smManagerService.getAllUsers();
        return newUsers.toArray(new User[newUsers.size()]);

    }

    @Override
    protected void updateUi(Object... objs) {
        if (null == objs) {
            return;
        }
        List<Object> newArrayList = YuepObjectUtils.newArrayList(objs);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        UserMaintenanceController userManagerController = module.getController(UserMaintenanceModel.class,
                UserMaintenanceView.class, UserMaintenanceController.class);
        UserListController userListController = module.getController(UserListModel.class, UserListView.class,
                UserListController.class);
        userListController.getClientView().updateTable(newArrayList);
        userManagerController.getClientView().getWindow().dispose();

    }

}
