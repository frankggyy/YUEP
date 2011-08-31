/*
 * $Id: ApplyModifyPermissionBundleAction.java, 2011-4-15 ÉÏÎç11:04:04 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.core.client.smmanager.controller.ModifyPermissionBundleController;
import com.yuep.nms.core.client.smmanager.controller.PermManagerController;
import com.yuep.nms.core.client.smmanager.model.ModifyPermissionBundleModel;
import com.yuep.nms.core.client.smmanager.model.PermManagerModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.ModifyPermissionBundleView;
import com.yuep.nms.core.client.smmanager.view.PermManagerView;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: ApplyModifyPermissionBundleAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-15 ÉÏÎç11:04:04
 * modified [who date description]
 * check [who date description]
 */
public class ApplyModifyPermissionBundleAction extends XAbstractAction {
    private static final long serialVersionUID = 4245455805978387626L;

    @SuppressWarnings("unchecked")
    public ApplyModifyPermissionBundleAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        // TODO Auto-generated constructor stub
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        ModifyPermissionBundleController modifyController = module.getController(ModifyPermissionBundleModel.class, ModifyPermissionBundleView.class, ModifyPermissionBundleController.class);       
        modifyController.collectData();
        List<PermissionGroup> groupList = modifyController.getDatas();
        if(CollectionUtils.isEmpty(groupList))
            return null;
        PermissionGroup group = groupList.get(0);
        smManagerService.updatePermissionGroup(group);
        List<PermissionGroup> permList = smManagerService.getAllPermissionGroups();
        return permList.toArray(new PermissionGroup[permList.size()]);
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        if(null==objs){
            return;
        }
        List<Object> newList = YuepObjectUtils.newArrayList(objs);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        PermManagerController mainController = module.getController(PermManagerModel.class, PermManagerView.class, PermManagerController.class);
        ModifyPermissionBundleController modifyController = module.getController(ModifyPermissionBundleModel.class, ModifyPermissionBundleView.class, ModifyPermissionBundleController.class);
        mainController.getClientView().updatePortTable(newList);
        modifyController.getClientView().getWindow().dispose();
    }

}
