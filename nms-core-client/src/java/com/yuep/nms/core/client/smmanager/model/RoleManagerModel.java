/*
 * $Id: RoleManagerModel.java, 2011-4-1 ÏÂÎç01:05:49 WangRui Exp $
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
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: RoleManagerModel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-1 ÏÂÎç01:05:49
 * modified [who date description]
 * check [who date description]
 */
public class RoleManagerModel extends AbstractTabModel<Role> {

    /**
     * @see com.yuep.core.client.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        List<Role> roleList = smManagerService.getAllRoles();
        setChanged();
        notifyObservers(roleList);
    }

}
