/*
 * $Id: AbstractXAction.java, 2009-2-9 下午03:51:53 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

import java.awt.event.KeyEvent;
import java.lang.reflect.Method;

import javax.swing.JMenuItem;

import org.apache.commons.lang.StringUtils;

import twaver.Node;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: AbstractXAction
 * </p>
 * <p>
 * Description:action抽象基类
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 下午03:51:53
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractXAction extends XActionBase implements SensitiveAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7409903220213826647L;

    /**
     * 选择对象的使能状态 for <code>selectedEnabled</code>
     */
    private boolean selectedEnabled;

    /**
     * 标志这个Action是否支持多选操作 for <code>isMultiple</code>
     */
    private boolean isMultiple;

    /**
     * 数据实例的提供者 for <code>instanceProvider</code>
     */
    protected InstanceProvider instanceProvider;

    /**
     * 激发action时所选中的对象 for <code>objects</code>
     */
    protected Object[] selectedObjects;

    protected JMenuItem menuItem;

    public AbstractXAction(boolean isMultiple) {
        this(isMultiple, "");
    }

    /**
     * constructor.
     * 
     * @param isMultiple
     *            是否支持多选操作
     * @param actionId
     *            校验权限的actionId
     */
    public AbstractXAction(boolean isMultiple, String actionId) {
        this(actionId);
        this.isMultiple = isMultiple;
        setSelectedEnabled(false);
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
    public AbstractXAction(Boolean isMultiple, String actionId, Object[] selectedObjects) {
        this(actionId);
        this.isMultiple = isMultiple;
        this.selectedObjects = selectedObjects;
        initActionEnabled();
    }

    /**
     * Constructor.
     * 
     * @param actionId
     *            校验权限的actionId
     */
    public AbstractXAction(String actionId) {
        super(actionId);
    }
    
    public AbstractXAction(String actionId,String textKey,char mnemonic) {
        super(actionId);
        if(StringUtils.isNotEmpty(textKey)){
            String text = ClientCoreContext.getString(textKey);
            int vk = XGuiUtils.getMnemonicKey(mnemonic);
            if (text.toLowerCase().indexOf(String.valueOf(mnemonic).toLowerCase()) == -1) {
                text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
            }
            putValue(NAME, text);
            putValue(MNEMONIC_KEY, vk);
        }
    }

    /**
     * Constructor.构造action是提供所选择的数据信息，使用该信息判断action的是能状态。
     * 
     * @param actionId
     *            校验权限的actionId
     * @param selectedObjects
     *            所选择的数据信息
     */
    public AbstractXAction(String actionId, Object[] selectedObjects) {
        this(false, actionId, selectedObjects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tekview.ocean.client.common.action.BussinessAllowable#bussinessAllowabled
     * (com.tekview.ocean.client.common.action.InstanceProvider)
     */
    @Override
    public boolean bussinessAllowabled(InstanceProvider instanceProvider) {
        this.instanceProvider = instanceProvider;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tekview.ocean.client.common.action.BussinessAllowable#bussinessAllowabled (java.lang.Object[])
     */
    @Override
    public boolean bussinessAllowabled(Object[] selectedObjects) {
        return true;
    }

    /**
     * 初始化action的权限使能状态
     * 
     * @param actionId
     */
    protected void initPermission(String actionId) {
        boolean accessable = true; // 判断是否有权限访问
        if (StringUtils.isEmpty(actionId)) {
            accessable = true;
        } else {
            if (selectedObjects != null) {
                // 拓扑图的右键菜单
                for (Object obj : selectedObjects) {
                    if (obj instanceof Node) {
                        Object userObject = ((Node) obj).getUserObject();
                        String methodName = "getMoNaming";

                        Object id;
                        try {
                            Method method = userObject.getClass().getMethod(methodName);
                            id = method.invoke(userObject);
                            accessable = ClientCoreContext.getAccessCheck().check(actionId,id);
                            if(accessable)
                                continue;
                            else
                                break;
                        } catch (Exception e) {
                            ClientCoreContext.getLogger().error("", e);
                        }
                    }
                }
            } else {
                // 没有选中任何element，应该是主菜单
                accessable = ClientCoreContext.getAccessCheck().check(actionId);
            }
        }

        // 来对没有权限的菜单disable掉
        setPermissionEnabled(accessable);
    }

    /**
     * 在有数据的情况下直接初始化action的使能状态。
     */
    private void initActionEnabled() {
        int selectedCount = 0;
        if (selectedObjects == null) {
            selectedCount = 1;
        } else {
            selectedCount = selectedObjects.length;
        }
        boolean bussinessAllowabled = bussinessAllowabled(selectedObjects);
        setActionEnabaled(bussinessAllowabled, selectedCount);
    }

    /**
     * 根据选择状态判断action的使能状态
     * 
     * @return boolean
     */
    protected boolean isSelectedEnabled() {
        return selectedEnabled;
    }

    /**
     * 根据选择对象的变化，更新action的使能状态。
     * 
     * @see com.tekview.ocean.client.common.action.SensitiveAction#selectionChanged(com.tekview.ocean.client.common.action.InstanceProvider)
     */
    public void selectionChanged(InstanceProvider instanceProvider) {
        this.instanceProvider = instanceProvider;
        int selectedCount = instanceProvider.getSelectedCount();
        boolean bussinessAllowabled = bussinessAllowabled(instanceProvider);
        setActionEnabaled(bussinessAllowabled, selectedCount);
    }

    /**
     * 设置action的使能状态
     * 
     * @param bussinessAllowabled
     *            业务上是否允许action使用
     * @param selectedCount
     *            选择数据的条数
     */
    private void setActionEnabaled(boolean bussinessAllowabled, int selectedCount) {
        if (selectedCount == 0) {
            setSelectedEnabled(false);
        } else if (isMultiple) {
            setSelectedEnabled(true);
            setEnabled(bussinessAllowabled);
        } else {
            if (selectedCount > 1) {
                setSelectedEnabled(false);
            } else {
                setSelectedEnabled(true);
                setEnabled(bussinessAllowabled);
            }
        }
    }

    public void setMenuItem(JMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    protected void setMenuItemSelected() {
        menuItem.setSelected(true);
    }

    /**
     * 根据对对象的选中设置action的使能状态，在设置中要先判断该action权限的使能状态
     * 
     * @param selectedEnabled
     */
    protected void setSelectedEnabled(boolean selectedEnabled) {
        if (isPermissionEnabled()) {
            this.selectedEnabled = selectedEnabled;
            super.setEnabled(selectedEnabled);
        } else {
            this.selectedEnabled = false;
        }
    }
}
