/*
 * $Id: DialogUtils.java, 2009-2-21 下午01:52:19 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.util;

import java.awt.Component;
import java.awt.Window;
import java.util.concurrent.ExecutionException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.exception.YuepBatchException;
import com.yuep.base.exception.YuepException;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.window.BatchResultDialog;
import com.yuep.core.client.component.window.MessageDialog;
import com.yuep.core.client.component.window.WaitingDialog;
import com.yuep.core.client.component.window.MessageDialog.MessageType;
import com.yuep.core.client.component.window.WaitingDialog.TerminateListener;
import com.yuep.core.client.main.mainframe.action.system.SystemExitAction;

/**
 * <p>
 * Title: DialogUtils
 * </p>
 * <p>
 * Description:客户端对话框工具类，比如打开提示窗、确认窗、异常展示窗等
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-21 下午01:52:19
 * modified [who date description]
 * check [who date description]
 */
public class DialogUtils {

    /**
     * 确认提示框
     * @see javax.swing.JOptionPane#showConfirmDialog
     */
    public static int showConfirmDialog(Component parentComponent, Object message) {
        setParentComponentLocale(parentComponent);
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showConfirmDialog(parentComponent, textStr);
    }

    /**
     * 删除操作提示框
     * 
     * @param window
     * @return 返回值使用GuiConstants中RetValues接口中的常量做为比较
     * @see SystemExitAction
     */
    public static int showDeleteConfirmDialog(Window window) {
        return DialogUtils.showYesNoOptionDialog(window, "common.confirm.delete.message",
                "common.confirm.delete.title");
    }
    
    /**
     * 操作完毕后的提示框
     * @param window
     * @return
     */
    public static void showCompletedConfirmDialog(Window window) {
        DialogUtils.showInfoDialog(window, "common.confirm.completed.message");
    }

    /**
     * 确认提示框
     * @see javax.swing.JOptionPane#showConfirmDialog
     */
    public static int showConfirmDialog(Component parentComponent, Object message, String title,
            int optionType) {
        setParentComponentLocale(parentComponent);
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showConfirmDialog(parentComponent, textStr, ClientCoreContext.getString(title),
                optionType);
    }

    /**
     * 确认提示框
     * @see javax.swing.JOptionPane#showConfirmDialog
     */
    public static int showConfirmDialog(Component parentComponent, Object message, String title,
            int optionType, int messageType, Icon icon) {
        setParentComponentLocale(parentComponent);
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showConfirmDialog(parentComponent, textStr, ClientCoreContext.getString(title),
                optionType, messageType, icon);
    }

    /**
     * 消息窗口
     * 
     * @param message
     * @param window
     */
    public static void showInfoDialog(Component window, String message) {
        setParentComponentLocale(window);
        String textStr = ClientCoreContext.getString(message);
        JOptionPane.showMessageDialog(window, textStr, ClientCoreContext.getString("common.info.title"),
                GuiConstants.Messages.INFORMATION_MESSAGE);
    }

    /**
     * 警告窗口
     * 
     * @param message
     * @param window
     */
    public static void showWarnDialog(Component window, String message) {
        setParentComponentLocale(window);
        String textStr = ClientCoreContext.getString(message);
        JOptionPane.showMessageDialog(window, textStr, ClientCoreContext.getString("common.waring.title"),
                GuiConstants.Messages.WARNING_MESSAGE);
    }

    /**
     * 错误信息窗口
     * 
     * @param message
     * @param window
     */
    public static void showErrorDialog(Component window, String message) {
        setParentComponentLocale(window);
        String textStr = ClientCoreContext.getString(message);
        JOptionPane.showMessageDialog(window, textStr, ClientCoreContext.getString("common.error.title"),
                GuiConstants.Messages.ERROR_MESSAGE);
    }

    /**
     * 消息窗口
     * 
     * @param message
     * @param window
     */
    public static void showInfoDialog(Component window, String message, Icon icon) {
        setParentComponentLocale(window);
        String textStr = ClientCoreContext.getString(message);
        JOptionPane.showMessageDialog(window, textStr, ClientCoreContext.getString("common.info.title"),
                GuiConstants.Messages.INFORMATION_MESSAGE, icon);
    }

    /**
     * 警告窗口
     * 
     * @param message
     * @param window
     */
    public static void showWarnDialog(Component window, String message, Icon icon) {
        setParentComponentLocale(window);
        String textStr = ClientCoreContext.getString(message);
        JOptionPane.showMessageDialog(window, textStr, ClientCoreContext.getString("common.waring.title"),
                GuiConstants.Messages.WARNING_MESSAGE, icon);
    }

    /**
     * 错误提示框
     * @param window
     * @param e
     */
    public static void showErrorExpandDialog(Component window, Throwable e) {
        String stackString = "";
        String message = "";

        Throwable e2 = null;
        // 先把concurrent包装的异常解出来，然后再进行YotcException处理
        if (e instanceof ExecutionException) {
            ExecutionException ee = (ExecutionException) e;
            e2 = ee.getCause();
        }
        if (e2 == null)
            e2 = e;

        if (e2 instanceof YuepException) {
            String solution=null;
            YuepException ex = (YuepException) e2;
            stackString = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(ex);
            int errorCode = ex.getErrorCode();
            if(errorCode>=YuepException.INTERNAL_ERROR){
                message = XGuiUtils.getMessageByErrorCode(errorCode, ex.getSource());
                solution = XGuiUtils.getSolutionByErrorCode(errorCode, ex.getSource());
            }else{
                message=ExceptionUtils.getCommonExceptionInfo(e2);
            }
            showMessageDialog(window, ClientCoreContext.getString("common.error.title"), message, stackString,
                    solution, MessageType.Error);
        }else if(e2 instanceof YuepBatchException){
            showBatchResultDialog((Window)window,(YuepBatchException)e2);
        } else {
            if(e2==null)
                return;
            message = e2.getMessage();
            ToStringBuilder tsb = new ToStringBuilder(e2, ToStringStyle.MULTI_LINE_STYLE).append("catalog",
                    e2.getClass().getSimpleName()).append("message", e2.getMessage());
            if (e2 != null) {
                String fullCause = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e2);
                tsb.append("cause", fullCause);
            }
            stackString = tsb.toString();
            showMessageDialog(window, ClientCoreContext.getString("common.error.title"), message, stackString,
                    "", MessageType.Error);
        }
    }

    /**
     * 显示批量处理异常窗口
     * @param parentWindow
     * @param yotcBatchException
     */
    public static void showBatchResultDialog(final Window parentWindow,final YuepBatchException yotcBatchException){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BatchResultDialog batchResultDialog=new BatchResultDialog(parentWindow,yotcBatchException);
                batchResultDialog.setVisible(true);
            }

        });
    
    }
    
    /**
     * 所有传入参数都是国际化后的值
     * @param window
     * @param stackString
     * @param message
     */
    public static void showMessageDialog(final Component window, final String title, final String message,
            final String stackString, final String solution, final MessageType messageType) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MessageDialog messageDialog = new MessageDialog((Window) window, title, message, stackString, solution, messageType);
                messageDialog.setVisible(true);
            }

        });
    }

    /**
     * 错误信息窗口
     * 
     * @param message
     * @param window
     */
    public static void showErrorDialog(Component window, String message, Icon icon) {
        setParentComponentLocale(window);
        String textStr = ClientCoreContext.getString(message);
        JOptionPane.showMessageDialog(window, textStr, ClientCoreContext.getString("common.error.title"),
                GuiConstants.Messages.ERROR_MESSAGE, icon);
    }

    /**
     * 提示选择提示框
     * @see javax.swing.JOptionPane#showOptionDialog
     */
    public static int showOptionDialog(Component parentComponent, Object message, String title,
            int optionType, int messageType, Icon icon, Object[] options, Object initialValue) {
        setParentComponentLocale(parentComponent);
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showOptionDialog(parentComponent, textStr, ClientCoreContext.getString(title),
                optionType, messageType, icon, options, initialValue);
    }

    /**
     * 输入框
     * @see javax.swing.JOptionPane#showInputDialog
     */
    public static String showInputDialog(Object message) {
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showInputDialog(textStr);
    }

    /**
     * 输入框
     * @see javax.swing.JOptionPane#showInputDialog
     */
    public static String showInputDialog(Object message, Object initialSelectionValue) {
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showInputDialog(textStr, initialSelectionValue);
    }

    /**
     * 输入框
     * @see javax.swing.JOptionPane#showInputDialog
     */
    public static String showInputDialog(Component parentComponent, Object message) {
        setParentComponentLocale(parentComponent);
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showInputDialog(parentComponent, textStr);
    }

    /**
     * 输入框
     * @see javax.swing.JOptionPane#showInputDialog
     */
    public static String showInputDialog(Component parentComponent, Object message,
            Object initialSelectionValue) {
        setParentComponentLocale(parentComponent);
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showInputDialog(parentComponent, textStr, initialSelectionValue);
    }

    /**
     * 输入框
     * @see javax.swing.JOptionPane#showInputDialog
     */
    public static String showInputDialog(Component parentComponent, Object message, String title,
            int messageType) {
        setParentComponentLocale(parentComponent);
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showInputDialog(parentComponent, textStr, ClientCoreContext.getString(title),
                messageType);

    }

    /**
     * 输入框
     * @see javax.swing.JOptionPane#showInputDialog
     */
    public static Object showInputDialog(Component parentComponent, Object message, String title,
            int messageType, Icon icon, Object[] selectionValues, Object initialSelectionValue) {
        setParentComponentLocale(parentComponent);
        String textStr = "";
        if (message != null)
            textStr = ClientCoreContext.getString(message.toString());
        return JOptionPane.showInputDialog(parentComponent, textStr, ClientCoreContext.getString(title),
                messageType, icon, selectionValues, initialSelectionValue);
    }

    /**
     * yes和no的确认框
     * @param parentComponent
     * @param message
     * @param title
     * @return
     */
    public static int showYesNoOptionDialog(Component parentComponent, Object message, String title) {
        setParentComponentLocale(parentComponent);
        return showOptionDialog(parentComponent, message, title, GuiConstants.Options.YES_NO_OPTION,
                GuiConstants.Messages.QUESTION_MESSAGE, (Icon) null, null, 1);

    }

    /**
     * 设置父控件
     * @param parentComponent
     */
    private static void setParentComponentLocale(Component parentComponent) {
        if (parentComponent != null)
            parentComponent.setLocale(ClientCoreContext.getLocale());
    }

    /**
     * 等待框
     * @param terminate
     * @param closed
     * @param listener
     * @return
     */
    public static WaitingDialog showWaitingDialog(boolean terminate, boolean closed,
            TerminateListener listener) {
        return showWaitingDialog(terminate, closed, listener, null);
    }

    /**
     * 等待框
     * @param terminate
     * @param closed
     * @param listener
     * @param window
     * @return
     */
    public static WaitingDialog showWaitingDialog(boolean terminate, boolean closed,
            TerminateListener listener, Window window) {
        WaitingDialog waitingDialog = new WaitingDialog(window);
        waitingDialog.showWaitingDialog(terminate, closed, listener);
        return waitingDialog;
    }
    
    /**
     * 弹出等待窗口，可以定制窗口的title,message
     * @param terminate
     * @param closed
     * @param listener
     * @param window
     * @param title
     * @param message
     * @return
     */
    public static WaitingDialog showWaitingDialog(boolean terminate, boolean closed,
            TerminateListener listener, Window window, String title, String message) {
        WaitingDialog waitingDialog = new WaitingDialog(window,title,message);
        waitingDialog.showWaitingDialog(terminate, closed, listener);
        return waitingDialog;
    }

    /**
     * 等待框
     * @param window
     * @return
     */
    public static WaitingDialog showWaitingDialog(Window window) {
        WaitingDialog waitingDialog = new WaitingDialog(window);
        waitingDialog.showWaitingDialog(false, false, null);
        return waitingDialog;
    }

    /**
     * 等待框
     * @return
     */
    public static WaitingDialog showWaitingDialog() {
        WaitingDialog waitingDialog = new WaitingDialog();
        waitingDialog.showWaitingDialog(false, false, null);
        return waitingDialog;
    }
    
    
}
