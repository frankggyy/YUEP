/*
 * $Id: SaveAddIpRange.java, 2011-4-25 下午12:53:20 luwei Exp $
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
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.util.format.IpAddrFormatter;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.XAbstractAction;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.client.smmanager.controller.AddIpRangeController;
import com.yuep.nms.core.client.smmanager.controller.UserInfoController;
import com.yuep.nms.core.client.smmanager.model.AddIpRangeModel;
import com.yuep.nms.core.client.smmanager.model.UserInfoModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.AddIpRangeView;
import com.yuep.nms.core.client.smmanager.view.UserInfoView;
import com.yuep.nms.core.common.smcore.model.IpRange;
/**
 * <p>
 * Title: SaveAddIpRange
 * </p>
 * <p>
 * Description:保存为用户分配的登陆IP范围
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:53:20
 * modified [who date description]
 * check [who date description]
 */
public class SaveAddIpRange extends XAbstractAction {

    private static final long serialVersionUID = 4534573462805501214L;

    /**
     * @param isMultiple
     * @param actionId
     * @param controller
     */
    public SaveAddIpRange(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        AddIpRangeController ipRangeController = module.getController(AddIpRangeModel.class, AddIpRangeView.class,
                AddIpRangeController.class);
        final AddIpRangeView ipRangeView = ipRangeController.getClientView();
        final UserInfoController userController = module.getController(UserInfoModel.class, UserInfoView.class,
                UserInfoController.class);
        final UserInfoView userInfoView = (UserInfoView) userController.getClientView();
        ipRangeController.collectData();
        List<IpRange> ipRangeList = ipRangeController.getDatas();
        IpRange ipRange = ipRangeList.get(0);
        if (IpAddrFormatter.compare(ipRange.getStartIpAddress(), ipRange.getEndIpAddress()) > 0) {
            DialogUtils.showInfoDialog(ipRangeView.getWindow(), "IpRange.endIpLittle");
            return;
        }

        List<IpRange> allIpRange = userInfoView.getAllIpRange();
        if (CollectionUtils.isNotEmpty(allIpRange)) {
            for (IpRange tableIpRange : allIpRange) {
                if (ipRange.getStartIpAddress().equalsIgnoreCase(tableIpRange.getStartIpAddress())) {
                    DialogUtils.showInfoDialog(ipRangeController.getClientView().getWindow(), "IpRange.alreadyExist");
                    break;
                }
            }
        }

        allIpRange.add(ipRange);
        userInfoView.updateIpRangeTable(allIpRange);
        ipRangeController.getClientView().getWindow().dispose();

    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#commitData(java.lang.Object[])
     */
    @Override
    protected Object[] commitData(Object... objs) {
        // TODO Auto-generated method stub
        return new Object[] {};
    }

    /**
     * @see com.yuep.core.client.component.menu.action.XAbstractAction#updateUi(java.lang.Object[])
     */
    @Override
    protected void updateUi(Object... objs) {
        // TODO Auto-generated method stub

    }

}
