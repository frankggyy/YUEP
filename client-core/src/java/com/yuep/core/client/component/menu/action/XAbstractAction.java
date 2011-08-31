/*
 * $Id: XAbstractAction.java, 2010-3-25 上午11:30:13 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.SwingWorker;

import com.yuep.core.client.component.window.WaitingDialog;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: XAbstractAction
 * </p>
 * <p>
 * Description:action抽象基类
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-25 上午11:30:13
 * modified [who date description]
 * check [who date description]
 */
@SuppressWarnings("unchecked")
public abstract class XAbstractAction extends AbstractXAction {

    private static final long serialVersionUID = -863519896895433662L;

    protected ClientController controller;

    /**
     * 是否出现等待框
     */
    private boolean isWaiting = false;

    /**
     * constructor.
     * 
     * @param isMultiple
     *            是否支持多选操作
     * @param actionId
     *            校验权限的actionId
     */
    public XAbstractAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId);
        this.controller = controller;
    }

    /**
     * Constructor.构造action是提供所选择的数据信息，使用该信息判断action的是能状态。
     * 
     * @param isMultiple
     *            是否支持多选操作
     * @param actionId
     *            校验权限的actionId
     * @param selectedObjects
     *            所选择的数据信息
     */
    public XAbstractAction(Boolean isMultiple, String actionId, Object[] selectedObjects, ClientController controller) {
        super(isMultiple, actionId, selectedObjects);
        this.controller = controller;
    }

    /**
     * @param actionId
     *            校验权限的actionId
     * @param controller
     */
    public XAbstractAction(String actionId, ClientController controller,String textKey,char mnemonic) {
        super(actionId,textKey,mnemonic);
        this.controller = controller;
    }
    
    /**
     * @param actionId
     *            校验权限的actionId
     * @param controller
     */
    public XAbstractAction(String actionId, ClientController controller) {
        super(actionId);
        this.controller = controller;
    }

    /**
     * Constructor.
     * 
     * @param actionId
     *            校验权限的actionId
     */
    public XAbstractAction(String actionId) {
        super(actionId);
    }
    
    /**
     * Constructor.
     * 
     * @param actionId
     *            校验权限的actionId
     */
    public XAbstractAction(String actionId,String textKey,char mnemonic) {
        super(actionId,textKey,mnemonic);
    }

    /**
     * Constructor.构造action是提供所选择的数据信息，使用该信息判断action的是能状态。
     * 
     * @param actionId
     *            校验权限的actionId
     * @param selectedObjects
     *            所选择的数据信息
     */
    public XAbstractAction(String actionId, Object[] selectedObjects, ClientController controller) {
        super(false, actionId, selectedObjects);
        this.controller = controller;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isWaiting) {
            Window window = null;
            if (controller != null)
                window = controller.getWindow();
            final WaitingDialog waitingDialog = DialogUtils.showWaitingDialog(false, false, null, window);

            new SwingWorker<Void, Void>() {
                private Object[] results = null;

                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        if (controller != null)
                            results = commitData(controller.getDatas().toArray(new Object[]{}));
                        else
                            results = commitData(new Object[]{});
                    } catch (Exception e) {
                        if (controller != null)
                            DialogUtils.showErrorExpandDialog(controller.getWindow(), e);
                        else
                            DialogUtils.showErrorExpandDialog(null, e);    
                    } finally {
                        waitingDialog.closeWaitingDialog();
                    }
                    return null;
                }

                @Override
                protected void done() {
                    super.done();
                    try {
                        updateUi(results);
                    } catch (Exception e) {
                        if (controller != null)
                            DialogUtils.showErrorExpandDialog(controller.getWindow(), e);
                        else
                            DialogUtils.showErrorExpandDialog(null, e);
                    } finally {
                        waitingDialog.closeWaitingDialog();
                        if (controller != null)
                            controller.getWindow().toFront();
                    }
                }
            }.execute();
        } else {
            try {
                Object[] results = null;
                if (controller != null)
                    results = commitData(controller.getDatas().toArray(new Object[]{}));
                else
                    results = commitData(new Object[]{});
                updateUi(results);
            } catch (Exception ex) {
                if (controller != null)
                    DialogUtils.showErrorExpandDialog(controller.getWindow(), ex);
                else
                    DialogUtils.showErrorExpandDialog(null, ex);
            }
        }

    }

    /**
     * run in background,调用后在EDT中会继续调用updateUi
     * 提交数据接口，例如提交到数据库或下发到设备等，具体如何提交由具体子类实现
     * @param objs 要提交的数据，从构造Action的参数ClientController中获取
     * @return 要做界面更新的数据，此参数作为updateUi的输入参数
     */
    protected abstract Object[] commitData(Object... objs);

    /**
     * 更新界面，数据有commitData的返回值来提供
     * @param objs commitData的返回值
     */
    protected abstract void updateUi(Object... objs);

    /**
     * 设置是否需要等待对话框
     * @param isWaiting
     */
    public void setWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
    }
}
