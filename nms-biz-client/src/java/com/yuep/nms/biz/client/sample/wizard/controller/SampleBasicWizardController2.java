/*
 * $Id: SampleBasicWizardController2.java, 2010-3-31 ÉÏÎç10:30:25 aaron lee Exp $
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
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.wizard.model.SampleBasicWizardModel1;
import com.yuep.nms.biz.client.sample.wizard.model.SampleBasicWizardModel2;
import com.yuep.nms.biz.client.sample.wizard.model.SampleBasicWizardModel3;
import com.yuep.nms.biz.client.sample.wizard.model.SampleWizardData;
import com.yuep.nms.biz.client.sample.wizard.view.SampleBasicWizardView1;
import com.yuep.nms.biz.client.sample.wizard.view.SampleBasicWizardView2;
import com.yuep.nms.biz.client.sample.wizard.view.SampleBasicWizardView3;

/**
 * <p>
 * Title: SampleBasicWizardController2
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 ÉÏÎç10:30:25
 * modified [who date description]
 * check [who date description]
 */
public class SampleBasicWizardController2 extends BasicWizardController<SampleWizardData, SampleBasicWizardView2, SampleBasicWizardModel2>{

    /**
     * @param viewClass
     * @param modelClass
     */
    public SampleBasicWizardController2(Class<SampleBasicWizardView2> viewClass,
            Class<SampleBasicWizardModel2> modelClass) {
        super(viewClass, modelClass);
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardController#getNextBasicWizardController()
     */
    @Override
    protected BasicWizardController getNextBasicWizardController() {
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        return module.getController(SampleBasicWizardModel3.class, SampleBasicWizardView3.class,
                SampleBasicWizardController3.class);
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardController#getPreviousBasicWizardController()
     */
    @Override
    protected BasicWizardController getPreviousBasicWizardController() {
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        return module.getController(SampleBasicWizardModel1.class, SampleBasicWizardView1.class,
                SampleBasicWizardController1.class);
    }
}
