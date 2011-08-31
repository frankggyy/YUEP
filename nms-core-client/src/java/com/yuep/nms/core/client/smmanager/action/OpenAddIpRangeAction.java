/*
 * $Id: OpenAddIpRangeAction.java, 2011-4-20 下午04:11:31 luwei Exp $
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

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.AddIpRangeController;
import com.yuep.nms.core.client.smmanager.model.AddIpRangeModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.AddIpRangeView;
import com.yuep.nms.core.common.smcore.model.IpRange;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;
/**
 * <p>
 * Title: OpenAddIpRangeAction
 * </p>
 * <p>
 * Description:打开为用户添加登陆IP范围的界面
 * </p>
 * 
 * @author luwei
 * created 2011-4-20 下午04:11:31
 * modified [who date description]
 * check [who date description]
 */
public class OpenAddIpRangeAction extends AbstractXAction {

    public OpenAddIpRangeAction(String actionId) {
        super(actionId);
        String text = ClientCoreContext.getString("Button.add");
        int vk = XGuiUtils.getMnemonicKey('a');
        text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
        putValue(NAME, text);
        putValue(MNEMONIC_KEY, vk);
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 4048322808087562203L;

    @Override
    public void actionPerformed(ActionEvent e) {
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        AddIpRangeController ipRangeController = module.getController(AddIpRangeModel.class, AddIpRangeView.class,
                AddIpRangeController.class);
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);
        List<IpRange> ipRanges = smManagerService.getAllUsersIpRange();
        ipRangeController.initData(ipRanges);
        ClientCoreContext.getWindowManager().openAsFrame(ipRangeController);

    }

}
