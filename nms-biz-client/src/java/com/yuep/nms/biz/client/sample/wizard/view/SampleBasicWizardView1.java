/*
 * $Id: SampleBasicWizardView1.java, 2010-3-31 上午10:29:10 aaron lee Exp $
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

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.NumberEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.component.validate.editor.DefaultXEditor;
import com.yuep.core.client.component.validate.editor.XNumberEditor;
import com.yuep.core.client.component.validate.validator.NumberValidator;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.core.client.component.wizard.BasicWizardView;
import com.yuep.nms.biz.client.sample.wizard.model.SampleWizardData;

/**
 * <p>
 * Title: SampleBasicWizardView1
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 上午10:29:10
 * modified [who date description]
 * check [who date description]
 */
public class SampleBasicWizardView1 extends BasicWizardView<SampleWizardData> {

    private static final long serialVersionUID = 4604848448020253668L;
    private XNumberEditor<NumberValidator> idNumberField;
    private XNumberEditor<NumberValidator> numberField2;
    private DefaultXEditor<StringValidator> stringField3;

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#collectData()
     */
    @Override
    public List<SampleWizardData> collectData() {
        if(cacheData == null)
            cacheData = new SampleWizardData();
        cacheData.setId(new Long(idNumberField.getText()));
        cacheData.setInteger1(new Integer(numberField2.getText()));
        cacheData.setStr1(stringField3.getText());
        List<SampleWizardData> list = new ArrayList<SampleWizardData>();
        list.add(cacheData);
        return list;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel contentPane = swingFactory.getPanel();
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] {
                { 10, 130, XTableLayout.FILL, 10 }, swingFactory.getTableLayoutRowParam(3,4,6) });
        contentPane.setLayout(tableLayout);
        JLabel label1 = swingFactory.getLabel(new LabelDecorator("long:"));
        idNumberField = swingFactory.getXEditor(new NumberEditorDecorator("long:", true, 0, 100000));
        JLabel label2 = swingFactory.getLabel(new LabelDecorator("Integer："));
        numberField2 = swingFactory.getXEditor(new NumberEditorDecorator("Integer：", true, 0, 100000));
        JLabel label3 = swingFactory.getLabel(new LabelDecorator("String："));
        stringField3 = swingFactory.getXEditor(new StringEditorDecorator("String：", true, 1, 64));

        contentPane.add(label1, "1,1,f,c");
        contentPane.add(idNumberField, "2,1,f,c");
        contentPane.add(label2, "1,3,f,c");
        contentPane.add(numberField2, "2,3,f,c");
        contentPane.add(label3, "1,5,f,c");
        contentPane.add(stringField3, "2,5,f,c");
        return contentPane;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "这个界面展示了long，int，String型数据控件";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#getHeader()
     */
    @Override
    protected String getHeader() {
        return "这是第一步";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#isEndWizard()
     */
    @Override
    protected boolean isEndWizard() {
        return false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#isStartWizard()
     */
    @Override
    protected boolean isStartWizard() {
        return true;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.wizard.BasicWizardView#initBasicWizardData(java.util.List)
     */
    @Override
    protected void initBasicWizardData(List<SampleWizardData> data) {
        if (cacheData != null) {
            idNumberField.setText(String.valueOf(cacheData.getId()));
            numberField2.setText(String.valueOf(cacheData.getInteger1()));
            stringField3.setText(cacheData.getStr1());
        }else{
            idNumberField.setText("");
            numberField2.setText("");
            stringField3.setText("");
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
