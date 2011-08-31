/*
 * $Id: MyPanel.java, 2010-3-3 ����11:19:35 ChenYong Exp $
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
 * Description: ���в������
 * 
 * 1. �����ֹ����֣�����ָ��������ܿհ������ȣ�ֱ�����м���ؼ����� 
 * 2. ���ÿ���JLabel�������ֿ�Ȳ�һ�µ����⣬��������Զ�Ϊ��һ�в�������п� 
 * 3. �ɽ�ָ��������ʾ�����أ��·���������²��֣��������ĳЩ�ؼ�֮������г��ֿհ��е����� 
 * 4. ����ӿؼ�ΪJPanelʱ��panel����ĵĿؼ���ʾ��ȫ���Ƽ�JPanel����TableLayout���������ƿؼ��и� 
 * 5. ��ӿؼ���ʱ��ʹ�ñ����еĽӿڷ��� addRow() 
 * 6. ���Ѿ����εĽ��������ӻ�ɾ��(ͨ��ע��)һ������ܷ��㣬����ά�� 
 * 7. ��׳�Ի��д����飬��ӭ��Ҳ��ϲ��Ժ�����
 * 
 * 
 * TODO ���Դ�������ʾ/���ض����л�ʱ��JScrollPane�е�������ʾ��Щ�쳣����bug�������������2010/3/31
 * </p>
 * 
 * @author ChenYong
 * created 2010-3-3 ����11:19:35
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
     * ֱ�Ӽ���һ���У��ޱ�ǩ�������ڷָ�������������һ�пؼ�������
     * 
     * @param c
     *            ������ֱ�����JLabel���������Ҫ������ʾһ��JLabel��������JPanel�У��������
     */
    public void addRow(Component c) {
        addRow(null, c);
    }

    /**
     * �����Label�У�ָ���иߣ���Ҫ�ر�ָ���и�ʱ����
     * 
     * ���磺JProgressBar,JSeperator,JTextArea�ȷǳ����и����
     * 
     * @param c
     * @param h
     */
    public void addRow(Component c, int h) {
        addRow(null, c, h);
    }

    /**
     * ���һ�Կؼ�, JLabel -- JTextField,JComboBox,JPanel...(���пؼ�)
     * 
     * @param lbText
     *            ��ǩText
     * @param c
     *            �ؼ�, ���cΪJPanel�����䲼�־�����֤ vgap = 0�� �Ƽ���TableLayout���ָ�JPanel
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
     * ���һ�Կؼ�, JLabel + JTextField,JComboBox,JPanel...
     * 
     * @param lbText
     * @param c
     *            ���
     * @param h
     *            �涨�иߣ����磬���JTextArea����Ҫ��չ��Χ�Ŀؼ�ʱ����
     */
    public void addRow(String lbText, Component c, int h) {
        if (c == null)
            throw new NullPointerException("Component c is null");

        if (c instanceof JLabel)
            throw new IllegalArgumentException("Component c is not allowed to be a JLabel instance!!");

        JLabel lb = null;
        if (lbText != null)
            lb = ClientCoreContext.getSwingFactory().getLabel(new LabelDecorator(lbText));
        // lb = new JLabel(lbText); // ����main����ʱ����

        layout.setNewRowHeight(h);
        if (lb != null)
            add(lb, "");
        if (c != null)
            add(c, "");
    }

    /**
     * ��Ҫ��ʾ/����ĳ���������ֱ�Ӵ�������������������ӵ�������������Ҫ������������
     * ���磬�ı���tf��һ��JButton������һ��JPanel�У���������ӵ�RowPanel��ʱ��
     * ��Ҫ��ʾ/���ظ��У�ֱ�Ӵ���tf.getParent()����
     * 
     * @param c
     *            cΪ��JLabel����
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
     * ��ʾ������ָ���У���0�п�ʼ������rowΪ�����к�
     * 
     * ÿ���������ӹ����е��к�һһ��Ӧ�����ػ���ʾ����������ԭ�к�ֵ
     * 
     * ���磺��0���������֮�󣬵�1��������ƣ������к���Ϊ1
     * 
     * ��������֮�������صĸ��Ӳ��������׻����кţ�����ʹ��setRowVisible(Component c, boolean isVisible)
     * 
     * ԭ�����Ƿ�������ĳЩ�ֲ������Ŀؼ��У��ݲ������ӿ�
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
        final JScrollPane jsp = new JScrollPane(new JTextArea()); // �������ݴ��ڻ�������

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

        pane.addRow(new JTextField()); // Ĭ�ϸ߶�
        pane.addRow(new JTextField(), 23); // ָ���߶�

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
