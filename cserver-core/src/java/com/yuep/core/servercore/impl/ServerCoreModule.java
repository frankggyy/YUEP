/*
 * $Id: ServerCoreModule.java, 2010-9-17 ����03:03:22 aaron Exp $
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
 * Description: server/sbi���������ĵ�һ��ģ��:������Ϣ,�Ự,�˳�,rmiԶ�̵ȴ���
 * </p>
 * 
 * @author aaron
 * created 2010-9-17 ����03:03:22
 * modified [who date description]
 * check [who date description]
 */
public class ServerCoreModule extends DefaultModule {

    /**
     * spring����
     */
    protected SpringService    springService;
    
    /**
     * �Ự����
     */
    protected SessionService   sessionService;
    
    /**
     * facade������
     */
    protected FacadeManagerImpl facadeManager;

    /**
     * proxy������
     */
    protected ProxyManagerImpl  proxyManager;
    
    /**
     * param key:����spring�ļ���·��
     */
    private static final String PARAM_KEY_SRRING_LOCATION="configLocations";
    
    @Override
    public void start() {
        // ���ø�ģ��Ϊcoreģ��
        ModuleContext.getInstance().setCoreModule(this);
        CoreContextImpl.init();
        
        ModuleContext.getInstance().setContainerLogger(logger);
        
        // rmi�˿ڱ�ռ�ã����쳣
        String rmiPortStr=ModuleContext.getInstance().getSystemParam(ContainerPropertiesManager.KEY_RMI_PORT);
        checkPortUsed(rmiPortStr);

        // ��ʼ����������
        initCommonService();
        initSpringService();
        initCommunicateService();
        initMessageService();
    }
    
    /**
     * ���˿��Ƿ�ʹ�ù�
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
     * ��ʼ����־�ȷ���
     */
    private void initCommonService(){
        // ��ʼ����־
        try{
            // �Ե��������(����spring,hibernate)����־�������
            Log4jConfigurer.initLogging("classpath:modules/"+getModuleName()+"/conf/log4j-3rdparty.properties");
        }catch(Exception ex){
            System.err.println("init the third party framework logger failed,ex="+ExceptionUtils.getCommonExceptionInfo(ex));
        }
    }
    
    /**
     * ��ʼ��spring�����������spring bean,������ע��Ϊservice
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
     * ��ʼ����ϵͳͨ����ص�service
     */
    private void initCommunicateService(){
        try {
            RemoteConnector remoteConnector=new RemoteConnectorImpl();
            CoreContext.getInstance().setRemoteService("remoteConnector", RemoteConnector.class, remoteConnector);
            
            sessionService = new SessionServiceImpl();
            CoreContext.getInstance().setLocalService("sessionService", SessionService.class, sessionService);
            logger.info("session service init completed.");
            
            // facade��������ʼ��
            facadeManager=new FacadeManagerImpl();
            facadeManager.init();
            CoreContext.getInstance().setLocalService("facadeManager", FacadeManager.class, facadeManager);

            RemoteInvocationFacadeImpl remoteInvocationFacade=new RemoteInvocationFacadeImpl();
            CoreContext.getInstance().setRemoteService("remoteInvocationFacade", RemoteInvocationFacade.class, remoteInvocationFacade);
            remoteInvocationFacade.init();
            logger.info("facade init completed.");
            
            // proxy��������ʼ��
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
     * ��ʼ����Ϣ����
     */
    private void initMessageService(){
        // ��ʼ��remote��Ϣ�������Ĳ���
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
        
        // ��ʼ��JMS��Ϣ����
        MessageServiceManager messageServiceManager = MessageServiceManagerFactory.getMessageServiceManager();
        MessageServantImpl messageServant = new MessageServantImpl((MessageServiceManagerImpl) messageServiceManager);
        messageServant.init(); 
        CoreContext.getInstance().setRemoteService("messageServant", MessageServant.class, messageServant);
    }
    
    /**
     * ��ȡjms��������IP��ַ�������localhost�ģ���ʹ�ñ����Ļ�����
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
