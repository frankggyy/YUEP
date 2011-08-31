/*
 * $Id: SmCoreContext.java, 2011-3-30 ����10:59:55 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

import com.yuep.base.log.def.Logger;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SmCoreContext
 * </p>
 * <p>
 * Description: sm core�����ģ���һЩ������Ϣ
 * </p>
 * 
 * @author sufeng
 * created 2011-3-30 ����10:59:55
 * modified [who date description]
 * check [who date description]
 */
public class SmCoreContext {

    private static ApplicationContext ctx;
    private static Map<Class<?>,Map<String,FacadeMethod[]>> serviceInvokeInfo=new ConcurrentHashMap<Class<?>,Map<String,FacadeMethod[]>>();
    private static Logger smCoreLogger;
    
    private static CoreContext coreContext;
    
    private final static String MO_TYPE_CARD="card";
    private final static String MO_TYPE_PORT="port";
    
    public static void setCoreContext(CoreContext coreContext) {
        SmCoreContext.coreContext = coreContext;
    }
    
    public static void setCtx(ApplicationContext ctx) {
        SmCoreContext.ctx = ctx;
    }
    
    /**
     * ��ȡspring bean
     * @param <T>
     * @param beanName
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName,Class<T> clazz){
        return (T)ctx.getBean(beanName);
    }
    
    /**
     * ��ȡsmcoreģ���logger
     * @return
     */
    public static Logger getLogger(){
        return smCoreLogger;
    }
    
    public static void setLogger(Logger logger){
        SmCoreContext.smCoreLogger=logger;
    }
    
    /**
     * ��ȡ���ط���
     * @param <T>
     * @param serviceName
     * @param serviceItf
     * @return
     */
    public static <T> T getLocalService(String serviceName, Class<T> serviceItf){
        return coreContext.local().getService(serviceName, serviceItf);
    }
    
    /**
     * ������Ϣ
     * @param msgName
     * @param msgBody
     */
    public static void publishMessage(String msgName,Serializable msgBody){
        coreContext.publish(msgName, msgBody);
    }
    
    /**
     * ���ı�����Ϣ
     * @param name
     * @param receiver
     */
    public static void subscribeLocalMessage(String name, MessageReceiver receiver){
        coreContext.local().subscribe(name, receiver);
    }

    /**
     * ��ȡfacade�ӿڲ�(����ͻ��ˡ�����ӿڵ�service)��annotation
     * @param serviceClz
     * @param methodName
     * @param paramType
     * @return
     */
    public static FacadeMethod[] getFacadeMethods(Class<?> serviceClz, String methodName,Class<?>[] paramType){
        Map<String, FacadeMethod[]> map = serviceInvokeInfo.get(serviceClz);
        if(map==null){
            map=new ConcurrentHashMap<String, FacadeMethod[]>();
            serviceInvokeInfo.put(serviceClz,map);
        }
        FacadeMethod[] annotations = map.get(methodName);
        if(annotations==null){
            // ��һ�ζ�
            try{
                Method method = serviceClz.getMethod(methodName,paramType);
                FacadeMethod annotation = method.getAnnotation(FacadeMethod.class);
                if(annotation==null)
                    annotations=new FacadeMethod[0]; //[0]��ʾû��ע��
                else
                    annotations=new FacadeMethod[]{annotation};
                map.put(methodName, annotations);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return annotations;
    }
    
    /**
     * �ǲ��ɹ����mo��
     * @param moNaming
     * @return
     */
    public static boolean isUnmanagedMo(MoNaming moNaming){
        String moType = moNaming.getMoType();
        if(MO_TYPE_CARD.equals(moType))
            return true;
        if(MO_TYPE_PORT.equals(moType))
            return true;
        return false;
    }
    
    
}
