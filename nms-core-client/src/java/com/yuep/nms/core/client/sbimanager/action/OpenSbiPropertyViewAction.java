/*
 * $Id: OpenSbiPropertyViewAction.java, 2011-4-20 上午10:19:16 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.sbimanager.action;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.nms.core.client.sbimanager.controller.SbiManagerController;
import com.yuep.nms.core.client.sbimanager.controller.SbiPropertyController;
import com.yuep.nms.core.client.sbimanager.model.SbiPropertyModel;
import com.yuep.nms.core.client.sbimanager.view.SbiPropertyView;

/**
 * <p>
 * Title: OpenSbiPropertyViewAction
 * </p>
 * <p>
 * Description:打开sbi属性界面
 * </p>
 * 
 * @author yangtao
 * created 2011-4-20 上午10:19:16
 * modified [who date description]
 * check [who date description]
 */
public class OpenSbiPropertyViewAction extends XAbstractAction {
    private static final long serialVersionUID = 3363854867543972083L;
    
    private SbiManagerController sbiManagerController;
    public OpenSbiPropertyViewAction(String actionId,SbiManagerController sbiManagerController) {
        super(actionId);
        this.setWaiting(false);
        this.sbiManagerController=sbiManagerController;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        return null;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        SbiPropertyController sbiPropertyController=new SbiPropertyController(SbiPropertyView.class,SbiPropertyModel.class);
        sbiPropertyController.setSbiManagerController(sbiManagerController);
        ClientCoreContext.getWindowManager().openAsDialog(sbiPropertyController);
    }

   

}
