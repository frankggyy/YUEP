/*
 * $Id: OplogView.java, 2011-4-13 下午05:59:55 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.lang.StringUtils;

import twaver.swing.TableLayout;
import twaver.table.TTable;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.calendar.JCalendar;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.factory.decorator.editor.ComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.navigator.AbstractTabView;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.component.table.renderer.I18nCellRenderer;
import com.yuep.core.client.component.table.renderer.I18nComboBoxRenderer;
import com.yuep.core.client.component.table.renderer.Id2NameCellRenderer;
import com.yuep.core.client.component.table.renderer.Id2NameComboBoxRenderer;
import com.yuep.core.client.component.table.renderer.TimeCellRenderer;
import com.yuep.core.client.component.table.renderer.XTableCellRenderer;
import com.yuep.core.client.component.validate.editor.XComboBoxEditor;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.core.client.smmanager.action.OperationLogExportAction;
import com.yuep.nms.core.client.smmanager.action.OperationLogQueryAction;
import com.yuep.nms.core.common.smcore.model.OperationLog;
import com.yuep.nms.core.common.smcore.model.OperationLogCondition;

/**
 * <p>
 * Title: OplogView
 * </p>
 * <p>
 * Description: 查询操作日志
 * </p>
 * 
 * @author sufeng
 * created 2011-4-25 下午05:59:55
 * modified [who date description]
 * check [who date description]
 */
public class OplogView extends AbstractTabView<Object> {

    private static final long serialVersionUID = 4874355264394566965L;
    private XTable table;
    private JButton queryButton;
    private JButton exportButton;

    private XComboBoxEditor userCombobox;
    private XComboBoxEditor oplogResultCombobox;
    private JCalendar startTime;
    private JCalendar endTime;
    private XComboBoxEditor categoryCombobox;
    private XComboBoxEditor optypeCombobox;
    private XComboBoxEditor sourceCombobox;
    private Map<String, XTableCellRenderer> renderers = new HashMap<String, XTableCellRenderer>();

    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {
        OperationLogQueryAction query = new OperationLogQueryAction(OperationLogQueryAction.class.getSimpleName());
        queryButton.setAction(query);

        OperationLogExportAction export = new OperationLogExportAction(OperationLogExportAction.class.getSimpleName());
        exportButton.setAction(export);
    }

    @Override
    protected String getDescription() {
        return "smmanager.oplog.description";
    }

    @Override
    protected JButton[] getSensitiveButtons() {
        return new JButton[] {};
    }

    public OperationLogCondition getOperationLogCondition() {
        OperationLogCondition condition = new OperationLogCondition();
        if (userCombobox.getSelectedItem() != null) {
            String userName = userCombobox.getSelectedItem().toString();
            if (StringUtils.isNotEmpty(userName))
                condition.setUserName(userName);
        }

        if (oplogResultCombobox.getSelectedItem() != null) {
            Integer opResult = (Integer) oplogResultCombobox.getSelectedItem();
            if (opResult != -1)
                condition.setResult(opResult);
        }

        if (categoryCombobox.getSelectedItem() != null) {
            Integer category = (Integer) categoryCombobox.getSelectedItem();
            if (category != -1)
                condition.setCategory(category);
        }

        if (optypeCombobox.getSelectedItem() != null) {
            String optype = optypeCombobox.getSelectedItem().toString();
            if (StringUtils.isNotEmpty(optype)) {
                List<String> operateNames = new ArrayList<String>();
                operateNames.add(optype);
                condition.setOperateNames(operateNames);
            }
        }

        if (sourceCombobox.getSelectedItem() != null) {
            String source = sourceCombobox.getSelectedItem().toString();
            if (StringUtils.isNotEmpty(source)) {
                List<String> objects = new ArrayList<String>();
                objects.add(source);
                condition.setObjects(objects);
            }
        }

        condition.setStartTime(startTime.getTime());
        condition.setEndTime(endTime.getTime());

        return condition;
    }

    @Override
    public void constructUi() {
        Map<Integer, String> opResultMap = new HashMap<Integer, String>();
        opResultMap.put(-1, "");
        opResultMap.put(OperationLog.LOG_RESULT_SUCCESSED, ClientCoreContext
                .getString("OperationLog.LOG_RESULT_SUCCESSED"));
        opResultMap.put(OperationLog.LOG_RESULT_EXCEPTION, ClientCoreContext
                .getString("OperationLog.LOG_RESULT_EXCEPTION"));
        opResultMap.put(OperationLog.LOG_RESULT_FAILED, ClientCoreContext.getString("OperationLog.LOG_RESULT_FAILED"));

        Map<Integer, String> categoryMap = new HashMap<Integer, String>();
        categoryMap.put(-1, "");
        categoryMap.put(OperationLog.LOG_CATALOG_SECURITY, ClientCoreContext
                .getString("OperationLog.LOG_CATALOG_SECURITY"));
        categoryMap.put(OperationLog.LOG_CATALOG_SYS, ClientCoreContext.getString("OperationLog.LOG_CATALOG_SYS"));
        categoryMap.put(OperationLog.LOG_CATALOG_OPERATION, ClientCoreContext
                .getString("OperationLog.LOG_CATALOG_OPERATION"));

        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        double[][] ds = new double[][] { { TableLayout.FILL }, { 140, TableLayout.FILL } };
        JPanel pane = swingFactory.getPanel(swingFactory.getTableLayout(ds));// 整个框架

        double[][] topds = new double[][] { { TableLayout.FILL }, { TableLayout.FILL, 32 } };
        JPanel inputPane = swingFactory.getPanel(swingFactory.getTableLayout(topds));// 条件上半区

        double[][] condds = new double[][] { { 10, 80, 5, TableLayout.FILL, 10, 80, 5, TableLayout.FILL },
                swingFactory.getTableLayoutRowParam(4, 1, 1) };
        JPanel condPane = swingFactory.getPanel(swingFactory.getTableLayout(condds));// 条件输入区

        JLabel userLabel = swingFactory
                .getLabel(new LabelDecorator(ClientCoreContext.getString("smmanager.oplog.user")));
        JLabel oplogResultLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext
                .getString("smmanager.oplog.oplogresult")));
        JLabel startTimeLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext
                .getString("smmanager.oplog.starttime")));
        JLabel endTimeLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext
                .getString("smmanager.oplog.endtime")));
        JLabel categoryLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext
                .getString("smmanager.oplog.category")));
        JLabel optypeLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext
                .getString("smmanager.oplog.optype")));
        JLabel sourceLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext
                .getString("smmanager.oplog.source")));
        userCombobox = swingFactory.getXEditor(new ComboBoxEditorDecorator("user"));

        oplogResultCombobox = swingFactory.getXEditor(new ComboBoxEditorDecorator("oplogresult"));
        oplogResultCombobox.setRenderer(new Id2NameComboBoxRenderer(opResultMap));

        startTime = new JCalendar();
        endTime = new JCalendar();

        categoryCombobox = swingFactory.getXEditor(new ComboBoxEditorDecorator("category"));
        categoryCombobox.setRenderer(new Id2NameComboBoxRenderer(categoryMap));

        optypeCombobox = swingFactory.getXEditor(new ComboBoxEditorDecorator("optype"));
        optypeCombobox.setRenderer(new I18nComboBoxRenderer());

        sourceCombobox = swingFactory.getXEditor(new ComboBoxEditorDecorator("source"));
        condPane.add(userLabel, "1,1,f,c");
        condPane.add(userCombobox, "3,1,f,c");
        condPane.add(oplogResultLabel, "5,1,f,c");
        condPane.add(oplogResultCombobox, "7,1,f,c");
        condPane.add(startTimeLabel, "1,3,f,c");
        condPane.add(startTime, "3,3,f,c");
        condPane.add(endTimeLabel, "5,3,f,c");
        condPane.add(endTime, "7,3,f,c");
        condPane.add(categoryLabel, "1,5,f,c");
        condPane.add(categoryCombobox, "3,5,f,c");
        condPane.add(optypeLabel, "5,5,f,c");
        condPane.add(optypeCombobox, "7,5,f,c");
        condPane.add(sourceLabel, "1,7,f,c");
        condPane.add(sourceCombobox, "3,7,f,c");
        inputPane.add(condPane, "0,0,f,f");

        queryButton = swingFactory.getButton(new ButtonDecorator("smmanager.oplog.query", 'Q'));
        exportButton = swingFactory.getButton(new ButtonDecorator("smmanager.oplog.export", 'E'));
        JPanel btnPane = swingFactory.getPanel(swingFactory.getFlowLayout(FlowLayout.RIGHT)); // 按钮
        btnPane.add(queryButton);
        btnPane.add(exportButton);
        inputPane.add(btnPane, "0,1,f,f");
        pane.add(inputPane, "0,0,f,f");

        table = new XTable(OperationLog.class, "id", new String[] { "ip", "userName", "category",
                "operateName", "source", "operateResult", "operateTime" });
        table.setAutoResizeMode(TTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane bottomPane = swingFactory.getScrollPane(table);
        pane.add(bottomPane, "0,1,f,f");
        I18nCellRenderer opNameRenderer = new I18nCellRenderer();
        table.getColumnByName("operateName").setCellRenderer(opNameRenderer);
        renderers.put("operateName", opNameRenderer);

        Id2NameCellRenderer opresultRenderer = new Id2NameCellRenderer();
        opresultRenderer.setId2NameMap(opResultMap);
        table.getColumnByName("operateResult").setCellRenderer(opresultRenderer);
        renderers.put("operateResult", opresultRenderer);

        Id2NameCellRenderer categoryRenderer = new Id2NameCellRenderer();
        categoryRenderer.setId2NameMap(categoryMap);
        table.getColumnByName("category").setCellRenderer(categoryRenderer);
        renderers.put("category", categoryRenderer);

        TimeCellRenderer timeRenderer = new TimeCellRenderer();
        table.getColumnByName("operateTime").setCellRenderer(timeRenderer);
        renderers.put("operateTime", timeRenderer);

        setLayout(swingFactory.getTableLayout(new double[][] { { TableLayout.FILL, 5 }, { TableLayout.FILL } }));
        add(pane, "0,0,f,f");

        // 设置缺省条件
        long now = System.currentTimeMillis();
        long before7Days = now - 7 * 24 * 60 * 60 * 1000;
        startTime.setTime(before7Days);
        // endTime.setTime(now);

        oplogResultCombobox.addItem(-1);
        oplogResultCombobox.addItem(OperationLog.LOG_RESULT_SUCCESSED);
        oplogResultCombobox.addItem(OperationLog.LOG_RESULT_FAILED);
        oplogResultCombobox.addItem(OperationLog.LOG_RESULT_EXCEPTION);

        categoryCombobox.addItem(-1);
        categoryCombobox.addItem(OperationLog.LOG_CATALOG_OPERATION);
        categoryCombobox.addItem(OperationLog.LOG_CATALOG_SECURITY);
        categoryCombobox.addItem(OperationLog.LOG_CATALOG_SYS);
    }

    @Override
    public JButton getDefaultButton() {
        return queryButton;
    }

    @Override
    public JComponent getDefaultFocus() {
        return null;
    }

    @Override
    public String getHelpId() {
        return "smmanager.oplog";
    }

    @Override
    public String getTitle() {
        return "smmanager.oplog.title";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof List))
            return;
        List<?> list = (List<?>) arg;
        if (list.size() < 1)
            return;
        String updateType = (String) list.get(0);
        if ("init".equals(updateType)) {
            List<String> users = (List<String>) list.get(1);
            List<String> sources = (List<String>) list.get(2);
            List<String> operates = (List<String>) list.get(3);

            userCombobox.removeAllItems();
            for (String user : users)
                userCombobox.addItem(user);

            optypeCombobox.removeAllItems();
            for (String operate : operates)
                optypeCombobox.addItem(operate);

            sourceCombobox.removeAllItems();
            for (String source : sources)
                sourceCombobox.addItem(source);

        } else if ("queryresult".equals(updateType)) {
            Object[] objs = (Object[]) list.get(1);
            if (objs != null) {
                table.clearDatas();
                for (Object obj : objs) {
                    table.addRowData((OperationLog) obj);
                }
            }
        }
    }

    @Override
    public List<Object> collectData() {
        return null;// (List)table.getAllDatas();
    }

    @Override
    protected void initializeData(List<Object> data) {
        update(null, data);
    }

    public XTable getTable() {
        return table;
    }

    public Map<String, XTableCellRenderer> getRenderers() {
        return renderers;
    }

}
