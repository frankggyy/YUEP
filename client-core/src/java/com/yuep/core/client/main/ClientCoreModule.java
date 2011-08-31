/*
 * $Id: ClientCoreModule.java, 2010-10-14 ����05:58:38 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.Module;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.main.login.processing.DefaultLoadResource;
import com.yuep.core.client.main.login.processing.DefaultLoginProcessor;
import com.yuep.core.client.module.ClientModule;
import com.yuep.core.client.spring.XI18nResourceBundleMessageSource;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.CoreException;
import com.yuep.core.container.impl.CoreContextImpl;
import com.yuep.core.proxy.def.ProxyManager;
import com.yuep.core.proxy.impl.ProxyManagerImpl;
import com.yuep.core.spring.def.SpringService;
import com.yuep.core.spring.impl.ExtendClassPathXmlApplicationContext;
import com.yuep.core.spring.impl.SpringServiceImpl;

/**
 * <p>
 * Title: ClientCoreModule
 * </p>
 * <p>
 * Description:�ͻ��˺���ģ�飬������Spring����Դ��application context�ļ���
 * </p>
 * 
 * @author aaron
 * created 2010-10-14 ����05:58:38
 * modified [who date description]
 * check [who date description]
 */
public class ClientCoreModule extends ClientModule {

    /**
     * param key:����spring�ļ���·��
     */
    private static final String PARAM_KEY_SRRING_LOCATION="configLocations";
    
    private String[] configLocations;
    private SpringService springService;
    
    @Override
    public void start() {
        ModuleContext.getInstance().setCoreModule(this);
        CoreContextImpl.init();
        
        initParam();
        
        try{
            // �Ե��������(����spring)����־�������
            Log4jConfigurer.initLogging("classpath:modules/"+getModuleName()+"/conf/log4j-3rdparty.properties");
        }catch(Exception ex){
            System.err.println("init the third party framework logger failed,ex="+ExceptionUtils.getCommonExceptionInfo(ex));
        }
        
        ModuleContext.getInstance().setContainerLogger(logger);
        ClientCoreContext.setLogger(logger);
        
        // ��ʼ��spring
        initSpringService();
        
        // ��ȡ���������ļ�,��ʼ���뵽��������
        initData();
    }
    
    /**
     * ������
     */
    private void initParam(){
        String locations = getModuleParams().get(PARAM_KEY_SRRING_LOCATION);
        configLocations = locations.split(",");
    }
    
    /**
     * ��ʼ��spring
     */
    private void initSpringService(){
        springService=new SpringServiceImpl();
        CoreContext.getInstance().setLocalService("springService", SpringService.class, springService);
        
        try {
            ApplicationContext ctx = new ExtendClassPathXmlApplicationContext(classLoader,null,configLocations);
            springService.setCoreAppCtx(ctx);
        } catch (Exception e) {
            String errorInfo=ExceptionUtils.getCommonExceptionInfo(e);
            System.err.println(errorInfo);
            throw new CoreException(CoreException.SPRING_LOAD_FAILED,errorInfo);
        }
    }
    
    /**
     * ������ʼ��
     */
    private void initData() {
        // proxy�ĳ�ʼ��
        ProxyManagerImpl proxyManager=new ProxyManagerImpl();
        CoreContext.getInstance().setLocalService("proxyManager", ProxyManager.class, proxyManager);
        ((CoreContextImpl)CoreContext.getInstance()).getRemoteServiceManager().init();
        
        // ��ʼ��i18n�Ĵ���
        DefaultLoadResource resource = new DefaultLoadResource();
        resource.loadResource(getModuleName());
        List<String> moduleNames = getModuleNames();
        XI18nResourceBundleMessageSource messageResource = springService.getBean("messageSource",
                XI18nResourceBundleMessageSource.class);
        messageResource.loadI18nFiles(moduleNames);
        ApplicationContext appCtx = springService.getCoreAppCtx();
        ClientCoreContext.setApplicationContext(appCtx);
        
        // ��ȡ��¼��������,����login
        DefaultLoginProcessor<StartObj> loginProcessor = new DefaultLoginProcessor<StartObj>();
        loginProcessor.interpreterProcessor("login-process.xml");
        StartObj startObj = new StartObj();
        loginProcessor.process(startObj);
    }

    /**
     * ����ģ����
     * @return
     */
    private List<String> getModuleNames() {
        Map<Class<? extends Module>, Module> modules = ClientCoreContext.getModules();
        List<String> moduleNames = new ArrayList<String>();
        Set<Entry<Class<? extends Module>, Module>> entrySet = modules.entrySet();
        for (Entry<Class<? extends Module>, Module> entry : entrySet) {
            Module module = entry.getValue();
            String moduleName = module.getModuleName();
            moduleNames.add(moduleName);
        }
        return moduleNames;
    }

    /**
     * @see com.yuep.core.module.def.ModuleManager4Container#stop()
     */
    @Override
    public void stop() {
    }

}
