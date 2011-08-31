/*
 * $Id: RoleManagerController.java, 2011-4-1 下午01:07:05 WangRui Exp $
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
import com.yuep.nms.core.client.smmanager.model.RoleManagerModel;
import com.yuep.nms.core.client.smmanager.view.RoleManagerView;
import com.yuep.nms.core.common.smcore.model.Role;

/**
 * <p>
 * Title: RoleManagerController
 * </p>
 * <p>
 * Description:角色管理
 * </p>
 * 
 * @author WangRui
 * created 2011-4-1 下午01:07:05
 * modified [who date description]
 * check [who date description]
 */
public class RoleManagerController extends AbstractTabController<Role, RoleManagerView, RoleManagerModel> {

    /**
     * @param v
     * @param m
     */
    public RoleManagerController(Class<RoleManagerView> v, Class<RoleManagerModel> m) {
        super(v, m);
        // TODO Auto-generated constructor stub
    }

}
