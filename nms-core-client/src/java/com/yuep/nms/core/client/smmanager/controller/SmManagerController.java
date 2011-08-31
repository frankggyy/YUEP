/*
 * $Id: SmManagerController.java, 2011-4-1 下午01:20:13 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.controller;

import com.yuep.core.client.component.navigator.DataNavigationController;
import com.yuep.nms.core.client.smmanager.model.SmManagerModel;
import com.yuep.nms.core.client.smmanager.view.SmManagerView;

/**
 * <p>
 * Title: SmManagerController
 * </p>
 * <p>
 * Description:安全管理主窗口的controller
 * </p>
 * 
 * @author WangRui
 * created 2011-4-1 下午01:20:13
 * modified [who date description]
 * check [who date description]
 */
public class SmManagerController extends DataNavigationController<Object, SmManagerView, SmManagerModel> {
    /**
     * @param v
     * @param m
     */
    public SmManagerController(Class<SmManagerView> v, Class<SmManagerModel> m) {
        super(v, m);
        // TODO Auto-generated constructor stub
    }    

}
