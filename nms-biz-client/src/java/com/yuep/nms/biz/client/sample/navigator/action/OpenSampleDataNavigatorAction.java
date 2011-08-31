/*
 * $Id: OpenSampleDataNavigatorAction.java, 2010-3-30 ÏÂÎç01:42:04 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.navigator.action;

import java.awt.event.ActionEvent;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.navigator.controller.SampleDataNavigatorController;
import com.yuep.nms.biz.client.sample.navigator.model.SampleDataNavigatorModel;
import com.yuep.nms.biz.client.sample.navigator.view.SampleDataNavigatorView;

/**
 * <p>
 * Title: OpenSampleDataNavigatorAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-30 ÏÂÎç01:42:04
 * modified [who date description]
 * check [who date description]
 */
public class OpenSampleDataNavigatorAction extends AbstractXAction {
    private static final long serialVersionUID = 7616530137074964843L;

    /**
     * @param actionId
     */
    public OpenSampleDataNavigatorAction(String actionId) {
        super(actionId);
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        SampleDataNavigatorController sampleDataNavigatorController = module.getController(
                SampleDataNavigatorModel.class, SampleDataNavigatorView.class,
                SampleDataNavigatorController.class);
        ClientCoreContext.getWindowManager().openAsFrame(sampleDataNavigatorController);
        sampleDataNavigatorController.initData();
    }
}
