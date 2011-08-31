/*
 * $Id: CreateSampleTableDataAction.java, 2010-3-29 ÏÂÎç04:41:49 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.action;

import java.util.Arrays;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.mvc.controller.MvcSampleController;
import com.yuep.nms.biz.client.sample.mvc.model.MvcSampleModel;
import com.yuep.nms.biz.client.sample.mvc.view.MvcSampleView;

/**
 * <p>
 * Title: CreateSampleTableDataAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 ÏÂÎç04:41:49
 * modified [who date description]
 * check [who date description]
 */
public class CreateSampleTableDataAction extends XAbstractAction {
    private static final long serialVersionUID = 3193780668273245447L;

    /**
     * @param actionId
     * @param controller
     */
    public CreateSampleTableDataAction(String actionId, ClientController controller) {
        super(actionId, controller);
    }

    /**
     * @see com.ycignp.veapo.client.framework.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        MvcSampleController controller2 = module.getController(MvcSampleModel.class, MvcSampleView.class,
                MvcSampleController.class);
        controller2.addDatas(Arrays.asList(objs));
        ((ClientController) controller).dispose();
        return null;
    }

    /**
     * @see com.ycignp.veapo.client.framework.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
    }
}
