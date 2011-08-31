/*
 * $Id: SampleBasicWizardView2.java, 2010-3-31 上午10:29:21 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.wizard.view;

import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.CheckBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.EnumComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.IpAddressEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.validate.editor.XEnumComboBox;
import com.yuep.core.client.component.validate.editor.XIpAddressEditor;
import com.yuep.core.client.component.validate.validator.IpAddressValidator;
import com.yuep.core.client.component.window.MessageDialog.MessageType;
import com.yuep.core.client.component.wizard.BasicWizardView;
import com.yuep.nms.biz.client.sample.wizard.model.SampleWizardData;

/**
 * <p>
 * Title: SampleBasicWizardView2
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 上午10:29:21
 * modified [who date description]
 * check [who date description]
 */
public class SampleBasicWizardView2 extends BasicWizardView<SampleWizardData>{

    private static final long serialVersionUID = 4604848448020253668L;
    private XEnumComboBox enumComboBox;
    private XIpAddressEditor<IpAddressValidator> ipAddressField;
    private JCheckBox checkBox;

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#collectData()
     */
    @Override
    public List<SampleWizardData> collectData() {
        cacheData.setMessageType((MessageType) enumComboBox.getSelectedEnumItem());
        cacheData.setIp(ipAddressField.getText());
        cacheData.setB1(checkBox.isSelected());
        List<SampleWizardData> list = new ArrayList<SampleWizardData>();
        list.add(cacheData);
        return list;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel contentPane = swingFactory.getPanel();
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] {
                { 10, 130, TableLayout.FILL, 10 }, swingFactory.getTableLayoutRowParam(3,4,6) });
        contentPane.setLayout(tableLayout);
        JLabel label1 = swingFactory.getLabel(new LabelDecorator("Enum:"));
        enumComboBox = swingFactory.getEnumComboBox(MessageType.class,new EnumComboBoxEditorDecorator("Enum:"));
        JLabel label2 = swingFactory.getLabel(new LabelDecorator("IP："));
        ipAddressField = swingFactory.getXEditor(new IpAddressEditorDecorator("IP：", true));
        JLabel label3 = swingFactory.getLabel(new LabelDecorator("boolean："));
        checkBox = swingFactory.getXEditor(new CheckBoxEditorDecorator("boolean："));
        
        contentPane.add(label1, "1,1,f,c");
        contentPane.add(enumComboBox, "2,1,f,c");
        contentPane.add(label2, "1,3,f,c");
        contentPane.add(ipAddressField, "2,3,f,c");
        contentPane.add(label3, "1,5,f,c");
        contentPane.add(checkBox, "2,5,f,c");
        return contentPane;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "这里展示的是IP地址控件，CheckBox控件和枚举类型的ComboBoxEditor";
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#getHeader()
     */
    @Override
    protected String getHeader() {
        return "这是第二步";
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#isEndWizard()
     */
    @Override
    protected boolean isEndWizard() {
        return false;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#isStartWizard()
     */
    @Override
    protected boolean isStartWizard() {
        return false;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#initBasicWizardData(java.util.List)
     */
    @Override
    protected void initBasicWizardData(List<SampleWizardData> data) {
        if (cacheData != null) {
            enumComboBox.setSelectedItem(cacheData.getMessageType());
            ipAddressField.setText(cacheData.getIp());
            checkBox.setSelected(cacheData.isB1());
        }else{
            enumComboBox.setSelectedItem(MessageType.Error);
            ipAddressField.setText("");
            checkBox.setSelected(false);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#validateData()
     */
    @Override
    protected boolean validateData() {
        return true;
    }

}
