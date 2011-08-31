/*
 * $Id: RoleAddView.java, 2011-4-2 ÏÂÎç04:02:13 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.collections.CollectionUtils;

import twaver.TDataBox;
import twaver.swing.TableLayout;
import twaver.table.TTable;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.TextAreaEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.validate.editor.XTextAreaEditor;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.client.smmanager.action.SaveRoleAction;
import com.yuep.nms.core.client.smmanager.model.PermGroupModel;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smmanager.model.MoPermission;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: RoleAddView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-2 ÏÂÎç04:02:13
 * modified [who date description]
 * check [who date description]
 */
public class AddRoleView extends AbstractValidateView<Object> {

    private static final long serialVersionUID = 8903481450460942586L;
    protected XTextAreaEditor<StringValidator> roleNameEditor;
    protected XTextAreaEditor<StringValidator> descriptionEditor;
    private TDataBox box;
    private MoTreeTable moTreeTable;

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {
        SaveRoleAction saveAction = new SaveRoleAction(true, null, controller);
        okButton.addActionListener(saveAction);

        moTreeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    List<MoPermission> moPerms = getSelectedMoPerms();
                    if (moPerms == null || moPerms.size() < 1)
                        return;
                    else {
                        MoPermission moPerm = moPerms.get(0);
                        SmManagerClientModule module = ClientCoreContext.getModule(SmManagerClientModule.class);
                        DefaultClientController<PermissionGroup, PermGroupView, PermGroupModel> controller = module
                                .getDefaultClientController(PermGroupView.class, PermGroupModel.class);
                        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                                SmManagerService.class);
                        List<PermissionGroup> permGroupList = smManagerService.getAllPermissionGroups();
                        PermissionGroup perm = new PermissionGroup();
                        perm.setGroupName("");
                        permGroupList.add(perm);
                        if (null != moPerm.getGroupName()) {
                            String existedPermGroup = moPerm.getGroupName();
                            for (int i = 0; i < permGroupList.size(); i++) {
                                PermissionGroup pg = permGroupList.get(i);
                                if (pg.getGroupName().equals(existedPermGroup)) {
                                    permGroupList.remove(pg);
                                    i--;
                                }
                            }
                        }
                        controller.initData(permGroupList, true);
                        WindowManager windowManager = ClientCoreContext.getWindowManager();
                        windowManager.openAsDialog(controller);
                        return;
                    }
                }
            }
        });
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] { { TableLayout.FILL },
                { 4, 80, TableLayout.FILL, 4 } });
        JPanel mainPane = swingFactory.getPanel(tableLayout);
        JPanel contentPane = createCenterPane();
        JPanel treePane = createTreePane();
        mainPane.add(contentPane, "0,1,f,0");
        mainPane.add(treePane, "0,2,f,0");
        return mainPane;
    }

    private JPanel createCenterPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel contentPane = swingFactory.getPanel();
        JPanel roleBasicSep = (JPanel) swingFactory.getXTitleSeparator("Role.basicInfo");
        JLabel roleNameLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext.getString("Role.roleName")));
        JLabel descriptionLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext
                .getString("Role.description")));
        roleNameEditor = swingFactory.getXEditor(new TextAreaEditorDecorator(""));
        descriptionEditor = swingFactory.getXEditor(new TextAreaEditorDecorator(""));
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] { { 10, 130, TableLayout.FILL, 10 },
                swingFactory.getTableLayoutRowParam(5, 2, 3) });
        contentPane.setLayout(tableLayout);
        contentPane.add(roleBasicSep, "0,1,3,c");
        contentPane.add(roleNameLabel, "1,3,f,c");
        contentPane.add(roleNameEditor, "2,3,f,c");
        contentPane.add(descriptionLabel, "1,5,f,c");
        contentPane.add(descriptionEditor, "2,5,f,c");
        return contentPane;
    }

    private JPanel createTreePane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel contentPane = swingFactory.getPanel();
        box = new TDataBox();
        moTreeTable = new MoTreeTable(box);
        JScrollPane scrollPane = swingFactory.getScrollPane(moTreeTable);
        moTreeTable.setAutoResizeMode(TTable.AUTO_RESIZE_ALL_COLUMNS);
        contentPane.setLayout(swingFactory.getTableLayout(new double[][] { { TableLayout.FILL },
                { 20, TableLayout.FILL } }));
        JPanel pemSep = (JPanel) swingFactory.getXTitleSeparator("PermissionGroup");
        contentPane.add(pemSep, "0,0,f,c");
        contentPane.add(scrollPane, "0,1,f,c");
        revalidate();
        repaint();
        return contentPane;
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "Role.title.description";
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<Object> collectData() {
        Role role = new Role();
        role.setRoleName(roleNameEditor.getText());
        role.setDescription(descriptionEditor.getText());
        List<Object> roleList = new ArrayList<Object>();
        roleList.add(role);
        return roleList;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return roleNameEditor;
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
        return "Role.add.title";
    }

    public void initTreeData(List<MoPermission> data) {
        if (CollectionUtils.isEmpty(data))
            return;
        List<MoPermission> mos = (List<MoPermission>) data;
        moTreeTable.getDataBox().clear();
        moTreeTable.addMos(mos);
        moTreeTable.getTree().expandAll();
    }

    public List<MoPermission> getAllMoPerms() {
        return moTreeTable.getAllDatas();
    }

    public List<MoPermission> getSelectedMoPerms() {
        return moTreeTable.getSelectedDatas();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof List) {
            moTreeTable.updateMoTree((List<String>) arg);
        }
    }

}
