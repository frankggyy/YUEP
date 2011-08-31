/*
 * $Id: ApplyAddPermissionBundleAction.java, 2011-5-10 下午05:42:12 WangRui Exp $
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

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.client.smmanager.controller.AddPermissionBundleController;
import com.yuep.nms.core.client.smmanager.controller.PermManagerController;
import com.yuep.nms.core.client.smmanager.model.ModifyPermissionBundleModel;
import com.yuep.nms.core.client.smmanager.model.PermManagerModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.AddPermissionBundleView;
import com.yuep.nms.core.client.smmanager.view.PermManagerView;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: ApplyAddPermissionBundleAction
 * </p>
 * <p>
 * Description:对添加的权限集进行保存的action
 * </p>
 * 
 * @author WangRui
 * created 2011-5-10 下午05:42:12
 * modified [who date description]
 * check [who date description]
 */
public class ApplyAddPermissionBundleAction extends XAbstractAction{
    private static final long serialVersionUID = -8824558998440360020L;
    
    @SuppressWarnings("unchecked")
    public ApplyAddPermissionBundleAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
    }
    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        AddPermissionBundleController addController = module.getController(ModifyPermissionBundleModel.class, AddPermissionBundleView.class, AddPermissionBundleController.class);       
        addController.collectData();
        List<PermissionGroup> groupList = addController.getDatas();
        if(CollectionUtils.isEmpty(groupList))
            return null;
        PermissionGroup group = groupList.get(0);
        List<PermissionGroup> permListInDb = smManagerService.getAllPermissionGroups();
        List<String> existedGroups = new ArrayList<String>();
        for(PermissionGroup pg : permListInDb){
            existedGroups.add(pg.getGroupName());
        }
        if(existedGroups.contains(group.getGroupName())){
            DialogUtils.showInfoDialog(addController.getClientView().getWindow(), "PermissionGroup.save.existed");
            return null;
        }
        smManagerService.updatePermissionGroup(group);
        List<PermissionGroup> permList = smManagerService.getAllPermissionGroups();
        return permList.toArray(new PermissionGroup[permList.size()]);
    }

    @Override
    protected void updateUi(Object... objs) {
        if(null==objs){
            return;
        }
        List<Object> newList = YuepObjectUtils.newArrayList(objs);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        PermManagerController mainController = module.getController(PermManagerModel.class, PermManagerView.class, PermManagerController.class);
        AddPermissionBundleController addController = module.getController(ModifyPermissionBundleModel.class, AddPermissionBundleView.class, AddPermissionBundleController.class);
        mainController.getClientView().updatePortTable(newList);
        addController.getClientView().getWindow().dispose();   
        
    }
    

}
