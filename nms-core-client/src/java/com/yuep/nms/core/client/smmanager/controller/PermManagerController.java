/*
 * $Id: PermManagerController.java, 2011-4-13 下午05:59:14 WangRui Exp $
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
import com.yuep.nms.core.client.smmanager.model.PermManagerModel;
import com.yuep.nms.core.client.smmanager.view.PermManagerView;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;

/**
 * <p>
 * Title: PermManagerController
 * </p>
 * <p>
 * Description:权限管理
 * </p>
 * 
 * @author WangRui
 * created 2011-4-13 下午05:59:14
 * modified [who date description]
 * check [who date description]
 */
public class PermManagerController extends AbstractTabController<PermissionGroup, PermManagerView, PermManagerModel> {

    /**
     * @param v
     * @param m
     */
    public PermManagerController(Class<PermManagerView> v, Class<PermManagerModel> m) {
        super(v, m);
        // TODO Auto-generated constructor stub
    }


}
