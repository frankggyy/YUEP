/*
 * $Id: XToolBar.java, 2009-2-18 上午09:59:36 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.toolbar.interpreter.xmldao;

import java.util.List;

/**
 * <p>
 * Title: XToolBar
 * </p>
 * <p>
 * Description:工具栏的XML Dao映射类
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-18 上午09:59:36
 * modified [who date description]
 * check [who date description]
 */
public class XToolBar {

	/**
	 * toolBar包含多个XToolBarButton
	 */
    List<XToolBarButton> toolBarButtons;

    /**
     * @return the toolBarButtons
     */
    public List<XToolBarButton> getToolBarButtons() {
        return toolBarButtons;
    }

    /**
     * @param toolBarButtons
     *            the toolBarButtons to set
     */
    public void setToolBarButtons(List<XToolBarButton> toolBarButtons) {
        this.toolBarButtons = toolBarButtons;
    }
}
