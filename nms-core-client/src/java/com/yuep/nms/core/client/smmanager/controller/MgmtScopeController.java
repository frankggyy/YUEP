/*
 * $Id: MgmtScopeController.java, 2011-4-25 下午12:21:06 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.controller;

import com.yuep.core.client.component.navigator.AbstractTabController;
import com.yuep.nms.core.client.smmanager.model.MgmtScopeModel;
import com.yuep.nms.core.client.smmanager.view.MgmtScopeView;

/**
 * <p>
 * Title: MgmtScopeController
 * </p>
 * <p>
 * Description:用户管理网元、域范围的Controller
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:21:06
 * modified [who date description]
 * check [who date description]
 */
public class MgmtScopeController extends AbstractTabController<Object, MgmtScopeView, MgmtScopeModel> {

    /**
     * @param v
     * @param m
     */
    public MgmtScopeController(Class<MgmtScopeView> v, Class<MgmtScopeModel> m) {
        super(v, m);
        // TODO Auto-generated constructor stub
    }

}
