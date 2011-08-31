/*
 * $Id: XToolBarView.java, 2009-2-18 下午02:44:03 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.toolbar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.Module;
import com.yuep.core.bootstrap.def.module.ModulePriority;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.toolbar.interpreter.ToolBarInterpreter;
import com.yuep.core.client.component.toolbar.interpreter.ToolBarInterpreterImpl;
import com.yuep.core.client.component.toolbar.interpreter.xmldao.XToolBar;
import com.yuep.core.client.component.toolbar.interpreter.xmldao.XToolBarButton;

/**
 * <p>
 * Title: XToolBarView
 * </p>
 * <p>
 * Description:扩展的工具栏
 * </p>
 * <p>
 * 支持显示工具栏，搜索栏，告警栏和Quickview栏
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-18 下午02:44:03
 * modified [who date description]
 * check [who date description]
 */
public class XToolBarView extends JPanel {

    private static final long serialVersionUID = -6665939208439152077L;

    /**
     * 工具栏
     */
    private JToolBar toolBar;

    private transient ToolBarInterpreter interpreter;

    /**
     * constructor.
     */
    public XToolBarView() {

        constructToolBar();
        setOpaque(true);
    }

    /**
     * 创建ToolBar
     */
    private void constructToolBar() {
        BorderLayout borderLayout = ClientCoreContext.getSwingFactory().getBorderLayout();
        borderLayout.setVgap(0);
        setLayout(borderLayout);
    }

    /**
     * 初始化工具栏
     */
    public void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setBorderPainted(false);
        FlowLayout mgr = ClientCoreContext.getSwingFactory().getFlowLayout(FlowLayout.LEFT);
        mgr.setVgap(0);
        toolBar.setLayout(mgr);
        toolBar.setFloatable(false);
        toolBar.setBorderPainted(false);
        toolBar.setMargin(new Insets(0, 0, 0, 0));
        toolBar.setRollover(true);
        interpreter = new ToolBarInterpreterImpl();
        ModulePriority modulePriority = ModuleContext.getInstance().getModulePriority();
        List<Module> startOrder = modulePriority.getStartOrder();
        List<String> moduleNames = new ArrayList<String>();
        for (Module module : startOrder) {
            String moduleName = module.getModuleName();
            moduleNames.add(moduleName);
        }
        interpreter.loadMainMenuFiles(moduleNames.toArray(new String[] {}));
        List<XToolBar> toolBars = interpreter.getXToolBars();
        if(CollectionUtils.isEmpty(toolBars))
            return;
        for (XToolBar xtoolBar : toolBars) {
            List<XToolBarButton> toolBarButtons = xtoolBar.getToolBarButtons();
            for (XToolBarButton xtoolBarButton : toolBarButtons) {
                JButton toolBarButton = interpreter.getToolBarButton(xtoolBarButton);
                addToolBarButton(toolBarButton);
            }
        }
        add(toolBar, BorderLayout.LINE_START);
        toolBar.setOpaque(false);
    }

    public void addToolBarButton(JButton button) {
        if (button != null)
            toolBar.add(button);
    }
}
