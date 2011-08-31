/*
 * $Id: DeleteSbiPropertyAction.java, 2011-4-20 ÉÏÎç11:08:09 yangtao Exp $
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
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.sbimanager.module.constants.SbiManagerModuleConstants;
import com.yuep.nms.core.common.sbimanager.service.SbiManager;

/**
 * <p>
 * Title: DeleteSbiPropertyAction
 * </p>
 * <p>
 * Description:É¾³ýSBIProperty
 * </p>
 * 
 * @author yangtao
 * created 2011-4-20 ÉÏÎç11:08:09
 * modified [who date description]
 * check [who date description]
 */
public class DeleteSbiPropertyAction extends XAbstractAction {

    private static final long serialVersionUID = -6679597963039684007L;
    private SbiManagerController sbiManagerController;
    public DeleteSbiPropertyAction(String actionId,SbiManagerController sbiManagerController) {
        super(actionId);
        this.sbiManagerController=sbiManagerController;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        SbiProperty sbiProperty=sbiManagerController.getSelectedSbiProperty();
        if(sbiProperty==null)
            return null;
        SbiManager sbiManager=ClientCoreContext.getRemoteService(SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);
        sbiManager.deleteSbi(sbiProperty.getSbiNaming());
        return new Object[]{sbiProperty};
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        if(objs==null)
            return;
        SbiProperty sbiProperty=(SbiProperty)objs[0];
        sbiManagerController.deleteSbiProperty(sbiProperty);
    }

}
