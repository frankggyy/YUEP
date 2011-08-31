/*
 * $Id: SampleDataNavigatorController.java, 2010-3-30 ионГ11:04:58 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.navigator.controller;

import com.yuep.core.client.component.navigator.DataNavigationController;
import com.yuep.nms.biz.client.sample.navigator.model.SampleDataNavigatorModel;
import com.yuep.nms.biz.client.sample.navigator.view.SampleDataNavigatorView;

/**
 * <p>
 * Title: SampleDataNavigatorController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-30 ионГ11:04:58
 * modified [who date description]
 * check [who date description]
 */
public class SampleDataNavigatorController extends DataNavigationController<Object, SampleDataNavigatorView, SampleDataNavigatorModel>{

    /**
     * @param viewClass
     * @param modelClass
     */
    public SampleDataNavigatorController(Class<SampleDataNavigatorView> viewClass,
            Class<SampleDataNavigatorModel> modelClass) {
        super(viewClass, modelClass);
    }

}
