/*
 * $Id: MenuInterpreter.java, 2009-4-8 下午05:38:29 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.interpreter;

import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import com.yuep.core.client.component.menu.interpreter.xmldao.XMenu;

/**
 * <p>
 * Title: MenuInterpreter
 * </p>
 * <p>
 * Description:配置菜单解析器
 * </p>
 * 
 * @author aaron lee
 * created 2009-4-8 下午05:38:29
 * modified [who date description]
 * check [who date description]
 */
public interface MenuInterpreter {

    /**
     * 根据菜单名获得所需要的菜单
     * 
     * @param menuName
     *            菜单名
     */
    public JMenu getMenu(String menuName);

    /**
     * @param menuName
     *            菜单名
     * @param selectedButtonTexts
     *            被选中菜单的text
     * @return
     */
    public JMenu getMenu(String menuName, String[] selectedButtonTexts);

    /**
     * 根据菜单名和参数获得所需要的右键菜单
     * 
     * @param menuName
     *            菜单名
     * @param paramObjs
     *            参数
     * @return JPopupMenu 所需要右键的菜单
     */
    public JPopupMenu getPopupMenu(String menuName);

    /**
     * 根据菜单名和参数获得所需要的右键菜单
     * 
     * @param menuName
     *            菜单名
     * @param paramObjs
     *            参数
     * @return JPopupMenu 所需要右键的菜单
     */
    public JPopupMenu getPopupMenu(String menuName, Object[] paramObjs, Object[] selectObjects);

    /**
     * @param menuName
     *            菜单名
     * @param paramObjs
     *            参数
     * @param selectObjects
     * @param judgeSelectedObject
     *            是否可以选择不同类型对象
     * @return JPopupMenu 所需要右键的菜单
     */
    public JPopupMenu getPopupMenu(String menuName, Object[] paramObjs, Object[] selectObjects,
            boolean judgeSelectedObject);

    /**
     * 根据菜单名和参数获得所需要的右键菜单
     * 
     * @param menuName
     *            菜单名
     * @param paramObjs
     *            参数
     * @param selectedButtonTexts
     *            当前被选中的radioButton的text
     * @return JPopupMenu 所需要右键的菜单
     */
    public JPopupMenu getPopupMenu(String menuName, Object[] paramObjs, Object[] selectObjects,
            String[] selectedButtonTexts);
    
    Map<String, XMenu> getXmenuMap();

    /**
     * 加载menu配置文件
     * @param fileNames 要加载的菜单文件名
     */
    public void loadMenuFiles(String... fileNames);
    
    /**
     * 加载主菜单，主菜单的文件名固定为"main-menu-data.xml"
     * @param modules 要添加主菜单的模块
     */
    public void loadMainMenuFiles(String... modules);
}
