/*
 * $Id: SelectRoleView .java, 2011-4-25 上午11:39:06 luwei Exp $
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

import javax.swing.JComponent;
import javax.swing.JPanel;

import twaver.Element;
import twaver.Node;
import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.tree.YuepCheckableFilter;
import com.yuep.core.client.component.tree.XTree;
import com.yuep.core.client.component.validate.editor.XTreeEditor;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.client.smmanager.action.SaveSelectRoleAction;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;
/**
 * <p>
 * Title: SelectRoleView
 * </p>
 * <p>
 * Description:用用户分配角色的View
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 上午11:39:06
 * modified [who date description]
 * check [who date description]
 */
public class SelectRoleView extends AbstractValidateView<Role> {
    private SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
    private XTreeEditor tree;
    List<Element> selectedNodes;

    @Override
    protected void addButtonListener(ClientController controller) {
        SaveSelectRoleAction saveAction = new SaveSelectRoleAction(true, "", controller);
        okButton.addActionListener(saveAction);

    }

    @Override
    protected JComponent createContentPane() {
        JPanel panel = swingFactory.getPanel(swingFactory.getTableLayout(new double[][] {
                { 5, 5, TableLayout.FILL, 5 }, { 5, 23, TableLayout.FILL } }));
        JPanel seperator = (JPanel) swingFactory.getXTitleSeparator("SelectRole.selectRole");
        tree = new XTreeEditor("");
        tree.setRequsted(true);
        tree.setTTreeSelectionMode(XTree.CHECK_DESCENDANT_ANCESTOR_SELECTION);
        tree.setCheckableFilter(new YuepCheckableFilter() {
            @Override
            public boolean isCheckable(Element arg0) {
                return true;
            }
        });
        tree.setRootVisible(false);
        panel.add(seperator, "1,1,2,c");
        panel.add(swingFactory.getScrollPane(tree), "1,2,2,c");

        return panel;
    }

    @Override
    protected String getDescription() {
        // TODO Auto-generated method stub
        return "SelectRole.des";
    }

    @Override
    public List<Role> collectData() {
        selectedNodes = tree.getSelectedNodes();
        List<Role> roles = new ArrayList<Role>();
        for (Element node : selectedNodes) {
            roles.add((Role) node.getUserObject());
        }
        return roles;
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
        return "SelectRole.title";
    }

    private Element createNode(Element parentNode, String key, Role role) {
        Element node = new Node();
        node.setUserObject(key); // 唯一标识
        node.setName(ClientCoreContext.getString(key));
        node.setParent(parentNode);
        node.setIcon(ClientCoreContext.getResourceFactory().getIconUrl("smmanager-client", "users-mgnt.png")
                .toString());
        node.setUserObject(role);
        tree.addNode(node);
        return node;
    }

    private void createTree() {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);

        List<Role> roles = smManagerService.getAllRoles();
        for (Role role : roles) {
            createNode(null, role.getRoleName(), role);
        }
    }

    @Override
    protected void initializeData(List data) {
        createTree();
    }

}
