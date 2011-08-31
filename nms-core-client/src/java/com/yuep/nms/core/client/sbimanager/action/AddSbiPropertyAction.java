/*
 * $Id: AddSbiPropertyAction.java, 2011-4-20 上午10:35:54 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.sbimanager.action;

import java.util.List;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.nms.core.client.sbimanager.controller.SbiPropertyController;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.sbimanager.module.constants.SbiManagerModuleConstants;
import com.yuep.nms.core.common.sbimanager.service.SbiManager;

/**
 * <p>
 * Title: AddSbiPropertyAction
 * </p>
 * <p>
 * Description:增加sbi属性
 * </p>
 * 
 * @author yangtao
 * created 2011-4-20 上午10:35:54
 * modified [who date description]
 * check [who date description]
 */
public class AddSbiPropertyAction extends XAbstractAction {
    private static final long serialVersionUID = -6291551826105691430L;
    private SbiPropertyController sbiPropertyController;
    public AddSbiPropertyAction(String actionId,SbiPropertyController sbiPropertyController) {
        super(actionId);
        this.sbiPropertyController=sbiPropertyController;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        sbiPropertyController.collectData();
        List<SbiProperty> sbiProperties=sbiPropertyController.getDatas();
        SbiManager sbiManager=ClientCoreContext.getRemoteService(SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);

        MoNaming nm=sbiPropertyController.getSbiManagerController().getSelectedNm().getMoNaming();
        SbiProperty newSbiProperty=sbiManager.createSbi(nm,sbiProperties.get(0));
        return new Object[]{newSbiProperty};
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        SbiProperty sbiProperty=(SbiProperty)objs[0];
        sbiPropertyController.getSbiManagerController().addSbiProperties(sbiProperty);
        sbiPropertyController.dispose();
    }

}
