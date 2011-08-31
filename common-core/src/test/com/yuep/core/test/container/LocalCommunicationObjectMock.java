/*
 * $Id: LocalCommunicationObjectMock.java, 2011-5-10 ÉÏÎç09:08:14 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.test.container;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.util.CollectionUtils;

import com.yuep.base.exception.YuepException;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.message.def.MessageReceiver;

/**
 * <p>
 * Title: LocalCommunicationObjectMock
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Administrator
 * created 2011-5-10 ÉÏÎç09:08:14
 * modified [who date description]
 * check [who date description]
 */
public class LocalCommunicationObjectMock implements CommunicateObject {

    private Map<String,Object> localServiceObjects=new ConcurrentHashMap<String,Object>();
    private Map<String,List<MessageReceiver>> localMessageReceivers=new ConcurrentHashMap<String,List<MessageReceiver>>();
    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CommunicateObject#getService(java.lang.String, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getService(String serviceName, Class<T> serviceItf) {
        return (T)localServiceObjects.get(serviceName);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CommunicateObject#subscribe(java.lang.String, com.yuep.core.message.def.MessageReceiver)
     */
    @Override
    public void subscribe(String name, MessageReceiver receiver) {
        if(!localMessageReceivers.containsKey(name)){
            localMessageReceivers.put(name, new CopyOnWriteArrayList<MessageReceiver>());
        }
        localMessageReceivers.get(name).add(receiver);
    }
    
    /**
     * (non-Javadoc)
     * @see com.yuep.core.container.def.CommunicateObject#unsubscribe(java.lang.String, com.yuep.core.message.def.MessageReceiver)
     */
    @Override
    public void unsubscribe(String name, MessageReceiver receiver) {
        if(!localMessageReceivers.containsKey(name))
            return;
        localMessageReceivers.get(name).remove(receiver);
    }
    
    
    public void setLocalService(String serviceName, Class<?> interfaceClass,Object serviceInstance){
        if(localServiceObjects.containsKey(serviceName))
            throw new YuepException("serviceName is existed");
        localServiceObjects.put(serviceName, serviceInstance);
    }
    
    
    public void notifyLocalMessage(String messageName, Serializable messageInfo){
       List<MessageReceiver> messageReceivers=localMessageReceivers.get(messageName);
       if(CollectionUtils.isEmpty(messageReceivers))
           return;
       for(MessageReceiver messageReceiver:messageReceivers){
           messageReceiver.receive(this, messageName, messageInfo);
       }
    }

}
