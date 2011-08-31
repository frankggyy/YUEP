/*
 * $Id: SaveRoleAction.java, 2011-4-2 下午02:39:17 WangRui Exp $
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

import org.apache.commons.lang.StringUtils;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.client.smmanager.controller.AddRoleController;
import com.yuep.nms.core.client.smmanager.controller.RoleManagerController;
import com.yuep.nms.core.client.smmanager.model.AddRoleModel;
import com.yuep.nms.core.client.smmanager.model.RoleManagerModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.AddRoleView;
import com.yuep.nms.core.client.smmanager.view.RoleManagerView;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smmanager.model.MoPermission;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: SaveRoleAction
 * </p>
 * <p>
 * Description:保存角色
 * </p>
 * 
 * @author WangRui
 * created 2011-4-2 下午02:39:17
 * modified [who date description]
 * check [who date description]
 */
public class SaveRoleAction extends XAbstractAction {
    
    private static final long serialVersionUID = 2327059951199800403L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    @SuppressWarnings("unchecked")
    public SaveRoleAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        AddRoleController roleController = module.getController(AddRoleModel.class, AddRoleView.class,
                AddRoleController.class);
        AddRoleView clientView = (AddRoleView) roleController.getClientView();
        roleController.collectData();
        List<Object> selectedDatas = roleController.getDatas();
        Role newRole = (Role) selectedDatas.get(0);
        List<Role> allRoles = smManagerService.getAllRoles();
        for (Role dbRole : allRoles) {
            if (newRole.getRoleName().equalsIgnoreCase(dbRole.getRoleName())) {
                DialogUtils.showInfoDialog(roleController.getClientView().getWindow(), "Role.add.fail");
                return null;
            }
        }
        //设一个标志位,判断此添加的角色中的mo是否与权限集相关联,若没有任何mo与权限集关联,角色不予保存
        Boolean flag = false;
        List<MoPermission> moPermList = clientView.getAllMoPerms();
        List<MoPermGroup> permGroupToDb = new ArrayList<MoPermGroup>();
        if (null == moPermList) {
            DialogUtils.showInfoDialog(roleController.getClientView().getWindow(), "Role.add.fail.moperm");
            return null;
        }

        for (MoPermission moPerm : moPermList) {
            // 如果选中的mo节点中权限项不为空或空权限集,且至少有一项权限项与某个mo相关联，才会入库
            if (StringUtils.isNotEmpty(moPerm.getGroupName()) && !"".equals(moPerm.getGroupName())) {
                flag = true;
                MoPermGroup moPermGroup = new MoPermGroup();
                moPermGroup.setRoleName(newRole.getRoleName());
                moPermGroup.setGroupName(moPerm.getGroupName());
                moPermGroup.setMoName(moPerm.getMo().getMoNaming());
                permGroupToDb.add(moPermGroup);
                if (null == newRole.getMoList()) {
                    List<MoNaming> moList = new ArrayList<MoNaming>();
                    moList.add(moPerm.getMo().getMoNaming());
                    newRole.setMoList(moList);
                }
                if (!newRole.getMoList().contains(moPerm.getMo().getMoNaming()))
                    newRole.getMoList().add(moPerm.getMo().getMoNaming());
            }
        }
        if (!flag) {
            DialogUtils.showInfoDialog(roleController.getClientView().getWindow(), "Role.add.fail.moperm");
            return null;
        }
        
        //先保存role,再保存此role对应的权限集
        smManagerService.addRole(newRole);
        smManagerService.addMoPerm(permGroupToDb.toArray(new MoPermGroup[permGroupToDb.size()]));        
        allRoles.add(newRole);
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
        AddRoleController addRoleController = module.getController(AddRoleModel.class, AddRoleView.class,
                AddRoleController.class);
        RoleManagerController roleManagerController = module.getController(RoleManagerModel.class,
                RoleManagerView.class, RoleManagerController.class);
        roleManagerController.getClientView().updateRoleTable(newArrayList);
        addRoleController.getClientView().getWindow().dispose();
    }

}
