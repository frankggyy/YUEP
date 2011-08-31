/*
 * $Id: DeletePermissionBundleAction.java, 2011-4-15 ÏÂÎç04:14:20 WangRui Exp $
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
import com.yuep.nms.core.client.smmanager.controller.PermManagerController;
import com.yuep.nms.core.client.smmanager.model.PermManagerModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.PermManagerView;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: DeletePermissionBundleAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-15 ÏÂÎç04:14:20
 * modified [who date description]
 * check [who date description]
 */
public class DeletePermissionBundleAction extends XAbstractAction {

    private static final long serialVersionUID = -2517768461796287464L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    @SuppressWarnings("unchecked")
    public DeletePermissionBundleAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        String text = ClientCoreContext.getString("PermissionGroup.button.del");
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
        int optionCode = DialogUtils.showConfirmDialog(null,"smmanager.confirm.continue","smmanager.confirm.title",JOptionPane.OK_CANCEL_OPTION);
        if(JOptionPane.OK_OPTION!=optionCode)
            return null;
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        PermManagerController permController = module.getController(PermManagerModel.class, PermManagerView.class, PermManagerController.class);
        permController.collectData();
        List<PermissionGroup> permList = permController.getDatas();
        Boolean flag=false;
        for(PermissionGroup group : permList){
            flag=smManagerService.deletePermissionGroup(group.getGroupName());
            if(!flag){
                DialogUtils.showInfoDialog(permController.getClientView().getWindow(), ClientCoreContext.getString("PermissionGroup.delete.fail"));
                break;
            }
        }
        List<PermissionGroup> newPermList = smManagerService.getAllPermissionGroups();
        return newPermList.toArray(new PermissionGroup[newPermList.size()]);
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        if(null==objs)
            return;
        List<Object> newArrayList = YuepObjectUtils.newArrayList(objs);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        PermManagerController permController = module.getController(PermManagerModel.class, PermManagerView.class, PermManagerController.class);
        permController.getClientView().updatePortTable(newArrayList);
    }

}
