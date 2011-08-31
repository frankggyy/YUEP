/*
 * $Id: GuiConstants.java, 2009-2-21 下午01:53:57 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.util;

import javax.swing.JOptionPane;

/**
 * <p>
 * Title: GuiConstants
 * </p>
 * <p>
 * Description:界面常量类
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-21 下午01:53:57
 * modified [who date description]
 * check [who date description]
 */
public interface GuiConstants {

    /**
     * when TreeComboPane dialog was closed, RetValues indicates its result
     */
    public interface RetValues {
        /**
         * commonly, user click ok button to close the dialog
         */
        int OK_OPTION = JOptionPane.OK_OPTION;
        /**
         * commonly, user click cancel button to close the dialog
         */
        int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;

        /**
         * commonly, user select YES to close the dialog
         */
        int YES_OPTION = JOptionPane.YES_OPTION;
        /**
         * commonly, user select NO to close the dialog
         */
        int NO_OPTION = JOptionPane.NO_OPTION;
        /**
         * 表示用户没有操作，往往是直接点击dialog的关闭按钮
         */
        int ORIGIN_OPTION = 0;

    }

    /**
     *  选择按钮的常量
     * <p>
     * Title: Options
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @author sufeng
     */
    public interface Options {
        int YES_NO_OPTION = JOptionPane.YES_NO_OPTION;
        int YES_NO_CANCEL_OPTION = JOptionPane.YES_NO_CANCEL_OPTION;
        int OK_CANCEL_OPTION = JOptionPane.OK_CANCEL_OPTION;
        int DEFAULT_OPTION = JOptionPane.DEFAULT_OPTION;
    }

    /**
     * 提示的消息常量
     * <p>
     * Title: Messages
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @author sufeng
     */
    public interface Messages {
        int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
        int PLAIN_MESSAGE = JOptionPane.PLAIN_MESSAGE;
        int INFORMATION_MESSAGE = JOptionPane.INFORMATION_MESSAGE;
        int QUESTION_MESSAGE = JOptionPane.QUESTION_MESSAGE;
        int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;
    }
    
}
