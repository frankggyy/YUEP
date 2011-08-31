/*
 * $Id: UserListView.java, 2011-4-25 上午11:39:06 luwei Exp $
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.collections.CollectionUtils;

import twaver.swing.TableLayout;
import twaver.table.TTable;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.navigator.AbstractTabView;
import com.yuep.core.client.component.table.TableActionHelper;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.core.client.smmanager.action.ActiveUserAction;
import com.yuep.nms.core.client.smmanager.action.DeactiveUserAction;
import com.yuep.nms.core.client.smmanager.action.DeleteUserAction;
import com.yuep.nms.core.client.smmanager.action.ModifyUserAction;
import com.yuep.nms.core.client.smmanager.action.OpenUserMaintenanceAction;
import com.yuep.nms.core.client.smmanager.controller.UserMaintenanceController;
import com.yuep.nms.core.client.smmanager.model.UserMaintenanceModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.client.smmanager.utils.TimeTableCellRenderer;
import com.yuep.nms.core.client.smmanager.utils.UserStateCellRenderer;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: UserListView
 * </p>
 * <p>
 * Description:展示用户列表的View
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 上午11:39:06
 * modified [who date description]
 * check [who date description]
 */
public class UserListView extends AbstractTabView<User> {

    private static final long serialVersionUID = 1L;
    private XTable<User> table;
    private SwingFactory swingFactory;
    private JButton addBtn;
    private JButton delBtn;
    private JButton activeBtn;
    private JButton inactiveBtn;
    private JButton modifyBtn;
    protected TableActionHelper tableActionHelper;

    @Override
    protected <V, M> void addButtonListener(ClientController<User, V, M> controller) {
        OpenUserMaintenanceAction userManagerAction = new OpenUserMaintenanceAction(OpenUserMaintenanceAction.class.getSimpleName());
        addBtn.setAction(userManagerAction);
        DeleteUserAction delAction = new DeleteUserAction(true, DeleteUserAction.class.getSimpleName(), controller);
        ModifyUserAction modifyAction = new ModifyUserAction(false, ModifyUserAction.class.getSimpleName(), controller);
        modifyBtn.setAction(modifyAction);
        delBtn.setAction(delAction);
        ActiveUserAction activeAction = new ActiveUserAction(true, ActiveUserAction.class.getSimpleName(), controller);
        activeBtn.setAction(activeAction);
        DeactiveUserAction inactiveAction = new DeactiveUserAction(true, DeactiveUserAction.class.getSimpleName(), controller);
        inactiveBtn.setAction(inactiveAction);

        tableActionHelper.addSensitiveAction(delAction);
        tableActionHelper.addSensitiveAction(modifyAction);
        tableActionHelper.addSensitiveAction(activeAction);
        tableActionHelper.addSensitiveAction(inactiveAction);
        table.addMouseListener(new MouseAdapter() {
            @SuppressWarnings("unchecked")
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    List<User> users = table.getSelectionDatas();
                    if (users == null || users.size() < 1) {
                        return;
                    } else {
                        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                                SmManagerService.class);
                        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
                        UserMaintenanceController userManagerController = module.getController(
                                UserMaintenanceModel.class, UserMaintenanceView.class, UserMaintenanceController.class);
                        Object[] objects = new Object[3];
                        objects[0] = smManagerService.getUser(users.get(0).getUserName());
                        // objects[1]存放isView的标志，为true时，表示为查看；
                        objects[1] = true;
                        // objects[2]存放isModify的标示，为true时，表示修改；
                        objects[2] = false;
                        userManagerController.initData(objects);
                        ClientCoreContext.getWindowManager().openAsFrame(userManagerController);
                    }
                }
            }
        });

    }

    @Override
    protected String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected JButton[] getSensitiveButtons() {
        return new JButton[]{};
    }

    @Override
    public List<User> collectData() {
        return table.getAllDatas();
    }

    public List<User> getSelectedData() {
        return table.getSelectionDatas();
    }

    @Override
    public void constructUi() {
        String propertyName = " ";
        String[] propertyNames = new String[] { "userName", "state", "department", "expiredTime", "passwordExpiredTime" };
        swingFactory = ClientCoreContext.getSwingFactory();
        table = swingFactory.getXTableEditor(propertyName, User.class, User.class, "id", propertyNames);
        table.getColumnByName("expiredTime").setCellRenderer(new TimeTableCellRenderer());
        table.getColumnByName("passwordExpiredTime").setCellRenderer(new TimeTableCellRenderer());
        table.getColumnByName("state").setCellRenderer(new UserStateCellRenderer());
        table.setAutoResizeMode(TTable.AUTO_RESIZE_ALL_COLUMNS);
        tableActionHelper = new TableActionHelper(table);
        table.addTableListener(tableActionHelper);
        JScrollPane scrollPane = swingFactory.getScrollPane(table);
        setLayout(swingFactory.getBorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(initButtonPanel(), BorderLayout.EAST);

    }

    public JPanel initButtonPanel() {
        double[][] ds = new double[][] { { 10, TableLayout.FILL, 10 }, swingFactory.getTableLayoutRowParam(6, 5, 5) };
        JPanel btnPanel = swingFactory.getPanel(swingFactory.getTableLayout(ds));
        addBtn = swingFactory.getButton(new ButtonDecorator("Button.add", 'A'));
        modifyBtn = swingFactory.getButton(new ButtonDecorator("Button.modify", 'M'));
        delBtn = swingFactory.getButton(new ButtonDecorator("Button.delete", 'D'));
        activeBtn = swingFactory.getButton(new ButtonDecorator("Button.active", 'E'));
        inactiveBtn = swingFactory.getButton(new ButtonDecorator("Button.inactive", 'I'));
        btnPanel.add(addBtn, "1,1,f,c");
        btnPanel.add(modifyBtn, "1,3,f,c");
        btnPanel.add(delBtn, "1,5,f,c");
        btnPanel.add(activeBtn, "1,7,f,c");
        btnPanel.add(inactiveBtn, "1,9,f,c");
        return btnPanel;
    }

    @Override
    public JButton getDefaultButton() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JComponent getDefaultFocus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getHelpId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof List) {
            table.getTableModel().clearRawData();
            List<User> list = (List<User>) arg;
            if (CollectionUtils.isEmpty(list))
                return;
            for (User user : list) {
                table.addRowData(user);
            }
        }

    }

    public void updateTable(List<Object> list) {
        table.getTableModel().clearRawData();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (Object obj : list) {
            if (obj instanceof User) {
                table.addRowData((User) obj);
            }
        }

    }

}
