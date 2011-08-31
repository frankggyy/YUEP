/*
 * $Id: DefaultPopupMenuAction.java, 2009-2-26 ����05:34:11 aaron lee Exp $
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
 * Description:�ͻ��˸��������ļ��Զ����ز˵���action
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-26 ����05:34:11
 * modified [who date description]
 * check [who date description]
 */
public abstract class DefaultPopupMenuAction extends AbstractXAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 9096903657476096768L;

    /**
     * �������
     */
    protected Object paramObj;

    /**
     * �������������
     */
    protected String paramClass;

    /**
     * �ͻ���ͨ�������ļ��Զ����ز˵���actionʱʹ�õĹ��췽��
     * 
     * @param isMultiple
     *            �Ƿ�֧�ֶ�ѡ
     * @param actionId
     *            У��Ȩ�޵�actionId
     * @param selectedObjects
     *            ��ѡ�еĶ����б�
     * @param paramObj
     *            action�ڲ�ʹ�õ������Ķ���
     * @param paramClazz
     *            action�ڲ�ʹ�õ������Ķ�������
     */
    public DefaultPopupMenuAction(Boolean isMultiple, String actionId, Object[] selectedObjects,
            Object paramObj, String paramClazz) {
        super(isMultiple, actionId, selectedObjects);
        this.paramObj = paramObj;
        this.paramClass = paramClazz;
    }

    /**
     * ��ӹ��캯����ֱ�ӵ����ϲ㹹�캯�� Ŀ�ģ�ʹ���˲˵������˵��ܹ���ͬһ��ACTION
     * 
     * @param actionId
     */
    public DefaultPopupMenuAction(String actionId) {
        super(actionId);
    }

    /**
     * ���ݲ������ֻ�ȡ����ֵ�ķ���
     * 
     * @param property
     *            ������
     * @return Object ����ֵ
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