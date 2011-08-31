/*
 * $Id: ProxyFacadeRemoteServiceManager.java, 2011-1-31 ����12:47:42 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.proxy.impl;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.YuepLog;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.facade.def.RemoteInvocationFacade;
import com.yuep.core.proxy.def.ProxyManager;
import com.yuep.core.service.impl.local.LocalServiceManager;
import com.yuep.core.service.impl.remote.DefaultRemoteServiceManagerImpl;
import com.yuep.core.service.impl.remote.importer.RemoteServiceProxyFactory;

/**
 * <p>
 * Title: ProxyFacadeRemoteServiceManager
 * </p>
 * <p>
 * Description: invoke seq: request module->proxy-->|-->facade->response module
 * </p>
 * 
 * @author sufeng
 * created 2011-1-31 ����12:47:42
 * modified [who date description]
 * check [who date description]
 */
public class ProxyFacadeRemoteServiceManager extends DefaultRemoteServiceManagerImpl {

    private ProxyManagerImpl proxyManager;
    private Logger logger; 
    
    @Override
    public void init(){
        ProxyManager service = CoreContext.getInstance().local().getService("proxyManager", ProxyManager.class);
        proxyManager=(ProxyManagerImpl)service;
        
        initLogger();
    }
    
    private void initLogger(){
        logger=YuepLog.getDefaultLogger();
    }
    
    /**
     * �ͷ��񵼳�ΪRMI����
     * @param rmiPort
     * @param serviceName
     * @param interfaceClass
     * @param service
     */
    public void exportRemoteService(int rmiPort,String serviceName, Class<?> interfaceClass,Object service) {
        if(interfaceClass.equals(RemoteInvocationFacade.class)){
            super.exportRemoteService(rmiPort, serviceName, interfaceClass, service);
        }else{
            if(logger==null)
                initLogger();
            logger.info("register proxy Remote service:"+serviceName);
            try{
                localServiceManager.setLocalService(serviceName, interfaceClass, service);
                //CoreContext.getInstance().setLocalService(serviceName, interfaceClass, service);
            }catch(Exception ex){
                // ��ĳ��Զ�̽ӿ��Ѿ���Ϊ���ؽӿ�ע��󣬴�ʱ�ᱨע���ظ���Ӧ�ú�������쳣
                logger.info(ExceptionUtils.getCommonExceptionInfo(ex));
            }
        }
    }
    
    /**
     * Զ�̷�������ά��һ��local service��������������local service��ͻ
     */
    private LocalServiceManager localServiceManager=new LocalServiceManager();

    /**
     * ��ȡ������ı��ؽӿڷ�����
     * @param serviceInterfaceClassName
     * @return
     */
    public String getLocalServiceNameByClassName(Class<?> serviceInterfaceClassName){
        return localServiceManager.getLocalServiceNameByClassName(serviceInterfaceClassName);
    }
    
    /**
     * ��ȡ������ı��ؽӿڷ���
     * @param <T>
     * @param serviceName
     * @param serviceItf
     * @return
     */
    public <T> T getLocalService(String serviceName, Class<T> serviceItf){
        return localServiceManager.getLocalService(serviceName,serviceItf);
    }
    
    /**
     * ��ȡԶ�̷���(�˴������棬��ȫ���ϲ���cache
     * @param remoteIp
     * @param remoteServerIp
     * @param serviceName
     * @param serviceItf ����ӿ�����
     * 
     */
    public synchronized <T> T getRemoteService(RemoteCommunicateObject remote,String serviceName, Class<T> serviceItf) {
        if(serviceItf.equals(RemoteInvocationFacade.class)){
            return super.getRemoteService(remote, serviceName, serviceItf);
        }else{
            proxyManager.createProxy(remote);
            RemoteProxyAdvice proxyAdvice = proxyManager.getProxyAdvisor(remote);
            T obj = RemoteServiceProxyFactory.getRemoteServiceByAdvice(serviceItf,proxyAdvice);
            return obj;
        }
    }
    
}
