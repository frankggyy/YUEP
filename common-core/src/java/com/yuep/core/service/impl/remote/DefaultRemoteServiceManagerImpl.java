/*
 * $Id: DefaultRemoteServiceManagerImpl.java, 2011-1-31 ����03:10:34 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.service.impl.remote;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.core.container.def.CoreException;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.service.impl.remote.exporter.RemoteServiceExporter;
import com.yuep.core.service.impl.remote.importer.RemoteServiceProxyFactory;

/**
 * <p>
 * Title: DefaultRemoteServiceManagerImpl
 * </p>
 * <p>
 * Description: ��ͨ��facade,proxyģʽ��Զ�̷����������ʵ��
 * </p>
 * 
 * @author sufeng
 * created 2011-1-31 ����03:10:34
 * modified [who date description]
 * check [who date description]
 */
public class DefaultRemoteServiceManagerImpl implements RemoteServiceManager{
    
    @Override
    public void init() {
    }
    
    /**
     * �ͷ��񵼳�ΪRMI����
     * @param rmiPort
     * @param serviceName
     * @param interfaceClass
     * @param service
     */
    public void exportRemoteService(int rmiPort,String serviceName, Class<?> interfaceClass,Object service) {
        try{ 
            RemoteServiceExporter.register(interfaceClass, service, rmiPort);
        }catch(Exception ex){
            throw new CoreException(CoreException.REGISTER_REMOTE_SERVICE_FAILED,ex,ExceptionUtils.getCommonExceptionInfo(ex)); 
        }
    }

    /**
     * ��ȡԶ�̷���(�˴������棬��ȫ���ϲ���cache)
     * @param remoteCommunicationObject
     * @param serviceName
     * @param serviceItf ����ӿ�����
     * 
     */
    public synchronized <T> T getRemoteService(RemoteCommunicateObject remoteCommunicationObject,String serviceName, Class<T> serviceItf) {
        String remoteIp=remoteCommunicationObject.getRemoteIp();
        int remoteServerPort=remoteCommunicationObject.getRemoteServerPort();
        T obj = RemoteServiceProxyFactory.getRemoteService(remoteIp,remoteServerPort,serviceItf);
        return obj;
    }
    
    @Override
    public void unregisterService(String serviceName, Class<?> serviceItf) {
        // TODO ����֮ǰ�����ۣ�v0.2ֻ��Ҫ�ṩ�ӿڣ�����Ҫ�ṩʵ��
        throw new UnsupportedOperationException();
    }
    
}
