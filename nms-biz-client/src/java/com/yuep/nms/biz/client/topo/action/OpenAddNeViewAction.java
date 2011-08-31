/*
 * $Id: OpenAddNeViewAction.java, 2011-4-19 ÏÂÎç03:46:31 aaron Exp $
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
import java.util.ArrayList;
import java.util.List;

import twaver.SubNetwork;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.DefaultPopupMenuAction;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.core.client.mvc.DefaultClientModel;
import com.yuep.nms.biz.client.topo.TopoClientModule;
import com.yuep.nms.biz.client.topo.view.AddNeView;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.sbimanager.module.constants.SbiManagerModuleConstants;
import com.yuep.nms.core.common.sbimanager.service.SbiManager;

/**
 * <p>
 * Title: OpenAddNeViewAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron
 * created 2011-4-19 ÏÂÎç03:46:31
 * modified [who date description]
 * check [who date description]
 */
public class OpenAddNeViewAction extends DefaultPopupMenuAction {

    private static final long serialVersionUID = 7586583424285972415L;

    /**
     * @param isMultiple
     * @param actionId
     * @param selectedObjects
     * @param paramObj
     * @param paramClazz
     */
    public OpenAddNeViewAction(Boolean isMultiple, String actionId, Object[] selectedObjects, Object paramObj,
            String paramClazz) {
        super(isMultiple, actionId, selectedObjects, paramObj, paramClazz);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        WindowManager windowManager = ClientCoreContext.getWindowManager();
        TopoClientModule module = ClientCoreContext.getModule(TopoClientModule.class);
        DefaultClientController<Object, AddNeView, DefaultClientModel<Object>> controller = module
                .getDefaultClientController(AddNeView.class);
        if (selectedObjects == null)
            return;
        Object object = selectedObjects[0];
        MoNaming parent = null;
        if(object instanceof SubNetwork){
            SubNetwork network = (SubNetwork) object;
            Object id = network.getID();
            parent = (MoNaming) id;
        }
        windowManager.openAsDialog(controller);
        SbiManager sbiManager = ClientCoreContext.getRemoteService(SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);
        List<SbiProperty> sbiList=new ArrayList<SbiProperty>();
        try{
        	 sbiList = sbiManager.getAllSbiProperties(parent);
        }catch(Exception ex){
        	 
        }
        List<Object> objectList=YuepObjectUtils.newArrayList(parent,sbiList);
        controller.initData(objectList);
    }

}
