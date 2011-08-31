/*
 * $Id: DeleteRoleAction.java, 2011-4-12 下午05:07:52 WangRui Exp $
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.RoleManagerController;
import com.yuep.nms.core.client.smmanager.model.RoleManagerModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.RoleManagerView;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: DeleteRoleAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-12 下午05:07:52
 * modified [who date description]
 * check [who date description]
 */
public class DeleteRoleAction extends XAbstractAction {

    private static final long serialVersionUID = -9006528645481173759L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    @SuppressWarnings("unchecked")
    public DeleteRoleAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        String text = ClientCoreContext.getString("Role.button.del");
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
        RoleManagerController roleManagerController = module.getController(RoleManagerModel.class,
                RoleManagerView.class, RoleManagerController.class);
        roleManagerController.collectData();
        List<MoPermGroup> delPermList = new ArrayList<MoPermGroup>();
        List<String> delRoles = new ArrayList<String>();
        List<Role> roleList = roleManagerController.getDatas();
        List<User> userList = smManagerService.getAllUsers();
        Set<String> attachedRoles = new HashSet<String>();
        //如果此角色与用户有关联，则不能删除
        for(User user:userList){
            attachedRoles.addAll(user.getRoles());
        }
        for (Role role : roleList) {
            if(attachedRoles.contains(role.getRoleName())){
                DialogUtils.showInfoDialog(roleManagerController.getClientView().getWindow(), ClientCoreContext.getString("Role.delete.fail.userAttached"));
                return null;
            }
            List<MoNaming> moList = role.getMoList();
            if (null != moList) {
                for (MoNaming moName : moList) {
                    MoPermGroup moPermGroup = smManagerService.getMoPerm(moName, role.getRoleName());
                    delPermList.add(moPermGroup);
                }
            }
            delRoles.add(role.getRoleName());
        }
        smManagerService.deleteMoPerm(delPermList.toArray(new MoPermGroup[delPermList.size()]));
        smManagerService.deleteRole(delRoles.toArray(new String[delRoles.size()]));
        List<Role> allRoles = smManagerService.getAllRoles();
        return allRoles.toArray(new Role[allRoles.size()]);
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
        RoleManagerController roleManagerController = module.getController(RoleManagerModel.class,
                RoleManagerView.class, RoleManagerController.class);
        roleManagerController.getClientView().updateRoleTable(newArrayList);

    }

}
