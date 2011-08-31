/*
 * $Id: UpdateRoleAction.java, 2011-4-13 ����09:14:24 WangRui Exp $
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
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.nms.core.client.smmanager.controller.RoleManagerController;
import com.yuep.nms.core.client.smmanager.model.RoleManagerModel;
import com.yuep.nms.core.client.smmanager.model.UpdateRoleModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.RoleManagerView;
import com.yuep.nms.core.client.smmanager.view.UpdateRoleView;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smmanager.model.MoPermission;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: UpdateRoleAction
 * </p>
 * <p>
 * Description:���½�ɫ
 * </p>
 * 
 * @author WangRui
 * created 2011-4-13 ����09:14:24
 * modified [who date description]
 * check [who date description]
 */
public class UpdateRoleAction extends XAbstractAction {
    private static final long serialVersionUID = 7102680796591935238L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    @SuppressWarnings("unchecked")
    public UpdateRoleAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        // TODO Auto-generated constructor stub
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        DefaultClientController updateController = module.getDefaultClientController(UpdateRoleView.class,
                UpdateRoleModel.class);
        updateController.collectData();
        List<Role> roleList = updateController.getDatas();
        Role selectData = roleList.get(0);
        // ����ҳ���д�������roleName��db�в����role
        Role role = smManagerService.getRoleByName(selectData.getRoleName());
        if (null == role) {
            role = new Role();
            role.setRoleName(selectData.getRoleName());
        }
        role.setDescription(selectData.getDescription());
        UpdateRoleView clientView = (UpdateRoleView) updateController.getClientView();
        List<MoPermission> moPermList = clientView.getAllMoPerms();
        List<MoPermGroup> updateList = new ArrayList<MoPermGroup>();// ����޸Ĺ��Ľ�ɫ��Ӧmo��Ȩ�޼���list,����forѭ����һ���Ը���
        List<MoPermGroup> addList = new ArrayList<MoPermGroup>();// �����ӵĽ�ɫ��Ӧmo��Ȩ�޼���list,����forѭ����һ���Ը���
        List<MoPermGroup> delList = new ArrayList<MoPermGroup>();// �����Ҫɾ����Ȩ�޼���list
        // ���ѡ���������еĶ��mo�ڵ�
        if (null != moPermList) {
            for (MoPermission moPerm : moPermList) {
                // ���ѡ�е�mo�ڵ���Ȩ���Ϊ�ջ��Ȩ�޼���������޸���Ȩ���������˴���Ȩ����
                if (StringUtils.isNotEmpty(moPerm.getGroupName()) && !"".equals(moPerm.getGroupName())) {
                    if (null == role.getMoList()) {
                        List<MoNaming> moList = new ArrayList<MoNaming>();
                        moList.add(moPerm.getMo().getMoNaming());
                        role.setMoList(moList);
                    }
                    if (!role.getMoList().contains(moPerm.getMo().getMoNaming()))
                        role.getMoList().add(moPerm.getMo().getMoNaming());
                    // ��db�в�,����д���mo��rulk��Ӧ��Ȩ�������޸Ĵ�Ȩ���������Ӵ���Ȩ����
                    MoPermGroup moPermGroup = null;
                    moPermGroup = smManagerService.getMoPerm(moPerm.getMo().getMoNaming(), role.getRoleName());
                    if (null != moPermGroup) {
                        // ���db�д��ڴ˽�ɫ��Ӧmo��Ȩ�޼�,��Ȩ�޼�û�ı�,�򲻸Ķ����ݿ��еĴ���
                        if (moPermGroup.getGroupName().equals(moPerm.getGroupName()))
                            continue;
                        moPermGroup.setGroupName(moPerm.getGroupName());
                        moPermGroup.setMoName(moPerm.getMo().getMoNaming());
                        moPermGroup.setRoleName(role.getRoleName());
                        updateList.add(moPermGroup);
                    } else {
                        moPermGroup = new MoPermGroup();
                        moPermGroup.setGroupName(moPerm.getGroupName());
                        moPermGroup.setMoName(moPerm.getMo().getMoNaming());
                        moPermGroup.setRoleName(role.getRoleName());
                        addList.add(moPermGroup);
                    }
                } else {
                    // ���role��Ӧ��ĳ��mo��Ȩ����Ϊ��,���п���û���κ�Ȩ�����ԭ��db�������ڰ�����Ϊ��
                    //��db�в��Ӧ����,���������Ҫ����ɾ��
                    MoPermGroup moPermGroup = smManagerService.getMoPerm(moPerm.getMo().getMoNaming(), role
                            .getRoleName());
                    if (null != moPermGroup) {
                        delList.add(moPermGroup);
                    }
                }
            }
        }
        if (updateList.size() > 0)
            smManagerService.updateMoPerm(role.getRoleName(), updateList.toArray(new MoPermGroup[updateList.size()]));
        if (addList.size() > 0)
            smManagerService.addMoPerm(addList.toArray(new MoPermGroup[addList.size()]));
        if (delList.size() > 0)
            smManagerService.deleteMoPerm(delList.toArray(new MoPermGroup[delList.size()]));
        smManagerService.updateRole(role);
        List<Role> allRoles = smManagerService.getAllRoles();
        return allRoles.toArray(new Role[allRoles.size()]);
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void updateUi(Object... objs) {
        List<Object> newArrayList = YuepObjectUtils.newArrayList(objs);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        DefaultClientController updateController = module.getDefaultClientController(UpdateRoleView.class,
                UpdateRoleModel.class);
        RoleManagerController roleManagerController = module.getController(RoleManagerModel.class,
                RoleManagerView.class, RoleManagerController.class);
        roleManagerController.getClientView().updateRoleTable(newArrayList);
        updateController.getClientView().getWindow().dispose();
    }

}
