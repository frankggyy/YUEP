/*
 * $Id: OnlineUserKickAction.java, 2011-4-20 上午11:20:55 WangRui Exp $
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

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractButtonAction;
import com.yuep.core.client.component.validate.editor.XTableEditor;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.core.session.def.Session;
import com.yuep.nms.core.client.smcore.def.SmCoreClientService;
import com.yuep.nms.core.client.smmanager.controller.OnlineUserController;
import com.yuep.nms.core.client.smmanager.model.OnlineUserModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.OnlineUserView;
import com.yuep.nms.core.common.smcore.exception.SmException;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: OnlineUserKickAction
 * </p>
 * <p>
 * Description:踢出在线用户
 * </p>
 * 
 * @author sufeng
 * created 2011-4-25 下午15:02:55
 * modified [who date description]
 * check [who date description]
 */
public class OnlineUserKickAction extends AbstractButtonAction {
    
    private static final long serialVersionUID = 5711203987847227583L;

    public OnlineUserKickAction(String actionId) {
        super(actionId);
        
        putValue(NAME, ClientCoreContext.getString("smmanager.onlineuser.kick"));
        putValue(MNEMONIC_KEY, XGuiUtils.getMnemonicKey('K'));
    }

    private XTableEditor<Session> table;
    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        OnlineUserController controller2 = module.getController(OnlineUserModel.class, OnlineUserView.class, OnlineUserController.class);
        table = controller2.getClientView().getTable();
        List<Session> sessions = table.getSelectionDatas();
        if(CollectionUtils.isEmpty(sessions))
            return null;
        Session session=sessions.get(0);
        
        SmCoreClientService smCoreClientService = ClientCoreContext.getLocalService("smCoreClientService", SmCoreClientService.class);
        if(session.getSessionId().equals(smCoreClientService.getCurrentSessionId()))
            throw new SmException(SmException.KICK_SELF);
        
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        smManagerService.kickOff(session.getSessionId());
        return new Object[]{ session };
    }

    @Override
    protected void updateUi(Object... objs) {
        if(objs==null || objs.length==0)
            return ;
        
        table.deleteRowData((Session)objs[0]);
    }

}
