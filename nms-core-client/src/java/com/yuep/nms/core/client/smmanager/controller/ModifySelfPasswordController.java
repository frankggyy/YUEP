/*
 * $Id: ModifySelfPasswordController.java, 2011-4-27 下午01:52:26 sufeng Exp $
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
import com.yuep.nms.core.client.smmanager.model.ModifySelfPasswordModel;
import com.yuep.nms.core.client.smmanager.view.ModifySelfPasswordView;

/**
 * <p>
 * Title: ModifySelfPasswordController
 * </p>
 * <p>
 * Description:修改自身密码
 * </p>
 * 
 * @author sufeng
 * created 2011-4-27 下午01:52:26
 * modified [who date description]
 * check [who date description]
 */
public class ModifySelfPasswordController extends AbstractClientController<Object, ModifySelfPasswordView, ModifySelfPasswordModel> {

    public ModifySelfPasswordController(Class<ModifySelfPasswordView> viewClass,
            Class<ModifySelfPasswordModel> modelClass) {
        super(viewClass, modelClass);
    }

}
