/*
 * $Id: SwingFactory.java, 2010-10-15 ����03:19:36 aaron Exp $
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
 * Description:�ͻ���Swing�������������swing�ؼ����Զ�У��ؼ������ֹ�������Panel�ȿؼ��Ļ�ȡ����
 * </p>
 * 
 * @author aaron
 * created 2010-10-15 ����03:19:36
 * modified [who date description]
 * check [who date description]
 */
public interface SwingFactory {

    /**
     * ��ȡһ����Border
     * 
     * @param top
     *            �����϶
     * @param left
     *            �����϶
     * @param bottom
     *            �����϶
     * @param right
     *            �����϶
     * @return �յ�Border
     */
    Border getEmptyBorder(int top, int left, int bottom, int right);

    /**
     * ��ȡ�б���ı߿�
     * 
     * @param s
     *            ����
     * @return Border �б���ı߿�
     */
    Border getTitleBorder(String titleKey);

    /**
     * ��ȡFlowLayout
     * 
     * @param i
     *            align
     * @return FlowLayout
     * @see FlowLayout
     */
    FlowLayout getFlowLayout(int i);
    FlowLayout getFlowLayout(int i,int hgap, int vgap);

    /**
     * ��ȡ GridLayout
     * 
     * @param rows
     *            ����
     * @param cols
     *            ����
     * @return GridLayout
     * @see GridLayout
     */
    GridLayout getGridLayout(int rows, int cols);

    /**
     * ��ȡCardLayout
     * 
     * @return CardLayout
     */
    CardLayout getCardLayout();

    /**
     * ��ȡBorderLayout
     * 
     * @return BorderLayout
     * @see BorderLayout
     */
    BorderLayout getBorderLayout();

    /**
     * ��ȡ��񲼾ֹ����������в���
     * 
     * @param ds
     *            ���ֲ���
     * @return LayoutManager ��񲼾ֹ�����
     * @see XTableLayout
     */
    XTableLayout getTableLayout(double[][] ds);

    /**
     * ��ȡ��񲼾����Ĳ���
     * 
     * @param rowsCount
     *            ����
     * @param topGap
     *            �ϲ���϶
     * @param bottomGap
     *            �²���϶
     * @return double[] ��񲼾����Ĳ���
     */
    double[] getTableLayoutRowParam(int rowsCount, int topGap, double bottomGap);

    /**
     * ��ȡJButton
     * 
     * @param decorator
     *            װ����
     * @return JButton
     */
    JButton getButton(Decorator<JButton> decorator);

    /**
     * ��ȡ JToggleButton
     * 
     * @param decorator
     *            װ����
     * @return JToggleButton
     */
    JToggleButton getToggleButton(Decorator<JToggleButton> decorator);

    /**
     * ��ȡJRadioButton
     * 
     * @param decorator
     *            װ����
     * @return JRadioButton
     */
    JRadioButton getRadioButton(Decorator<JRadioButton> decorator);

    /**
     * ��ȡ JPopupMenu
     * 
     * @return JPopupMenu
     */
    JPopupMenu getPopupMenu(Decorator<JPopupMenu> decorator);

    /**
     * ��ȡJMenuItem
     * 
     * @return JMenuItem
     */
    JMenuItem getJMenuItem(Decorator<JMenuItem> decorator);

    /**
     * ��ȡLabel
     * 
     * @param decorator
     *            װ����
     * @return JLabel
     */
    JLabel getLabel(Decorator<JLabel> decorator);

    /**
     * ��ȡ�Զ�У��ؼ�
     * @param decorator �ؼ�װ����
     * @return
     */
    <T> T getXEditor(Decorator<T> decorator);
    
    /**
     * ��ȡXEnumComboBox�ؼ�
     * @param clazz �ؼ��洢��ö�ٱ���������
     * @return
     */
    <T> XEnumComboBox getEnumComboBox(Class clazz,Decorator<T> decorator);

    /**
     * ��ȡJPanel
     * 
     * @return JPanel
     */
    JPanel getPanel();

    /**
     * ��ȡJPanel��ΪPanel���ò���
     * 
     * @param layout
     *            ����
     * @return JPanel
     */
    JPanel getPanel(LayoutManager layout);

    /**
     * ��ȡJScrollPane
     * 
     * @return JScrollPane
     */
    JScrollPane getScrollPane();

    /**
     * ��ȡjScrollPane,����������
     * 
     * @param jc
     * @return JScrollPane
     */
    JScrollPane getScrollPane(JComponent jc);

    /**
     * ��ȡJSplitPane
     * 
     * @return JSplitPane
     */
    JSplitPane getSplitPane();

    /**
     * ��ȡJTabbedPane
     * 
     * @return JTabbedPane
     */
    JTabbedPane getTabbedPane();

    /**
     * ��ȡ�ָ���
     * @return JSeparator �ָ���
     */
    JSeparator getSeparator();

    /**
     * ��ȡ������
     * 
     * @return JProgressBar
     */
    JProgressBar getProgressBar();

    /**
     * ��ȡ�б���ķָ���
     * 
     * @param title
     *            ����
     * @return Component �б���ķָ��ߵ�Panel
     */
    Component getXTitleSeparator(String title);

    /**
     * ��ȡ���ؼ�
     * @param propertyName ������
     * @param <T>
     *            ���չ�ֵ���������
     * @param <R>
     *            ��ʵ����������
     * @param clazz
     *            ���չ�ֵ���������
     * @param realClass
     *            ��ʵ����������
     * @param uniquePropertyName
     *            �����չ�ֵ����ݵ�Ψһ������
     * @param propertyNames
     *            �����չ�ֵ��У���չ�ֵ�������������Ӧ
     * @return XTable<T> ���ؼ�
     */
    <T, R> XTableEditor<T> getXTableEditor(String propertyName,Class<T> clazz, Class<R> realClass, String uniquePropertyName,
            String[] propertyNames);
    /**
     * ��ȡ���ؼ�
     * @param propertyName ������
     * @param <T>
     *            ���չ�ֵ���������
     * @param <R>
     *            ��ʵ����������
     * @param clazz
     *            ���չ�ֵ���������
     * @param realClass
     *            ��ʵ����������
     * @param uniquePropertyName
     *            �����չ�ֵ����ݵ�Ψһ������
     * @param propertyNames
     *            �����չ�ֵ��У���չ�ֵ�������������Ӧ
     * @return XTable<T> ���ؼ�
     */
    <T, R> XTable<T> getXTable(Class<T> clazz, Class<R> realClass, String uniquePropertyName,
            String[] propertyNames);
    
    /**
     * ͨ�õĻ�ȡ�ؼ��Ľӿڣ����ݲ���Decorator����ȡ��ͬ�Ŀؼ�
     * @param <T> ����Ŀؼ�����
     * @param decorator �ؼ���װ����
     * @return T ����Ŀؼ�����
     */
    <T> T getComponentEditor(Decorator<T> decorator);
    
    /**
     * ��ȡXTree
     * @return XTree
     */
    XTree getXTree();
}
