/*
 * $Id: AddPermGroupAction.java, 2011-4-20 上午11:20:55 WangRui Exp $
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

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractButtonAction;
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.nms.core.client.smmanager.controller.AddRoleController;
import com.yuep.nms.core.client.smmanager.controller.RoleManagerController;
import com.yuep.nms.core.client.smmanager.model.AddRoleModel;
import com.yuep.nms.core.client.smmanager.model.ModifyPermissionBundleModel;
import com.yuep.nms.core.client.smmanager.model.RoleManagerModel;
import com.yuep.nms.core.client.smmanager.model.UpdateRoleModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.AddRoleView;
import com.yuep.nms.core.client.smmanager.view.PermGroupView;
import com.yuep.nms.core.client.smmanager.view.RoleManagerView;
import com.yuep.nms.core.client.smmanager.view.UpdateRoleView;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;

/**
 * <p>
 * Title: AddPermGroupAction
 * </p>
 * <p>
 * Description: 添加权限组
 * </p>
 * 
 * @author WangRui
 * created 2011-4-20 上午11:20:55
 * modified [who date description]
 * check [who date description]
 */
public class AddPermGroupAction extends AbstractButtonAction {
    private static final long serialVersionUID = -2348037140683708890L;
    private Boolean isCreat;

    public AddPermGroupAction(String actionId) {
        super(actionId);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Object[] commitData(Object... objs) {
        // TODO Auto-generated method stub
        return new Object[]{};
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void updateUi(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        DefaultClientController<PermissionGroup, PermGroupView, ModifyPermissionBundleModel> PermController = module
                .getDefaultClientController(PermGroupView.class, ModifyPermissionBundleModel.class);
        List<String> groupList = ((PermGroupView) PermController.getClientView()).getDatas();
        if (isCreat) {
            AddRoleController addRoleController = module.getController(AddRoleModel.class, AddRoleView.class,
                    AddRoleController.class);
            List<Object> list = new ArrayList<Object>();
            list.addAll(groupList);
            addRoleController.modifyDatas(list);

        } else {
            RoleManagerController roleManagerController = module.getController(RoleManagerModel.class,
                    RoleManagerView.class, RoleManagerController.class);
            roleManagerController.collectData();
            // 此处得到上上个页面选中的数据,由于不涉及到数据(rolename,description的更新),没有用到这个list
            DefaultClientController updateController = module.getDefaultClientController(UpdateRoleView.class,
                    UpdateRoleModel.class);
            updateController.modifyDatas(groupList);
        }

        PermController.getClientView().getWindow().dispose();

    }

    public void setIsCreat(Boolean isCreat) {
        this.isCreat = isCreat;
    }

}
