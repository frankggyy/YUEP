/*
 * $Id: OpenSbiManagerViewAction.java, 2011-4-20 上午09:41:57 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.sbimanager.action;

import java.awt.event.ActionEvent;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.DefaultPopupMenuAction;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.nms.core.client.sbimanager.controller.SbiManagerController;
import com.yuep.nms.core.client.sbimanager.model.SbiManagerModel;
import com.yuep.nms.core.client.sbimanager.view.SbiManagerView;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;

/**
 * <p>
 * Title: OpenSbiManagerViewAction
 * </p>
 * <p>
 * Description:打开sbi管理界面
 * </p>
 * 
 * @author yangtao
 * created 2011-4-20 上午09:41:57
 * modified [who date description]
 * check [who date description]
 */
public class OpenSbiManagerViewAction extends DefaultPopupMenuAction {
    
    private static final long serialVersionUID = 1385749607648991239L;
    
    public OpenSbiManagerViewAction(String actionId) {
        super(actionId);
    }
    /**
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        WindowManager windowManager=ClientCoreContext.getWindowManager();
        SbiManagerController sbiManagerController=new SbiManagerController(SbiManagerView.class,SbiManagerModel.class);
        windowManager.openAsDialog(sbiManagerController);
       
        MoCore moCore=ClientCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        sbiManagerController.setSelectedNm(moCore.getRootMo());
    }

}
