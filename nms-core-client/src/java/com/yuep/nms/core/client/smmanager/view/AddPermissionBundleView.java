/*
 * $Id: AddPermissionBundleView.java, 2011-5-10 下午05:40:59 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.core.client.smmanager.action.ApplyAddPermissionBundleAction;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;

/**
 * <p>
 * Title: AddPermissionBundleView
 * </p>
 * <p>
 * Description:添加权限集的页面
 * </p>
 * 
 * @author WangRui
 * created 2011-5-10 下午05:40:59
 * modified [who date description]
 * check [who date description]
 */
public class AddPermissionBundleView extends ModifyPermissionBundleView {
    private static final long serialVersionUID = -3350795002647969066L;

    @Override
    protected <V, M> void addButtonListener(ClientController<PermissionGroup, V, M> controller) {
        ApplyAddPermissionBundleAction action = new ApplyAddPermissionBundleAction(false,"",controller);
        okButton.addActionListener(action);       
    }
}
