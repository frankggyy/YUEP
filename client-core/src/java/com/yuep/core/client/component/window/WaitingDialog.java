/*
 * $Id: WaitingDialog.java, 2009-2-19 下午05:48:25 liujingyi Exp $
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
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: WaitingDialog
 * </p>
 * <p>
 * Description:等待对话框，可以设置是否可以关闭和中断任务
 * </p>
 * 
 * @author liujingyi
 * created 2009-2-19 下午05:48:25
 * modified [who date description]
 * check [who date description]
 */
public class WaitingDialog extends JDialog {

    private static final long serialVersionUID = -5459348129424007466L;

    private final int DEFAULT_HEIGHT = 150;
    private final int DEFAULT_WIDTH = 450;

    private JButton closeButton;
    private JButton terminateButton;
    private JProgressBar progressBar;

    private TerminateListener listerner;
    private String message;

    public WaitingDialog() {
        this(ClientCoreContext.getMainFrame(), null, null);
    }

    public WaitingDialog(Window parent) {
        this(parent, null, null);
    }

    public WaitingDialog(Window parent, String title, String message) {
        super(parent == null ? ClientCoreContext.getMainFrame() : parent, title == null ? ClientCoreContext
                .getString("common.waitingdialog.title") : ClientCoreContext.getString(title),
                ModalityType.APPLICATION_MODAL);

        this.message = (message == null ? "common.waitingdialog.message" : message);
        initGUI();
    }

    /**
     * 
     */
    private void initGUI() {
        setIconImage(ClientCoreContext.getResourceFactory().getImage("logo.gif"));
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        XGuiUtils.centerWindow(this);

        double[][] para = { { TableLayout.FILL }, { 18, 10, 20 } };
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel progressPanel = swingFactory.getPanel(swingFactory.getTableLayout(para));
        progressPanel.setBorder(swingFactory.getEmptyBorder(15, 10, 15, 10));

        UIManager.getDefaults().put("ProgressBarUI", "com.sun.java.swing.plaf.windows.WindowsProgressBarUI");
        UIManager.getDefaults()
                .put("ProgressBar.indeterminateInsets", new Insets(3, 3, 3, 3));
        progressBar = swingFactory.getProgressBar();
        progressBar.setIndeterminate(true);

        JLabel label = swingFactory.getLabel(new LabelDecorator(message));
        label.setBorder(swingFactory.getEmptyBorder(15, 5, 5, 0));
        progressPanel.add(progressBar, "0,0,f,f");
        progressPanel.add(label, "0,2,f,f");

        JPanel buttonPanel = swingFactory.getPanel();
        buttonPanel.setBorder(swingFactory.getEmptyBorder(0, 0, 5, 5));
        FlowLayout flowLayout = swingFactory.getFlowLayout(FlowLayout.RIGHT);
        flowLayout.setHgap(10);
        flowLayout.setVgap(5);
        buttonPanel.setLayout(flowLayout);

        terminateButton = swingFactory.getButton(new ButtonDecorator("common.waitingdialog.terminate",'T'));
        terminateButton.addActionListener(new TerminateActionListener());

        closeButton = swingFactory.getButton(new ButtonDecorator("common.waitingdialog.close",'C'));
        closeButton.addActionListener(new CloseActionListener());

        buttonPanel.add(terminateButton);
        buttonPanel.add(closeButton);
        add(progressPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    /**
     * 显示等待对话框
     * 
     * @param canTerminate
     *            是否可以停止任务
     * @param canClose
     *            是否可以关闭对话框
     * @param listener
     *            点击停止按钮的监听器
     */
    public synchronized void showWaitingDialog(boolean canTerminate, boolean canClose,
            TerminateListener listener) {
        assert !isVisible();

        this.listerner = listener;

        if (canClose) {
            setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        } else {
            setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        }
        closeButton.setVisible(canClose);
        terminateButton.setVisible(canTerminate);

        if (!canTerminate && !canClose) {
            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT - 20);
        }

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    setVisible(true);
                } catch (Exception ex) {
                    System.err.println("showWaitingDialog run setVisible,ex=" + ex.getMessage());
                }
            }
        });

    }

    /**
     * 关闭等待对话框
     */
    public synchronized void closeWaitingDialog() {
        if (isVisible()) {
            if (SwingUtilities.isEventDispatchThread()) {
                dispose();
            } else {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        dispose();
                    }
                });
            }
        }
    }

    private final class CloseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            listerner = null;
        }
    }

    private final class TerminateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            if (listerner != null) {
                listerner.terminate();
                listerner = null;
            }
        }
    }

    public static interface TerminateListener {
        void terminate();
    }

    public static void main(String[] args) {
        WaitingDialog dialog = new WaitingDialog();
        dialog.showWaitingDialog(false, true, new TerminateListener() {

            @Override
            public void terminate() {
            }
        });
        dialog.dispose();
    }
}
