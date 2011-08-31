/*
 * $Id: DefaultProxyProcessor.java, 2011-1-31 上午11:08:48 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.proxy.impl;

import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.facade.def.RemoteInvocationFacade;
import com.yuep.core.proxy.def.ProxyProcessor;

/**
 * <p>
 * Title: DefaultProxyProcessor
 * </p>
 * <p>
 * Description: 缺省的ProxyProcessor处理，可以被替换
 * </p>
 * 
 * @author sufeng
 * created 2011-1-31 上午11:08:48
 * modified [who date description]
 * check [who date description]
 */
public class DefaultProxyProcessor implements ProxyProcessor{

    private RemoteInvocationFacade facade;

    public DefaultProxyProcessor(RemoteCommunicateObject remoteCommunicateObject){
        facade=remoteCommunicateObject.getService("remoteInvocationFacade", RemoteInvocationFacade.class);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Object invoke(Class<?> serviceClz, String methodName, Class<?>[] paramType, Object[] args, Long sessionId) throws Throwable{
        // 直接转发调用到远端RemoteInvocationFacade上
        return facade.remoteInvoke(serviceClz, methodName, paramType, args, sessionId);
    }

}
