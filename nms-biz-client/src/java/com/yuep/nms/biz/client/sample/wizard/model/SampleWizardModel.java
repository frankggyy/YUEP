/*
 * $Id: SampleWizardModel.java, 2010-3-31 ÉÏÎç10:26:30 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.wizard.model;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.wizard.BasicWizardController;
import com.yuep.core.client.component.wizard.BasicWizardModel;
import com.yuep.core.client.component.wizard.BasicWizardView;
import com.yuep.core.client.mvc.AbstractClientModel;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.wizard.controller.SampleWizardController;
import com.yuep.nms.biz.client.sample.wizard.view.SampleWizardView;

/**
 * <p>
 * Title: SampleWizardModel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 ÉÏÎç10:26:30
 * modified [who date description]
 * check [who date description]
 */
public class SampleWizardModel extends AbstractClientModel<Object> {

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        BasicWizardController<Object, BasicWizardView<Object>, BasicWizardModel<Object>> currentController = module
                .getController(SampleWizardModel.class, SampleWizardView.class, SampleWizardController.class)
                .getCurrentController();
        currentController.clearData();
    }

}
