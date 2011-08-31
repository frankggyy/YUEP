/*
 * $Id: XDeleteAction.java, 2009-8-28 ����03:11:11 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

import java.awt.event.ActionEvent;

import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.GuiConstants;

/**
 * <p>
 * Title: XDeleteAction
 * </p>
 * <p>
 * Description:ɾ��
 * </p>
 * 
 * @author aaron lee
 * created 2009-8-28 ����03:11:11
 * modified [who date description]
 * check [who date description]
 */
public abstract class XDeleteAction extends XAbstractAction {
    private static final long serialVersionUID = 588211175033894706L;

    /**
     * constructor.
     * 
     * @param isMultiple
     *            �Ƿ�֧�ֶ�ѡ����
     * @param actionId
     *            У��Ȩ�޵�actionId
     */
    public XDeleteAction(boolean isMultiple, String actionId, ClientController controller) {
        super(isMultiple, actionId, controller);
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
    public XDeleteAction(Boolean isMultiple, String actionId, Object[] selectedObjects, ClientController controller) {
        super(isMultiple, actionId, selectedObjects, controller);
    }

    /**
     * @param actionId
     *            У��Ȩ�޵�actionId
     * @param controller
     */
    public XDeleteAction(String actionId, ClientController controller,String textKey,char mnemonic) {
        super(actionId,textKey,mnemonic);
        this.controller = controller;
    }

    /**
     * Constructor.
     * 
     * @param actionId
     *            У��Ȩ�޵�actionId
     */
    public XDeleteAction(String actionId) {
        super(actionId);
    }

    /**
     * Constructor.����action���ṩ��ѡ���������Ϣ��ʹ�ø���Ϣ�ж�action������״̬��
     * 
     * @param actionId
     *            У��Ȩ�޵�actionId
     * @param selectedObjects
     *            ��ѡ���������Ϣ
     */
    public XDeleteAction(String actionId, Object[] selectedObjects, ClientController controller) {
        super(false, actionId, selectedObjects, controller);
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int isDelete = DialogUtils.showDeleteConfirmDialog(null);
        if (isDelete == GuiConstants.RetValues.YES_OPTION) {
            super.actionPerformed(e);
        }
    }
}
