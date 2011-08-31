/*
 * $Id: SampleEditorView.java, 2010-3-31 下午03:47:09 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.editor.view;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;

import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.CheckBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.EnumComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.IpAddressEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.MacAddressEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.NumberEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.PasswordEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.TextAreaEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.validate.editor.DefaultXEditor;
import com.yuep.core.client.component.validate.editor.XCheckBoxEditor;
import com.yuep.core.client.component.validate.editor.XEnumComboBox;
import com.yuep.core.client.component.validate.editor.XIpAddressEditor;
import com.yuep.core.client.component.validate.editor.XMacAddressEditor;
import com.yuep.core.client.component.validate.editor.XNumberEditor;
import com.yuep.core.client.component.validate.editor.XPasswordFieldEditor;
import com.yuep.core.client.component.validate.editor.XTextAreaEditor;
import com.yuep.core.client.component.validate.validator.IpAddressValidator;
import com.yuep.core.client.component.validate.validator.MacAddressValidator;
import com.yuep.core.client.component.validate.validator.NumberValidator;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.core.client.component.window.MessageDialog.MessageType;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.editor.controller.SampleEditorController;
import com.yuep.nms.biz.client.sample.editor.model.SampleEditorData;
import com.yuep.nms.biz.client.sample.editor.model.SampleEditorModel;

/**
 * <p>
 * Title: SampleEditorView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 下午03:47:09
 * modified [who date description]
 * check [who date description]
 */
public class SampleEditorView extends AbstractValidateView<SampleEditorData> {

    private static final long serialVersionUID = -5827219813421980944L;
    private XEnumComboBox comboBox;
    private XCheckBoxEditor checkbox;
    private XMacAddressEditor<MacAddressValidator> macField;
    private XIpAddressEditor<IpAddressValidator> ipField;
    private XTextAreaEditor<StringValidator> areaField;
    private XPasswordFieldEditor passwordField;
    private DefaultXEditor<StringValidator> stringField;
    private XNumberEditor<NumberValidator> numberField;

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel contentPane = swingFactory.getPanel();
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] { { 10, 130, TableLayout.FILL, 10 },
                swingFactory.getTableLayoutRowParam(8, 4, 6) });
        contentPane.setLayout(tableLayout);
        JLabel label1 = swingFactory.getLabel(new LabelDecorator("SampleEditorView.label1"));
        numberField = swingFactory.getXEditor(new NumberEditorDecorator("SampleEditorView.label1", true, 0, 100000));
        numberField.setDescription("SampleEditorView.numberField.desc");
        JLabel label2 = swingFactory.getLabel(new LabelDecorator("SampleEditorView.label2"));
        stringField = swingFactory.getXEditor(new StringEditorDecorator("SampleEditorView.label2", true, 1, 128));
        stringField.setDescription("SampleEditorView.StringField.desc");
        JLabel label3 = swingFactory.getLabel(new LabelDecorator("SampleEditorView.label3"));
        passwordField = swingFactory.getXEditor(new PasswordEditorDecorator("SampleEditorView.label3"));
        passwordField.setDescription("SampleEditorView.passwordField.desc");
        JLabel label4 = swingFactory.getLabel(new LabelDecorator("SampleEditorView.label4"));
        areaField = swingFactory.getXEditor(new TextAreaEditorDecorator("SampleEditorView.label4", true, 1, 256));
        areaField.setDescription("SampleEditorView.textArea.desc");
        JLabel label5 = swingFactory.getLabel(new LabelDecorator("IP地址："));
        ipField = swingFactory.getXEditor(new IpAddressEditorDecorator("IP地址：", true));
        ipField.setDescription("这是一个IP地址编辑的控件");
        JLabel label6 = swingFactory.getLabel(new LabelDecorator("MAC地址："));
        macField = swingFactory.getXEditor(new MacAddressEditorDecorator("MAC地址：", true));
        macField.setDescription("这是一个MAC地址编辑的控件");
        JLabel label7 = swingFactory.getLabel(new LabelDecorator("Checkbox："));
        checkbox = swingFactory.getXEditor(new CheckBoxEditorDecorator("Checkbox："));
        checkbox.setDescription("这是一个Boolean型变量通过CheckBox编辑的控件");
        JLabel label8 = swingFactory.getLabel(new LabelDecorator("ComboBox："));
        comboBox = swingFactory.getEnumComboBox(MessageType.class, new EnumComboBoxEditorDecorator("ComboBox："));
        comboBox.setDescription("这是一个枚举数据通过ComboBoxEditor编辑的控件");
        contentPane.add(label1, "1,1,f,c");
        contentPane.add(numberField, "2,1,f,c");
        contentPane.add(label2, "1,3,f,c");
        contentPane.add(stringField, "2,3,f,c");
        contentPane.add(label3, "1,5,f,c");
        contentPane.add(passwordField, "2,5,f,c");
        contentPane.add(label4, "1,7,f,c");
        contentPane.add(areaField, "2,7,f,c");
        contentPane.add(label5, "1,9,f,c");
        contentPane.add(ipField, "2,9,f,c");
        contentPane.add(label6, "1,11,f,c");
        contentPane.add(macField, "2,11,f,c");
        contentPane.add(label7, "1,13,f,c");
        contentPane.add(checkbox, "2,13,f,c");
        contentPane.add(label8, "1,15,f,c");
        contentPane.add(comboBox, "2,15,f,c");
        return contentPane;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "SampleEditorView.desc";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#collectData()
     */
    @Override
    public List<SampleEditorData> collectData() {
        SampleEditorData exampleEditorData = new SampleEditorData();
        exampleEditorData.setNumber(new Integer(numberField.getText()));
        exampleEditorData.setStr(stringField.getText());
        exampleEditorData.setArea(areaField.getText());
        exampleEditorData.setCheckbox(checkbox.isSelected());
        exampleEditorData.setComboBox((MessageType) comboBox.getSelectedEnumItem());
        exampleEditorData.setIp(ipField.getText());
        exampleEditorData.setMac(macField.getText());
        exampleEditorData.setPassword(new String(passwordField.getPassword()));
        List<SampleEditorData> list = new ArrayList<SampleEditorData>();
        list.add(exampleEditorData);
        return list;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return macField;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        return "Data Editor";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        return "SampleEditorView.title";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#addButtonListener(com.ycignp.veapo.client.framework.module.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<SampleEditorData, V, M> controller) {
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
                module.getController(SampleEditorModel.class, SampleEditorView.class, SampleEditorController.class)
                        .getDatas();
                DialogUtils.showInfoDialog(getWindow(), "保存成功！");
            }

        });
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#initializeData(java.util.List)
     */
    @Override
    protected void initializeData(List<SampleEditorData> data) {
        if (CollectionUtils.isEmpty(data)) {
            numberField.setText("");
            stringField.setText("");
            checkbox.setSelected(false);
            comboBox.setSelectedItem(MessageType.Error);
            ipField.setText("");
            macField.setText("");
            areaField.setText("");
            passwordField.setText("");
        } else {
            SampleEditorData exampleEditorData = data.get(0);
            numberField.setText(String.valueOf(exampleEditorData.getNumber()));
            stringField.setText(exampleEditorData.getStr());
            checkbox.setSelected(exampleEditorData.isCheckbox());
            comboBox.setSelectedItem(exampleEditorData.getComboBox());
            ipField.setText(exampleEditorData.getIp());
            macField.setText(exampleEditorData.getMac());
            areaField.setText(exampleEditorData.getArea());
            passwordField.setText(exampleEditorData.getPassword());
        }
    }
}
