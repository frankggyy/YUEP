/*
 * $Id: OperationLogQueryAction.java, 2011-4-20 上午11:20:55 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.action;

import java.util.ArrayList;
import java.util.List;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractButtonAction;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.OplogController;
import com.yuep.nms.core.client.smmanager.model.OplogModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.OplogView;
import com.yuep.nms.core.common.smcore.model.OperationLog;
import com.yuep.nms.core.common.smcore.model.OperationLogCondition;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: OperationLogQueryAction
 * </p>
 * <p>
 * Description:查询操作日志
 * </p>
 * 
 * @author sufeng
 * created 2011-4-25 下午15:02:55
 * modified [who date description]
 * check [who date description]
 */
public class OperationLogQueryAction extends AbstractButtonAction {
    
    private static final long serialVersionUID = 5711203987847227583L;

    public OperationLogQueryAction(String actionId) {
        super(actionId);
        
        putValue(NAME, ClientCoreContext.getString("smmanager.oplog.query"));
        putValue(MNEMONIC_KEY, XGuiUtils.getMnemonicKey('Q'));
    }

    @Override
    protected Object[] commitData(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        OplogController controller = module.getController(OplogModel.class, OplogView.class, OplogController.class);
        OperationLogCondition condition = controller.getClientView().getOperationLogCondition();
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        List<OperationLog> operationLogs = smManagerService.getOperationLog(condition);
        return operationLogs.toArray();
    }

    @Override
    protected void updateUi(Object... objs) {
        List<Object> list=new ArrayList<Object>();
        list.add("queryresult");
        list.add(objs);
        
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        OplogController controller2 = module.getController(OplogModel.class, OplogView.class, OplogController.class);
        controller2.modifyDatas(list);
    }

}
