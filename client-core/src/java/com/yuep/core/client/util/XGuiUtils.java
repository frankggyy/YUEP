/*
 * $Id: YotcGuiUtils.java, 2009-2-12 下午05:01:16 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.util;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.exception.YuepBatchException;
import com.yuep.base.exception.YuepException;
import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: YotcGuiUtils
 * </p>
 * <p>
 * Description: GUI操作工具类
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-12 下午05:01:16
 * modified [who date description]
 * check [who date description]
 */
public class XGuiUtils {

    /**
     * register key action when VK_ESCAPE pressed
     */
    public static void registerEscapeAction(JRootPane pane, ActionListener l) {
        KeyStroke keyStroke = KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE);

        pane.registerKeyboardAction(l, keyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /**
     * Pub given window to the screen center.
     * 
     * @param window
     *            the window which will be centered.
     */
    public synchronized static void centerWindow(Window window) {
        Dimension screen = window.getToolkit().getScreenSize();
        window.setLocation((int) (screen.getWidth() - window.getWidth()) / 2,
                (int) (screen.getHeight() - window.getHeight()) / 2);
    }

    /**
     * 设置界面的缺省按钮(enter键响应的按钮)
     * 
     * @param jButton
     */
    public static void setDefaultButton(JButton jButton) {
        if (jButton == null) {
            throw new IllegalArgumentException();
        }
        jButton.getRootPane().setDefaultButton(jButton);
    }

    /**
     * 将Character转换成action mnemonic key所需要的int值
     * 
     * @param c
     *            Character 要转换的对象
     */
    public static int getMnemonicKey(char c) {
        int vk = c;
        if (vk >= 'a' && vk <= 'z') {
            vk -= ('a' - 'A');
        }
        return vk;
    }

    public static void addDefaultFocus(Window window, final JComponent component) {
        window.addWindowFocusListener(new WindowAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * java.awt.event.WindowAdapter#windowGainedFocus(java.awt.event
             * .WindowEvent)
             */
            @Override
            public void windowGainedFocus(WindowEvent e) {
                component.requestFocusInWindow();
            }
        });
    }

    /**
     * 添加键盘Esc事件的处理方法；
     * <p>
     * 主要是通知窗口监听器关闭事件
     * 
     * @param window
     *            添加监听键盘esc事件的窗口
     * @author aaron lee
     */
    public static void addKeyStrokeAction_Esc(final Window window) {
        if (window == null)
            throw new IllegalArgumentException();
        JRootPane rootPane = null;
        if (window instanceof JFrame) {// 判断传入参数是否是JFrame
            rootPane = ((JFrame) window).getRootPane();
        } else if (window instanceof JDialog) {// 判断传入参数是否是JDialog
            rootPane = ((JDialog) window).getRootPane();
        }
        // 判断rootPane是否为null，如果为null，return
        if (rootPane == null)
            return;
        // 键盘Esc事件
        Action escapeAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            {
                putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
            }

            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        };
        // 取得Esc的KeyStroke
        KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
        // 将Esc action binding到窗口
        rootPane.getActionMap().put("ESCAPE", escapeAction);
    }
    
    /**
     * 根据ErrorCode获取错误信息
     * @param errorCode 错误代码
     * @param source 错误源
     * @return String 错误信息
     */
    public static String getMessageByErrorCode(int errorCode, String... source) {
        String messageKey = errorCode + ".message";
        String message = ClientCoreContext.getString(messageKey, source);
        return message;
    }
    
    /**
     * 得到异常(普通异常、批量异常、YuepException)国际化的message
     * @param th
     * @return
     */
    public static String getMessageByException(Throwable arg){
        Throwable th=arg;
        if(arg instanceof ExecutionException){
            ExecutionException eEx=(ExecutionException)arg;
            th = eEx.getCause();
        }else if(arg instanceof InvocationTargetException){
            InvocationTargetException iEx=(InvocationTargetException)arg;
            th=iEx.getTargetException();
        }
        if(th==null)
            return null;
        
        if(th instanceof YuepException){
            YuepException xtEx=(YuepException)th;
            return getMessageByErrorCode(xtEx.getErrorCode(), xtEx.getSource());
        }else if(th instanceof YuepBatchException){
            YuepBatchException xtBatchEx=(YuepBatchException)th;
            StringBuilder sb=new StringBuilder();
            Map<Object, Throwable> exMap = xtBatchEx.getThrowables();
            for (Map.Entry<Object, Throwable> entry : exMap.entrySet()) {
                Object object = entry.getKey();
                if (object == null)
                    continue;
                sb.append("source:" + object.toString() + "ex=");
                Throwable th1 = entry.getValue();
                if (th1 instanceof YuepException) {
                    YuepException xtEx1 = (YuepException) th1;
                    sb.append(getMessageByErrorCode(xtEx1.getErrorCode(), xtEx1.getSource()));
                } else {
                    sb.append(ExceptionUtils.getCommonExceptionInfo(th));
                }
                sb.append(";");
             }
            return sb.toString();
        }else{
            return ExceptionUtils.getCommonExceptionInfo(th);
        }
    }
    
    /**
     * 根据ErrorCode获取解决方案
     * @param errorCode 错误代码
     * @param source 错误源
     * @return String 解决方案
     */
    public static String getSolutionByErrorCode(int errorCode, String... source) {
        String solutionKey = errorCode + ".solution";
        String solution = ClientCoreContext.getString(solutionKey, source);
        if (solution.indexOf(".solution") != -1)
            return "";
        return solution;
    }
    
    /**
     * 字符串转换方法
     * 
     * @param name
     *            需要转换的字符串
     * @return String 转换后的字符串
     * @author aaron lee
     */
    public static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
}
