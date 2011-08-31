/*
 * $Id: StatusBarCell.java, 2009-3-5 ����09:26:47 aaron lee Exp $
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
 * Description:״̬���ϵ�״̬��
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 ����09:26:47
 * modified [who date description]
 * check [who date description]
 */
public interface StatusBarCell {
    /**
     * ��ȡ״̬��Swing JComponent
     * @return
     *       Swing JComponent
     */
    JComponent getComponent();
    /**
     * ��ȡ״̬����
     * @return
     *        Status
     */
    Status getStatus();
    /**
     * ����״̬����
     * @param status
     */
    void setStatus(Status status);
    /**
     * ��ʾToptip
     * @param message
     */
    void showBubble(Object message);
    /**
     * ����TopTip
     */
    void hideBubble();
    /**
     * ˢ��
     */
    void refresh();
}
