/*
 * $Id: RoleController.java, 2011-4-2 下午03:11:01 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.controller;

import com.yuep.core.client.mvc.AbstractClientController;
import com.yuep.nms.core.client.smmanager.model.AddRoleModel;
import com.yuep.nms.core.client.smmanager.view.AddRoleView;

/**
 * <p>
 * Title: RoleController
 * </p>
 * <p>
 * Description:增加角色
 * </p>
 * 
 * @author WangRui
 * created 2011-4-2 下午03:11:01
 * modified [who date description]
 * check [who date description]
 */
public class AddRoleController extends AbstractClientController<Object, AddRoleView,AddRoleModel> {

    /**
     * @param viewClass
     * @param modelClass
     */
    public AddRoleController(Class<AddRoleView> viewClass, Class<AddRoleModel> modelClass) {
        super(viewClass, modelClass);
    }
}
