/*
 * $Id: MvcSampleController.java, 2010-3-29 обнГ02:17:01 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.controller;

import com.yuep.core.client.mvc.AbstractClientController;
import com.yuep.nms.biz.client.sample.mvc.model.MvcSampleModel;
import com.yuep.nms.biz.client.sample.mvc.view.MvcSampleView;

/**
 * <p>
 * Title: MvcSampleController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 обнГ02:17:01
 * modified [who date description]
 * check [who date description]
 */
public class MvcSampleController extends AbstractClientController<Object, MvcSampleView, MvcSampleModel>{

    /**
     * @param viewClass
     * @param modelClass
     */
    public MvcSampleController(Class<MvcSampleView> viewClass, Class<MvcSampleModel> modelClass) {
        super(viewClass, modelClass);
    }
    
    public void deleteSelectionExampleTableData(){
        clientView.deleteSelectionExampleTableData();
    }
}
