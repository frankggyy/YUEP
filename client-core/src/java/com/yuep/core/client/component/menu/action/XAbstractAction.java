/*
 * $Id: XAbstractAction.java, 2010-3-25 ����11:30:13 aaron lee Exp $
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
 * Description:action�������
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-25 ����11:30:13
 * modified [who date description]
 * check [who date description]
 */
@SuppressWarnings("unchecked")
public abstract class XAbstractAction extends AbstractXAction {

    private static final long serialVersionUID = -863519896895433662L;

    protected ClientController controller;

    /**
     * �Ƿ���ֵȴ���
     */
    private boolean isWaiting = false;

    /**
     * constructor.
     * 
     * @param isMultiple
     *            �Ƿ�֧�ֶ�ѡ����
     * @param actionId
     *            У��Ȩ�޵�actionId
     */
    public XAbstractAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId);
        this.controller = controller;
    }

    /**
     * Constructor.����action���ṩ��ѡ���������Ϣ��ʹ�ø���Ϣ�ж�action������״̬��
     * 
     * @param isMultiple
     *            �Ƿ�֧�ֶ�ѡ����
     * @param actionId
     *            У��Ȩ�޵�actionId
     * @param selectedObjects
     *            ��ѡ���������Ϣ
     */
    public XAbstractAction(Boolean isMultiple, String actionId, Object[] selectedObjects, ClientController controller) {
        super(isMultiple, actionId, selectedObjects);
        this.controller = controller;
    }

    /**
     * @param actionId
     *            У��Ȩ�޵�actionId
     * @param controller
     */
    public XAbstractAction(String actionId, ClientController controller,String textKey,char mnemonic) {
        super(actionId,textKey,mnemonic);
        this.controller = controller;
    }
    
    /**
     * @param actionId
     *            У��Ȩ�޵�actionId
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
     *            У��Ȩ�޵�actionId
     */
    public XAbstractAction(String actionId) {
        super(actionId);
    }
    
    /**
     * Constructor.
     * 
     * @param actionId
     *            У��Ȩ�޵�actionId
     */
    public XAbstractAction(String actionId,String textKey,char mnemonic) {
        super(actionId,textKey,mnemonic);
    }

    /**
     * Constructor.����action���ṩ��ѡ���������Ϣ��ʹ�ø���Ϣ�ж�action������״̬��
     * 
     * @param actionId
     *            У��Ȩ�޵�actionId
     * @param selectedObjects
     *            ��ѡ���������Ϣ
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
     * run in background,���ú���EDT�л��������updateUi
     * �ύ���ݽӿڣ������ύ�����ݿ���·����豸�ȣ���������ύ�ɾ�������ʵ��
     * @param objs Ҫ�ύ�����ݣ��ӹ���Action�Ĳ���ClientController�л�ȡ
     * @return Ҫ��������µ����ݣ��˲�����ΪupdateUi���������
     */
    protected abstract Object[] commitData(Object... objs);

    /**
     * ���½��棬������commitData�ķ���ֵ���ṩ
     * @param objs commitData�ķ���ֵ
     */
    protected abstract void updateUi(Object... objs);

    /**
     * �����Ƿ���Ҫ�ȴ��Ի���
     * @param isWaiting
     */
    public void setWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
    }
}
