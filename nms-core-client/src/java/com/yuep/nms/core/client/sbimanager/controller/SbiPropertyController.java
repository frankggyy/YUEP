/*
 * $Id: SbiPropertyController.java, 2011-4-20 上午10:18:36 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.sbimanager.controller;

import com.yuep.core.client.mvc.AbstractClientController;
import com.yuep.nms.core.client.sbimanager.model.SbiPropertyModel;
import com.yuep.nms.core.client.sbimanager.view.SbiPropertyView;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;

/**
 * <p>
 * Title: SbiPropertyController
 * </p>
 * <p>
 * Description:sbi属性
 * </p>
 * 
 * @author yangtao
 * created 2011-4-20 上午10:18:36
 * modified [who date description]
 * check [who date description]
 */
public class SbiPropertyController  extends
        AbstractClientController<SbiProperty, SbiPropertyView, SbiPropertyModel> {

    private SbiManagerController sbiManagerController;
    
    public SbiPropertyController(Class<SbiPropertyView> viewClass,Class<SbiPropertyModel> modelClass) {
        super(viewClass, modelClass);
    }

    /**
     * 获取SbiManagerController
     * @return
     */
    public SbiManagerController getSbiManagerController() {
        return sbiManagerController;
    }

    /**
     * 设置SbiManagerController
     * @param sbiManagerController
     */
    public void setSbiManagerController(SbiManagerController sbiManagerController) {
        this.sbiManagerController = sbiManagerController;
    }

    
}
