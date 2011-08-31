/*
 * $Id: SampleWizardController.java, 2010-3-31 ÉÏÎç10:27:17 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.wizard.controller;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.wizard.BasicWizardController;
import com.yuep.core.client.component.wizard.ContainerWizardController;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.wizard.model.SampleBasicWizardModel1;
import com.yuep.nms.biz.client.sample.wizard.model.SampleWizardModel;
import com.yuep.nms.biz.client.sample.wizard.view.SampleBasicWizardView1;
import com.yuep.nms.biz.client.sample.wizard.view.SampleWizardView;

/**
 * <p>
 * Title: SampleWizardController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 ÉÏÎç10:27:17
 * modified [who date description]
 * check [who date description]
 */
public class SampleWizardController extends ContainerWizardController<Object, SampleWizardView, SampleWizardModel> {

    /**
     * @param viewClass
     * @param modelClass
     */
    public SampleWizardController(Class<SampleWizardView> viewClass, Class<SampleWizardModel> modelClass) {
        super(viewClass, modelClass);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.ContainerWizardController#getFirstBasicController()
     */
    @Override
    protected BasicWizardController getFirstBasicController() {
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        return module.getController(SampleBasicWizardModel1.class, SampleBasicWizardView1.class,
                SampleBasicWizardController1.class);
    }
}
