/*
 * $Id: ModifySelfPasswordOpenAction.java, 2011-3-30 下午04:04:58 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.action;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.nms.core.client.smmanager.controller.ModifySelfPasswordController;
import com.yuep.nms.core.client.smmanager.model.ModifySelfPasswordModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.ModifySelfPasswordView;

/**
 * <p>
 * Title: ModifySelfPasswordOpenAction
 * </p>
 * <p>
 * Description:修改自己的密码
 * </p>
 * 
 * @author sufeng
 * created 2011-4-27 下午04:04:58
 * modified [who date description]
 * check [who date description]
 */
public class ModifySelfPasswordOpenAction extends XAbstractAction {

    private static final long serialVersionUID = 8542073795021718700L;

    public ModifySelfPasswordOpenAction(String actionId) {
        super(actionId);
    }

    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        ModifySelfPasswordController modifySelfPasswordController = module.getController(ModifySelfPasswordModel.class,ModifySelfPasswordView.class, ModifySelfPasswordController.class);
        WindowManager windowManager = ClientCoreContext.getWindowManager();
        windowManager.openAsFrame(modifySelfPasswordController);
        return new Object[]{};
    }

    @Override
    protected void updateUi(Object... objs) {
        
    }
    
}
