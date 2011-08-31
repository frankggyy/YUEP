/*
 * $Id: SampleBasicWizardController1.java, 2010-3-31 ÉÏÎç10:30:19 aaron lee Exp $
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
import com.yuep.nms.biz.client.sample.wizard.model.SampleWizardData;
import com.yuep.nms.biz.client.sample.wizard.view.SampleBasicWizardView1;
import com.yuep.nms.biz.client.sample.wizard.view.SampleBasicWizardView2;

/**
 * <p>
 * Title: SampleBasicWizardController1
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 ÉÏÎç10:30:19
 * modified [who date description]
 * check [who date description]
 */
public class SampleBasicWizardController1 extends
        BasicWizardController<SampleWizardData, SampleBasicWizardView1, SampleBasicWizardModel1> {

    /**
     * @param viewClass
     * @param modelClass
     */
    public SampleBasicWizardController1(Class<SampleBasicWizardView1> viewClass,
            Class<SampleBasicWizardModel1> modelClass) {
        super(viewClass, modelClass);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardController#getNextBasicWizardController()
     */
    @Override
    protected BasicWizardController getNextBasicWizardController() {
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        return module.getController(SampleBasicWizardModel2.class, SampleBasicWizardView2.class,
                SampleBasicWizardController2.class);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardController#getPreviousBasicWizardController()
     */
    @Override
    protected BasicWizardController getPreviousBasicWizardController() {
        return null;
    }
}
