/*
 * $Id: XActionBase.java, 2009-2-9 下午03:44:43 aaron lee Exp $
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
 * Description:主要进行对action权限的校验
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 下午03:44:43
 * modified [who date description]
 * check [who date description]
 */
public abstract class XActionBase extends AbstractAction implements BussinessAllowable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6026282974596166596L;

    /**
     * 权限的使能状态 for <code>permissionEnabled</code>
     */
    protected boolean permissionEnabled = true;

    /**
     * constructor
     * 
     * @param actionId
     *            如果不需要权限控制，该参数为空
     */
    public XActionBase(String actionId) {
        if (!StringUtils.isEmpty(actionId)) {
            initPermission(actionId);
        }
    }

    /**
     * 初始化action的权限使能状态
     * 
     * @param actionId
     */
    protected abstract void initPermission(String actionId);

    /**
     * 判断该action的权限使能状态
     * 
     * @return 权限的使能状态
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
     * 设置该action的权限使能状态
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
