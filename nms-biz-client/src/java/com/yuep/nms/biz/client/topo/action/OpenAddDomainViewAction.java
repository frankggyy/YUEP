/*
 * $Id: OpenAddDomainViewAction.java, 2011-4-19 ÏÂÎç03:46:58 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.topo.action;

import java.awt.event.ActionEvent;

import twaver.SubNetwork;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.DefaultPopupMenuAction;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.core.client.mvc.DefaultClientModel;
import com.yuep.nms.biz.client.topo.TopoClientModule;
import com.yuep.nms.biz.client.topo.view.AddDomainOrGroupView;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: OpenAddDomainViewAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron
 * created 2011-4-19 ÏÂÎç03:46:58
 * modified [who date description]
 * check [who date description]
 */
public class OpenAddDomainViewAction extends DefaultPopupMenuAction{

    private static final long serialVersionUID = 7586583424285972415L;

    /**
     * @param isMultiple
     * @param actionId
     * @param selectedObjects
     * @param paramObj
     * @param paramClazz
     */
    public OpenAddDomainViewAction(Boolean isMultiple, String actionId, Object[] selectedObjects, Object paramObj, String paramClazz) {
        super(isMultiple, actionId, selectedObjects, paramObj, paramClazz);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        WindowManager windowManager = ClientCoreContext.getWindowManager();
        TopoClientModule module = ClientCoreContext.getModule(TopoClientModule.class);
        DefaultClientController<Object, AddDomainOrGroupView, DefaultClientModel<Object>> controller = module.getDefaultClientController(AddDomainOrGroupView.class);
        if(selectedObjects == null)
            return;
        Object object = selectedObjects[0];
        MoNaming parent = null;
        if(object instanceof SubNetwork){
            SubNetwork network = (SubNetwork) object;
            Object id = network.getID();
            parent = (MoNaming) id;
        }
        windowManager.openAsDialog(controller);
        controller.initData(parent);
    }

}
