/*
 * $Id: PermissionBundlePanel.java, 2011-4-14 上午10:48:49 WangRui Exp $
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import twaver.Element;
import twaver.Node;
import twaver.TDataBox;
import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.TextAreaEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.tree.YuepCheckableFilter;
import com.yuep.core.client.component.tree.XTree;
import com.yuep.core.client.component.validate.editor.XTextAreaEditor;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.nms.core.common.smcore.model.Permission;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smmanager.model.ModulePermission;

/**
 * <p>
 * Title: PermissionBundlePanel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-14 上午10:48:49
 * modified [who date description]
 * check [who date description]
 */
public class PermissionBundlePanel extends JPanel {
    private static final long serialVersionUID = 791344606384266984L;

    private static final String moPerfix="perm.";
    private XTextAreaEditor<StringValidator> tfPermGroupName;
    private XTextAreaEditor<StringValidator> tfPermGroupDescription;
    private TDataBox permGroupDataBox = new TDataBox();
    private XTree tree;

    public void initPanel() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        setLayout(swingFactory.getBorderLayout());

        double[][] ds = new double[][] { { 10, 120, TableLayout.FILL, 10 },
                { 20, 20, 10, 20, 10, 20, 10, TableLayout.FILL, 10 } };
        JPanel mainPanel = new JPanel(swingFactory.getTableLayout(ds));

        JPanel pemSep = (JPanel) swingFactory.getXTitleSeparator("PermissionGroup.basicInfo");
        JLabel nameLabel = swingFactory.getLabel(new LabelDecorator("smmanager.permissionbundle.name"));
        tfPermGroupName = swingFactory.getXEditor(new TextAreaEditorDecorator(""));
        mainPanel.add(pemSep, "0,0,3,c");
        mainPanel.add(nameLabel, "1,1,f,c");
        mainPanel.add(tfPermGroupName, "2,1,f,c");

        JLabel descriptionLabel = swingFactory.getLabel(new LabelDecorator("smmanager.permissionbundle.description"));
        tfPermGroupDescription = swingFactory.getXEditor(new TextAreaEditorDecorator(""));
        mainPanel.add(descriptionLabel, "1,3,f,c");
        mainPanel.add(tfPermGroupDescription, "2,3,f,c");

        JLabel permGroupLabel = swingFactory.getLabel(new LabelDecorator("smmanager.permissionbundle.usergroupbundle"));
        mainPanel.add(permGroupLabel, "1,5,2,c");

        tree = swingFactory.getXTree();
        tree.setTTreeSelectionMode(XTree.CHECK_DESCENDANT_ANCESTOR_SELECTION);
        tree.setCheckableFilter(new YuepCheckableFilter() {
            @Override
            public boolean isCheckable(Element arg0) {
                return true;
            }
        });
        tree.setRootVisible(false);
        tree.setDataBox(permGroupDataBox);
        JScrollPane scrollPane = swingFactory.getScrollPane();
        scrollPane.getViewport().add(tree);
        mainPanel.add(scrollPane, "1,7,2,c");
        add(mainPanel, BorderLayout.CENTER);
    }

    public void update(List<ModulePermission> moduleList, PermissionGroup currentPermGroup) {
        permGroupDataBox.clear();
        HashSet<String> permNames = new HashSet<String>();
        if (currentPermGroup == null) { // 新建
            createPermTree(null, permNames, moduleList);
            tfPermGroupName.setEnabled(true);
            tfPermGroupName.setText("");
            tfPermGroupDescription.setText("");
        } else { // 修改或查看
            tfPermGroupName.setEnabled(false);
            tfPermGroupName.setText(currentPermGroup.getGroupName());
            tfPermGroupDescription.setText(currentPermGroup.getDescription());

            List<String> permissionNames = currentPermGroup.getPermissions();
            if (CollectionUtils.isNotEmpty(permissionNames))
                permNames.addAll(permissionNames);
            createPermTree(null, permNames, moduleList);
        }
    }

    /**
     * @param userGroupPerms
     * @param permissionGroup
     */
    private void createPermTree(Element parentNode, Set<String> userGroupPerms, List<ModulePermission> moduleList) {
        for (ModulePermission emsModule : moduleList) {
            String moduleDescription = ClientCoreContext.getString(moPerfix+emsModule.getDescription());
            List<Permission> perms = emsModule.getPermissions();
            Element moduleNode = createNode(parentNode, moduleDescription, emsModule.getModuleName());
            if (CollectionUtils.isNotEmpty(perms)) {
                Element childrenNode ;
                for (Permission perm : perms) {                   
                    String description = ClientCoreContext.getString(perm.getDescription());
                    childrenNode = createNode(moduleNode, description, perm.getPermissionId());
                    if (userGroupPerms.contains(perm.getPermissionId())) {
                        childrenNode.setSelected(true);
                        if (!moduleNode.isSelected())
                            moduleNode.setSelected(true);

                    } else {
                        childrenNode.setSelected(false);
                    }
                }
            }

            List<ModulePermission> subModules = emsModule.getChildren();
            if (subModules != null) // 递归
                createPermTree(moduleNode, userGroupPerms, subModules);

        }
    }

    private Element createNode(Element parentNode, String description, String key) {
        Element node = new Node();
        node.setUserObject(key); // 唯一标识
        if (StringUtils.isNotEmpty(description))
            node.setName(description);
        else
            node.setName(ClientCoreContext.getString(moPerfix+key));
        node.setParent(parentNode);
        node.setIcon(ClientCoreContext.getResourceFactory().getIconUrl("smmanager-client", "permission-group-icon.png")
                .toString());
        permGroupDataBox.addElement(node);
        return node;
    }

    @SuppressWarnings("unchecked")
    public PermissionGroup collectData() {
        PermissionGroup group = new PermissionGroup();
        group.setDescription(tfPermGroupDescription.getText());
        group.setGroupName(tfPermGroupName.getText());
        List<String> permissionNames = new ArrayList<String>();
        group.setPermissions(permissionNames);

        List<Element> allElements = permGroupDataBox.getAllElements();
        if (CollectionUtils.isEmpty(allElements))
            return group;
        for (Element node : allElements) {
            if (node.isSelected() && node.childrenSize() == 0) { // 叶子节点是权限项
                Object userObject = node.getUserObject();
                String permName = (String) userObject;
                permissionNames.add(permName);
            }
        }
        return group;
    }

}
