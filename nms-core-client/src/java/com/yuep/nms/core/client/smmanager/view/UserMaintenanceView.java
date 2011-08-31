/*
 * $Id: UserMaintenanceView.java, 2011-4-25 上午11:39:06 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.navigator.AbstractTabController;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.client.smmanager.action.SaveUserAction;
import com.yuep.nms.core.client.smmanager.controller.MgmtScopeController;
import com.yuep.nms.core.client.smmanager.controller.UserInfoController;
import com.yuep.nms.core.client.smmanager.model.MgmtScopeModel;
import com.yuep.nms.core.client.smmanager.model.UserInfoModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;

/**
 * <p>
 * Title: UserMaintenanceView
 * </p>
 * <p>
 * Description:维护用户基本信息及网元管理范围的View
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 上午11:39:06
 * modified [who date description]
 * check [who date description]
 */
public class UserMaintenanceView extends AbstractValidateView<Object> {

    private static final long serialVersionUID = -8595968454753127198L;

    private SwingFactory swingFactory = ClientCoreContext.getSwingFactory();

    private List<AbstractTabController<?, ?, ?>> tabControllers;

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {

        SaveUserAction saveAction = new SaveUserAction(true, "", controller);

        okButton.addActionListener(saveAction);

    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        JPanel panel = swingFactory.getPanel(swingFactory.getBorderLayout());
        JTabbedPane tpPanel = swingFactory.getTabbedPane();
        tabControllers = new ArrayList<AbstractTabController<?, ?, ?>>();
        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
        MgmtScopeController managementScopeController = module.getController(MgmtScopeModel.class,
                MgmtScopeView.class, MgmtScopeController.class);
        UserInfoController userInfoController = module.getController(UserInfoModel.class, UserInfoView.class,
                UserInfoController.class);
        tabControllers.add(userInfoController);
        tabControllers.add(managementScopeController);
        for (AbstractTabController<?, ?, ?> controller : tabControllers) {
            String title = ClientCoreContext.getString(controller.getClientView().getTitle());
            controller.initView();
            tpPanel.add(title, controller.getClientView());
        }
        panel.add(tpPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        // TODO Auto-generated method stub
        return "UserManager.des";
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<Object> collectData() {
        List<Object> objects = new ArrayList<Object>();
        for (AbstractTabController<?, ?, ?> controller : tabControllers) {
            controller.collectData();
            objects.add(controller.getDatas());
        }
        return objects;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return "UserManager.title";
    }

    @Override
    protected Dimension getValidateViewPreferredSize() {
        return new Dimension(800, 620);
    }

    @Override
    protected void initializeData(List<Object> data) {
        for (AbstractTabController<?, ?, ?> controller : tabControllers) {
            controller.initData(data);
        }
    }

}
