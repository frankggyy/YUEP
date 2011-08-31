/*
 * $Id: AbstractNavigationModel.java, 2009-3-16 上午10:48:27 Victor Exp $
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
 * Description:抽象的导航控件的Model
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-16 上午10:48:27
 * @modified aaron lee 2010-3-30 将其从<code>AbstractClientModel<T></code>继承
 * check [who date description]
 */
public abstract class AbstractNavigationModel<T> extends AbstractClientModel<T> {
    private Map<Object, List<AbstractTabController<?, ?, ?>>> tabMap = new ConcurrentHashMap<Object, List<AbstractTabController<?,?,?>>>();
    protected List<Element> navigationNodes = new ArrayList<Element>();

    /**
     * 根据对象和方法名获取导航数据的KEY
     * @param obj 数据对象
     * @param methodName 方法名
     * @return String 组合后的KEY
     */
    protected String getKeyByMethodName(Object obj,String methodName) {
        if(StringUtils.isEmpty(methodName))
            return obj.getClass().getSimpleName();
        Object invoke = null;
        try {
            Method method = obj.getClass().getMethod(methodName);
            invoke = method.invoke(obj);
        } catch (Exception e) {
            // TODO 这里的异常不处理实际使用中不应该抛异常
            e.printStackTrace();
        }
        return obj.getClass().getSimpleName() + ":" + invoke;
    }
    
    /**
     * 创建导航树
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
     * 添加一个节点到树中，这个节点必须已经设置好父子关系
     * 
     * @param node
     */
    public void addNode(Node node) {
        setChanged();
        notifyObservers(node);
    }

    /**
     * 国际化获取接口封装
     * @return <code>String</code> 国际化后的字符串
     */
    protected String getString(String key, Object... values) {
        return ClientCoreContext.getString(key, values);
    }

    /**
     * 获取导航树节点信息列表
     * @return <code>List<Element></code> 导航树节点信息列表
     */
    protected abstract List<Element> getNavigationNodes();

    /**
     * 获取导航界面信息的MAP
     * @return <code>Map<Object, List<AbstractTabController<?, ?, ?>>></code> 界面信息的MAP
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
