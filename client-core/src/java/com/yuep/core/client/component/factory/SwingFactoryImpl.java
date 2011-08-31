/*
 * $Id: SwingFactoryImpl.java, 2010-10-15 下午03:28:33 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.factory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.Decorator;
import com.yuep.core.client.component.factory.decorator.editor.TableEditorDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.component.separator.XTitledSeparator;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.component.tree.XTree;
import com.yuep.core.client.component.validate.editor.XEnumComboBox;
import com.yuep.core.client.component.validate.editor.XTableEditor;

/**
 * <p>
 * Title: SwingFactoryImpl
 * </p>
 * <p>
 * Description:客户端Swing组件工厂的实现类
 * </p>
 * 
 * @author aaron
 * created 2010-10-15 下午03:28:33
 * modified [who date description]
 * check [who date description]
 */
public class SwingFactoryImpl implements SwingFactory {

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getBorderLayout()
     */
    @Override
    public BorderLayout getBorderLayout() {
        return new BorderLayout();
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getTitleBorder(java.lang.String)
     */
    @Override
    public Border getTitleBorder(String titleKey) {
        String textStr = ClientCoreContext.getString(titleKey);
        return BorderFactory.createTitledBorder(textStr);
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getCardLayout()
     */
    @Override
    public CardLayout getCardLayout() {
        return new CardLayout();
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getEmptyBorder(int,
     *      int, int, int)
     */
    @Override
    public Border getEmptyBorder(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getFlowLayout(int)
     */
    @Override
    public FlowLayout getFlowLayout(int i) {
        return new FlowLayout(i);
    }
    

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getFlowLayout(int, int, int)
     */
    @Override
    public FlowLayout getFlowLayout(int i, int hgap, int vgap) {
        return new FlowLayout(i,hgap,vgap);
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getGridLayout(int,
     *      int)
     */
    @Override
    public GridLayout getGridLayout(int rows, int cols) {
        return new GridLayout(rows, cols);
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getTableLayout(double[][])
     */
    @Override
    public XTableLayout getTableLayout(double[][] ds) {
        return new XTableLayout(ds);
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getTableLayoutRowParam(int,
     *      int, double)
     */
    @Override
    public double[] getTableLayoutRowParam(int rowsCount, int topGap, double bottomGap) {
        int size = rowsCount * 2 + 1;
        double[] rows = new double[size];
        rows[0] = topGap;
        for (int i = 1; i < size; i++) {
            if (i % 2 == 0) {
                rows[i] = 4;
            } else {
                rows[i] = 22;
            }
        }
        int length = rows.length;
        rows[length - 1] = bottomGap;
        return rows;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getButton(com.yuep.core.client.component.factory.decorator.Decorator)
     */
    @Override
    public JButton getButton(Decorator<JButton> decorator) {
        JButton button = new JButton();
        if (decorator != null)
            decorator.decorate(button);
        return button;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getToggleButton(com.yuep.core.client.component.factory.decorator.Decorator)
     */
    @Override
    public JToggleButton getToggleButton(Decorator<JToggleButton> decorator) {
        JToggleButton toggleButton = new JToggleButton();
        if (decorator != null)
            decorator.decorate(toggleButton);
        return toggleButton;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getRadioButton(com.yuep.core.client.component.factory.decorator.Decorator)
     */
    @Override
    public JRadioButton getRadioButton(Decorator<JRadioButton> decorator) {
        JRadioButton radioButton = new JRadioButton();
        if (decorator != null)
            decorator.decorate(radioButton);
        return radioButton;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getJMenuItem(com.yuep.core.client.component.factory.decorator.Decorator)
     */
    @Override
    public JMenuItem getJMenuItem(Decorator<JMenuItem> decorator) {
        JMenuItem menuItem = new JMenuItem();
        if (decorator != null)
            decorator.decorate(menuItem);
        return menuItem;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getPopupMenu(com.yuep.core.client.component.factory.decorator.Decorator)
     */
    @Override
    public JPopupMenu getPopupMenu(Decorator<JPopupMenu> decorator) {
        JPopupMenu popupMenu = new JPopupMenu();
        if (decorator != null)
            decorator.decorate(popupMenu);
        return popupMenu;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getLabel(com.yuep.core.client.component.factory.decorator.Decorator)
     */
    @Override
    public JLabel getLabel(Decorator<JLabel> decorator) {
        JLabel label = new JLabel();
        if(decorator != null)
            decorator.decorate(label);
        return label;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getPanel()
     */
    @Override
    public JPanel getPanel() {
        return new JPanel();
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getPanel(java.awt.LayoutManager)
     */
    @Override
    public JPanel getPanel(LayoutManager layout) {
        JPanel pane = new JPanel();
        pane.setLayout(layout);
        return pane;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getScrollPane()
     */
    @Override
    public JScrollPane getScrollPane() {
        return new JScrollPane();
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getScrollPane(javax.swing.JComponent)
     */
    @Override
    public JScrollPane getScrollPane(JComponent jc) {
        return new JScrollPane(jc);
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getSplitPane()
     */
    @Override
    public JSplitPane getSplitPane() {
        return new JSplitPane();
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getTabbedPane()
     */
    @Override
    public JTabbedPane getTabbedPane() {
        return new JTabbedPane();
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getSeparator(int)
     */
    @Override
    public JSeparator getSeparator() {
        JSeparator sep = new JSeparator();
        return sep;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getProgressBar()
     */
    @Override
    public JProgressBar getProgressBar() {
        return new JProgressBar();
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getXTitleSeparator(java.lang.String)
     */
    @Override
    public Component getXTitleSeparator(String title) {
        JPanel pane = new XTitledSeparator(title);
        return pane;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getXTableEditor(java.lang.String, java.lang.Class, java.lang.Class, java.lang.String, java.lang.String[])
     */
    @Override
    public <T, R> XTableEditor<T> getXTableEditor(String propertyName, Class<T> clazz, Class<R> realClass,
            String uniquePropertyName, String[] propertyNames) {
        XTableEditor<T> table = new XTableEditor<T>(clazz, realClass, uniquePropertyName, propertyNames);
        TableEditorDecorator decorator = new TableEditorDecorator(propertyName);
        decorator.decorate(table);
        return table;
    }
    
    public <T, R> XTable<T> getXTable(Class<T> clazz, Class<R> realClass,
            String uniquePropertyName, String[] propertyNames) {
        XTable<T> table = new XTable<T>(clazz, realClass, uniquePropertyName, propertyNames);
        return table;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getXEditor(com.yuep.core.client.component.factory.decorator.Decorator)
     */
    @Override
    public <T> T getXEditor(Decorator<T> decorator) {
        if(decorator==null)
            return null;
        T t = decorator.contructEditor();
        if(decorator != null)
            decorator.decorate(t);
        return t;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getEnumComboBox(java.lang.Class, com.yuep.core.client.component.factory.decorator.Decorator)
     */
    @Override
    public <T> XEnumComboBox getEnumComboBox(Class clazz, Decorator<T> decorator) {
        XEnumComboBox comboBox = new XEnumComboBox(clazz);
        decorator.decorate((T) comboBox);
        return comboBox;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getComponentEditor(com.yuep.core.client.component.factory.decorator.Decorator)
     */
    @Override
    public <T> T getComponentEditor(Decorator<T> decorator) {
        T editor = decorator.contructEditor();
        decorator.decorate(editor);
        return editor;
    }

    /**
     * @see com.yuep.core.client.component.factory.SwingFactory#getXTree()
     */
    @Override
    public XTree getXTree() {
        XTree tree = new XTree();
        return tree;
    }

}
