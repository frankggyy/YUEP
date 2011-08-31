/*
 * $Id: ServerCoreModule.java, 2010-9-17 下午03:03:22 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.servercore.impl;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.exception.YuepException;
import com.yuep.base.util.cmd.CmdUtils;
import com.yuep.base.util.format.IpAddrFormatter;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.ContainerPropertiesManager;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.CoreContext4Container;
import com.yuep.core.container.def.CoreException;
import com.yuep.core.container.impl.CoreContextImpl;
import com.yuep.core.facade.def.FacadeManager;
import com.yuep.core.facade.def.RemoteInvocationFacade;
import com.yuep.core.facade.impl.FacadeManagerImpl;
import com.yuep.core.facade.impl.RemoteInvocationFacadeImpl;
import com.yuep.core.message.def.MessageMetadata;
import com.yuep.core.message.impl.MessageServant;
import com.yuep.core.message.impl.MessageServantImpl;
import com.yuep.core.message.impl.MessageServiceManager;
import com.yuep.core.message.impl.MessageServiceManagerFactory;
import com.yuep.core.message.impl.MessageServiceManagerImpl;
import com.yuep.core.proxy.def.ProxyManager;
import com.yuep.core.proxy.impl.ProxyManagerImpl;
import com.yuep.core.servercore.def.ExitService;
import com.yuep.core.service.impl.remote.exporter.RemoteConnector;
import com.yuep.core.service.impl.remote.exporter.RemoteConnectorImpl;
import com.yuep.core.session.def.SessionService;
import com.yuep.core.session.impl.SessionServiceImpl;
import com.yuep.core.spring.def.SpringService;
import com.yuep.core.spring.impl.ExtendClassPathXmlApplicationContext;
import com.yuep.core.spring.impl.SpringServiceImpl;

/**
 * <p>
 * Title: ServerCoreModule
 * </p>
 * <p>
 * Description: server/sbi容器启动的第一个模块:包括消息,会话,退出,rmi远程等处理
 * </p>
 * 
 * @author aaron
 * created 2010-9-17 下午03:03:22
 * modified [who date description]
 * check [who date description]
 */
public class ServerCoreModule extends DefaultModule {

    /**
     * spring服务
     */
    protected SpringService    springService;
    
    /**
     * 会话服务
     */
    protected SessionService   sessionService;
    
    /**
     * facade管理器
     */
    protected FacadeManagerImpl facadeManager;

    /**
     * proxy管理器
     */
    protected ProxyManagerImpl  proxyManager;
    
    /**
     * param key:配置spring文件的路径
     */
    private static final String PARAM_KEY_SRRING_LOCATION="configLocations";
    
    @Override
    public void start() {
        // 设置该模块为core模块
        ModuleContext.getInstance().setCoreModule(this);
        CoreContextImpl.init();
        
        ModuleContext.getInstance().setContainerLogger(logger);
        
        // rmi端口被占用，抛异常
        String rmiPortStr=ModuleContext.getInstance().getSystemParam(ContainerPropertiesManager.KEY_RMI_PORT);
        checkPortUsed(rmiPortStr);

        // 初始化各个服务
        initCommonService();
        initSpringService();
        initCommunicateService();
        initMessageService();
    }
    
    /**
     * 检查端口是否使用过
     * @param rmiPortStr
     */
    private void checkPortUsed(String rmiPortStr){
        if(rmiPortStr==null || rmiPortStr.equals("") || rmiPortStr.equals("0"))
            throw new YuepException(YuepException.INTERNAL_ERROR,"rmi port is invalid");
        Boolean[] result=CmdUtils.checkPorts("TCP", new String[]{ rmiPortStr });
        boolean isUsed=(result==null || result.length==0 ? false : result[0]);
        if(isUsed){
            String info="port "+rmiPortStr+" is used,it will exit";
            System.out.println(info);
            throw new CoreException(CoreException.PORT_USED,rmiPortStr);
        }
    }
    
    /**
     * 初始化日志等服务
     */
    private void initCommonService(){
        // 初始化日志
        try{
            // 对第三方框架(比如spring,hibernate)的日志输出处理
            Log4jConfigurer.initLogging("classpath:modules/"+getModuleName()+"/conf/log4j-3rdparty.properties");
        }catch(Exception ex){
            System.err.println("init the third party framework logger failed,ex="+ExceptionUtils.getCommonExceptionInfo(ex));
        }
    }
    
    /**
     * 初始化spring，包括里面的spring bean,并将其注册为service
     */
    private void initSpringService(){
        springService=new SpringServiceImpl();
        CoreContext.getInstance().setLocalService("springService", SpringService.class, springService);
        
        String springLocations = getModuleParams().get(PARAM_KEY_SRRING_LOCATION);
        String[] configLocations = springLocations.split(",");
        try {
            ApplicationContext ctx = new ExtendClassPathXmlApplicationContext(classLoader,null,configLocations);
            springService.setCoreAppCtx(ctx);
        }catch(RuntimeException ex2){
            throw ex2;
        }catch (Exception e) {
            String errorInfo=ExceptionUtils.getCommonExceptionInfo(e);
            System.err.println(errorInfo);
            throw new CoreException(CoreException.CORE_MODULE_INIT_FAILED,errorInfo);
        }
    }
    
    
    /**
     * 初始化子系统通信相关的service
     */
    private void initCommunicateService(){
        try {
            RemoteConnector remoteConnector=new RemoteConnectorImpl();
            CoreContext.getInstance().setRemoteService("remoteConnector", RemoteConnector.class, remoteConnector);
            
            sessionService = new SessionServiceImpl();
            CoreContext.getInstance().setLocalService("sessionService", SessionService.class, sessionService);
            logger.info("session service init completed.");
            
            // facade管理器初始化
            facadeManager=new FacadeManagerImpl();
            facadeManager.init();
            CoreContext.getInstance().setLocalService("facadeManager", FacadeManager.class, facadeManager);

            RemoteInvocationFacadeImpl remoteInvocationFacade=new RemoteInvocationFacadeImpl();
            CoreContext.getInstance().setRemoteService("remoteInvocationFacade", RemoteInvocationFacade.class, remoteInvocationFacade);
            remoteInvocationFacade.init();
            logger.info("facade init completed.");
            
            // proxy管理器初始化
            proxyManager=new ProxyManagerImpl();
            CoreContext.getInstance().setLocalService("proxyManager", ProxyManager.class, proxyManager);
            ((CoreContextImpl)CoreContext.getInstance()).getRemoteServiceManager().init();
            
            ExitService exitService = new ExitServiceImpl();
            CoreContext.getInstance().setRemoteService("exitService", ExitService.class, exitService);
        }catch(RuntimeException ex2){
            throw ex2;
        }catch (Exception e) {
            String errorInfo=ExceptionUtils.getCommonExceptionInfo(e);
            System.err.println(errorInfo);
            throw new CoreException(CoreException.CORE_MODULE_INIT_FAILED,errorInfo);
        }
    }
    
    /**
     * 初始化消息服务
     */
    private void initMessageService(){
        // 初始化remote消息服务器的参数
        String jmsServerIp = getJmsServerHost(moduleParams);
        String jmsServerPort = moduleParams.get(ContainerPropertiesManager.KEY_JMS_PORT);
        if(jmsServerPort==null)
            return;

        String jmsServerNamingPort = moduleParams.get(ContainerPropertiesManager.KEY_JMS_NAMING_PORT);
        String initial = moduleParams.get(ContainerPropertiesManager.KEY_JMS_FACTORY_CLASS);
        MessageMetadata messageMetadata=new MessageMetadata();
        messageMetadata.setServerIp(jmsServerIp);
        messageMetadata.setNamingPort(Integer.valueOf(jmsServerNamingPort));
        messageMetadata.setInitial(initial);
        messageMetadata.setPort(Integer.valueOf(jmsServerPort));
        
        CoreContext coreContext = CoreContext.getInstance();
        String rmiPort = ModuleContext.getInstance().getSystemParam(ContainerPropertiesManager.KEY_RMI_PORT);
        messageMetadata.setTopicName("topic."+rmiPort);
        ((CoreContext4Container)coreContext).setSelfMessageMetadata(messageMetadata);
        
        // 初始化JMS消息发送
        MessageServiceManager messageServiceManager = MessageServiceManagerFactory.getMessageServiceManager();
        MessageServantImpl messageServant = new MessageServantImpl((MessageServiceManagerImpl) messageServiceManager);
        messageServant.init(); 
        CoreContext.getInstance().setRemoteService("messageServant", MessageServant.class, messageServant);
    }
    
    /**
     * 获取jms服务器的IP地址，如果是localhost的，则使用本机的机器名
     * @param params
     * @return
     */
    private String getJmsServerHost(Map<String, String> params){
        String jmsServerIp = params.get(ContainerPropertiesManager.KEY_JMS_IP);
        if(jmsServerIp==null || jmsServerIp.equals("0.0.0.0") || jmsServerIp.equals("127.0.0.1")
                || jmsServerIp.equals("localhost")){
            return CmdUtils.getLocalHostName();
        }else if(IpAddrFormatter.isIPAddress(jmsServerIp)){
            throw new CoreException(CoreException.CORE_MODULE_INIT_FAILED,ContainerPropertiesManager.KEY_JMS_IP+" is an invalid ip");
        }
        return jmsServerIp;
    }

    @Override
    public void stop() {
    }

}
