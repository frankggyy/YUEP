/*
 * $Id: PermGroupView.java, 2011-4-20 ÉÏÎç10:07:35 WangRui Exp $
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;

import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.ComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.client.smmanager.action.AddPermGroupAction;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;

/**
 * <p>
 * Title: PermGroupView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-20 ÉÏÎç10:07:35
 * modified [who date description]
 * check [who date description]
 */
public class PermGroupView extends AbstractValidateView<PermissionGroup> {

    private static final long serialVersionUID = -6487993096026173884L;
    private JComboBox permGroupBox = null;
    private Boolean isCreat;
    private AddPermGroupAction action;

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<PermissionGroup, V, M> controller) {
        action = new AddPermGroupAction("");
        action.setIsCreat(isCreat);
        okButton.addActionListener(action);
        permGroupBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton.setEnabled(true);
            }
        });
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel mainPane = swingFactory.getPanel(swingFactory.getBorderLayout());
        JPanel contentPane = creatContentPane();
        mainPane.add(contentPane, BorderLayout.NORTH);
        return mainPane;
    }

    private JPanel creatContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        double[][] para = { { 4, 80, TableLayout.FILL, 4 }, { 20,TableLayout.FILL } };
        JPanel panel = swingFactory.getPanel(swingFactory.getTableLayout(para));
        JPanel permSelectSep = (JPanel) swingFactory.getXTitleSeparator("PermissionGroup.select");
        JLabel permLable = swingFactory.getLabel(new LabelDecorator(ClientCoreContext.getString("PermissionGroup")));
        permGroupBox = swingFactory.getXEditor(new ComboBoxEditorDecorator("PermissionGroup"));
        permGroupBox.setEditable(true);
        panel.add(permSelectSep, "1,0,3,c");
        panel.add(permLable, "1,1,f,c");
        panel.add(permGroupBox, "2,1,f,c");
        return panel;
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<PermissionGroup> collectData() {
        return null;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        // TODO Auto-generated method stub
        return okButton;
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
        return "PermissionGroup.title";
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#initializeData(java.util.List)
     */
    @Override
    protected void initializeData(List<PermissionGroup> data) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (PermissionGroup pg : data) {
            model.addElement(pg.getGroupName());
        }
        permGroupBox.setModel(model);
        okButton.setEnabled(true);
    }

    public List<String> getDatas() {
        List<String> list = new ArrayList<String>();
        String gName = String.valueOf(permGroupBox.getSelectedItem());
        list.add(gName);
        return list;

    }

    @Override
    protected Dimension getValidateViewPreferredSize() {
        return new Dimension(400, 200);
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#update(java.util.Observable,
     *      java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (null != arg && arg instanceof Boolean) {
            isCreat = (Boolean) arg;
            action.setIsCreat(isCreat);
        }
    }

}
