/*
 * $Id: CoreContextMock.java, 2011-5-9 下午03:11:20 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.base.test;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.ContainerPropertiesManager;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageMetadata;

/**
 * <p>
 * Title: CoreContextMock
 * </p>
 * <p>
 * Description:模拟一个CoreContext来方便测试
 * </p>
 * 
 * @author sufeng
 * created 2011-5-9 下午03:11:20
 * modified [who date description]
 * check [who date description]
 */
public class CoreContextMock extends CoreContext {

    private LocalCommunicateObjectMock local;
    
    private CoreContextMock(){
        local=new LocalCommunicateObjectMock();
    }
    
    public static void init(){
        instance=new CoreContextMock();
    }
    
    @Override
    public ContainerPropertiesManager getPropertiesManager() {
        return null;
    }

    @Override
    public MessageMetadata getSelfMessageMetadata() {
        return null;
    }

    @Override
    public CommunicateObject local() {
        return local;
    }

    @Override
    public void publish(String messageName, Serializable messageInfo) {
        
    }

    @Override
    public RemoteCommunicateObject remote(String ip, int port) {
        RemoteCommunicateObjectMock remote=new RemoteCommunicateObjectMock(ip,port);
        return remote;
    }

    @Override
    public void setLocalService(String serviceName, Class<?> interfaceClass, Object serviceInstance) {
        local.setService(serviceName, serviceInstance);
    }

    private Map<String,Object> remoteServices=new ConcurrentHashMap<String, Object>();
    
    @Override
    public void setRemoteService(String serviceName, Class<?> interfaceClass, Object serviceInstance) {
        remoteServices.put(serviceName, serviceInstance);
    }

    @Override
    public void unregisterLocalService(String serviceName, Class<?> serviceItf) {
    }

    @Override
    public void unregisterRemoteService(String serviceName, Class<?> serviceItf) {
        
    }

}
