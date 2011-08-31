/*
 * $Id: SmManagerModel.java, 2011-4-1 上午10:23:35 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.model;

import java.util.ArrayList;
import java.util.List;

import twaver.Element;
import twaver.Node;

import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.navigator.AbstractTabController;
import com.yuep.core.client.component.navigator.DataNavigationModel;
import com.yuep.nms.core.client.smmanager.controller.OnlineUserController;
import com.yuep.nms.core.client.smmanager.controller.OplogController;
import com.yuep.nms.core.client.smmanager.controller.PermManagerController;
import com.yuep.nms.core.client.smmanager.controller.RoleManagerController;
import com.yuep.nms.core.client.smmanager.controller.UserListController;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.view.OnlineUserView;
import com.yuep.nms.core.client.smmanager.view.OplogView;
import com.yuep.nms.core.client.smmanager.view.PermManagerView;
import com.yuep.nms.core.client.smmanager.view.RoleManagerView;
import com.yuep.nms.core.client.smmanager.view.UserListView;

/**
 * <p>
 * Title: SmManagerModel
 * </p>
 * <p>
 * Description:sm管理主model
 * </p>
 * 
 * @author WangRui
 * created 2011-4-1 上午10:23:35
 * modified [who date description]
 * check [who date description]
 */
public class SmManagerModel extends DataNavigationModel<Object> {

	private Node roleRoot;
	private Node userRoot;
	private Node permRoot;
	private Node oplogRoot;
	private Node onlineUserRoot;
	private String moduleName;

	/**
	 * @see com.yuep.core.client.component.navigator.DataNavigationModel#init(java.lang.Object[])
	 */
	@Override
	public void init(Object... objects) {
		boList.clear();
		
		SmManagerClientModule module = ModuleContext.getInstance().getModule(SmManagerClientModule.class);
		moduleName=module.getModuleName();
	}
    /**
     * @see com.yuep.core.client.component.navigator.AbstractNavigationModel#getNavigationNodes()
     */
    @Override
    protected List<Element> getNavigationNodes() {        
        return constructNodes();
    }
  
	private List<Element> constructNodes() {
		List<Element> datas = new ArrayList<Element>();
		Node root = new Node();
		String rootString = getString("smmanager.title");
		root.setUserObject(rootString);
		root.setName(rootString);
		root.setBusinessObject(rootString);
		root.setIcon(ClientCoreContext.getResourceFactory().getIconUrl(moduleName, "sm-icon.png").toString());
		datas.add(root);
		List<AbstractTabController<?, ?, ?>> rootControllerList = new ArrayList<AbstractTabController<?, ?, ?>>();
		getTabMap().put(rootString, rootControllerList);

		
		String userString = getString("smmanager.user");
		userRoot = new Node("smmanager.user");
        userRoot.setName(userString);
        userRoot.setUserObject(userString);
        userRoot.setName(userString);
        userRoot.setBusinessObject(userString);
        userRoot.setIcon(ClientCoreContext.getResourceFactory().getIconUrl(moduleName, "user.png").toString());
        userRoot.setParent(root);
        getTabMap().put(userString, getUserControllers());
        datas.add(userRoot);
		
        roleRoot = new Node("Role.manager");
		String roleString = getString("Role.manager");
		roleRoot.setName(roleString);
		roleRoot.setUserObject(roleString);
		roleRoot.setName(roleString);
		roleRoot.setBusinessObject(roleString);
		roleRoot.setIcon(ClientCoreContext.getResourceFactory().getIconUrl(moduleName, "users-mgnt.png").toString());
		roleRoot.setParent(root);
		getTabMap().put(roleString, getRoleControllers());
		datas.add(roleRoot);

		
		permRoot = new Node("Permission.manager");
		String permString = getString("Permission.manager");
        permRoot.setName(permString);
        permRoot.setUserObject(permString);
        permRoot.setName(permString);
        permRoot.setBusinessObject(permString);
        permRoot.setIcon(ClientCoreContext.getResourceFactory().getIconUrl(moduleName, "permission-group-icon.png").toString());
        permRoot.setParent(root);
        getTabMap().put(permString, getPermissionControllers());
        datas.add(permRoot);
        
        oplogRoot = new Node("smmanager.oplog");
        String oplogString = getString("smmanager.oplog");
        oplogRoot.setName(oplogString);
        oplogRoot.setUserObject(oplogString);
        oplogRoot.setName(oplogString);
        oplogRoot.setBusinessObject(oplogString);
        oplogRoot.setIcon(ClientCoreContext.getResourceFactory().getIconUrl(moduleName, "oplog-icon.png").toString());
        oplogRoot.setParent(root);
        getTabMap().put(oplogString, getOplogControllers());
        datas.add(oplogRoot);
        
        onlineUserRoot = new Node("smmanager.onlineuser");
        String onlineUserString = getString("smmanager.onlineuser");
        onlineUserRoot.setName(onlineUserString);
        onlineUserRoot.setUserObject(onlineUserString);
        onlineUserRoot.setName(onlineUserString);
        onlineUserRoot.setBusinessObject(onlineUserString);
        onlineUserRoot.setIcon(ClientCoreContext.getResourceFactory().getIconUrl(moduleName, "onlineuser-icon.png").toString());
        onlineUserRoot.setParent(root);
        getTabMap().put(onlineUserString, getOnlineUserControllers());
        datas.add(onlineUserRoot);

		if (datas.size() > 1)
			datas.get(1).setSelected(true);
		else
			datas.get(0).setSelected(true);
		return datas;

	}

	private List<AbstractTabController<?, ?, ?>> getRoleControllers() {
		List<AbstractTabController<?, ?, ?>> roleControlerList = new ArrayList<AbstractTabController<?, ?, ?>>();
		SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
		RoleManagerController controller = module.getController(RoleManagerModel.class, RoleManagerView.class, RoleManagerController.class);
		roleControlerList.add(controller);
		return roleControlerList;
	}

	// 添加用戶管理相关的controller
	private List<AbstractTabController<?, ?, ?>> getUserControllers() {
		List<AbstractTabController<?, ?, ?>> userControlerlist = new ArrayList<AbstractTabController<?, ?, ?>>();
		SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
		UserListController userController = module.getController(UserListModel.class, UserListView.class, UserListController.class);
		userControlerlist.add(userController);
		return userControlerlist;
	}
	
	private List<AbstractTabController<?,?,?>> getPermissionControllers(){
        List<AbstractTabController<?, ?, ?>> permControlerList = new ArrayList<AbstractTabController<?, ?, ?>>();
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        PermManagerController permController = module.getController(PermManagerModel.class, PermManagerView.class, PermManagerController.class);
        permControlerList.add(permController);
        return permControlerList;
    }
	
	private List<AbstractTabController<?,?,?>> getOplogControllers(){
        List<AbstractTabController<?, ?, ?>> oplogControlerList = new ArrayList<AbstractTabController<?, ?, ?>>();
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        OplogController oplogController = module.getController(OplogModel.class, OplogView.class, OplogController.class);
        oplogControlerList.add(oplogController);
        return oplogControlerList;
    }
	
	private List<AbstractTabController<?,?,?>> getOnlineUserControllers(){
        List<AbstractTabController<?, ?, ?>> onlineUserControlerList = new ArrayList<AbstractTabController<?, ?, ?>>();
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        OnlineUserController onlineUserController = module.getController(OnlineUserModel.class, OnlineUserView.class, OnlineUserController.class);
        onlineUserControlerList.add(onlineUserController);
        return onlineUserControlerList;
    }

}
