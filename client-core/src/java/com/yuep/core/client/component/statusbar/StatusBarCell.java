/*
 * $Id: StatusBarCell.java, 2009-3-5 上午09:26:47 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar;

import javax.swing.JComponent;

/**
 * <p>
 * Title: StatusBarCell
 * </p>
 * <p>
 * Description:状态栏上的状态块
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午09:26:47
 * modified [who date description]
 * check [who date description]
 */
public interface StatusBarCell {
    /**
     * 获取状态块Swing JComponent
     * @return
     *       Swing JComponent
     */
    JComponent getComponent();
    /**
     * 获取状态对象
     * @return
     *        Status
     */
    Status getStatus();
    /**
     * 设置状态对象
     * @param status
     */
    void setStatus(Status status);
    /**
     * 显示Toptip
     * @param message
     */
    void showBubble(Object message);
    /**
     * 隐藏TopTip
     */
    void hideBubble();
    /**
     * 刷新
     */
    void refresh();
}
