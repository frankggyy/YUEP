/*
 * $Id: ManagementScopeView.java, 2011-4-23 上午11:22:35 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import twaver.Element;
import twaver.Node;
import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.navigator.AbstractTabView;
import com.yuep.core.client.component.tree.YuepCheckableFilter;
import com.yuep.core.client.component.validate.editor.XTreeEditor;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.smcore.model.User;

/**
 * <p>
 * Title: MgmtScopeView
 * </p>
 * <p>
 * Description:为用添加网元管理范围的View
 * </p>
 * 
 * @author luwei
 * created 2011-4-23 上午11:22:35
 * modified [who date description]
 * check [who date description]
 */
public class MgmtScopeView extends AbstractTabView<Object> {

    private static final long serialVersionUID = 1L;

    private SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
    private XTreeEditor tree;
    private MoCore moCore = ClientCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
    Boolean isView = false;

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
     * @see com.yuep.core.client.mvc.ClientView#constructUi()
     */
    @Override
    public void constructUi() {
        setLayout(swingFactory.getTableLayout(new double[][] { { 5, 10, TableLayout.FILL, 5 },
                { 10, 23, TableLayout.FILL } }));
        JPanel seperator = (JPanel) swingFactory.getXTitleSeparator("UserScope.choose");
        add(seperator, "1,1,3,c");
        tree = new XTreeEditor("UserScope.choose");
        tree.setRootVisible(false);
        tree.setRequsted(true);
        tree.setTTreeSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tree.setCheckableFilter(new YuepCheckableFilter() {
            @Override
            public boolean isCheckable(Element arg0) {
                return true;
            }
        });

        add(swingFactory.getScrollPane(tree), "2,2,f,t");

    }

    public void initTree() {
        List<Mo> allMos = moCore.getAllMos();
        for (Mo mo : allMos) {
            addMo2Tree(mo);
        }
    }

    /**
     * @param mo
     */
    private void addMo2Tree(Mo mo) {
        String moType = mo.getMoNaming().getMoType();
        if ("card".equals(moType))
            return;
        if ("port".equals(moType))
            return;
        MoNaming parent = mo.getParent();
        Element element = null;
        element = new Node(mo.getMoNaming());
        if (element == null)
            return;
        element.setName(mo.getDisplayName());
        element.setUserObject(mo);
        if (parent == null) {
            element.setParent(null);
        } else {
            Element parentElement = tree.getXNode(parent);
            element.setParent(parentElement);
        }
        tree.addNode(element);
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        // TODO Auto-generated method stub
        return null;
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
        return "UseScope.title";
    }

    /**
     * @see com.yuep.core.client.component.navigator.AbstractTabView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {
        // TODO Auto-generated method stub

    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> collectData() {
        List<MoNaming> namingList = new ArrayList<MoNaming>();
        List<Element> selectedElements = tree.getDataBox().getSelectionModel().getAllSelectedElement();
        for (Element element : selectedElements) {
            Mo mo = (Mo) element.getUserObject();
            namingList.add(mo.getMoNaming());
        }
        List<Object> objects = new ArrayList<Object>();
        objects.addAll(namingList);
        return objects;
    }

    @Override
    protected void initializeData(List<Object> data) {
        tree.getDataBox().clear();
        initTree();
        if (data == null || data.size() < 1) {
            setEditabled(this, true, null);
            tree.setEnabled(true);
            return;
        }
        tree.getDataBox().getSelectionModel().clearSelection();
        User user = (User) data.get(0);
        isView = (Boolean) data.get(1);
        List<MoNaming> namings = user.getMgmtScope();
        for (MoNaming moNaming : namings) {
            Element node = tree.getXNode(moNaming);
            node.setSelected(true);
        }
        tree.expandAll();
        tree.getDataBox().getAllElements();
        setEditabled(this, !isView, null);
        tree.setEnabled(!isView);
    }

}
