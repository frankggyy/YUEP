/*
 * $Id: ClientCoreContext.java, 2010-9-26 ����04:23:32 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client;

import java.awt.Window;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.yuep.base.log.def.Logger;
import com.yuep.base.resource.ResourceFactory;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.Module;
import com.yuep.core.bootstrap.def.module.ModulePriority;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.SwingFactoryImpl;
import com.yuep.core.client.component.menu.XMenuBarView;
import com.yuep.core.client.component.output.OutputManager;
import com.yuep.core.client.component.statusbar.view.StatusBarManager;
import com.yuep.core.client.component.toolbar.XToolBarView;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.permit.AccessCheck;
import com.yuep.core.client.permit.AlwayTrueAccessCheck;
import com.yuep.core.client.serverproxy.def.ServerProxy;
import com.yuep.core.client.serverproxy.impl.DefaultServerProxy;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.message.def.MessageReceiver;


/**
 * <p>
 * Title: ClientCoreContext
 * </p>
 * <p>
 * Description:�ͻ��˺��ĵ�������
 * </p>
 * 
 * @author aaron
 * created 2010-9-26 ����04:23:32
 * modified [who date description]
 * check [who date description]
 */
public class ClientCoreContext {
    
    /** ��ǰ�ͻ��˵����� */
    private static Locale locale = Locale.getDefault();
    
    /** �ͻ�����Դ���� */
    private static ResourceFactory resourceFactory;
    
    /**
     * �ؼ�����
     */
    private static SwingFactory swingFactory;
    
    /**  �ͻ��������� */
    private static Window mainFrame;
    
    /**
     * ������˵���
     */
    private static XMenuBarView menuBarView;
    
    /**
     * �����湤����
     */
    private static XToolBarView toolBarView;
    
    /**
     * ״̬��������
     */
    private static StatusBarManager statusBarManager;

    /**
     * ��־�����������
     */
    private static OutputManager outputManager;
    
    /** �ͻ��˴��ڹ����� */
    private static WindowManager windowManager;
    
    /**
     * �ͻ��˽����Ƿ񻺴�
     */
    private static boolean mvcCache = true;
    
    /**
     * Spring��Application context
     */
    private static ApplicationContext applicationContext;
    
    /**
     * ͨ�Ŷ���
     */
    private static CommunicateObject localCommunicateObject=CoreContext.getInstance().local();

    /**
     * ��־
     */
    private static Logger logger;
    
    /**
     * �ͻ���У��Ȩ�޵Ľӿ�
     */
    private static AccessCheck accessCheck=new AlwayTrueAccessCheck(); //Ĭ�϶��ɷ���
    
    public static void setLogger(Logger logger) {
        ClientCoreContext.logger = logger;
    }
    
    public static Logger getLogger() {
        return logger;
    }
    
    /**
     * ��ȡ�ͻ�����Դ����
     * @return the resourceFactory
     */
    public static ResourceFactory getResourceFactory() {
        return resourceFactory;
    }
    
    /**
     * ����ģ������ͷ���һ��ģ��
     * @param <T> �����ģ������
     * @param moduleClass ����ģ���Class
     * @return �����ģ��
     */
    public static <T extends Module> T getModule(Class<T> moduleClass) {
        return ModuleContext.getInstance().getModule(moduleClass);
    }

    /**
     * ��ȡ�ͻ�������ģ��
     * @return �ͻ�������ģ��
     */
    public static Map<Class<? extends Module>, Module> getModules() {
        return ModuleContext.getInstance().getModules();
    }
    
    /**
     * ��ȡ�ͻ���ģ������ȼ�
     * @return ģ�����ȼ�����
     */
    public static ModulePriority getModulePriority(){
        return ModuleContext.getInstance().getModulePriority();
    }

    /**
     * ���ÿͻ��˵���Դ����
     * @param resourceFactory the resourceFactory to set
     */
    public static void setResourceFactory(ResourceFactory resourceFactory) {
        ClientCoreContext.resourceFactory = resourceFactory;
    }

    /**
     * ��ȡ�ͻ����������window
     * @return the mainFrame
     */
    public static Window getMainFrame() {
        return mainFrame;
    }

    /**
     * ���ÿͻ���������
     * @param mainFrame the mainFrame to set
     */
    public static void setMainFrame(Window mainFrame) {
        ClientCoreContext.mainFrame = mainFrame;
    }

    /**
     * ����key��ȡ���ʻ����value
     * 
     * @param key
     *            ���ʻ���key
     * @return key��Ӧ��չʾ����
     */
    public static String getString(String key) {
        return getString(key,new Object[0]);
    }

    /**
     * ����key��ȡ���ʻ����value
     * 
     * @param key
     *            ���ʻ���key
     * @param objects
     *            ����
     * @return key��Ӧ��չʾ����
     */
    public static String getString(String key, Object... objects) {
        if (key == null)
            return null;

        if(objects==null)
            objects=new Object[0];
        String result = null;
        try {
            result = applicationContext.getMessage(key, objects, null, locale);
        } catch (Exception e) {
            System.err.println("internationalization " + key + "failure!"
                    + e.getMessage());
        }
        if (result == null) {
            return key;
        }
        return result.trim();
    }
    
    /**
     * ���ÿͻ���locale
     * @param locale
     *            the locale to set
     */
    public static void setLocale(Locale locale) {
        // ���locale�仯�ˣ���Ҫ���ͼƬ����
        if (ClientCoreContext.locale != locale) {
            resourceFactory.reset();
        }
        ClientCoreContext.locale = locale;
        resourceFactory.setLocale(locale);
    }

    /**
     * ��ȡ�ͻ���Locale
     * @return the locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * �ͻ��˽���ؼ�������ȡ
     * @return the SwingFactory
     */
    public static SwingFactory getSwingFactory() {
        if(swingFactory == null)
            swingFactory = new SwingFactoryImpl();
        return swingFactory;
    }
    
    /**
     * �жϿͻ��˽����Ƿ񻺴�
     * @return mvcCache Ϊ<code>true</code>�ǻ�����棬���򲻻���
     */
    public static boolean isMvcCache() {
        return mvcCache;
    }

    /**
     * ���ÿͻ��˽���Ļ���ģʽ
     * @param mvcCache �Ƿ񻺴�
     */
    public static void setMvcCache(boolean mvcCache) {
        ClientCoreContext.mvcCache = mvcCache;
    }

    /**
     * ��ȡ������Ĳ˵���
     * @return the menuBarView
     */
    public static XMenuBarView getMenuBarView() {
        return menuBarView;
    }

    /**
     * ����������Ĳ˵���
     * @param menuBarView the menuBarView to set
     */
    public static void setMenuBarView(XMenuBarView menuBarView) {
        ClientCoreContext.menuBarView = menuBarView;
    }

    /**
     * ��ȡ�����湤����
     * @return the toolBarView
     */
    public static XToolBarView getToolBarView() {
        return toolBarView;
    }

    /**
     * ���������湤����
     * @param toolBarView the toolBarView to set
     */
    public static void setToolBarView(XToolBarView toolBarView) {
        ClientCoreContext.toolBarView = toolBarView;
    }

    /**
     * ��ȡ������״̬��
     * @return the statusBarManager
     */
    public static StatusBarManager getStatusBarManager() {
        return statusBarManager;
    }

    /**
     * ������״̬������
     * @param statusBarManager the statusBarManager to set
     */
    public static void setStatusBarManager(StatusBarManager statusBarManager) {
        ClientCoreContext.statusBarManager = statusBarManager;
    }

    /**
     * ��������Ϣ��������������ȡ
     * @return the outputManager
     */
    public static OutputManager getOutputManager() {
        return outputManager;
    }

    /**
     * ������������Ϣ�����������
     * @param outputManager the outputManager to set
     */
    public static void setOutputManager(OutputManager outputManager) {
        ClientCoreContext.outputManager = outputManager;
    }

    /**
     * ��ȡ�ͻ��˴��ڹ�����
     * @return the windowManager
     */
    public static WindowManager getWindowManager() {
        return windowManager;
    }

    /**
     * ���ÿͻ��˴��ڹ�����
     * @param windowManager the windowManager to set
     */
    public static void setWindowManager(WindowManager windowManager) {
        ClientCoreContext.windowManager = windowManager;
    }

    /**
     * ��ȡ�ͻ���SpringӦ�ó����������
     * @return the applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * ���ÿͻ���Spring��Ӧ�ó���������
     * @param applicationContext the applicationContext to set
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        ClientCoreContext.applicationContext = applicationContext;
    }
    
    /**
     * ���ӵ�Զ��(�����)
     * @param remoteSideIp Զ�˷���˵�IP
     * @param port
     */
    public static void connect2Remote(String remoteSideIp, int port){
        serverProxy.connect2Remote(remoteSideIp, port);
    }
    
    /**
     * �Ͽ������˵�����
     */
    public static void disconnect(){
        serverProxy.disconnect();
    }
    
    /**
     * ��ȡһ���ͻ��˱��ط���ӿ�
     * @param <T> ����Ҫ��ȡ��service�ӿ�����
     * @param serviceName ����service������
     * @param serviceItf ����service�Ľӿ�����
     * @return T ����Ҫ��ȡ��service
     */
    public static <T> T getLocalService(String serviceName,Class<T> serviceItf){
        return localCommunicateObject.getService(serviceName, serviceItf);
    }
    
    /**
     * ע�᱾�ط���
     * @param serviceName
     * @param interfaceClass
     * @param serviceInstance
     */
    public static void setLocalService(String serviceName, Class<?> interfaceClass, Object serviceInstance){
        CoreContext.getInstance().setLocalService(serviceName, interfaceClass, serviceInstance);
    }
    
    /**
     * ���Ŀͻ��˱��ص���Ϣ(�����ڵ���Ϣ)
     * @param name ��Ϣ��
     * @param receiver ��Ϣ������
     */
    public static void subscribeLocal(String name, MessageReceiver receiver){
        localCommunicateObject.subscribe(name, receiver);
    }
    
    /**
     * ȡ�����Ŀͻ��˱��ص���Ϣ(�����ڵ���Ϣ)
     * @param name ��Ϣ��
     * @param receiver ��Ϣ������
     */
    public static void unsubscribeLocal(String name, MessageReceiver receiver){
        localCommunicateObject.unsubscribe(name, receiver);
    }
    
    /**
     * ����local��Ϣ
     * @param messageName
     * @param messageInfo
     */
    public static void publicLocalMessage(String messageName,Serializable messageInfo){
        CoreContext.getInstance().publish(messageName, messageInfo);
    }

    /**
     * ��ȡԶ�ˣ����磺����ˣ��ķ���ӿ�
     * @param <T>
     * @param serviceName
     * @param serviceItf
     * @return
     */
    public static <T> T getRemoteService(String serviceName,Class<T> serviceItf){
        return serverProxy.getRemoteService(serviceName, serviceItf);
    }
    
    /**
     * ����remote��Ϣ
     * @param name ��Ϣ��
     * @param receiver ��Ϣ������
     */
    public static void subscribeRemote(String name, MessageReceiver receiver){
        serverProxy.subscribeRemote(name, receiver);
    }
    
    /**
     * ȡ������remote��Ϣ
     * @param name ��Ϣ��
     * @param receiver ��Ϣ������
     */
    public static void unsubscribeRemote(String name, MessageReceiver receiver){
        serverProxy.unsubscribeRemote(name, receiver);
    }
    
    /**
     * ���ؿͻ����ڷ���˵�session id
     * @return
     */
    public static Long getSessionId(){
        return serverProxy.getSessionId();
    }
    
    /**
     * server��GUI���proxy,���滻
     */
    private static ServerProxy serverProxy=new DefaultServerProxy();
    
    /**
     * ����ӿڣ�����server proxy
     * @param serverProxy
     */
    public static void setServerProxy(ServerProxy serverProxy) {
        ClientCoreContext.serverProxy = serverProxy;
    }
    
    /**
     * Ȩ����֤�ӿ�
     * @return
     */
    public static AccessCheck getAccessCheck() {
        return accessCheck;
    }
    
    /**
     * ����Ȩ����֤�ӿ�
     * @param accessCheck
     */
    public static void setAccessCheck(AccessCheck accessCheck) {
        ClientCoreContext.accessCheck = accessCheck;
    }

}
