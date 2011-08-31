/*
 * $Id: StatusBarView.java, 2009-3-5 ����10:21:20 aaron lee Exp $
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
 * Description:״̬���ӿ�
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 ����10:21:20
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
     * ��ĳ��״̬���Ϸ�����һ������ToolTip��С��ʾ��������ʽ���Բο�M2K������Ͽ��ķ�ӳ ��ʱ���Բ�ʵ��
     * @param index
     *            �к�
     * @param bubbleMsg
     *            ��ʾ��Ϣ
     * @param delaySeconds
     *            ��ʾ��ʱ�䣬���ֵС��0����Ӧ��һֱ��ʾ��ֱ���û�ͨ��������������رգ����ߵ���hideBubble����
     */
    void popupBubble(int index, Object bubbleMsg, int delaySeconds);

    /**
     * ����ToolTip
     * @param index
     *  �к�
     */
    void hideBubble(int index);
}
