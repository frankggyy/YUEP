/*
 * $Id: UserListModel.java, 2011-4-25 下午12:57:19 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.model;

import java.util.List;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.navigator.AbstractTabModel;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;
/**
 * <p>
 * Title: UserListModel
 * </p>
 * <p>
 * Description:展示用户列表的Model
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:57:19
 * modified [who date description]
 * check [who date description]
 */
public class UserListModel extends AbstractTabModel<User> {

	@Override
	public void init(Object... objects) {
	    final SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
	    List<User> userList = smManagerService.getAllUsers();
	    setChanged();
        notifyObservers(userList);      
    }

}
