/*
 * $Id: UserInfoController.java, 2011-4-25 下午12:21:06 luwei Exp $
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
import com.yuep.nms.core.client.smmanager.model.UserInfoModel;
import com.yuep.nms.core.client.smmanager.view.UserInfoView;
/**
 * <p>
 * Title:UserInfoController
 * </p>
 * <p>
 * Description:用户详细信息界面的Controller
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:20:20
 * modified [who date description]
 * check [who date description]
 */
public class UserInfoController extends AbstractTabController<Object, UserInfoView, UserInfoModel> {

    public UserInfoController(Class viewClass, Class modelClass) {
        super(viewClass, modelClass);
        // TODO Auto-generated constructor stub
    }

}
