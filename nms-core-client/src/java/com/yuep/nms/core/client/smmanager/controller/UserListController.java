/*
 * $Id: UserListController.java, 2011-4-25 下午12:21:06 luwei Exp $
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
import com.yuep.nms.core.client.smmanager.model.UserListModel;
import com.yuep.nms.core.client.smmanager.view.UserListView;
import com.yuep.nms.core.common.smcore.model.User;
/**
 * <p>
 * Title:UserListController
 * </p>
 * <p>
 * Description:用户列表界面的Controller
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:20:20
 * modified [who date description]
 * check [who date description]
 */
public class UserListController extends
		AbstractTabController<User, UserListView, UserListModel> {

	public UserListController(Class<UserListView> v, Class<UserListModel> m) {
		super(v, m);
		// TODO Auto-generated constructor stub
	}

}
