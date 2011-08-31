/*
 * $Id: ModifyPermissionBundleAction.java, 2011-4-14 ÏÂÎç01:27:58 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.ModifyPermissionBundleController;
import com.yuep.nms.core.client.smmanager.controller.PermManagerController;
import com.yuep.nms.core.client.smmanager.model.ModifyPermissionBundleModel;
import com.yuep.nms.core.client.smmanager.model.PermManagerModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.ModifyPermissionBundleView;
import com.yuep.nms.core.client.smmanager.view.PermManagerView;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;

/**
 * <p>
 * Title: ModifyPermissionBundleAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-14 ÏÂÎç01:27:58
 * modified [who date description]
 * check [who date description]
 */
public class ModifyPermissionBundleAction extends AbstractXAction {
    private static final long serialVersionUID = 4429162216593919055L;

    /**
     * @param isMultiple
     * @param actionId
     */
    public ModifyPermissionBundleAction(boolean isMultiple, String actionId) {
        super(isMultiple, actionId);
        String text = ClientCoreContext.getString("PermissionGroup.button.update");
        int vk = XGuiUtils.getMnemonicKey('u');
        text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
        putValue(NAME, text);
        putValue(MNEMONIC_KEY, vk);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        PermManagerController permController = module.getController(PermManagerModel.class, PermManagerView.class,
                PermManagerController.class);
        ModifyPermissionBundleController modifyController = module.getController(ModifyPermissionBundleModel.class,
                ModifyPermissionBundleView.class, ModifyPermissionBundleController.class);
        PermissionGroup perm = null;
        permController.collectData();
        List<PermissionGroup> permList = permController.getDatas();
        if (CollectionUtils.isNotEmpty(permList))
            perm = permList.get(0);
        WindowManager windowManager = ClientCoreContext.getWindowManager();
        windowManager.openAsDialog(modifyController);
        modifyController.initData(perm);

    }

}
