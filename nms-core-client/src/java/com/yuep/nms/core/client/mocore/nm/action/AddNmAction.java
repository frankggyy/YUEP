/*
 * $Id: AddNmAction.java, 2011-4-19 下午02:50:16 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.mocore.nm.action;

import java.util.List;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.nms.core.client.mocore.nm.controller.AddNmController;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.momanager.module.constants.MoManagerModuleConstants;
import com.yuep.nms.core.common.momanager.service.MoManager;

/**
 * <p>
 * Title: AddNmAction
 * </p>
 * <p>
 * Description:增加下级网管
 * </p>
 * 
 * @author yangtao
 * created 2011-4-19 下午02:50:16
 * modified [who date description]
 * check [who date description]
 */
public class AddNmAction extends XAbstractAction {
    
    private static final long serialVersionUID = 5171027729435446005L;
    
    private AddNmController addNmController;
    
    public AddNmAction(String actionId,AddNmController addNmController) {
        super(actionId);
        this.addNmController=addNmController;
    }
    
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        MoManager moManager=(MoManager)ClientCoreContext.getRemoteService(MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        addNmController.collectData();
        List<ManagedNodeProperty> managedNodeProperties=addNmController.getDatas();
        ManagedNodeProperty managedNodeProperty=managedNodeProperties.get(0);
        Mo mo=moManager.createManagedNode(addNmController.getSelectedMo().getMoNaming(), managedNodeProperty, "nm");
        return new Object[]{mo};
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
    	addNmController.dispose();
    }

}
