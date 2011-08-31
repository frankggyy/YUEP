/*
 * $Id: WindowManager.java, 2009-3-2 上午10:04:51 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.window;

import java.awt.Window;

import com.yuep.core.client.mvc.AbstractClientModel;
import com.yuep.core.client.mvc.AbstractClientView;
import com.yuep.core.client.mvc.ClientController;

/**
 * <p>
 * Title: WindowManager
 * </p>
 * <p>
 * Description: 客户端窗口管理器
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-2 上午10:04:51
 * modified [who date description]
 * check [who date description]
 */
public interface WindowManager {
    
    /**
     * 打开窗口
     * @param <T>
     * @param controller
     * @param title
     */
    <T extends Object> void openAsFrame(
            ClientController<T, ? extends AbstractClientView<T>, ? extends AbstractClientModel<T>> controller,Object... titles);

    /**
     * 打开窗口
     * @param <T>
     * @param controller
     * @param title
     */
    <T extends Object> void openAsDialog(
            ClientController<T, ? extends AbstractClientView<T>, ? extends AbstractClientModel<T>> controller,Object... titles);
    
    /**
     * 打开窗口
     * @param <T>
     * @param controller
     * @param isResizeable
     * @param titles
     */
    public <T> void openAsDialog(
            ClientController<T, ? extends AbstractClientView<T>, ? extends AbstractClientModel<T>> controller,
            boolean resizable, Object... titles);

    /**
     * 获得当前被打开的窗口，不包括主界面
     * 
     * @return 所有被打开的窗口，使用map存储，key为页面的helpId，value为被打开的页面
     */
    Window getCurrentOpenWindow(String id);
    
}
