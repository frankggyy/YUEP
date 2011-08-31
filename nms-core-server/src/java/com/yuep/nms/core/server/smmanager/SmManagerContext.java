/*
 * $Id: SmManagerContext.java, 2011-3-31 下午02:00:21 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smmanager;

import java.io.Serializable;

import com.yuep.core.container.def.CoreContext;

/**
 * <p>
 * Title: SmManagerContext
 * </p>
 * <p>
 * Description: sm manager模块的上下文，存一些全局变量和方法
 * </p>
 * 
 * @author sufeng
 * created 2011-3-31 下午02:00:21
 * modified [who date description]
 * check [who date description]
 */
public class SmManagerContext {

    private static CoreContext coreContext;
    
    public static void setCoreContext(CoreContext coreContext) {
        SmManagerContext.coreContext = coreContext;
    }
    
    /**
     * 注册远程服务
     * @param serviceName
     * @param interfaceClass
     * @param serviceInstance
     */
    public static void setRemoteService(String serviceName, Class<?> interfaceClass, Object serviceInstance){
        coreContext.setRemoteService(serviceName, interfaceClass, serviceInstance);
    }
    
    /**
     * 获取本地接口
     * @param <T>
     * @param serviceName
     * @param serviceItf
     * @return
     */
    public static <T> T getLocalService(String serviceName, Class<T> serviceItf){
        return coreContext.local().getService(serviceName, serviceItf);
    }
    
    /**
     * 发送消息
     * @param msgName
     * @param msgBody
     */
    public static void publishMessage(String msgName,Serializable msgBody){
        coreContext.publish(msgName, msgBody);
    }
    
}
