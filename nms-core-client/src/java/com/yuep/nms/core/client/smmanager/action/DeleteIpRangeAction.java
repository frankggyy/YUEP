/*
 * $Id: DeleteIpRangeAction.java, 2011-4-19 ÏÂÎç04:39:52 luwei Exp $
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
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.UserInfoController;
import com.yuep.nms.core.client.smmanager.model.UserInfoModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.UserInfoView;
import com.yuep.nms.core.common.smcore.model.IpRange;

/**
 * <p>
 * Title: DeleteIpRangeAction
 * </p>
 * <p>
 * Description:É¾³ýÓÃ»§µÄIPµÇÂ½·¶Î§
 * </p>
 * 
 * @author luwei
 * created 2011-4-19 ÏÂÎç04:39:52
 * modified [who date description]
 * check [who date description]
 */
public class DeleteIpRangeAction extends XAbstractAction {
    private static final long serialVersionUID = 6000070974287593136L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    public DeleteIpRangeAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        String text = ClientCoreContext.getString("Button.delete");
        int vk = XGuiUtils.getMnemonicKey('d');
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
        UserInfoController userController = module.getController(UserInfoModel.class, UserInfoView.class,
                UserInfoController.class);
        UserInfoView userInfoView = (UserInfoView) userController.getClientView();
        List<IpRange> allIpRange = userInfoView.getAllIpRange();
        List<IpRange> selectedIp = userInfoView.getSelectedIpRange();
        allIpRange.removeAll(selectedIp);
        userInfoView.updateIpRangeTable(allIpRange);

    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        // TODO Auto-generated method stub
        return new Object[]{};
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        // TODO Auto-generated method stub

    }

}
