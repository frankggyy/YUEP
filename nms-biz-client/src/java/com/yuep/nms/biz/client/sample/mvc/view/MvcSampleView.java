/*
 * $Id: MvcSampleView.java, 2010-3-29 下午02:16:41 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.view;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.apache.commons.collections.CollectionUtils;

import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.factory.decorator.editor.EnumComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.NumberEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.table.TableActionHelper;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.component.validate.editor.XEnumComboBox;
import com.yuep.core.client.component.validate.editor.XNumberEditor;
import com.yuep.core.client.component.validate.validator.NumberValidator;
import com.yuep.core.client.component.window.MessageDialog.MessageType;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.biz.client.sample.mvc.action.DeleteSampleTableDataAction;
import com.yuep.nms.biz.client.sample.mvc.action.OpenCreateRealSampleTableDataAction;
import com.yuep.nms.biz.client.sample.mvc.action.OpenModifyRealSampleTableDataAction;
import com.yuep.nms.biz.client.sample.mvc.model.RealSampleTableData;
import com.yuep.nms.biz.client.sample.mvc.model.SampleTableData;

/**
 * <p>
 * Title: MvcSampleView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 下午02:16:41
 * modified [who date description]
 * check [who date description]
 */
public class MvcSampleView extends AbstractValidateView<Object> {

    private static final long serialVersionUID = 6267153258086438377L;
    private XNumberEditor<NumberValidator> numberEditor;
    private XEnumComboBox enumComboBox;
    private JButton createButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private XTable<SampleTableData> table;
    private TableActionHelper actionHelper;
    private OpenModifyRealSampleTableDataAction modifyAction;
    private DeleteSampleTableDataAction deleteAction;

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#addButtonListener(com.ycignp.veapo.client.framework.module.mvc.ClientController)
     */
    @Override
    protected void addButtonListener(ClientController controller) {
        OpenCreateRealSampleTableDataAction createAction = new OpenCreateRealSampleTableDataAction("OpenCreateRealSampleTableDataAction");
        createButton.addActionListener(createAction);
        modifyAction = new OpenModifyRealSampleTableDataAction("OpenModifyRealSampleTableDataAction","common.button.modify", 'm');
        modifyButton.setAction(modifyAction);
        actionHelper.addSensitiveAction(modifyAction);
        deleteAction = new DeleteSampleTableDataAction("DeleteSampleTableDataAction", controller,"common.button.delete", 'd');
        deleteButton.setAction(deleteAction);
        actionHelper.addSensitiveAction(deleteAction);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel contentPane = swingFactory.getPanel(swingFactory.getBorderLayout());
        contentPane.add(createNorthPane(), BorderLayout.NORTH);
        contentPane.add(createCenterPane(), BorderLayout.CENTER);
        return contentPane;
    }

    private JComponent createNorthPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel mainPane = swingFactory.getPanel(swingFactory.getBorderLayout());
        mainPane.setBorder(swingFactory.getEmptyBorder(0, 5, 0, 5));
        JPanel contentPane = swingFactory.getPanel();
        mainPane.add(contentPane, BorderLayout.CENTER);
        contentPane.setBorder(swingFactory.getTitleBorder("控件展示："));
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] { { 10, 130, TableLayout.FILL, 10 },
                swingFactory.getTableLayoutRowParam(2, 0, 6) });
        contentPane.setLayout(tableLayout);
        JLabel numberLabel = swingFactory.getLabel(new LabelDecorator("数字控件："));
        numberEditor = swingFactory.getXEditor(new NumberEditorDecorator("数字控件：", true, 1, 22));
        JLabel enumLabel = swingFactory.getLabel(new LabelDecorator("枚举选择框控件："));
        enumComboBox = swingFactory.getEnumComboBox(MessageType.class, new EnumComboBoxEditorDecorator("枚举选择框控件："));
        contentPane.add(numberLabel, "1,1,f,c");
        contentPane.add(numberEditor, "2,1,f,c");
        contentPane.add(enumLabel, "1,3,f,c");
        contentPane.add(enumComboBox, "2,3,f,c");
        return mainPane;
    }

    private JComponent createCenterPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel mainPane = swingFactory.getPanel(swingFactory.getBorderLayout());
        mainPane.setBorder(swingFactory.getEmptyBorder(0, 5, 0, 5));
        JPanel contentPane = swingFactory.getPanel();
        mainPane.add(contentPane, BorderLayout.CENTER);
        contentPane.setBorder(swingFactory.getTitleBorder("表格展示"));
        LayoutManager tableLayout = swingFactory.getTableLayout(new double[][] { { TableLayout.FILL, 100 },
                { TableLayout.FILL } });
        contentPane.setLayout(tableLayout);
        table = swingFactory.getXTableEditor("", SampleTableData.class, RealSampleTableData.class, "id", new String[] {
                "column1", "column2", "column5", "column6", "column7" });
        actionHelper = new TableActionHelper(table);
        table.addTableListener(actionHelper);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = swingFactory.getScrollPane(table);
        createButton = swingFactory.getButton(new ButtonDecorator("common.button.create", 'c'));
        modifyButton = swingFactory.getButton(new ButtonDecorator());
        deleteButton = swingFactory.getButton(new ButtonDecorator());
        JPanel pane = swingFactory.getPanel();
        LayoutManager actionTableLayout = swingFactory.getTableLayout(new double[][] { { 10, TableLayout.FILL, 5 },
                { 20, 4, 20, 4, 20, TableLayout.FILL } });
        pane.setLayout(actionTableLayout);
        pane.add(modifyButton, "1,2,f,c");
        pane.add(deleteButton, "1,4,f,c");
        pane.add(createButton, "1,0,f,c");

        contentPane.add(scrollPane, "0,0,f,0");
        contentPane.add(pane, "1,0,f,0");
        return mainPane;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "这里是具体描述信息";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.validate.AbstractValidateView#getHeader()
     */
    @Override
    protected String getHeader() {
        return "这里是标题信息";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.validate.AbstractValidateView#getMessageLogoName()
     */
    @Override
    protected String getMessageLogoName() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.module.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return numberEditor;
    }

    public void deleteSelectionExampleTableData() {
        SampleTableData selectionExampleTableData = table.getSelectionData();
        table.deleteRowData(selectionExampleTableData);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#collectData()
     */
    @Override
    public List<Object> collectData() {
        SampleTableData exampleTableData = table.getSelectionData();
        List<Object> list = new ArrayList<Object>();
        list.add(exampleTableData);
        return list;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        return "example";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        return "这是一个例子";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#initializeData(java.util.List)
     */
    @Override
    protected void initializeData(List<Object> data) {
        modifyAction.setEnabled(false);
        deleteAction.setEnabled(false);
        if (CollectionUtils.isEmpty(data))
            return;
        for (Object object : data) {
            table.addRowData((SampleTableData) object);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.AbstractClientView#addDatas(java.util.List)
     */
    @Override
    public void addDatas(List<Object> datas) {
        if (CollectionUtils.isEmpty(datas))
            return;
        for (Object object : datas) {
            if (object instanceof SampleTableData) {
                table.addRowData((SampleTableData) object);
            }
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.AbstractClientView#deleteDatas(java.util.List)
     */
    @Override
    public void deleteDatas(List<Object> datas) {
        if (CollectionUtils.isEmpty(datas))
            return;
        for (Object object : datas) {
            if (object instanceof SampleTableData) {
                table.deleteRowData((SampleTableData) object);
            }
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.AbstractClientView#modifyDatas(java.util.List)
     */
    @Override
    public void modifyDatas(List<Object> datas) {
        if (CollectionUtils.isEmpty(datas))
            return;
        for (Object object : datas) {
            if (object instanceof SampleTableData) {
                table.updateRowData((SampleTableData) object);
            }
        }
    }
}
