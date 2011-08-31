/*
 * $Id: OnlineUserRefreshAction.java, 2011-4-20 上午11:20:55 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.action;

import java.util.List;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractButtonAction;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.core.session.def.Session;
import com.yuep.nms.core.client.smmanager.controller.OnlineUserController;
import com.yuep.nms.core.client.smmanager.model.OnlineUserModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.OnlineUserView;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: OnlineUserRefreshAction
 * </p>
 * <p>
 * Description:刷新在线用户
 * </p>
 * 
 * @author sufeng
 * created 2011-4-25 下午15:02:55
 * modified [who date description]
 * check [who date description]
 */
public class OnlineUserRefreshAction extends AbstractButtonAction {
    
    private static final long serialVersionUID = 5711203987847227583L;

    public OnlineUserRefreshAction(String actionId) {
        super(actionId);
        
        putValue(NAME, ClientCoreContext.getString("smmanager.onlineuser.refresh"));
        putValue(MNEMONIC_KEY, XGuiUtils.getMnemonicKey('R'));
    }

    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        List<Session> sessions = smManagerService.getAllOnlineUsers();
        return sessions.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void updateUi(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        OnlineUserController controller2 = module.getController(OnlineUserModel.class, OnlineUserView.class, OnlineUserController.class);
        List datas=YuepObjectUtils.newArrayList(objs);
        controller2.modifyDatas(datas);
    }

}
