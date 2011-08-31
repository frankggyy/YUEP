/*
 * $Id: OpenCreateRealSampleTableDataAction.java, 2010-3-29 ÏÂÎç04:14:25 aaron lee Exp $
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
import com.yuep.core.client.mvc.DefaultClientModel;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.mvc.view.CreateSampleTableDataView;

/**
 * <p>
 * Title: OpenCreateRealSampleTableDataAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 ÏÂÎç04:14:25
 * modified [who date description]
 * check [who date description]
 */
public class OpenCreateRealSampleTableDataAction extends AbstractXAction {
    private static final long serialVersionUID = 7616530137074964843L;

    /**
     * @param actionId
     */
    public OpenCreateRealSampleTableDataAction(String actionId) {
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
        DefaultClientController<Object, CreateSampleTableDataView, DefaultClientModel<Object>> controller = module
                .getDefaultClientController(CreateSampleTableDataView.class);
        ClientCoreContext.getWindowManager().openAsDialog(controller);
        controller.initData();
    }

}
