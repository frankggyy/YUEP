/*
 * 
 * $Id: RemoteServiceProxyFactory.java, 2009-2-21 ����03:03:09 Victor Exp $
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.service.impl.remote.importer;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.YuepLog;
import com.yuep.core.proxy.impl.RemoteProxyAdvice;

/**
 * <p>
 * Title: RemoteServiceProxyFactory
 * </p>
 * <p>
 * Description: �ͻ��˻�ȡ�����Զ�̽ӿڵĹ���
 * </p>
 * 
 * @author Victor
 * created 2009-2-21 ����03:03:09
 * modified [who date description]
 * check [who date description]
 */
public class RemoteServiceProxyFactory {
    
    /**
     * ��ȡ�����Զ��facade�ӿ�
     * 
     * @param <T>
     * @param ip
     * @param port
     * @param serviceInterface
     *            �����Ľӿ�����
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRemoteService(String ip,int port,Class<T> serviceInterface) {
        Logger logger =getLogger();
        logger.debug("Aware RemoteService:" + serviceInterface);
        XRmiProxyFactoryBean proxy = new XRmiProxyFactoryBean(ip,port);
        proxy.setServiceInterface(serviceInterface);
        proxy.setServiceUrl(serviceInterface.getSimpleName());
        proxy.setLookupStubOnStartup(false);
        proxy.setRefreshStubOnConnectFailure(true);
        proxy.afterPropertiesSet();
        logger.info("Success to proxy facade:" + serviceInterface.getName());
        return (T) proxy.getObject();
    }
    
    /**
     * ͨ��AOP����ģʽ����ȡԶ�̽ӿ�
     * @param <T>
     * @param serviceInterface ��Ҫ������Ľӿ�����
     * @param advice           ���ش�����
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRemoteServiceByAdvice(Class<T> serviceInterface,RemoteProxyAdvice advice) {
        try{
            ProxyFactory factory = new ProxyFactory();
            factory.setInterfaces(new Class[]{serviceInterface});
            RegexpMethodPointcutAdvisor advisor=new RegexpMethodPointcutAdvisor();
            advisor.setAdvice(advice);
            advisor.setPattern(".*");
            factory.addAdvisor(advisor);
            return (T)factory.getProxy();
        }catch(Exception ex){
            getLogger().warn(ExceptionUtils.getCommonExceptionInfo(ex));
        }
        return null;
    }
    
    private static Logger getLogger(){
        return YuepLog.getDefaultLogger();
    }

}
