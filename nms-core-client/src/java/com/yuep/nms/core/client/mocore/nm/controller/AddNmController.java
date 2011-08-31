/*
 * $Id: AddNmController.java, 2011-4-19 下午02:56:21 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.mocore.nm.controller;

import com.yuep.core.client.mvc.AbstractClientController;
import com.yuep.nms.core.client.mocore.nm.model.AddNmModel;
import com.yuep.nms.core.client.mocore.nm.view.AddNmView;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;

/**
 * <p>
 * Title: AddNmController
 * </p>
 * <p>
 * Description:增加下级网管
 * 
 * </p>
 * 
 * @author yangtao
 * created 2011-4-19 下午02:56:21
 * modified [who date description]
 * check [who date description]
 */
public class AddNmController extends AbstractClientController<ManagedNodeProperty, AddNmView, AddNmModel> {
    
	private Mo mo;
    public AddNmController(Class<AddNmView> viewClass,Class<AddNmModel> modelClass) {
        super(viewClass, modelClass);
    }
    
    /**
     * 设置被选择的Mo
     * @param mo
     */
    public void setSelectedMo(Mo mo){
    	this.mo=mo;
    }
    
    /**
     * 获取被选择的Mo
     * @return
     */
    public Mo getSelectedMo(){
    	return mo;
    }

}
