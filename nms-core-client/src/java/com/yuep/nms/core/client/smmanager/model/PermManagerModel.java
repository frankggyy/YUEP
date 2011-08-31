/*
 * $Id: PermManagerModel.java, 2011-4-13 ÏÂÎç06:03:53 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.model;

import java.awt.Window;
import java.util.List;

import javax.swing.SwingWorker;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.navigator.AbstractTabModel;
import com.yuep.core.client.component.window.WaitingDialog;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.client.smmanager.controller.SmManagerController;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.SmManagerView;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: PermManagerModel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-13 ÏÂÎç06:03:53
 * modified [who date description]
 * check [who date description]
 */
public class PermManagerModel extends AbstractTabModel<PermissionGroup> {

    /**
     * @see com.yuep.core.client.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        final SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        SmManagerController smManagerController = module.getController(SmManagerModel.class,SmManagerView.class, SmManagerController.class);
        final Window window = smManagerController.getClientView().getWindow();
        final WaitingDialog waitingDialog = DialogUtils.showWaitingDialog(window);
        new SwingWorker<List<PermissionGroup>,Void>(){
            @Override
            protected List<PermissionGroup> doInBackground() throws Exception {
                List<PermissionGroup> permGroupList = smManagerService.getAllPermissionGroups();
                return permGroupList;
            }

            /**
             * @see javax.swing.SwingWorker#done()
             */
            @Override
            protected void done() {
                List<PermissionGroup> permGroupList = null;
                try{
                    permGroupList = this.get();
                }catch (Exception ex) {
                    DialogUtils.showErrorExpandDialog(window, ex);
                }finally {
                    waitingDialog.closeWaitingDialog();
                }
                setChanged();
                notifyObservers(permGroupList);
            }            
        }.execute();
      
    }        

}
