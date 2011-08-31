/*
 * $Id: SampleTableDataView.java, 2010-3-29 下午04:16:17 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.view;

import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.CheckBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.EnumComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.NumberEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.component.validate.editor.DefaultXEditor;
import com.yuep.core.client.component.validate.editor.XEnumComboBox;
import com.yuep.core.client.component.validate.editor.XNumberEditor;
import com.yuep.core.client.component.validate.validator.NumberValidator;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.core.client.component.window.MessageDialog.MessageType;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.biz.client.sample.mvc.model.RealSampleTableData;
import com.yuep.nms.biz.client.sample.mvc.model.SampleTableData;

/**
 * <p>
 * Title: SampleTableDataView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 下午04:16:17
 * modified [who date description]
 * check [who date description]
 */
public abstract class SampleTableDataView extends AbstractValidateView<Object> {

    private static final long serialVersionUID = -5827219813421980944L;
    protected XNumberEditor<NumberValidator> idNumberField;
    protected XNumberEditor<NumberValidator> numberField2;
    protected DefaultXEditor<StringValidator> stringField3;
    protected DefaultXEditor<StringValidator> stringField4;
    protected XEnumComboBox enumComboBox5;
    protected DefaultXEditor<StringValidator> stringField6;
    protected JCheckBox checkBox7;

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel contentPane = swingFactory.getPanel();
        contentPane.setBorder(swingFactory.getTitleBorder("控件展示："));
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] {
                { 10, 130, XTableLayout.FILL, 10 }, swingFactory.getTableLayoutRowParam(6,4,6) });
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
        enumComboBox5 = swingFactory.getEnumComboBox(MessageType.class, new EnumComboBoxEditorDecorator("第五列："));
        JLabel label6 = swingFactory.getLabel(new LabelDecorator("第六列："));
        stringField6 = swingFactory.getXEditor(new StringEditorDecorator("第六列：", true, 1, 256));
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
        contentPane.add(stringField6, "2,11,f,c");
        contentPane.add(label7, "1,13,f,c");
        contentPane.add(checkBox7, "2,13,f,c");
        return contentPane;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "添加或者修改表格中的数据";
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#collectData()
     */
    @Override
    public List<Object> collectData() {
        RealSampleTableData realExampleTableData = new RealSampleTableData();
        String text = idNumberField.getText();
        realExampleTableData.setId(new Long(text));
        String text2 = numberField2.getText();
        realExampleTableData.setColumn2(Integer.parseInt(text2));
        String text3 = stringField3.getText();
        realExampleTableData.setColumn3(text3);
        String text4 = stringField4.getText();
        realExampleTableData.setColumn4(text4);
        Object selectedEnumItem = enumComboBox5.getSelectedEnumItem();
        realExampleTableData.setColumn5((MessageType) selectedEnumItem);
        String text5 = stringField6.getText();
        realExampleTableData.setColumn6(text5);
        realExampleTableData.setColumn7(checkBox7.isSelected());
        List<Object> list = new ArrayList<Object>();
        SampleTableData data = new SampleTableData();
        data.setRealExampleTableData(realExampleTableData);
        list.add(data);
        return list;
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
        return "Table Data Editor";
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        return "表格数据编辑";
    }
}
