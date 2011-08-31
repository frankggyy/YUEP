/*
 * $Id: AbstractNavigationModel.java, 2009-3-16 ����10:48:27 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import twaver.Element;
import twaver.Node;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.AbstractClientModel;

/**
 * <p>
 * Title: AbstractNavigationModel
 * </p>
 * <p>
 * Description:����ĵ����ؼ���Model
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-16 ����10:48:27
 * @modified aaron lee 2010-3-30 �����<code>AbstractClientModel<T></code>�̳�
 * check [who date description]
 */
public abstract class AbstractNavigationModel<T> extends AbstractClientModel<T> {
    private Map<Object, List<AbstractTabController<?, ?, ?>>> tabMap = new ConcurrentHashMap<Object, List<AbstractTabController<?,?,?>>>();
    protected List<Element> navigationNodes = new ArrayList<Element>();

    /**
     * ���ݶ���ͷ�������ȡ�������ݵ�KEY
     * @param obj ���ݶ���
     * @param methodName ������
     * @return String ��Ϻ��KEY
     */
    protected String getKeyByMethodName(Object obj,String methodName) {
        if(StringUtils.isEmpty(methodName))
            return obj.getClass().getSimpleName();
        Object invoke = null;
        try {
            Method method = obj.getClass().getMethod(methodName);
            invoke = method.invoke(obj);
        } catch (Exception e) {
            // TODO ������쳣������ʵ��ʹ���в�Ӧ�����쳣
            e.printStackTrace();
        }
        return obj.getClass().getSimpleName() + ":" + invoke;
    }
    
    /**
     * ����������
     */
    public void contructNavigation() {
        List<Element> datas = new ArrayList<Element>();
        datas.addAll(getNavigationNodes());
        setChanged();
        notifyObservers(tabMap);
        setChanged();
        notifyObservers(datas);
    }

    /**
     * ���һ���ڵ㵽���У�����ڵ�����Ѿ����úø��ӹ�ϵ
     * 
     * @param node
     */
    public void addNode(Node node) {
        setChanged();
        notifyObservers(node);
    }

    /**
     * ���ʻ���ȡ�ӿڷ�װ
     * @return <code>String</code> ���ʻ�����ַ���
     */
    protected String getString(String key, Object... values) {
        return ClientCoreContext.getString(key, values);
    }

    /**
     * ��ȡ�������ڵ���Ϣ�б�
     * @return <code>List<Element></code> �������ڵ���Ϣ�б�
     */
    protected abstract List<Element> getNavigationNodes();

    /**
     * ��ȡ����������Ϣ��MAP
     * @return <code>Map<Object, List<AbstractTabController<?, ?, ?>>></code> ������Ϣ��MAP
     */
    public Map<Object, List<AbstractTabController<?, ?, ?>>> getTabMap() {
        return tabMap;
    }
    
    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.AbstractClientModel#clearData()
     */
    @Override
    public void clearData() {
        navigationNodes.clear();
    }
}
