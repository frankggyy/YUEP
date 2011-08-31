/*
 * $Id: OnlineUserModel.java, 2011-4-13 下午06:03:53 WangRui Exp $
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
import com.yuep.core.session.def.Session;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: OnlineUserModel
 * </p>
 * <p>
 * Description:在线用户
 * </p>
 * 
 * @author sufeng
 * created 2011-4-13 下午06:03:53
 * modified [who date description]
 * check [who date description]
 */
public class OnlineUserModel extends AbstractTabModel<Object> {

    @Override
    public void init(Object... objects) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        List<Session> sessions = smManagerService.getAllOnlineUsers();
        boList.clear();
        boList.addAll(sessions);
    }  
    
    @Override
    public void modifyDatas(List<Object> datas) {
        setChanged();
        notifyObservers(datas);
    }

}
