/*
 * $Id: AddDomainOrGroupView.java, 2011-4-19 ÏÂÎç03:50:12 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.topo.view;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;

import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.ComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.menu.action.AbstractButtonAction;
import com.yuep.core.client.component.validate.editor.DefaultXEditor;
import com.yuep.core.client.component.validate.editor.XComboBoxEditor;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.momanager.module.constants.MoManagerModuleConstants;
import com.yuep.nms.core.common.momanager.service.MoManager;

/**
 * <p>
 * Title: AddDomainOrGroupView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron
 * created 2011-4-19 ÏÂÎç03:50:12
 * modified [who date description]
 * check [who date description]
 */
public class AddDomainOrGroupView extends AbstractValidateView<Object> {

    private static final long serialVersionUID = -3252214346664206430L;
    private DefaultXEditor<StringValidator> nameEditor;
    private MoNaming parent;
    private XComboBoxEditor typeEditor;

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {
        okButton.addActionListener(new AbstractButtonAction("") {
            private static final long serialVersionUID = -4625176666445968309L;
            @Override
            protected Object[] commitData(Object... objs) {
                Object selectedItem = typeEditor.getSelectedItem();
                String type = "";
                if (selectedItem != null) {
                    if (selectedItem.equals(ClientCoreContext.getString("AddDomainOrGroupView.type.domain"))) {
                        type = "domain";
                    } else if (selectedItem.equals(ClientCoreContext.getString("AddDomainOrGroupView.type.group"))) {
                        type = "group";
                    }
                }
                String domainName = nameEditor.getText();
                MoManager moManager = ClientCoreContext.getRemoteService(MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
                MoCore moCore = ClientCoreContext.getLocalService("moCore", MoCore.class);
                if(parent==null)
                	parent=moCore.getRootMo().getMoNaming();
                moManager.createManagedDomain(parent, domainName, type);
                return null;
            }
            @Override
            protected void updateUi(Object... objs) {
                dispose();
            }

        });
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel mainPanel = swingFactory.getPanel();
        double[][] ds = { { 10, 100, TableLayout.FILL, 10 }, swingFactory.getTableLayoutRowParam(2, 4, 4) };
        mainPanel.setLayout(swingFactory.getTableLayout(ds));
        JLabel typeLabel = swingFactory.getLabel(new LabelDecorator("AddDomainOrGroupView.type"));
        typeEditor = swingFactory.getXEditor(new ComboBoxEditorDecorator("AddDomainOrGroupView.type"));
        JLabel domainNameLabel = swingFactory.getLabel(new LabelDecorator("AddDomainOrGroupView.name"));
        nameEditor = swingFactory.getXEditor(new StringEditorDecorator("AddDomainOrGroupView.name", true, 1, 64));
        mainPanel.add(typeLabel, "1,1,f,c");
        mainPanel.add(typeEditor, "2,1,f,c");
        mainPanel.add(domainNameLabel, "1,3,f,c");
        mainPanel.add(nameEditor, "2,3,f,c");
        return mainPanel;
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "AddDomainOrGroupView.title";
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<Object> collectData() {
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
        return "AddDomainOrGroupView.title";
    }

    @Override
    protected void initializeData(List<Object> data) {
        if (CollectionUtils.isEmpty(data))
            return;
        typeEditor.addItem(ClientCoreContext.getString("AddDomainOrGroupView.type.domain"));
        typeEditor.addItem(ClientCoreContext.getString("AddDomainOrGroupView.type.group"));
        Object object = data.get(0);
        if (object instanceof MoNaming)
            this.parent = (MoNaming) object;
    }

}
