/*
 * $Id: PermManagerView.java, 2011-4-13 ÏÂÎç05:59:55 WangRui Exp $
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
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;

import twaver.swing.TableLayout;
import twaver.table.TTable;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.navigator.AbstractTabView;
import com.yuep.core.client.component.table.TableActionHelper;
import com.yuep.core.client.component.validate.editor.XTableEditor;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.core.client.smmanager.action.AddPermissionBundleAction;
import com.yuep.nms.core.client.smmanager.action.DeletePermissionBundleAction;
import com.yuep.nms.core.client.smmanager.action.ModifyPermissionBundleAction;
import com.yuep.nms.core.client.smmanager.controller.ModifyPermissionBundleController;
import com.yuep.nms.core.client.smmanager.model.ModifyPermissionBundleModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;

/**
 * <p>
 * Title: PermManagerView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-13 ÏÂÎç05:59:55
 * modified [who date description]
 * check [who date description]
 */
public class PermManagerView extends AbstractTabView<PermissionGroup> {
    private static final long serialVersionUID = -8918495929629882215L;

    protected XTableEditor<PermissionGroup> table;
    protected TableActionHelper tableActionHelper;
    protected JButton addButton;
    protected JButton delButton;
    protected JButton modifyButton;
    private ModifyPermissionBundleAction modifyAction;
    private AddPermissionBundleAction addAction;
    private DeletePermissionBundleAction delAction;

    /**
     * @see com.yuep.core.client.component.navigator.AbstractTabView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<PermissionGroup, V, M> controller) {
        modifyAction = new ModifyPermissionBundleAction(true, ModifyPermissionBundleAction.class.getSimpleName());
        modifyButton.setAction(modifyAction);
        tableActionHelper.addSensitiveAction(modifyAction);

        addAction = new AddPermissionBundleAction(false, ModifyPermissionBundleAction.class.getSimpleName());
        addButton.addActionListener(addAction);

        delAction = new DeletePermissionBundleAction(true, DeletePermissionBundleAction.class.getSimpleName(), controller);
        delButton.setAction(delAction);
        tableActionHelper.addSensitiveAction(delAction);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    List<PermissionGroup> perms = table.getSelectionDatas();
                    if (perms == null || perms.size() < 1) {
                        return;
                    } else {
                        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
                        ModifyPermissionBundleController modifyController = module.getController(
                                ModifyPermissionBundleModel.class, ModifyPermissionBundleView.class,
                                ModifyPermissionBundleController.class);
                        PermissionGroup perm = perms.get(0);
                        WindowManager windowManager = ClientCoreContext.getWindowManager();
                        windowManager.openAsDialog(modifyController);
                        modifyController.initData(perm);
                    }
                }
            }
        });
    }

    /**
     * @see com.yuep.core.client.component.navigator.AbstractTabView#getDescription()
     */
    @Override
    protected String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.yuep.core.client.component.navigator.AbstractTabView#getSensitiveButtons()
     */
    @Override
    protected JButton[] getSensitiveButtons() {
        // TODO Auto-generated method stub
        return new JButton[]{};
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<PermissionGroup> collectData() {
        return table.getSelectionDatas();
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#constructUi()
     */
    @Override
    public void constructUi() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        table = swingFactory.getXTableEditor("", PermissionGroup.class, PermissionGroup.class, "groupName",
                new String[] { "groupName", "description" });
        JPanel contentPanel = swingFactory.getPanel(swingFactory.getBorderLayout());
        setLayout(swingFactory.getTableLayout(new double[][] { { TableLayout.FILL }, { TableLayout.FILL } }));
        contentPanel.add(swingFactory.getScrollPane(table), BorderLayout.CENTER);
        tableActionHelper = new TableActionHelper(table);
        table.addTableListener(tableActionHelper);
        table.setAutoResizeMode(TTable.AUTO_RESIZE_ALL_COLUMNS);
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] { { 4, TableLayout.FILL, 120, 4 },
                { TableLayout.FILL } });
        JPanel mainPane = swingFactory.getPanel();
        mainPane.setLayout(tableLayout);
        mainPane.add(contentPanel, "1,0,f,0");
        mainPane.add(createBtnPane(), "2,0,f,0");
        setLayout(swingFactory.getBorderLayout());
        add(mainPane, BorderLayout.CENTER);

    }

    private Component createBtnPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        double[][] ds = new double[][] { { 8, TableLayout.FILL }, swingFactory.getTableLayoutRowParam(3, 1, 1) };
        JPanel btnPane = new JPanel(swingFactory.getTableLayout(ds));
        addButton = swingFactory.getButton(new ButtonDecorator("PermissionGroup.button.add", 'A'));
        delButton = swingFactory.getButton(new ButtonDecorator("PermissionGroup.button.del", 'D'));
        modifyButton = swingFactory.getButton(new ButtonDecorator("PermissionGroup.button.update", 'U'));
        btnPane.add(addButton, "1,1,f,c");
        btnPane.add(delButton, "1,3,f,c");
        btnPane.add(modifyButton, "1,5,f,c");
        return btnPane;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        return addButton;
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
        return "Permission.title";
    }

    /**
     * @see com.yuep.core.client.component.navigator.AbstractTabView#update(java.util.Observable,
     *      java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof List) {
            table.getTableModel().clearRawData();
            List<PermissionGroup> list = (List<PermissionGroup>) arg;
            if (CollectionUtils.isEmpty(list))
                return;
            for (PermissionGroup perm : list) {
                table.addRowData(perm);
            }
        }
        modifyAction.setEnabled(false);
        addAction.setEnabled(false);
    }

    public void updatePortTable(List<Object> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        table.getTableModel().clearRawData();
        for (Object obj : list) {
            if (obj instanceof PermissionGroup) {
                table.addRowData((PermissionGroup) obj);
            }
        }
    }

}
