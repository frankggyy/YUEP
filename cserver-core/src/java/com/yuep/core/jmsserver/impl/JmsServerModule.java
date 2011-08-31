/*
 * $Id: JmsServerModule.java, 2011-2-28 下午04:54:33 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.jmsserver.impl;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.StringUtils;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.ContainerPropertiesManager;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.CoreException;
import com.yuep.core.jmsserver.def.JmsServerManager;
import com.yuep.core.message.def.BaseMessage;
import com.yuep.core.message.def.MessageConst;
import com.yuep.core.message.def.MessageMetadata;

/**
 * <p>
 * Title: JmsServerModule
 * </p>
 * <p>
 * Description: jms服务器管理模块
 * </p>
 * 
 * @author sufeng
 * created 2011-2-28 下午04:54:33
 * modified [who date description]
 * check [who date description]
 */
public class JmsServerModule extends DefaultModule{

    /**
     * 管理jms服务器的service
     */
    private JmsServerManager jmsServerManager;
    
    /**
     * jms服务器的配置信息
     */
    private MessageMetadata messageMetadata;
    /**
     * 是否需要启动jms server进程
     */
    private String launch;
    
    @Override
    public void start() {
        messageMetadata = CoreContext.getInstance().getSelfMessageMetadata();
        launch = moduleParams.get(ContainerPropertiesManager.KEY_JMS_LAUNCH);
        
        // create jms server manager,通过反射来达到动态替换的效果
        String jmsServerManagerClassName=moduleParams.get(ContainerPropertiesManager.KEY_JMS_MANAGER_CLASS);
        if(StringUtils.isNotEmpty(jmsServerManagerClassName)){
            try{
                Class<?> jmsServerManagerClass = Class.forName(jmsServerManagerClassName, false, classLoader);
                Constructor<?> constructor = jmsServerManagerClass.getConstructor();
                Object obj = constructor.newInstance();
                jmsServerManager=(JmsServerManager)obj;
                CoreContext.getInstance().setLocalService("jmsServerManager", JmsServerManager.class, jmsServerManager);
            }catch(Exception ex){
                String info = ExceptionUtils.getCommonExceptionInfo(ex);
                logger.error(info);
                throw new CoreException(CoreException.JMS_MANAGER_INIT_FAILED,ex,info);
            }
        }else{
            logger.warn("this subsystem have no JMS Server Manager");
        }
        
        // 启动JMS服务器,init connection factory
        if("true".equals(launch)){
            jmsServerManager.start(messageMetadata);
            logger.info("launch JMS Server completed.");
        }
        
        // init topic
        boolean result=jmsServerManager.initTopic(messageMetadata);
        if(!result)
            throw new CoreException(CoreException.JMS_TOPIC_INIT_FAILED,"(host="+messageMetadata.getServerIp()
                    +",port="+messageMetadata.getPort()+",topic="+messageMetadata.getTopicName()+")");
        else
            logger.info("create topic completed,"+messageMetadata.getTopicName());
        
        // 初始化完毕后发送消息，这样可以让其他发送消息的模块进行初始化
        CoreContext.getInstance().publish(MessageConst.MSG_JMS_SERVER_INIT_COMPLETED, new BaseMessage(MessageConst.MSG_JMS_SERVER_INIT_COMPLETED,""));
    }

    @Override
    public void stop() {
        // 解绑topic
        if(jmsServerManager!=null){
            jmsServerManager.closeTopic(messageMetadata);
            
            // 停止jms进程
            if("true".equals(launch))
                jmsServerManager.shutdown(messageMetadata);
        }
    }

}
