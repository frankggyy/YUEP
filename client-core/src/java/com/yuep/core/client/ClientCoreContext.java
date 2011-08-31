/*
 * $Id: ClientCoreContext.java, 2010-9-26 下午04:23:32 aaron Exp $
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
 * Description:客户端核心的上下文
 * </p>
 * 
 * @author aaron
 * created 2010-9-26 下午04:23:32
 * modified [who date description]
 * check [who date description]
 */
public class ClientCoreContext {
    
    /** 当前客户端的语言 */
    private static Locale locale = Locale.getDefault();
    
    /** 客户端资源工厂 */
    private static ResourceFactory resourceFactory;
    
    /**
     * 控件工厂
     */
    private static SwingFactory swingFactory;
    
    /**  客户端主界面 */
    private static Window mainFrame;
    
    /**
     * 主界面菜单栏
     */
    private static XMenuBarView menuBarView;
    
    /**
     * 主界面工具栏
     */
    private static XToolBarView toolBarView;
    
    /**
     * 状态栏管理器
     */
    private static StatusBarManager statusBarManager;

    /**
     * 日志输出栏管理器
     */
    private static OutputManager outputManager;
    
    /** 客户端窗口管理器 */
    private static WindowManager windowManager;
    
    /**
     * 客户端界面是否缓存
     */
    private static boolean mvcCache = true;
    
    /**
     * Spring的Application context
     */
    private static ApplicationContext applicationContext;
    
    /**
     * 通信对象
     */
    private static CommunicateObject localCommunicateObject=CoreContext.getInstance().local();

    /**
     * 日志
     */
    private static Logger logger;
    
    /**
     * 客户端校验权限的接口
     */
    private static AccessCheck accessCheck=new AlwayTrueAccessCheck(); //默认都可访问
    
    public static void setLogger(Logger logger) {
        ClientCoreContext.logger = logger;
    }
    
    public static Logger getLogger() {
        return logger;
    }
    
    /**
     * 获取客户端资源工厂
     * @return the resourceFactory
     */
    public static ResourceFactory getResourceFactory() {
        return resourceFactory;
    }
    
    /**
     * 根据模块的类型返回一个模块
     * @param <T> 具体的模块类型
     * @param moduleClass 具体模块的Class
     * @return 具体的模块
     */
    public static <T extends Module> T getModule(Class<T> moduleClass) {
        return ModuleContext.getInstance().getModule(moduleClass);
    }

    /**
     * 获取客户端所有模块
     * @return 客户端所有模块
     */
    public static Map<Class<? extends Module>, Module> getModules() {
        return ModuleContext.getInstance().getModules();
    }
    
    /**
     * 获取客户端模块的优先级
     * @return 模块优先级对象
     */
    public static ModulePriority getModulePriority(){
        return ModuleContext.getInstance().getModulePriority();
    }

    /**
     * 设置客户端的资源工厂
     * @param resourceFactory the resourceFactory to set
     */
    public static void setResourceFactory(ResourceFactory resourceFactory) {
        ClientCoreContext.resourceFactory = resourceFactory;
    }

    /**
     * 获取客户端主界面的window
     * @return the mainFrame
     */
    public static Window getMainFrame() {
        return mainFrame;
    }

    /**
     * 设置客户端主界面
     * @param mainFrame the mainFrame to set
     */
    public static void setMainFrame(Window mainFrame) {
        ClientCoreContext.mainFrame = mainFrame;
    }

    /**
     * 根据key获取国际化后的value
     * 
     * @param key
     *            国际化的key
     * @return key对应的展示名称
     */
    public static String getString(String key) {
        return getString(key,new Object[0]);
    }

    /**
     * 根据key获取国际化后的value
     * 
     * @param key
     *            国际化的key
     * @param objects
     *            参数
     * @return key对应的展示名称
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
     * 设置客户端locale
     * @param locale
     *            the locale to set
     */
    public static void setLocale(Locale locale) {
        // 如果locale变化了，需要清除图片缓存
        if (ClientCoreContext.locale != locale) {
            resourceFactory.reset();
        }
        ClientCoreContext.locale = locale;
        resourceFactory.setLocale(locale);
    }

    /**
     * 获取客户端Locale
     * @return the locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * 客户端界面控件工厂获取
     * @return the SwingFactory
     */
    public static SwingFactory getSwingFactory() {
        if(swingFactory == null)
            swingFactory = new SwingFactoryImpl();
        return swingFactory;
    }
    
    /**
     * 判断客户端界面是否缓存
     * @return mvcCache 为<code>true</code>是缓存界面，否则不缓存
     */
    public static boolean isMvcCache() {
        return mvcCache;
    }

    /**
     * 设置客户端界面的缓存模式
     * @param mvcCache 是否缓存
     */
    public static void setMvcCache(boolean mvcCache) {
        ClientCoreContext.mvcCache = mvcCache;
    }

    /**
     * 获取主界面的菜单栏
     * @return the menuBarView
     */
    public static XMenuBarView getMenuBarView() {
        return menuBarView;
    }

    /**
     * 设置主界面的菜单栏
     * @param menuBarView the menuBarView to set
     */
    public static void setMenuBarView(XMenuBarView menuBarView) {
        ClientCoreContext.menuBarView = menuBarView;
    }

    /**
     * 获取主界面工具栏
     * @return the toolBarView
     */
    public static XToolBarView getToolBarView() {
        return toolBarView;
    }

    /**
     * 设置主界面工具栏
     * @param toolBarView the toolBarView to set
     */
    public static void setToolBarView(XToolBarView toolBarView) {
        ClientCoreContext.toolBarView = toolBarView;
    }

    /**
     * 获取主界面状态栏
     * @return the statusBarManager
     */
    public static StatusBarManager getStatusBarManager() {
        return statusBarManager;
    }

    /**
     * 主界面状态栏设置
     * @param statusBarManager the statusBarManager to set
     */
    public static void setStatusBarManager(StatusBarManager statusBarManager) {
        ClientCoreContext.statusBarManager = statusBarManager;
    }

    /**
     * 主界面消息输入器管理器获取
     * @return the outputManager
     */
    public static OutputManager getOutputManager() {
        return outputManager;
    }

    /**
     * 设置主界面消息输出区管理器
     * @param outputManager the outputManager to set
     */
    public static void setOutputManager(OutputManager outputManager) {
        ClientCoreContext.outputManager = outputManager;
    }

    /**
     * 获取客户端窗口管理器
     * @return the windowManager
     */
    public static WindowManager getWindowManager() {
        return windowManager;
    }

    /**
     * 设置客户端窗口管理器
     * @param windowManager the windowManager to set
     */
    public static void setWindowManager(WindowManager windowManager) {
        ClientCoreContext.windowManager = windowManager;
    }

    /**
     * 获取客户端Spring应用程序的上下文
     * @return the applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 设置客户端Spring的应用程序上下文
     * @param applicationContext the applicationContext to set
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        ClientCoreContext.applicationContext = applicationContext;
    }
    
    /**
     * 连接到远端(服务端)
     * @param remoteSideIp 远端服务端的IP
     * @param port
     */
    public static void connect2Remote(String remoteSideIp, int port){
        serverProxy.connect2Remote(remoteSideIp, port);
    }
    
    /**
     * 断开与服务端的连接
     */
    public static void disconnect(){
        serverProxy.disconnect();
    }
    
    /**
     * 获取一个客户端本地服务接口
     * @param <T> 具体要获取的service接口类型
     * @param serviceName 本地service的名字
     * @param serviceItf 本地service的接口类型
     * @return T 具体要获取的service
     */
    public static <T> T getLocalService(String serviceName,Class<T> serviceItf){
        return localCommunicateObject.getService(serviceName, serviceItf);
    }
    
    /**
     * 注册本地服务
     * @param serviceName
     * @param interfaceClass
     * @param serviceInstance
     */
    public static void setLocalService(String serviceName, Class<?> interfaceClass, Object serviceInstance){
        CoreContext.getInstance().setLocalService(serviceName, interfaceClass, serviceInstance);
    }
    
    /**
     * 订阅客户端本地的消息(进程内的消息)
     * @param name 消息名
     * @param receiver 消息接收者
     */
    public static void subscribeLocal(String name, MessageReceiver receiver){
        localCommunicateObject.subscribe(name, receiver);
    }
    
    /**
     * 取消订阅客户端本地的消息(进程内的消息)
     * @param name 消息名
     * @param receiver 消息接收者
     */
    public static void unsubscribeLocal(String name, MessageReceiver receiver){
        localCommunicateObject.unsubscribe(name, receiver);
    }
    
    /**
     * 发送local消息
     * @param messageName
     * @param messageInfo
     */
    public static void publicLocalMessage(String messageName,Serializable messageInfo){
        CoreContext.getInstance().publish(messageName, messageInfo);
    }

    /**
     * 获取远端（比如：服务端）的服务接口
     * @param <T>
     * @param serviceName
     * @param serviceItf
     * @return
     */
    public static <T> T getRemoteService(String serviceName,Class<T> serviceItf){
        return serverProxy.getRemoteService(serviceName, serviceItf);
    }
    
    /**
     * 订阅remote消息
     * @param name 消息名
     * @param receiver 消息接收者
     */
    public static void subscribeRemote(String name, MessageReceiver receiver){
        serverProxy.subscribeRemote(name, receiver);
    }
    
    /**
     * 取消订阅remote消息
     * @param name 消息名
     * @param receiver 消息接收者
     */
    public static void unsubscribeRemote(String name, MessageReceiver receiver){
        serverProxy.unsubscribeRemote(name, receiver);
    }
    
    /**
     * 本地客户端在服务端的session id
     * @return
     */
    public static Long getSessionId(){
        return serverProxy.getSessionId();
    }
    
    /**
     * server在GUI侧的proxy,可替换
     */
    private static ServerProxy serverProxy=new DefaultServerProxy();
    
    /**
     * 管理接口：设置server proxy
     * @param serverProxy
     */
    public static void setServerProxy(ServerProxy serverProxy) {
        ClientCoreContext.serverProxy = serverProxy;
    }
    
    /**
     * 权限验证接口
     * @return
     */
    public static AccessCheck getAccessCheck() {
        return accessCheck;
    }
    
    /**
     * 设置权限验证接口
     * @param accessCheck
     */
    public static void setAccessCheck(AccessCheck accessCheck) {
        ClientCoreContext.accessCheck = accessCheck;
    }

}
