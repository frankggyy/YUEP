/*
 * $Id: JoramManagerImpl.java, 2010-9-20 上午11:35:30 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.jmsserver.impl.joram;

import java.util.Properties;

import org.objectweb.joram.client.jms.Topic;
import org.objectweb.joram.client.jms.admin.AdminModule;
import org.objectweb.joram.client.jms.admin.User;
import org.objectweb.joram.client.jms.tcp.TopicTcpConnectionFactory;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.exception.YuepException;
import com.yuep.base.log.def.Logger;
import com.yuep.base.util.cmd.CmdUtils;
import com.yuep.base.util.sys.SysUtils;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.container.def.ContainerPropertiesManager;
import com.yuep.core.jmsserver.def.JmsServerManager;
import com.yuep.core.jmsserver.impl.JmsServerModule;
import com.yuep.core.message.def.MessageMetadata;

/**
 * <p>
 * Title: JoramManagerImpl
 * </p>
 * <p>
 * Description:管理joram服务器,里面的类需要使用joram提供的jar
 * </p>
 * 
 * @author sufeng
 * created 2010-9-20 上午11:35:30
 * modified [who date description]
 * check [who date description]
 */
public class JoramManagerImpl implements JmsServerManager {

    /**
     * 子系统的运行路径
     */
    private String homeDir;
    
    /**
     * 是否需要启动joram
     */
    private boolean checkStartFlag=false;

    @Override
    public void shutdown(MessageMetadata messageMetadata) {
        JmsServerModule module = ModuleContext.getInstance().getModule(JmsServerModule.class);
        Logger logger = module.getLogger();
        try{
            String serverIp;
            if(messageMetadata.getServerIp()==null)
                serverIp="localhost";
            else
                serverIp=messageMetadata.getServerIp();
            
            int port=messageMetadata.getPort();
            AdminModule.connect(serverIp,port,"root", "root", 10);
            
            AdminModule.stopServer();
            logger.info("joram will be stopped...");
        }catch(Exception ex){
            String info=ExceptionUtils.getCommonExceptionInfo(ex);
            System.out.println("[error] "+info);
            logger.error("",ex);
        }
    }

    @Override
    public void start(MessageMetadata messageMetadata) {
        JmsServerModule module = ModuleContext.getInstance().getModule(JmsServerModule.class);
        Logger logger = module.getLogger();
        homeDir = ModuleContext.getInstance().getSystemParam(ContainerPropertiesManager.KEY_HOME_DIR);
        
        //如果已启动了，则不重复启动
        if (checkIsRun(messageMetadata.getPort())){
            logger.info("JMS Server have started,will continue.");
            return;
        }
        
        // 以命令行方式启动joram
        String cmd = homeDir+"/modules/"+module.getModuleName()+"/joram/run/startup_server.bat";
        CmdUtils.exeCmdInNewThread(cmd, null);
        SysUtils.sleepNotException(1000);
        logger.info("JMS Server start completed.");
        
        // 初始化connection factory
        initContext(messageMetadata);
    }
    
    /**
     * jms服务是否已运行
     * @param port
     * @return
     */
    private boolean checkIsRun(int port) {
        Boolean[] result=CmdUtils.checkPorts("TCP", new String[]{ port+"" });
        checkStartFlag=result==null || result.length==0 ? false : result[0];
        return checkStartFlag;
    }
    
    /**
     * topic绑定到jndi上
     * @param messageMetadata
     */
    private void initContext(MessageMetadata messageMetadata){
        JmsServerModule module = ModuleContext.getInstance().getModule(JmsServerModule.class);
        Logger logger = module.getLogger();
        
        javax.naming.Context ictx =null;
        try {
            String serverIp;
            if(messageMetadata.getServerIp()==null)
                serverIp="localhost";
            else
                serverIp=messageMetadata.getServerIp();
            
            int port=messageMetadata.getPort();
            AdminModule.connect(serverIp,port,"root", "root", 10);
            
            // cf/queue/tcf的处理
            Properties env = new Properties();
            env.setProperty("java.naming.factory.initial",messageMetadata.getInitial());
            env.setProperty("java.naming.factory.host", serverIp);
            env.setProperty("java.naming.factory.port", messageMetadata.getNamingPort()+"");
            ictx = new javax.naming.InitialContext(env);
            try {
                javax.jms.TopicConnectionFactory tcf = TopicTcpConnectionFactory.create(serverIp, port);
                ictx.bind("tcf", tcf);
            } catch (Exception e) {
                String info=ExceptionUtils.getCommonExceptionInfo(e);
                logger.error(info);
                throw new YuepException(e);
            }
            logger.info("JORAM create connection factory [tcf] completed.");
            
            User.create("anonymous", "anonymous");
        } catch (Exception ex) {
            String info=ExceptionUtils.getCommonExceptionInfo(ex);
            System.err.println("[error] "+info);
        }finally{
            if(ictx!=null){
                try{
                    ictx.close();
                }catch(Exception ex){
                    String info=ExceptionUtils.getCommonExceptionInfo(ex);
                    System.err.println("[error] "+info);
                }
            }
            AdminModule.disconnect();           
        }
    }

    @Override
    public boolean initTopic(MessageMetadata messageMetadata) {
        javax.naming.Context ictx =null;
        try {
            String serverIp;
            if(messageMetadata.getServerIp()==null)
                serverIp="localhost";
            else
                serverIp=messageMetadata.getServerIp();
            
            int port=messageMetadata.getPort();
            AdminModule.connect(serverIp,port,"root", "root", 10);
            
            Properties env = new Properties();
            env.setProperty("java.naming.factory.initial",messageMetadata.getInitial());
            env.setProperty("java.naming.factory.host", serverIp);
            env.setProperty("java.naming.factory.port", messageMetadata.getNamingPort()+"");
            ictx = new javax.naming.InitialContext(env);
            
            // 先解绑定
            try{
                ictx.unbind(messageMetadata.getTopicName());
            }catch(Exception ex){
                System.out.println("[info] unbind "+messageMetadata.getTopicName()+" failed.");
            }
            
            // 再重新绑定
            Topic topic=Topic.create(messageMetadata.getTopicName());
            topic.setFreeReading();
            topic.setFreeWriting();
            ictx.bind(messageMetadata.getTopicName(),topic);
            return true;
        } catch (Exception ex) {
            String info=ExceptionUtils.getCommonExceptionInfo(ex);
            System.err.println("[error] "+info);
            return false;
        }finally{
            if(ictx!=null){
                try{
                    ictx.close();
                }catch(Exception ex){
                    String info=ExceptionUtils.getCommonExceptionInfo(ex);
                    System.err.println("[error] "+info);
                }
            }
            AdminModule.disconnect();           
        }
    }
    
    @Override
    public void closeTopic(MessageMetadata messageMetadata) {
        //  解除topic的jndi绑定
        javax.naming.Context ictx =null;
        try {
            String serverIp;
            if(messageMetadata.getServerIp()==null)
                serverIp="localhost";
            else
                serverIp=messageMetadata.getServerIp();
            
            int port=messageMetadata.getPort();
            AdminModule.connect(serverIp,port,"root", "root", 10);
            
            Properties env = new Properties();
            env.setProperty("java.naming.factory.initial",messageMetadata.getInitial());
            env.setProperty("java.naming.factory.host", serverIp);
            env.setProperty("java.naming.factory.port", messageMetadata.getNamingPort()+"");
            ictx = new javax.naming.InitialContext(env);

            ictx.unbind(messageMetadata.getTopicName());
        } catch (Exception ex) {
            String info=ExceptionUtils.getCommonExceptionInfo(ex);
            System.err.println("[error] "+info);
        }finally{
            if(ictx!=null){
                try{
                    ictx.close();
                }catch(Exception ex){
                    String info=ExceptionUtils.getCommonExceptionInfo(ex);
                    System.err.println("[error] "+info);
                }
            }
            AdminModule.disconnect();           
        }
    }

}
