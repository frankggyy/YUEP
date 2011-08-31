/*
 * $Id: SwingFactory.java, 2010-10-15 下午03:19:36 aaron Exp $
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

import com.yuep.core.client.component.factory.decorator.Decorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.component.tree.XTree;
import com.yuep.core.client.component.validate.editor.XEnumComboBox;
import com.yuep.core.client.component.validate.editor.XTableEditor;

/**
 * <p>
 * Title: SwingFactory
 * </p>
 * <p>
 * Description:客户端Swing组件工厂，包括swing控件、自动校验控件、布局管理器、Panel等控件的获取方法
 * </p>
 * 
 * @author aaron
 * created 2010-10-15 下午03:19:36
 * modified [who date description]
 * check [who date description]
 */
public interface SwingFactory {

    /**
     * 获取一个空Border
     * 
     * @param top
     *            上面空隙
     * @param left
     *            左面空隙
     * @param bottom
     *            下面空隙
     * @param right
     *            右面空隙
     * @return 空的Border
     */
    Border getEmptyBorder(int top, int left, int bottom, int right);

    /**
     * 获取有标题的边框
     * 
     * @param s
     *            标题
     * @return Border 有标题的边框
     */
    Border getTitleBorder(String titleKey);

    /**
     * 获取FlowLayout
     * 
     * @param i
     *            align
     * @return FlowLayout
     * @see FlowLayout
     */
    FlowLayout getFlowLayout(int i);
    FlowLayout getFlowLayout(int i,int hgap, int vgap);

    /**
     * 获取 GridLayout
     * 
     * @param rows
     *            行数
     * @param cols
     *            列数
     * @return GridLayout
     * @see GridLayout
     */
    GridLayout getGridLayout(int rows, int cols);

    /**
     * 获取CardLayout
     * 
     * @return CardLayout
     */
    CardLayout getCardLayout();

    /**
     * 获取BorderLayout
     * 
     * @return BorderLayout
     * @see BorderLayout
     */
    BorderLayout getBorderLayout();

    /**
     * 获取表格布局管理器，行列布局
     * 
     * @param ds
     *            布局参数
     * @return LayoutManager 表格布局管理器
     * @see XTableLayout
     */
    XTableLayout getTableLayout(double[][] ds);

    /**
     * 获取表格布局器的参数
     * 
     * @param rowsCount
     *            行数
     * @param topGap
     *            上部间隙
     * @param bottomGap
     *            下部间隙
     * @return double[] 表格布局器的参数
     */
    double[] getTableLayoutRowParam(int rowsCount, int topGap, double bottomGap);

    /**
     * 获取JButton
     * 
     * @param decorator
     *            装饰器
     * @return JButton
     */
    JButton getButton(Decorator<JButton> decorator);

    /**
     * 获取 JToggleButton
     * 
     * @param decorator
     *            装饰器
     * @return JToggleButton
     */
    JToggleButton getToggleButton(Decorator<JToggleButton> decorator);

    /**
     * 获取JRadioButton
     * 
     * @param decorator
     *            装饰器
     * @return JRadioButton
     */
    JRadioButton getRadioButton(Decorator<JRadioButton> decorator);

    /**
     * 获取 JPopupMenu
     * 
     * @return JPopupMenu
     */
    JPopupMenu getPopupMenu(Decorator<JPopupMenu> decorator);

    /**
     * 获取JMenuItem
     * 
     * @return JMenuItem
     */
    JMenuItem getJMenuItem(Decorator<JMenuItem> decorator);

    /**
     * 获取Label
     * 
     * @param decorator
     *            装饰器
     * @return JLabel
     */
    JLabel getLabel(Decorator<JLabel> decorator);

    /**
     * 获取自动校验控件
     * @param decorator 控件装饰器
     * @return
     */
    <T> T getXEditor(Decorator<T> decorator);
    
    /**
     * 获取XEnumComboBox控件
     * @param clazz 控件存储的枚举变量的类型
     * @return
     */
    <T> XEnumComboBox getEnumComboBox(Class clazz,Decorator<T> decorator);

    /**
     * 获取JPanel
     * 
     * @return JPanel
     */
    JPanel getPanel();

    /**
     * 获取JPanel，为Panel设置布局
     * 
     * @param layout
     *            布局
     * @return JPanel
     */
    JPanel getPanel(LayoutManager layout);

    /**
     * 获取JScrollPane
     * 
     * @return JScrollPane
     */
    JScrollPane getScrollPane();

    /**
     * 获取jScrollPane,并设置内容
     * 
     * @param jc
     * @return JScrollPane
     */
    JScrollPane getScrollPane(JComponent jc);

    /**
     * 获取JSplitPane
     * 
     * @return JSplitPane
     */
    JSplitPane getSplitPane();

    /**
     * 获取JTabbedPane
     * 
     * @return JTabbedPane
     */
    JTabbedPane getTabbedPane();

    /**
     * 获取分隔符
     * @return JSeparator 分隔符
     */
    JSeparator getSeparator();

    /**
     * 获取进度条
     * 
     * @return JProgressBar
     */
    JProgressBar getProgressBar();

    /**
     * 获取有标题的分割线
     * 
     * @param title
     *            标题
     * @return Component 有标题的分割线的Panel
     */
    Component getXTitleSeparator(String title);

    /**
     * 获取表格控件
     * @param propertyName 属性名
     * @param <T>
     *            表格展现的数据类型
     * @param <R>
     *            真实的数据类型
     * @param clazz
     *            表格展现的数据类型
     * @param realClass
     *            真实的数据类型
     * @param uniquePropertyName
     *            表格中展现的数据的唯一键属性
     * @param propertyNames
     *            表格中展现的列，与展现的数据属性名对应
     * @return XTable<T> 表格控件
     */
    <T, R> XTableEditor<T> getXTableEditor(String propertyName,Class<T> clazz, Class<R> realClass, String uniquePropertyName,
            String[] propertyNames);
    /**
     * 获取表格控件
     * @param propertyName 属性名
     * @param <T>
     *            表格展现的数据类型
     * @param <R>
     *            真实的数据类型
     * @param clazz
     *            表格展现的数据类型
     * @param realClass
     *            真实的数据类型
     * @param uniquePropertyName
     *            表格中展现的数据的唯一键属性
     * @param propertyNames
     *            表格中展现的列，与展现的数据属性名对应
     * @return XTable<T> 表格控件
     */
    <T, R> XTable<T> getXTable(Class<T> clazz, Class<R> realClass, String uniquePropertyName,
            String[] propertyNames);
    
    /**
     * 通用的获取控件的接口，根据参数Decorator来获取不同的控件
     * @param <T> 具体的控件类型
     * @param decorator 控件的装饰器
     * @return T 具体的控件对象
     */
    <T> T getComponentEditor(Decorator<T> decorator);
    
    /**
     * 获取XTree
     * @return XTree
     */
    XTree getXTree();
}
