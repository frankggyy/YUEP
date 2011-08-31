/*
 * $Id: LoginSplash.java, 2010-4-27 下午02:28:26 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import com.sun.java.swing.plaf.windows.WindowsProgressBarUI;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.label.IconLabelDecorator;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: LoginSplash
 * </p>
 * <p>
 * Description:登录界面的Splash闪屏
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午02:28:26
 * modified [who date description]
 * check [who date description]
 */
public class LoginSplash extends JWindow {
    private static final long serialVersionUID = -3041610692092857998L;
    private JProgressBar progressBar = new JProgressBar();

    /**
     * @param image
     */
    public LoginSplash() {
        super(new JFrame());
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        progressBar.setUI((WindowsProgressBarUI) WindowsProgressBarUI.createUI(progressBar));
        progressBar.setBorderPainted(false);
        pane.add(ClientCoreContext.getSwingFactory().getLabel(new IconLabelDecorator("splash.png")),
                BorderLayout.CENTER);
        pane.add(progressBar, BorderLayout.SOUTH);
        this.getContentPane().add(pane, BorderLayout.CENTER);
        this.pack();
        XGuiUtils.centerWindow(this);
        this.setVisible(true);
        this.toFront();
    }

    /**
     * 更新进度条
     * 
     * @param progress
     */
    public void updateProgress(int progress) {
        progressBar.setValue(progress);
    }
}
