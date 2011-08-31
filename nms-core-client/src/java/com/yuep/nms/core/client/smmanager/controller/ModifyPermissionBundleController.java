/*
 * $Id: ModifyPermissionBundleController.java, 2011-4-14 下午03:19:36 WangRui Exp $
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
import com.yuep.nms.core.client.smmanager.view.ModifyPermissionBundleView;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;

/**
 * <p>
 * Title: ModifyPermissionBundleController
 * </p>
 * <p>
 * Description:修改权限集
 * </p>
 * 
 * @author WangRui
 * created 2011-4-14 下午03:19:36
 * modified [who date description]
 * check [who date description]
 */
public class ModifyPermissionBundleController extends AbstractClientController<PermissionGroup, ModifyPermissionBundleView, ModifyPermissionBundleModel> {

    /**
     * @param viewClass
     * @param modelClass
     */
    public ModifyPermissionBundleController(Class<ModifyPermissionBundleView> viewClass,
            Class<ModifyPermissionBundleModel> modelClass) {
        super(viewClass, modelClass);
    }
}
