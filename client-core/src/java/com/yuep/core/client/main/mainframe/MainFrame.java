/*
 * $Id: MainFrame.java, 2010-4-27 下午03:44:56 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.XMenuBarView;
import com.yuep.core.client.component.output.OutputManager;
import com.yuep.core.client.component.statusbar.XStatusBar;
import com.yuep.core.client.component.statusbar.view.StatusBarManager;
import com.yuep.core.client.component.statusbar.view.StatusBarView;
import com.yuep.core.client.component.toolbar.XToolBarView;
import com.yuep.core.client.main.mainframe.action.system.SystemExitAction;

/**
 * <p>
 * Title: MainFrame
 * </p>
 * <p>
 * Description:网管客户端主界面
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:44:56
 * modified [who date description]
 * check [who date description]
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = -8976788242161434567L;

    private String title = ClientCoreContext.getString("common.mainframe.title");

    public MainFrame() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SystemExitAction systemExitAction = new SystemExitAction(SystemExitAction.class.getSimpleName());
                systemExitAction.actionPerformed(null);
            }

        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(screenSize.width, screenSize.height - 30));
        setIconImage(ClientCoreContext.getResourceFactory().getImage("logo.gif"));
        setExtendedState(MAXIMIZED_BOTH);
        setTitle(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * construct ui.
     */
    public void constructUi() {
        XMenuBarView menuBarView = ClientCoreContext.getMenuBarView();
        setJMenuBar(menuBarView.getMenuBar());
        Color background = menuBarView.getMenuBar().getBackground();
        XToolBarView toolBarView = ClientCoreContext.getToolBarView();
		toolBarView.setBackground(background);
        add(toolBarView, BorderLayout.NORTH);

        OutputManager outputManager = ClientCoreContext.getOutputManager();
        JSplitPane mainSplitPane = ClientCoreContext.getSwingFactory().getSplitPane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setDividerSize(3);
        mainSplitPane.setDividerLocation(screenSize.height * 4 / 5);
        mainSplitPane.setTopComponent(getTopoView());
        JComponent view = outputManager.getView();
        view.setMinimumSize(new Dimension(100, 24));
        mainSplitPane.setBottomComponent(view);
        // mainSplitPane.setOneTouchExpandable(true);
        add(mainSplitPane, BorderLayout.CENTER);
        StatusBarManager statusBarManager = ClientCoreContext.getStatusBarManager();
        StatusBarView statusBarView = statusBarManager.getStatusBarView();
        XStatusBar statusBar = statusBarView.getStatusBar();
        add(statusBar, BorderLayout.SOUTH);
    }
    
    /**
     * 右边展示区
     * @return
     */
    protected JComponent getTopoView(){
    	return ClientCoreContext.getSwingFactory().getPanel();
    }

    //public static void main(String[] args) {
    //    MainFrame mainFrame = new MainFrame();
    //    mainFrame.constructUi();
    //    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    mainFrame.setVisible(true);
    //}

    /**
     * 更新主界面title
     * 
     * @param subTitle
     *            变化的子title
     */
    public void updateTitle(String subTitle) {
        setTitle(title + " - " + subTitle);
    }
}
