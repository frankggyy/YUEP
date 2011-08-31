/*
 * $Id: OpenRoleManagerAction.java, 2011-5-5 ÏÂÎç03:00:44 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.action;

import twaver.Element;
import twaver.Node;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractMainMenuAction;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.nms.core.client.smmanager.controller.SmManagerController;
import com.yuep.nms.core.client.smmanager.model.SmManagerModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.SmManagerView;

/**
 * <p>
 * Title: OpenRoleManagerAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron
 * created 2011-5-5 ÏÂÎç03:00:44
 * modified [who date description]
 * check [who date description]
 */
public class OpenRoleManagerAction extends AbstractMainMenuAction {

    private static final long serialVersionUID = 8433253520391591707L;

    public OpenRoleManagerAction(String actionId) {
        super(actionId);
    }

    @Override
    protected Object[] commitData(Object... objs) {
        setWaiting(false);
        return new Object[]{};
    }

    @Override
    protected void updateUi(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        SmManagerController smManagerController = module.getController(SmManagerModel.class,SmManagerView.class, SmManagerController.class);
        WindowManager windowManager = ClientCoreContext.getWindowManager();
        windowManager.openAsFrame(smManagerController);
        smManagerController.initData();
        Element node = smManagerController.getClientView().getNodeById("Role.manager");
        smManagerController.selectedNode((Node) node);
    }

}
