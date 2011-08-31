/*
 * $Id: SampleSingleTabNavigatorView.java, 2010-3-30 上午11:22:47 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.navigator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.factory.decorator.editor.CheckBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.EnumComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.NumberEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.component.navigator.AbstractTabView;
import com.yuep.core.client.component.validate.editor.DefaultXEditor;
import com.yuep.core.client.component.validate.editor.XEnumComboBox;
import com.yuep.core.client.component.validate.editor.XNumberEditor;
import com.yuep.core.client.component.validate.validator.NumberValidator;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.core.client.component.window.MessageDialog.MessageType;
import com.yuep.core.client.mvc.ClientController;

/**
 * <p>
 * Title: SampleSingleTabNavigatorView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-30 上午11:22:47
 * modified [who date description]
 * check [who date description]
 */
public class SampleSingleTabNavigatorView extends AbstractTabView<Object>{

    private static final long serialVersionUID = 8247123315824009660L;

    protected XNumberEditor<NumberValidator> idNumberField;
    protected XNumberEditor<NumberValidator> numberField2;
    protected DefaultXEditor<StringValidator> stringField3;
    protected DefaultXEditor<StringValidator> stringField4;
    protected XEnumComboBox enumComboBox5;
    protected XNumberEditor<NumberValidator> numberField6;
    protected JCheckBox checkBox7;

    private JButton okButton;
    
    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractTabView#addButtonListener(com.ycignp.veapo.client.framework.module.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractTabView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "这事一个单Tab页的例子";
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractTabView#getSensitiveButtons()
     */
    @Override
    protected JButton[] getSensitiveButtons() {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#collectData()
     */
    @Override
    public List<Object> collectData() {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#constructUi()
     */
    @Override
    public void constructUi() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel contentPane = swingFactory.getPanel();
        contentPane.setBorder(swingFactory.getTitleBorder("控件展示："));
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] {
                { 10, 130, XTableLayout.FILL, 10 }, swingFactory.getTableLayoutRowParam(7,4,6) });
        contentPane.setLayout(tableLayout);
        JLabel label1 = swingFactory.getLabel(new LabelDecorator("ID:"));
        idNumberField = swingFactory.getXEditor(new NumberEditorDecorator("ID:", true, 0, 100000));
        JLabel label2 = swingFactory.getLabel(new LabelDecorator("第二列："));
        numberField2 = swingFactory.getXEditor(new NumberEditorDecorator("第二列：", true, 0, 100000));
        JLabel label3 = swingFactory.getLabel(new LabelDecorator("第三列："));
        stringField3 = swingFactory.getXEditor(new StringEditorDecorator("第三列：", true, 1, 64));
        JLabel label4 = swingFactory.getLabel(new LabelDecorator("第四列："));
        stringField4 = swingFactory.getXEditor(new StringEditorDecorator("第四列：", true, 1, 64));
        JLabel label5 = swingFactory.getLabel(new LabelDecorator("第五列："));
        enumComboBox5 = swingFactory.getEnumComboBox(MessageType.class,new EnumComboBoxEditorDecorator( "第五列："));
        JLabel label6 = swingFactory.getLabel(new LabelDecorator("第六列："));
        numberField6 = swingFactory.getXEditor(new NumberEditorDecorator("第六列：", true, 0, 100000));
        JLabel label7 = swingFactory.getLabel(new LabelDecorator("第七列："));
        checkBox7 = swingFactory.getXEditor(new CheckBoxEditorDecorator("第七列："));
        
        contentPane.add(label1, "1,1,f,c");
        contentPane.add(idNumberField, "2,1,f,c");
        contentPane.add(label2, "1,3,f,c");
        contentPane.add(numberField2, "2,3,f,c");
        contentPane.add(label3, "1,5,f,c");
        contentPane.add(stringField3, "2,5,f,c");
        contentPane.add(label4, "1,7,f,c");
        contentPane.add(stringField4, "2,7,f,c");
        contentPane.add(label5, "1,9,f,c");
        contentPane.add(enumComboBox5, "2,9,f,c");
        contentPane.add(label6, "1,11,f,c");
        contentPane.add(numberField6, "2,11,f,c");
        contentPane.add(label7, "1,13,f,c");
        contentPane.add(checkBox7, "2,13,f,c");
        setLayout(new BorderLayout());
        add(contentPane, BorderLayout.CENTER);
        okButton = swingFactory.getButton(new ButtonDecorator("common.button.save",'o'));
        JPanel southPane = swingFactory.getPanel(swingFactory.getFlowLayout(FlowLayout.RIGHT));
        southPane.add(okButton);
        add(southPane,BorderLayout.SOUTH);
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        return okButton;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return idNumberField;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        return "SINGLE Tab View";
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        return null;
    }

}
