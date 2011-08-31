/*
 * $Id: LocalCommunicateObjectMock.java, 2011-5-9 下午03:20:45 sufeng Exp $
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

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.message.def.MessageReceiver;

/**
 * <p>
 * Title: LocalCommunicateObjectMock
 * </p>
 * <p>
 * Description:模拟一个LocalCommunicateObject来方便测试
 * </p>
 * 
 * @author sufeng
 * created 2011-5-9 下午03:20:45
 * modified [who date description]
 * check [who date description]
 */
public class LocalCommunicateObjectMock implements CommunicateObject {

    private Map<String,Object> services=new ConcurrentHashMap<String, Object>();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getService(String serviceName, Class<T> serviceItf) {
        return (T)services.get(serviceName);
    }

    @Override
    public void subscribe(String name, MessageReceiver receiver) {
    }

    @Override
    public void unsubscribe(String name, MessageReceiver receiver) {
    }
    
    void setService(String serviceName,Object obj){
        services.put(serviceName, obj);
    }

}
