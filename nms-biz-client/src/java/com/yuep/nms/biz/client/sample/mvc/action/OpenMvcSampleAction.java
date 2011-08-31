/*
 * $Id: OpenMvcSampleAction.java, 2010-3-29 下午02:17:14 aaron lee Exp $
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
import com.yuep.core.client.component.window.MessageDialog.MessageType;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.mvc.controller.MvcSampleController;
import com.yuep.nms.biz.client.sample.mvc.model.MvcSampleModel;
import com.yuep.nms.biz.client.sample.mvc.model.RealSampleTableData;
import com.yuep.nms.biz.client.sample.mvc.view.MvcSampleView;

/**
 * <p>
 * Title: OpenMvcSampleAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 下午02:17:14
 * modified [who date description]
 * check [who date description]
 */
public class OpenMvcSampleAction extends AbstractXAction {
    private static final long serialVersionUID = 7616530137074964843L;

    /**
     * @param actionId
     */
    public OpenMvcSampleAction(String actionId) {
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
        MvcSampleController controller2 = module.getController(MvcSampleModel.class, MvcSampleView.class,
                MvcSampleController.class);
        ClientCoreContext.getWindowManager().openAsDialog(controller2);
        // 模拟数据进行初始化
        RealSampleTableData realExampleTableData = new RealSampleTableData();
        realExampleTableData.setId(1);
        realExampleTableData.setColumn2(0);
        realExampleTableData.setColumn3("显示");
        realExampleTableData.setColumn4("数据1");
        realExampleTableData.setColumn5(MessageType.Error);
        realExampleTableData.setColumn6("ne=1/rack=0/shelf=1/slot=2/port=3");
        realExampleTableData.setColumn7(false);
        RealSampleTableData realExampleTableData2 = new RealSampleTableData();
        realExampleTableData2.setId(2);
        realExampleTableData2.setColumn2(11);
        realExampleTableData2.setColumn3("显示");
        realExampleTableData2.setColumn4("数据2");
        realExampleTableData2.setColumn5(MessageType.Information);
        realExampleTableData2.setColumn6("ne=8/rack=0/shelf=1/slot=7/port=3");
        realExampleTableData2.setColumn7(true);
        controller2.initData(realExampleTableData, realExampleTableData2);
    }

}
