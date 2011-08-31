/*
 * $Id: MyPanel.java, 2010-3-3 上午11:19:35 ChenYong Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.separator.XTitledSeparator;

/**
 * <p>
 * Title: RowPanel
 * </p>
 * <p>
 * Description: 单行布局面板
 * 
 * 1. 无需手工布局，无需指定面板四周空白区域宽度，直接整行加入控件即可 
 * 2. 不用考虑JLabel标题文字宽度不一致的问题，布局类会自动为第一列采用最大列宽 
 * 3. 可将指定的行显示或隐藏，下方组件会重新布局，解决隐藏某些控件之后，面板中出现空白行的问题 
 * 4. 当添加控件为JPanel时，panel里面的的控件显示不全，推荐JPanel采用TableLayout布局以限制控件行高 
 * 5. 添加控件行时须使用本类中的接口方法 addRow() 
 * 6. 在已经成形的界面上增加或删除(通过注释)一行组件很方便，容易维护 
 * 7. 健壮性还有待考验，欢迎大家不断测试和完善
 * 
 * 
 * TODO 测试代码中显示/隐藏动作切换时，JScrollPane中的内容显示有些异常，此bug留待后续解决。2010/3/31
 * </p>
 * 
 * @author ChenYong
 * created 2010-3-3 上午11:19:35
 * modified [who date description]
 * check [who date description]
 */
public class XRowPanel extends JComponent {

    private static final long serialVersionUID = -6127060572026547879L;

    private static final int height = 20;
    private static final int barHeight = 17;
    private static final int panelHeight = 23;

    private XRowLayout layout = new XRowLayout();

    public static XRowPanel getNewInstance() {
        return new XRowPanel();
    }

    private XRowPanel() {
        this.setLayout(layout);
    }

    /**
     * 直接加入一整行，无标签，适用于分隔条，进度条，一行控件组面板等
     * 
     * @param c
     *            不允许直接添加JLabel对象，如果需要单独显示一个JLabel，先置于JPanel中，再行添加
     */
    public void addRow(Component c) {
        addRow(null, c);
    }

    /**
     * 添加无Label行，指定行高，需要特别指定行高时调用
     * 
     * 例如：JProgressBar,JSeperator,JTextArea等非常规行高组件
     * 
     * @param c
     * @param h
     */
    public void addRow(Component c, int h) {
        addRow(null, c, h);
    }

    /**
     * 添加一对控件, JLabel -- JTextField,JComboBox,JPanel...(单行控件)
     * 
     * @param lbText
     *            标签Text
     * @param c
     *            控件, 如果c为JPanel，则其布局尽量保证 vgap = 0， 推荐用TableLayout布局该JPanel
     */
    public void addRow(String lbText, Component c) {
        int h = height;
        if (c instanceof JProgressBar || c instanceof XTitledSeparator)
            h = barHeight;
        else if (c instanceof JPanel)
            h = panelHeight;

        addRow(lbText, c, h);
    }

    /**
     * 添加一对控件, JLabel + JTextField,JComboBox,JPanel...
     * 
     * @param lbText
     * @param c
     *            组件
     * @param h
     *            规定行高，例如，添加JTextArea等需要扩展范围的控件时调用
     */
    public void addRow(String lbText, Component c, int h) {
        if (c == null)
            throw new NullPointerException("Component c is null");

        if (c instanceof JLabel)
            throw new IllegalArgumentException("Component c is not allowed to be a JLabel instance!!");

        JLabel lb = null;
        if (lbText != null)
            lb = ClientCoreContext.getSwingFactory().getLabel(new LabelDecorator(lbText));
        // lb = new JLabel(lbText); // 测试main方法时启用

        layout.setNewRowHeight(h);
        if (lb != null)
            add(lb, "");
        if (c != null)
            add(c, "");
    }

    /**
     * 需要显示/隐藏某行组件对象，直接传进组件对象，如果该行添加的是容器，则需要传入容器对象
     * 例如，文本框tf和一个JButton是置于一个JPanel中，而后再添加到RowPanel中时，
     * 需要显示/隐藏该行，直接传入tf.getParent()即可
     * 
     * @param c
     *            c为非JLabel对象
     * @param isVisible
     */
    public void setRowVisible(Component c, boolean isVisible) {
        if (c instanceof JLabel)
            return;

        if (isVisible)
            layout.showRow(c);
        else
            layout.hideRow(c);

        doLayout();
    }

    /**
     * 显示或隐藏指定行，从0行开始计数，row为绝对行号
     * 
     * 每行组件与添加过程中的行号一一对应，隐藏或显示操作不更改原行号值
     * 
     * 例如：第0行组件隐藏之后，第1行组件上移，但其行号仍为1
     * 
     * 对于隐藏之后再隐藏的复杂操作，容易混淆行号，建议使用setRowVisible(Component c, boolean isVisible)
     * 
     * 原打算是方便隐藏某些局部变量的控件行，暂不公开接口
     * 
     * @param row
     */
    private void setRowVisible(int row, boolean isVisible) {
        if (isVisible)
            layout.showRow(row);
        else
            layout.hideRow(row);

        doLayout();
    }

    // //////////////test
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        frame.add(test1());
        frame.setBounds(250, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JComponent test3() {
        JPanel rootPane = new JPanel(new BorderLayout());
        final XRowPanel pane = XRowPanel.getNewInstance();
        JTextArea ta = new JTextArea();
        ta.setBorder(BorderFactory.createLineBorder(Color.blue));
        final JScrollPane jsp = new JScrollPane(new JTextArea()); // 里面内容存在绘制问题

        JPanel tap = new JPanel(new BorderLayout());
        tap.add(new JTextArea());

        pane.addRow(null, new JScrollPane(new JTextArea()), XRowLayout.FILL);
        pane.addRow("label2", jsp, XRowLayout.FILL);
        pane.addRow("label3", new JScrollPane(new JTextArea()), XRowLayout.FILL);
        pane.addRow("label4", ta, XRowLayout.FILL);

        JPanel btnPanel = new JPanel(new GridLayout(1, 0, 5, 0));
        final JButton btn1 = new JButton("show1");
        final JButton btn2 = new JButton("show2");
        final JButton btn6 = new JButton("show3");
        final JButton btn3 = new JButton("hide1");
        final JButton btn4 = new JButton("hide2");
        final JButton btn7 = new JButton("hide3");
        final JButton btn5 = new JButton("hide/show");

        btnPanel.add(btn1);
        btnPanel.add(btn2);
        btnPanel.add(btn6);
        btnPanel.add(btn3);
        btnPanel.add(btn4);
        btnPanel.add(btn7);
        btnPanel.add(btn5);

        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object source = e.getSource();
                if (source == btn1) {
                    pane.setRowVisible(0, true);
                } else if (source == btn2) {
                    pane.setRowVisible(1, true);
                } else if (source == btn3) {
                    pane.setRowVisible(0, false);
                } else if (source == btn4) {
                    pane.setRowVisible(1, false);
                } else if (source == btn6) {
                    pane.setRowVisible(3, true);
                } else if (source == btn7) {
                    pane.setRowVisible(3, false);
                } else {
                    pane.setRowVisible(2, v);
                    v = !v;
                }
            }
        };

        btn1.addActionListener(l);
        btn2.addActionListener(l);
        btn3.addActionListener(l);
        btn4.addActionListener(l);
        btn5.addActionListener(l);
        btn6.addActionListener(l);
        btn7.addActionListener(l);

        rootPane.add(pane, BorderLayout.CENTER);
        rootPane.add(btnPanel, BorderLayout.SOUTH);

        return rootPane;
    }

    private static JComponent test2() {

        JPanel rootPane = new JPanel(new BorderLayout());

        final XRowPanel pane = XRowPanel.getNewInstance();
        final JPanel checkPane = new JPanel(new FlowLayout());
        final JCheckBox checkbox = new JCheckBox("check1");
        checkPane.add(checkbox);
        checkPane.add(new JCheckBox("check2"));
        checkPane.add(new JCheckBox("check3"));
        JPanel lbPane = new JPanel(new BorderLayout());
        lbPane.add(new JLabel(
                "a single label, and this is a legal param and this label will fill the whole panel"));

        pane.addRow(new JTextField(), 20);
        pane.addRow(new JScrollPane(new JTextArea()), XRowLayout.FILL);
        pane.addRow("JLabel1", new JTextField());
        pane.addRow(new JProgressBar());
        pane.addRow(new XTitledSeparator("this is a title"));

        pane.addRow("Jlabel2", new JTextArea(), XRowLayout.FILL);
        pane.addRow("JLabel3", new JTextField());
        pane.addRow(checkPane);
        pane.addRow("JLabel5", new JTextField());
        pane.addRow(lbPane, 20);

        pane.addRow(new JScrollPane(new JTextArea()), XRowLayout.FILL);

        JPanel btnPanel = new JPanel(new GridLayout(1, 0, 5, 0));
        final JButton btn1 = new JButton("show1");
        final JButton btn2 = new JButton("show2");
        final JButton btn3 = new JButton("hide1");
        final JButton btn4 = new JButton("hide2");
        final JButton btn5 = new JButton("hide/show");

        btnPanel.add(btn1);
        btnPanel.add(btn2);
        btnPanel.add(btn3);
        btnPanel.add(btn4);
        btnPanel.add(btn5);

        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object source = e.getSource();
                if (source == btn1) {
                    pane.setRowVisible(0, true);
                } else if (source == btn2) {
                    pane.setRowVisible(5, true);
                } else if (source == btn3) {
                    pane.setRowVisible(0, false);
                } else if (source == btn4) {
                    pane.setRowVisible(5, false);
                } else {
                    pane.setRowVisible(checkbox.getParent(), v);
                    v = !v;
                }
            }
        };

        btn1.addActionListener(l);
        btn2.addActionListener(l);
        btn3.addActionListener(l);
        btn4.addActionListener(l);
        btn5.addActionListener(l);

        rootPane.add(pane, BorderLayout.CENTER);
        rootPane.add(btnPanel, BorderLayout.SOUTH);

        return rootPane;
    }

    private static boolean v = false;

    private static JComponent test1() {
        JPanel rootPane = new JPanel(new BorderLayout());

        final XRowPanel pane = XRowPanel.getNewInstance();
        final JSeparator sep = new JSeparator();
        final JPanel checkPane = new JPanel(new FlowLayout());
        final JCheckBox checkbox = new JCheckBox("check1");
        checkPane.add(checkbox);
        checkPane.add(new JCheckBox("check2"));
        checkPane.add(new JCheckBox("check3"));

        pane.addRow("JLabel1", new JTextField());
        pane.addRow(sep, 17);

        pane.addRow("JLabel3", new JTextField());
        pane.addRow(checkPane);
        pane.addRow("JLabel5", new JTextField());

        pane.addRow(new JTextField()); // 默认高度
        pane.addRow(new JTextField(), 23); // 指定高度

        pane.addRow(new JProgressBar(), 15);
        JTextArea ta = new JTextArea(10, 20);
        pane.addRow(ta, XRowLayout.FILL);

        pane.addRow(new JScrollPane(new JTextArea(10, 20)), XRowLayout.FILL);

        JPanel lbPane = new JPanel(new BorderLayout());
        lbPane.add(new JLabel(
                "a single label, and this is a legal param and this label will fill the whole panel"));
        pane.addRow(lbPane);

        JPanel btnPanel = new JPanel(new GridLayout(1, 0, 5, 0));
        final JButton btn1 = new JButton("show1");
        final JButton btn2 = new JButton("show2");
        final JButton btn3 = new JButton("hide1");
        final JButton btn4 = new JButton("hide2");
        final JButton btn5 = new JButton("hide/show");

        btnPanel.add(btn1);
        btnPanel.add(btn2);
        btnPanel.add(btn3);
        btnPanel.add(btn4);
        btnPanel.add(btn5);

        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object source = e.getSource();
                if (source == btn1) {
                    pane.setRowVisible(3, true);
                } else if (source == btn2) {
                    pane.setRowVisible(5, true);
                } else if (source == btn3) {
                    pane.setRowVisible(3, false);
                } else if (source == btn4) {
                    pane.setRowVisible(5, false);
                } else {
                    pane.setRowVisible(checkbox.getParent(), v);
                    v = !v;
                }
            }
        };

        btn1.addActionListener(l);
        btn2.addActionListener(l);
        btn3.addActionListener(l);
        btn4.addActionListener(l);
        btn5.addActionListener(l);

        pane.setBorder(BorderFactory.createTitledBorder("this title"));

        rootPane.add(pane, BorderLayout.CENTER);
        rootPane.add(btnPanel, BorderLayout.SOUTH);

        return rootPane;

    }
}
