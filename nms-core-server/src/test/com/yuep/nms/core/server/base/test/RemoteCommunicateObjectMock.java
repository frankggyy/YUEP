/*
 * $Id: RemoteCommunicateObjectMock.java, 2011-5-9 下午03:16:50 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.base.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageReceiver;

/**
 * <p>
 * Title: RemoteCommunicateObjectMock
 * </p>
 * <p>
 * Description:模拟一个RemoteCommunicateObject来方便测试
 * </p>
 * 
 * @author sufeng
 * created 2011-5-9 下午03:16:50
 * modified [who date description]
 * check [who date description]
 */
public class RemoteCommunicateObjectMock implements RemoteCommunicateObject{

    private String ip;
    private int port;
    
    private Map<String,Object> services=new ConcurrentHashMap<String, Object>();
    
    //private CoreContextMock coreContext;
    
    public RemoteCommunicateObjectMock(String ip, int port){
        resetRemoteServer(ip,port);
    }
    
    @Override
    public void cleanup() {
        
    }

    @Override
    public int getLinkStatus() {
        return 0;
    }

    @Override
    public String getRemoteIp() {
        return ip;
    }

    @Override
    public int getRemoteServerPort() {
        return port;
    }

    @Override
    public void resetRemoteServer(String ip, int port) {
        this.ip=ip;
        this.port=port;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getService(String serviceName, Class<T> serviceItf) {
        return (T)services.get(serviceName);
    }
    
    void setService(String serviceName,Object obj){
        services.put(serviceName, obj);
    }

    @Override
    public void subscribe(String name, MessageReceiver receiver) {
    }

    @Override
    public void unsubscribe(String name, MessageReceiver receiver) {
        
    }

}
