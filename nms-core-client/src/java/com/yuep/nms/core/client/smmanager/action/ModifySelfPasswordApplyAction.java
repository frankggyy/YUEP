/*
 * $Id: ModifySelfPasswordApplyAction.java, 2011-3-30 下午04:04:58 sufeng Exp $
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
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.client.smcore.def.SmCoreClientService;
import com.yuep.nms.core.client.smmanager.controller.ModifySelfPasswordController;
import com.yuep.nms.core.client.smmanager.model.ModifySelfPasswordModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.ModifySelfPasswordView;
import com.yuep.nms.core.common.smcore.exception.SmException;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: ModifySelfPasswordApplyAction
 * </p>
 * <p>
 * Description: 修改自己的密码（提交到后台）
 * </p>
 * 
 * @author sufeng
 * created 2011-4-27 下午04:04:58
 * modified [who date description]
 * check [who date description]
 */
public class ModifySelfPasswordApplyAction extends XAbstractAction {

    private static final long serialVersionUID = 8542073795021718700L;

    public ModifySelfPasswordApplyAction(String actionId) {
        super(actionId);
    }

    private ModifySelfPasswordController modifySelfPasswordController;
    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        modifySelfPasswordController = module.getController(ModifySelfPasswordModel.class,ModifySelfPasswordView.class, ModifySelfPasswordController.class);
        List<Object> datas = modifySelfPasswordController.getDatas();
        if(CollectionUtils.isEmpty(datas))
            return null;
        if(datas.size()!=2)
            throw new SmException(SmException.INTERNAL_ERROR,"ModifySelfPasswordView.collectData return is illegal");

        String oldPwd=(String)datas.get(0);
        String pwd=(String)datas.get(1);
        SmCoreClientService smCoreClientService=ClientCoreContext.getLocalService("smCoreClientService", SmCoreClientService.class);
        SmManagerService smManagerService=ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        smManagerService.setNewPassword(smCoreClientService.getCurrentUser(), oldPwd, pwd);
        DialogUtils.showCompletedConfirmDialog(modifySelfPasswordController.getClientView().getWindow());
        return new Object[0];
    }

    @Override
    protected void updateUi(Object... objs) {
        if(objs==null)
            return;
    }
    
}
