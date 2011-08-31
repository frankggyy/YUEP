/*
 * $Id: OpenSelectRoleAction.java, 2011-4-20 下午04:11:31 luwei Exp $
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

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.util.XGuiUtils;
import com.yuep.nms.core.client.smmanager.controller.SelectRoleController;
import com.yuep.nms.core.client.smmanager.model.SelectRoleModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.SelectRoleView;

/**
 * <p>
 * Title: OpenSelectRoleAction
 * </p>
 * <p>
 * Description:打开为用户分配角色的界面
 * </p>
 * 
 * @author luwei
 * created 2011-4-20 下午04:11:31
 * modified [who date description]
 * check [who date description]
 */
public class OpenSelectRoleAction extends AbstractXAction {

	private static final long serialVersionUID = -5496535472872167713L;

	public OpenSelectRoleAction(String actionId) {
		super(actionId);
		String text = ClientCoreContext.getString("Button.add");
		int vk = XGuiUtils.getMnemonicKey('a');
		text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
		putValue(NAME, text);
		putValue(MNEMONIC_KEY, vk);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
		SelectRoleController selectRoleController = module.getController(SelectRoleModel.class, SelectRoleView.class, SelectRoleController.class);
		selectRoleController.initData();
		ClientCoreContext.getWindowManager().openAsFrame(selectRoleController);
	}

}
