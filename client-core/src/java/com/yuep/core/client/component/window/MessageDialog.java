/*
 * $Id: MessageDialog.java, 2009-2-12 下午01:48:31 liujingyi Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.factory.decorator.editor.TextAreaEditorDecorator;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: MessageDialog
 * </p>
 * <p>
 * Description: 消息对话框，根据不同的消息类型可以显示不同的图标
 * </p>
 * 
 * @author liujingyi
 * created 2009-2-12 下午01:48:31
 * modified [who date description]
 * check [who date description]
 */
public class MessageDialog extends JDialog {

    private static final long serialVersionUID = -2412902150372304093L;

    private static final int HIDE_DETAIL_HEIGHT = 150;

    private static final int DETAIL_WIDTH = 500;

    private static final int SHOW_DETAIL_HEIGHT = 350;

    private final String DETAIL_BUTTON_TEXT = ClientCoreContext.getString("common.messagedialog.detail");

    private final String SOLUTION_BUTTON_TEXT = ClientCoreContext.getString("common.messagedialog.solution");

    private boolean isDetailShowing = false;
    private boolean isSolutionShowing = false;

    /**
     * 消息类型枚举，不同的类型对应不同的图标
     */
    public static enum MessageType {
        Information, Warning, Error;
    }

    protected MessageType messageType;
    protected String message;
    protected String detail;
    protected String solution;

    private JButton okButton;
    private JButton detailButton;
    private JButton solutionButton;

    private JPanel detailPanel;
    private JPanel contentPane;
    private JPanel messagePanel;

    private JTextArea detailTextArea;

    /**
     * @param parent
     *            父窗口
     * @param title
     *            对话框标题
     * @param message
     *            消息体
     * @param detail
     *            详细信息
     * @param messageType
     *            消息类型
     */
    public MessageDialog(Window parent, String title, String message, String detail, String solution,
            MessageType messageType) {
        super(parent, title, ModalityType.APPLICATION_MODAL);

        this.message = message;
        this.detail = detail;
        this.solution = solution;
        this.messageType = messageType;

        initGUI();
    }

    /**
     * 
     */
    protected void initGUI() {
        setIconImage(ClientCoreContext.getResourceFactory().getImage("logo.gif"));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(DETAIL_WIDTH, HIDE_DETAIL_HEIGHT);
        setMinimumSize(new Dimension(DETAIL_WIDTH, HIDE_DETAIL_HEIGHT));
        XGuiUtils.centerWindow(this);

        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        contentPane = swingFactory.getPanel();
        contentPane.setBorder(swingFactory.getEmptyBorder(10, 5, 10, 5));
        BorderLayout borderLayout = swingFactory.getBorderLayout();
        borderLayout.setHgap(10);
        borderLayout.setVgap(10);

        messagePanel = swingFactory.getPanel();
        messagePanel.setLayout(swingFactory.getBorderLayout());
        messagePanel.setPreferredSize(new Dimension(100, 96));

        JPanel imagePanel = swingFactory.getPanel();
        imagePanel.setPreferredSize(new Dimension(50, 10));
        imagePanel.setBorder(swingFactory.getEmptyBorder(10, 0, 0, 0));
        imagePanel.setLayout(swingFactory.getBorderLayout());
        JLabel label = swingFactory.getLabel(null);
        label.setIcon(getMessageIcon());
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);

        JTextArea messageArea = swingFactory.getXEditor(new TextAreaEditorDecorator(""));
        messageArea.setText(message);
        messageArea.setEditable(false);
        messageArea.setOpaque(false);
        messageArea.setLineWrap(true);
        messageArea.setBorder(null);
        messageArea.setRows(2);
        JScrollPane sp = swingFactory.getScrollPane(messageArea);
        sp.setBorder(swingFactory.getEmptyBorder(0, 10, 0, 10));

        JPanel buttonPanel = swingFactory.getPanel();
        FlowLayout flowLayout = swingFactory.getFlowLayout(FlowLayout.RIGHT);
        flowLayout.setHgap(10);
        flowLayout.setVgap(0);
        buttonPanel.setLayout(flowLayout);

        okButton = swingFactory.getButton(new ButtonDecorator("common.messagedialog.ok",'O'));
        okButton.addActionListener(new OkButtonActionListener());

        solutionButton = swingFactory.getButton(new ButtonDecorator(SOLUTION_BUTTON_TEXT + ">>",'S'));
        solutionButton.addActionListener(new SolutionButtonActionListener());

        detailButton = swingFactory.getButton(new ButtonDecorator(DETAIL_BUTTON_TEXT + " >>",'D'));
        detailButton.addActionListener(new DetailButtonActionListener());
        contentPane.setLayout(borderLayout);

        detailPanel = swingFactory.getPanel();
        detailPanel.setLayout(swingFactory.getBorderLayout());
        detailPanel.setVisible(false);

        detailTextArea = swingFactory.getXEditor(new TextAreaEditorDecorator(""));
        detailTextArea.setText(detail);
        detailTextArea.setEditable(false);
        detailTextArea.setRows(5);
        detailTextArea.setLineWrap(true);

        imagePanel.add(label, BorderLayout.CENTER);
        messagePanel.add(imagePanel, BorderLayout.WEST);
        messagePanel.add(sp, BorderLayout.CENTER);
        buttonPanel.add(okButton);
        buttonPanel.add(solutionButton);
        buttonPanel.add(detailButton);
        messagePanel.add(buttonPanel, BorderLayout.SOUTH);
        detailPanel.add(swingFactory.getScrollPane(detailTextArea));
        contentPane.add(messagePanel, BorderLayout.CENTER);
        contentPane.add(detailPanel, BorderLayout.SOUTH);
        setContentPane(contentPane);

        getRootPane().setDefaultButton(okButton);
    }

    private Icon getMessageIcon() {
        Icon icon = null;
        if (MessageType.Information == messageType) {
            icon = ClientCoreContext.getResourceFactory().getIcon("framework/dialog_info.png");
        } else if (MessageType.Warning == messageType) {
            icon = ClientCoreContext.getResourceFactory().getIcon("framework/dialog_warning.png");
        } else if (MessageType.Error == messageType) {
            icon = ClientCoreContext.getResourceFactory().getIcon("framework/dialog_error.png");
        }
        return icon;
    }

    private final class OkButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private final class SolutionButtonActionListener implements ActionListener {

        /**
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isSolutionShowing) {
                contentPane.removeAll();
                contentPane.add(messagePanel, BorderLayout.CENTER);
                solutionButton.setText(SOLUTION_BUTTON_TEXT + " >>");
                setSize(getWidth(), HIDE_DETAIL_HEIGHT);
                isSolutionShowing = false;
                isDetailShowing = false;
            } else {
                contentPane.removeAll();
                contentPane.add(messagePanel, BorderLayout.NORTH);
                detailTextArea.setText(solution);
                contentPane.add(detailPanel, BorderLayout.CENTER);
                solutionButton.setText("<< " + SOLUTION_BUTTON_TEXT);
                detailButton.setText(DETAIL_BUTTON_TEXT + ">>");
                setSize(getWidth(), SHOW_DETAIL_HEIGHT);
                isSolutionShowing = true;
                isDetailShowing = false;
            }
            detailPanel.setVisible(isSolutionShowing);
            validate();
            repaint();
        }

    }

    private final class DetailButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isDetailShowing) {
                contentPane.removeAll();
                contentPane.add(messagePanel, BorderLayout.CENTER);
                detailButton.setText(DETAIL_BUTTON_TEXT + " >>");
                setSize(getWidth(), HIDE_DETAIL_HEIGHT);
                isDetailShowing = false;
                isSolutionShowing = false;
            } else {
                contentPane.removeAll();
                contentPane.add(messagePanel, BorderLayout.NORTH);
                detailTextArea.setText(detail);
                contentPane.add(detailPanel, BorderLayout.CENTER);
                detailButton.setText("<< " + DETAIL_BUTTON_TEXT);
                solutionButton.setText(SOLUTION_BUTTON_TEXT + ">>");
                setSize(getWidth(), SHOW_DETAIL_HEIGHT);
                isDetailShowing = true;
                isSolutionShowing = false;
            }
            detailPanel.setVisible(isDetailShowing);
            validate();
            repaint();
        }
    }

    public static void main(String[] args) {
        new MessageDialog(null, "title", "message", "detail", "solution", MessageType.Error).setVisible(true);
    }
}
