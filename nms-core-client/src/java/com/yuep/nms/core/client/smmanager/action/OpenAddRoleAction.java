/*
 * $Id: OpenRoleAddAction.java, 2011-4-2 下午04:44:12 WangRui Exp $
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
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.nms.core.client.smmanager.controller.AddRoleController;
import com.yuep.nms.core.client.smmanager.model.AddRoleModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.AddRoleView;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.common.smmanager.model.MoPermission;

/**
 * <p>
 * Title: OpenRoleAddAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-2 下午04:44:12
 * modified [who date description]
 * check [who date description]
 */
public class OpenAddRoleAction extends AbstractXAction {
    private static final long serialVersionUID = 2739597820562825343L;

    public OpenAddRoleAction(String actionId) {
        super(actionId);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        AddRoleController controller = module.getController(AddRoleModel.class, AddRoleView.class,
                AddRoleController.class);
        List<MoPermission> moPermList = new ArrayList<MoPermission>();
        // 获取mo节点
        MoCore moCore = ClientCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        List<Mo> mos = moCore.getMos(new MoFilter() {
            @Override
            public boolean accept(Mo mo) {
                // 此处暂时硬编码
                if (mo.getType().equals("ne") || mo.getType().equals("nm")|| mo.getType().equals("domain")) {
                    return true;
                }
                return false;
            }
        });
        for (Mo mo : mos) {
            MoPermission moPerm = new MoPermission();
            moPerm.setMo(mo);
            moPerm.setGroupName("all");
            moPermList.add(moPerm);
        }

        ((AddRoleView) controller.getClientView()).initTreeData(moPermList);
        WindowManager windowManager = ClientCoreContext.getWindowManager();
        windowManager.openAsFrame(controller);
    }
}
