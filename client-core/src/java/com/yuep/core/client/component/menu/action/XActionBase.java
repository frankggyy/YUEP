/*
 * $Id: XActionBase.java, 2009-2-9 ����03:44:43 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

import javax.swing.AbstractAction;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Title: XActionBase
 * </p>
 * <p>
 * Description:��Ҫ���ж�actionȨ�޵�У��
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 ����03:44:43
 * modified [who date description]
 * check [who date description]
 */
public abstract class XActionBase extends AbstractAction implements BussinessAllowable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6026282974596166596L;

    /**
     * Ȩ�޵�ʹ��״̬ for <code>permissionEnabled</code>
     */
    protected boolean permissionEnabled = true;

    /**
     * constructor
     * 
     * @param actionId
     *            �������ҪȨ�޿��ƣ��ò���Ϊ��
     */
    public XActionBase(String actionId) {
        if (!StringUtils.isEmpty(actionId)) {
            initPermission(actionId);
        }
    }

    /**
     * ��ʼ��action��Ȩ��ʹ��״̬
     * 
     * @param actionId
     */
    protected abstract void initPermission(String actionId);

    /**
     * �жϸ�action��Ȩ��ʹ��״̬
     * 
     * @return Ȩ�޵�ʹ��״̬
     */
    protected boolean isPermissionEnabled() {
        return permissionEnabled;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.AbstractAction#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean newValue) {
        if (permissionEnabled) {
            super.setEnabled(newValue);
        }
    }

    /**
     * ���ø�action��Ȩ��ʹ��״̬
     * 
     * @param permissionEnabled
     */
    protected void setPermissionEnabled(boolean permissionEnabled) {
        boolean oldValue = this.permissionEnabled;
        if (oldValue != permissionEnabled) {
            this.permissionEnabled = permissionEnabled;
            super.setEnabled(permissionEnabled);
        }
    }
}
