/*
 * $Id: UnlockClientOpenAction.java, 2011-3-30 下午04:04:58 sufeng Exp $
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
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.core.client.mvc.DefaultClientModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.UnlockClientView;

/**
 * <p>
 * Title: UnlockClientOpenAction
 * </p>
 * <p>
 * Description:锁定客户端open
 * </p>
 * 
 * @author sufeng
 * created 2011-4-27 下午04:04:58
 * modified [who date description]
 * check [who date description]
 */
public class UnlockClientOpenAction extends XAbstractAction {

    private static final long serialVersionUID = -1228933227759131153L;

    public UnlockClientOpenAction(String actionId) {
        super(actionId);
    }

    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        DefaultClientController<Object, UnlockClientView, DefaultClientModel<Object>> controller2 = module.getDefaultClientController(UnlockClientView.class);
        WindowManager windowManager = ClientCoreContext.getWindowManager();
        windowManager.openAsDialog(controller2);
        controller2.initData();
        return new Object[]{};
    }

    @Override
    protected void updateUi(Object... objs) {
    }
    
}
