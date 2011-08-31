/*
 * $Id: OperationLogExportAction.java, 2011-4-20 上午11:20:55 WangRui Exp $
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
import com.yuep.core.client.component.table.XTableUtils;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.OplogController;
import com.yuep.nms.core.client.smmanager.model.OplogModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.OplogView;
import com.yuep.nms.core.common.smcore.model.OperationLog;

/**
 * <p>
 * Title: OperationLogExportAction
 * </p>
 * <p>
 * Description:导出操作日志
 * </p>
 * 
 * @author sufeng
 * created 2011-4-25 下午15:02:55
 * modified [who date description]
 * check [who date description]
 */
public class OperationLogExportAction extends AbstractButtonAction {
    
    private static final long serialVersionUID = -1762582957964259042L;

    public OperationLogExportAction(String actionId) {
        super(actionId);
        
        putValue(NAME, ClientCoreContext.getString("smmanager.oplog.export"));
        putValue(MNEMONIC_KEY, XGuiUtils.getMnemonicKey('E'));
    }

    @Override
    protected Object[] commitData(Object... objs) {
        return new Object[]{};
    }
    
    @Override
    protected void updateUi(Object... objs) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        OplogController controller = module.getController(OplogModel.class, OplogView.class, OplogController.class);
        OplogView view=controller.getClientView();
        List<String> notAddFields=new ArrayList<String>();
        notAddFields.add("id");
        notAddFields.add("detailInfo");
        XTableUtils.expertTable2File(view.getTable(), OperationLog.class,notAddFields, view.getRenderers(), view.getWindow());
    }

}
