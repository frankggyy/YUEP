/*
 * $Id: RoleManagerView.java, 2011-4-1 下午01:02:48 WangRui Exp $
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
import java.util.ArrayList;
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
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.nms.core.client.smmanager.action.DeleteRoleAction;
import com.yuep.nms.core.client.smmanager.action.OpenAddRoleAction;
import com.yuep.nms.core.client.smmanager.action.OpenUpdateRoleAction;
import com.yuep.nms.core.client.smmanager.model.UpdateRoleModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smmanager.model.MoPermission;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: RoleManagerView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-1 下午01:02:48
 * modified [who date description]
 * check [who date description]
 */
public class RoleManagerView extends AbstractTabView<Role> {
    private static final long serialVersionUID = -4635335049559693110L;
    protected XTable<Role> table;
    protected TableActionHelper tableActionHelper;
    protected JButton addButton;
    protected JButton delButton;
    protected JButton updateButton;
    private DeleteRoleAction delAction;
    private OpenUpdateRoleAction openUpdateAction;

    /**
     * @see com.yuep.core.client.component.navigator.AbstractTabView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<Role, V, M> controller) {
        OpenAddRoleAction addAction = new OpenAddRoleAction("");
        addButton.addActionListener(addAction);
        delAction = new DeleteRoleAction(true, DeleteRoleAction.class.getSimpleName(), controller);
        delButton.setAction(delAction);
        tableActionHelper.addSensitiveAction(delAction);
        openUpdateAction = new OpenUpdateRoleAction(true,OpenUpdateRoleAction.class.getSimpleName());
        updateButton.setAction(openUpdateAction);
        tableActionHelper.addSensitiveAction(openUpdateAction);

        table.addMouseListener(new MouseAdapter() {
            @SuppressWarnings("unchecked")
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    List<Role> roles = table.getSelectionDatas();
                    if (roles == null || roles.size() < 1) {
                        return;
                    } else {
                        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                                SmManagerService.class);
                        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
                        DefaultClientController updateController = module.getDefaultClientController(
                                UpdateRoleView.class, UpdateRoleModel.class);
                        String roleName = roles.get(0).getRoleName();
                        List<MoPermission> moPermList = new ArrayList<MoPermission>();
                        // 获取mo节点
                        MoCore moCore = ClientCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
                        List<Mo> mos = moCore.getMos(new MoFilter() {
                            @Override
                            public boolean accept(Mo mo) {
                                // 此处暂时硬编码
                                if (mo.getType().equals("ne") || mo.getType().equals("nm")
                                        || mo.getType().equals("domain")) {
                                    return true;
                                }
                                return false;
                            }
                        });
                        for (Mo mo : mos) {
                            MoPermission moPerm = new MoPermission();
                            moPerm.setMo(mo);
                            MoPermGroup permGroup = smManagerService.getMoPerm(mo.getMoNaming(), roleName);
                            if (null != permGroup)
                                moPerm.setGroupName(permGroup.getGroupName());
                            moPermList.add(moPerm);
                        }

                        updateController.initData(roles);
                        ((UpdateRoleView) updateController.getClientView()).initTreeData(moPermList);
                        WindowManager windowManager = ClientCoreContext.getWindowManager();
                        windowManager.openAsDialog(updateController);
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
        return new JButton[] {};
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<Role> collectData() {
        return table.getSelectionDatas();
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#constructUi()
     */
    @Override
    public void constructUi() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        table = swingFactory.getXTableEditor("", Role.class, Role.class, "roleName", new String[] { "roleName",
                "description" });
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
        addButton = swingFactory.getButton(new ButtonDecorator("Role.button.add", 'A'));
        delButton = swingFactory.getButton(new ButtonDecorator("Role.button.del", 'D'));
        updateButton = swingFactory.getButton(new ButtonDecorator("Role.button.update", 'U'));
        btnPane.add(addButton, "1,1,f,c");
        btnPane.add(delButton, "1,3,f,c");
        btnPane.add(updateButton, "1,5,f,c");
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
        return "Role.title";
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
            List<Role> list = (List<Role>) arg;
            if (CollectionUtils.isEmpty(list))
                return;
            for (Role role : list) {
                table.addRowData(role);
            }
        }
        delAction.setEnabled(false);
        openUpdateAction.setEnabled(false);
    }

    public void updateRoleTable(List<Object> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        table.getTableModel().clearRawData();
        for (Object obj : list) {
            if (obj instanceof Role) {
                table.addRowData((Role) obj);
            }
        }
    }

}
