/*
 * $Id: SelectRoleController.java, 2011-4-25 下午12:20:20 luwei Exp $
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
import com.yuep.nms.core.client.smmanager.model.SelectRoleModel;
import com.yuep.nms.core.client.smmanager.view.SelectRoleView;
import com.yuep.nms.core.common.smcore.model.Role;
/**
 * <p>
 * Title:SelectRoleController
 * </p>
 * <p>
 * Description:为用户分配角色的Controller
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:20:20
 * modified [who date description]
 * check [who date description]
 */
public class SelectRoleController extends AbstractClientController<Role, SelectRoleView, SelectRoleModel> {

	public SelectRoleController(Class<SelectRoleView> viewClass, Class<SelectRoleModel> modelClass) {
		super(viewClass, modelClass);
		// TODO Auto-generated constructor stub
	}

}
