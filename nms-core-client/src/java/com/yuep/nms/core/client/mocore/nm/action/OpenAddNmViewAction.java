/*
 * $Id: OpenAddNmViewAction.java, 2011-4-19 下午02:58:01 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.mocore.nm.action;

import java.awt.event.ActionEvent;

import twaver.Element;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.DefaultPopupMenuAction;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.nms.core.client.mocore.nm.controller.AddNmController;
import com.yuep.nms.core.client.mocore.nm.model.AddNmModel;
import com.yuep.nms.core.client.mocore.nm.view.AddNmView;
import com.yuep.nms.core.common.mocore.model.Mo;

/**
 * <p>
 * Title: OpenAddNmViewAction
 * </p>
 * <p>
 * Description:打开增加下级网管的窗口
 * </p>
 * 
 * @author yangtao
 * created 2011-4-19 下午02:58:01
 * modified [who date description]
 * check [who date description]
 */
public class OpenAddNmViewAction extends DefaultPopupMenuAction {

    private static final long serialVersionUID = 3402341219787737076L;
    
    public OpenAddNmViewAction(String actionId) {
        super(actionId);
    }

    public OpenAddNmViewAction(Boolean isMultiple, String actionId, Object[] selectedObjects, Object paramObj, String paramClazz) {
        super(isMultiple, actionId, selectedObjects, paramObj, paramClazz);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       Element selectedElement=(Element)this.selectedObjects[0];
       WindowManager windowManager=ClientCoreContext.getWindowManager();
       AddNmController addNmController=new AddNmController(AddNmView.class,AddNmModel.class);
       addNmController.setSelectedMo((Mo)selectedElement.getUserObject());
       windowManager.openAsDialog(addNmController);
    }

}
