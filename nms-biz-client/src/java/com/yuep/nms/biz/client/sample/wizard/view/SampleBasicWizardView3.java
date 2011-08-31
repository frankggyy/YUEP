/*
 * $Id: SampleBasicWizardView3.java, 2010-3-31 上午10:29:29 aaron lee Exp $
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

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.MacAddressEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.PasswordEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.component.validate.editor.XPasswordFieldEditor;
import com.yuep.core.client.component.wizard.BasicWizardView;
import com.yuep.nms.biz.client.sample.wizard.model.SampleWizardData;

/**
 * <p>
 * Title: SampleBasicWizardView3
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 上午10:29:29
 * modified [who date description]
 * check [who date description]
 */
public class SampleBasicWizardView3 extends BasicWizardView<SampleWizardData>{

    private static final long serialVersionUID = 4604848448020253668L;
    private JTextField macAddressField;
    private XPasswordFieldEditor passwordField;

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#collectData()
     */
    @Override
    public List<SampleWizardData> collectData() {
        cacheData.setMac(macAddressField.getText());
        cacheData.setPassword(new String(passwordField.getPassword()));
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
                { 10, 130, XTableLayout.FILL, 10 }, swingFactory.getTableLayoutRowParam(2,4,6) });
        contentPane.setLayout(tableLayout);
        JLabel label1 = swingFactory.getLabel(new LabelDecorator("Mac:"));
        macAddressField = swingFactory.getXEditor(new MacAddressEditorDecorator("Mac:"));
        JLabel label2 = swingFactory.getLabel(new LabelDecorator("Password："));
        passwordField = swingFactory.getXEditor(new PasswordEditorDecorator("Password："));
        
        contentPane.add(label1, "1,1,f,c");
        contentPane.add(macAddressField, "2,1,f,c");
        contentPane.add(label2, "1,3,f,c");
        contentPane.add(passwordField, "2,3,f,c");
        return contentPane;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "这个展示的是Mac地址控件和密码控件";
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#getHeader()
     */
    @Override
    protected String getHeader() {
        return "这是第三步";
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#isEndWizard()
     */
    @Override
    protected boolean isEndWizard() {
        return true;
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
            macAddressField.setText(cacheData.getMac());
            passwordField.setText(cacheData.getPassword());
        }else{
            macAddressField.setText("");
            passwordField.setText("");
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

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#finish(java.util.List)
     */
    @Override
    protected void finish(List<SampleWizardData> list) {
        // 在这里做完成方法的，主要是对这个组合，提交
        dispose();
    }

}
