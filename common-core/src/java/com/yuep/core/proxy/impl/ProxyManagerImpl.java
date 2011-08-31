/*
 * $Id: ProxyManagerImpl.java, 2011-1-31 ����01:27:40 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.proxy.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.proxy.def.ProxyManager;
import com.yuep.core.proxy.def.ProxyProcessor;

/**
 * <p>
 * Title: ProxyManagerImpl
 * </p>
 * <p>
 * Description: �����������ʵ��
 * </p>
 * 
 * @author sufeng
 * created 2011-1-31 ����01:27:40
 * modified [who date description]
 * check [who date description]
 */
public class ProxyManagerImpl implements ProxyManager{
    
    private Map<RemoteCommunicateObject,RemoteProxyAdvice> remoteMap=new ConcurrentHashMap<RemoteCommunicateObject,RemoteProxyAdvice>();
    
    /**
     * ����һ����Զ����ϵͳ��Proxy������ClientProxy,SbiProxy
     * @param comm
     */
    public void createProxy(RemoteCommunicateObject comm) {
        if(remoteMap.containsKey(comm))
            return;
        
        RemoteProxyAdvice remoteProxyAdvisor = new RemoteProxyAdvice(comm);
        remoteMap.put(comm, remoteProxyAdvisor);
    }
    
    /**
     * �õ�advisor
     * @param comm
     * @return
     */
    public RemoteProxyAdvice getProxyAdvisor(RemoteCommunicateObject comm) {
        return remoteMap.get(comm);
    }

    @Override
    public void closeProxy(RemoteCommunicateObject comm) {
        remoteMap.remove(comm);
    }

    @Override
    public synchronized ProxyProcessor getProxyProcessor(RemoteCommunicateObject comm) {
        RemoteProxyAdvice proxyAdvisor = getProxyAdvisor(comm);
        if(proxyAdvisor==null)
            return null;
        return proxyAdvisor.getProxyProcessor();
    }

    @Override
    public synchronized void setProxyProcessor(RemoteCommunicateObject comm,ProxyProcessor proxyInvoker) {
        RemoteProxyAdvice proxyAdvisor = getProxyAdvisor(comm);
        proxyAdvisor.setProxyProcessor(proxyInvoker);
    }
    
}
