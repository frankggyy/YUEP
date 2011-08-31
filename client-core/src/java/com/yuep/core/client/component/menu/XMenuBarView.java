/*
 * $Id: XMenuBarView.java, 2009-2-19 下午06:23:17 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.Module;
import com.yuep.core.bootstrap.def.module.ModulePriority;
import com.yuep.core.client.component.menu.interpreter.MenuInterpreterImpl;
import com.yuep.core.client.component.menu.interpreter.xmldao.XMenu;

/**
 * <p>
 * Title: XMenuBarView
 * </p>
 * <p>
 * Description:主菜单栏
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-19 下午06:23:17
 * modified [who date description]
 * check [who date description]
 */
public class XMenuBarView {

    private static final long serialVersionUID = -2392624466165056116L;
    private JMenuBar menuBar;
    private MenuInterpreterImpl interpreter;

    /**
     * 构造菜单栏
     * constructor.
     */
    public XMenuBarView() {
        constructMenuBar();
    }

    /**
     * construct menu bar.
     */
    private void constructMenuBar() {
        menuBar = new JMenuBar();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        menuBar.setMinimumSize(new Dimension(screenSize.width - 10, 0));
    }

    /**
     * 初始化菜单
     */
    public void initMenu() {
        interpreter = new MenuInterpreterImpl("");
        ModulePriority modulePriority = ModuleContext.getInstance().getModulePriority();
        List<Module> startOrder = modulePriority.getStartOrder();
        List<String> moduleNames = new ArrayList<String>();
        for (Module module : startOrder) {
            String moduleName = module.getModuleName();
            moduleNames.add(moduleName);
        }
        interpreter.loadMainMenuFiles(moduleNames.toArray(new String[] {}));
        List<XMenu> mainMenus = interpreter.getMainMenus();
        if(CollectionUtils.isEmpty(mainMenus))
            return;
        for (XMenu menu : mainMenus) {
            String name = menu.getName();
            addMenu(name);
        }
    }

    /**
     * 从配置文件中添加菜单
     * @param menuName
     */
    private void addMenu(String menuName) {
        JMenu menu = interpreter.getMenu(menuName);
        if (menu != null)
            menuBar.add(menu);
    }
    
    /**
     * 添加手工菜单
     * @param menu
     */
    public void addMenu(JMenu menu) {
        if (menu != null)
            menuBar.add(menu);
    }

    /**
     * 菜单栏
     * @return the menuBar
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
