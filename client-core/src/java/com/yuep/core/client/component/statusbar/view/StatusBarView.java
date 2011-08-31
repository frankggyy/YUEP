/*
 * $Id: StatusBarView.java, 2009-3-5 上午10:21:20 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar.view;

import javax.swing.Icon;

import com.yuep.core.client.component.statusbar.XStatusBar;

/**
 * <p>
 * Title: StatusBarView
 * </p>
 * <p>
 * Description:状态栏接口
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午10:21:20
 * modified [who date description]
 * check [who date description]
 */
public interface StatusBarView {
    /**
     * Returns status bar
     * @return statusBar
     */
    XStatusBar getStatusBar();
    
    
    /**
     * Refresh Status
     */
    void refreshStatus();
    
    /**
     * find index by status
     * @param statusKey
     * @return index
     */
    int getStatusIndex(String statusKey);
    
    
    /**
     * @param key the key of status
     * @param value
     */
    void updateStatusValue(String key, Object value);
    
    /**
     * Update status icon by key and icon.
     * @param key Key
     * @param icon Icom
     */
    void updateStatusIcon(String key, Icon icon);
    
       /**
     * 在某列状态的上方弹出一个类似ToolTip的小提示，具体形式可以参考M2K对网络断开的反映 暂时可以不实现
     * @param index
     *            列号
     * @param bubbleMsg
     *            显示消息
     * @param delaySeconds
     *            显示的时间，如果值小于0，则应该一直显示，直到用户通过界面上面点击其关闭，或者调用hideBubble方法
     */
    void popupBubble(int index, Object bubbleMsg, int delaySeconds);

    /**
     * 隐藏ToolTip
     * @param index
     *  列号
     */
    void hideBubble(int index);
}
