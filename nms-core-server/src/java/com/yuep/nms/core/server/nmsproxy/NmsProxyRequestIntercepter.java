/*
 * $Id: NmsProxyRequestIntercepter.java, 2011-5-16 下午09:24:27 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.nmsproxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.yuep.base.exception.YuepException;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemRequestIntercepter;

/**
 * <p>
 * Title: NmsProxyRequestIntercepter
 * </p>
 * <p>
 * Description:下级网管代理服务获取拦截器，拦截本级网管对下级网管服务调用
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午09:24:27
 * modified [who date description]
 * check [who date description]
 */
public class NmsProxyRequestIntercepter implements SubSystemRequestIntercepter {

    private NmsServiceAdapterManager nmsServiceAdapterManager;
    public void setNmsServiceAdapterManager(NmsServiceAdapterManager nmsServiceAdapterManager){
        this.nmsServiceAdapterManager=nmsServiceAdapterManager;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemRequestIntercepter#invoke(com.yuep.core.container.def.CommunicateObject, com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String, java.lang.Class, java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> Object invoke(SubSystemProxy subSystemProxy,MoNaming managedNode, String serviceName,
            Class<T> serviceIntefaceClass, Object proxy, Method method,
            Object[] args) {
        T remoteServiceAdapter=subSystemProxy.getCommunicateObject().getService(serviceName, serviceIntefaceClass);
        if(!(subSystemProxy.getCommunicateObject() instanceof RemoteCommunicateObject)){
            try {
                return method.invoke(remoteServiceAdapter, args);
            } catch (Exception ex) {
               throw processException(ex);
            }
        }
        Class<T> nmsRemoteServiceAdapterClass=(Class<T>) nmsServiceAdapterManager.getNmsServiceAdapterClass(serviceName);
        if(nmsRemoteServiceAdapterClass!=null)
            remoteServiceAdapter=(T)getServiceAdapter(subSystemProxy.getSubSystemProxyProperty().getSubSystemId(),serviceName,remoteServiceAdapter,serviceIntefaceClass,nmsRemoteServiceAdapterClass);
        else{
            args=toRemoteMoNaming(args);
        }
        try {
            return method.invoke(remoteServiceAdapter, args);
        } catch (Exception ex) {
            throw processException(ex);
        }
    
    }

    private Object getServiceAdapter(MoNaming managedNode,String serviceName,Object serviceRemoteObject,Class<?> serviceInterface,Class<?> serviceAdpaterClass){
        try {
            Constructor<?> constructor=serviceAdpaterClass.getConstructor(new Class<?>[]{MoNaming.class,serviceInterface});
            return constructor.newInstance(managedNode,serviceRemoteObject);
        } catch (Exception ex) {
            throw new YuepException(ex);
        }
    }
    
    
    private Object[] toRemoteMoNaming(Object[] args){
        return args;
    }
    
    private YuepException processException(Exception ex){
        if(ex instanceof InvocationTargetException){
            InvocationTargetException invocationTargetException=(InvocationTargetException)ex;
            if(invocationTargetException.getTargetException() instanceof YuepException)
            throw (YuepException)invocationTargetException.getTargetException();
        }
        throw new YuepException(ex);
    
    }
}
