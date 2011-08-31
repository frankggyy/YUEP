/*
 * $Id: ToolBarInterpreter.java, 2009-2-18 下午02:05:36 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.toolbar.interpreter;

import java.util.List;

import javax.swing.JButton;

import com.yuep.core.client.component.toolbar.interpreter.xmldao.XToolBar;
import com.yuep.core.client.component.toolbar.interpreter.xmldao.XToolBarButton;

/**
 * <p>
 * Title: ToolBarInterpreter
 * </p>
 * <p>
 * Description:工具栏解析器的接口
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-18 下午02:05:36
 * modified [who date description]
 * check [who date description]
 */
public interface ToolBarInterpreter {

	/**
	 * 获取ToolBar Button
	 * @param toolBarButton
	 * @return
	 */
    JButton getToolBarButton(XToolBarButton toolBarButton);
    
    /**
     * 获取该解释器包含的所有XToolBar
     * @return
     */
    List<XToolBar> getXToolBars();
    
    /**
     * 加载工具栏
     * @param modules 要添加主菜单的模块
     */
    public void loadMainMenuFiles(String... modules);
}
