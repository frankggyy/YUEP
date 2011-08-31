/*
 * $Id: AddPermissionBundleController.java, 2011-5-10 下午05:46:58 WangRui Exp $
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
import com.yuep.nms.core.client.smmanager.model.ModifyPermissionBundleModel;
import com.yuep.nms.core.client.smmanager.view.AddPermissionBundleView;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;


/**
 * <p>
 * Title: AddPermissionBundleController
 * </p>
 * <p>
 * Description:添加权限集的controler
 * </p>
 * 
 * @author WangRui
 * created 2011-5-10 下午05:46:58
 * modified [who date description]
 * check [who date description]
 */
public class AddPermissionBundleController extends AbstractClientController<PermissionGroup, AddPermissionBundleView, ModifyPermissionBundleModel> {

    /**
     * @param viewClass
     * @param modelClass
     */
    public AddPermissionBundleController(Class<AddPermissionBundleView> viewClass,
            Class<ModifyPermissionBundleModel> modelClass) {
        super(viewClass, modelClass);
    }

}
