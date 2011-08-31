/*
 * $Id: LocalComunicateObjectImpl.java, 2010-9-27 下午04:03:09 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.container.impl;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.message.def.MessageReceiver;

/**
 * <p>
 * Title: LocalComunicateObjectImpl
 * </p>
 * <p>
 * Description: 本地通信对象，实现同一JVM内的服务获取，消息订阅
 * </p>
 * 
 * @author aaron
 * created 2010-9-27 下午04:03:09
 * modified [who date description]
 * check [who date description]
 */
public class LocalCommunicateObjectImpl implements CommunicateObject{
    
    private CoreContextImpl coreContext;
    
    LocalCommunicateObjectImpl(CoreContextImpl coreContext){
        this.coreContext=coreContext;
    }
    
    /**
     * @see com.yuep.core.container.def.CommunicateObject#getService(java.lang.String, java.lang.Class)
     */
    @Override
    public <T> T getService(String serviceName, Class<T> serviceItf) {
        return coreContext.getLocalService(serviceName, serviceItf);
    }

    /**
     * @see com.yuep.core.container.def.CommunicateObject#subscribe(java.lang.String, com.yuep.core.message.def.MessageReceiver)
     */
    @Override
    public void subscribe(String name, MessageReceiver receiver) {
        coreContext.messageServiceManager.subscribe(name, receiver);
    }

    /**
     * @see com.yuep.core.container.def.CommunicateObject#unsubscribe(java.lang.String, com.yuep.core.message.def.MessageReceiver)
     */
    @Override
    public void unsubscribe(String name, MessageReceiver receiver) {
        coreContext.messageServiceManager.unsubscribe(name, receiver);
    }

}
