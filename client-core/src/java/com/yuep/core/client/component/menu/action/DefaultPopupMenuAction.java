/*
 * $Id: DefaultPopupMenuAction.java, 2009-2-26 下午05:34:11 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

import java.lang.reflect.Method;

import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: DefaultPopupMenuAction
 * </p>
 * <p>
 * Description:客户端根据配置文件自动加载菜单的action
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-26 下午05:34:11
 * modified [who date description]
 * check [who date description]
 */
public abstract class DefaultPopupMenuAction extends AbstractXAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 9096903657476096768L;

    /**
     * 所需参数
     */
    protected Object paramObj;

    /**
     * 所需参数的类型
     */
    protected String paramClass;

    /**
     * 客户端通过配置文件自动加载菜单和action时使用的构造方法
     * 
     * @param isMultiple
     *            是否支持多选
     * @param actionId
     *            校验权限的actionId
     * @param selectedObjects
     *            被选中的对象列表
     * @param paramObj
     *            action内部使用的上下文对象
     * @param paramClazz
     *            action内部使用的上下文对象类型
     */
    public DefaultPopupMenuAction(Boolean isMultiple, String actionId, Object[] selectedObjects,
            Object paramObj, String paramClazz) {
        super(isMultiple, actionId, selectedObjects);
        this.paramObj = paramObj;
        this.paramClass = paramClazz;
    }

    /**
     * 添加构造函数，直接调用上层构造函数 目的：使拓扑菜单与主菜单能共用同一个ACTION
     * 
     * @param actionId
     */
    public DefaultPopupMenuAction(String actionId) {
        super(actionId);
    }

    /**
     * 根据参数名字获取参数值的方法
     * 
     * @param property
     *            参数名
     * @return Object 参数值
     */
    public Object readFromObject(String property) {
        Object value = null;
        try {
            Class<?> clazz = Class.forName(paramClass);
            String getterName = "get" + XGuiUtils.capitalize(property);
            Method method = clazz.getMethod(getterName, new Class[0]);
            if (method == null) {
                getterName = "is" + XGuiUtils.capitalize(property);
                method = clazz.getMethod(getterName, new Class[0]);
            }
            value = method.invoke(paramObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public Object getParamObject() {
        return paramObj;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tekview.ocean.client.common.action.TekviewActionBase#bussinessAllowabled(com.tekview.ocean.client
     * .common.action.InstanceProvider)
     */
    @Override
    public boolean bussinessAllowabled(InstanceProvider instanceProvider) {
        // TODO Auto-generated method stub
        return true;
    }
}