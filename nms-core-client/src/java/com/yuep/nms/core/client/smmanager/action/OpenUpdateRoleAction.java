/*
 * $Id: OpenUpdateRoleAction.java, 2011-4-13 上午09:26:51 WangRui Exp $
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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.RoleManagerController;
import com.yuep.nms.core.client.smmanager.model.RoleManagerModel;
import com.yuep.nms.core.client.smmanager.model.UpdateRoleModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.RoleManagerView;
import com.yuep.nms.core.client.smmanager.view.UpdateRoleView;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smmanager.model.MoPermission;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: OpenUpdateRoleAction
 * </p>
 * <p>
 * Description:打开修改角色的界面
 * </p>
 * 
 * @author WangRui
 * created 2011-4-13 上午09:26:51
 * modified [who date description]
 * check [who date description]
 */
public class OpenUpdateRoleAction extends AbstractXAction {
    private static final long serialVersionUID = 2520586134045881041L;

    public OpenUpdateRoleAction(boolean isMultiple, String actionId) {
        super(isMultiple,actionId);
        String text = ClientCoreContext.getString("Role.button.update");
        int vk = XGuiUtils.getMnemonicKey('u');
        text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
        putValue(NAME, text);
        putValue(MNEMONIC_KEY, vk);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {
        //先从界面获取用户输入的数据
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        DefaultClientController updateController = module.getDefaultClientController(UpdateRoleView.class,
                UpdateRoleModel.class);
        RoleManagerController roleManagerController = module.getController(RoleManagerModel.class,
                RoleManagerView.class, RoleManagerController.class);
        roleManagerController.collectData();
        List roleList = roleManagerController.getDatas();
        Role role = (Role) roleList.get(0);
        String roleName = role.getRoleName();
        List<MoPermission> moPermList = new ArrayList<MoPermission>();
        // 获取mo节点
        MoCore moCore = ClientCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        List<Mo> mos = moCore.getMos(new MoFilter() {
            @Override
            public boolean accept(Mo mo) {
                return mo.getType().equals("ne") || mo.getType().equals("nm") || mo.getType().equals("domain") ;
            }
        });
        for (Mo mo : mos) {
            MoPermission moPerm = new MoPermission();
            moPerm.setMo(mo);
            MoPermGroup permGroup = smManagerService.getMoPerm(mo.getMoNaming(), roleName);
            if (null != permGroup)
                moPerm.setGroupName(permGroup.getGroupName());
            moPermList.add(moPerm);
        }
        updateController.initData(roleList);
        ((UpdateRoleView) updateController.getClientView()).initTreeData(moPermList);
        WindowManager windowManager = ClientCoreContext.getWindowManager();
        windowManager.openAsDialog(updateController);
    }

}
