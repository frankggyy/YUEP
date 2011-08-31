/*
 * $Id: OpenModifyRealSampleTableDataAction.java, 2010-3-29 ÏÂÎç04:55:57 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.action;

import java.awt.event.ActionEvent;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.mvc.controller.MvcSampleController;
import com.yuep.nms.biz.client.sample.mvc.model.ModifyRealSampleTableDataModel;
import com.yuep.nms.biz.client.sample.mvc.model.MvcSampleModel;
import com.yuep.nms.biz.client.sample.mvc.view.ModifySampleTableDataView;
import com.yuep.nms.biz.client.sample.mvc.view.MvcSampleView;

/**
 * <p>
 * Title: OpenModifyRealSampleTableDataAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 ÏÂÎç04:55:57
 * modified [who date description]
 * check [who date description]
 */
public class OpenModifyRealSampleTableDataAction extends AbstractXAction {
    /**
     * @param actionId
     * @param textKey
     * @param mnemonic
     */
    public OpenModifyRealSampleTableDataAction(String actionId, String textKey, char mnemonic) {
        super(actionId, textKey, mnemonic);
    }

    private static final long serialVersionUID = 7616530137074964843L;

    /**
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        MvcSampleController controller2 = module.getController(MvcSampleModel.class, MvcSampleView.class,
                MvcSampleController.class);
        DefaultClientController controller = module
                .getController(ModifyRealSampleTableDataModel.class,ModifySampleTableDataView.class,DefaultClientController.class);
        ClientCoreContext.getWindowManager().openAsDialog(controller);
        controller.initData(controller2.getDatas());
    }
}
