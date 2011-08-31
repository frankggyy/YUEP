/*
 * $Id: ClientModule.java, 2010-9-27 上午10:14:32 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.module;

import java.awt.Image;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Icon;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.interpreter.MenuInterpreter;
import com.yuep.core.client.component.menu.interpreter.MenuInterpreterImpl;
import com.yuep.core.client.mvc.AbstractClientModel;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.ClientModel;
import com.yuep.core.client.mvc.ClientView;
import com.yuep.core.client.mvc.DefaultClientController;
import com.yuep.core.client.mvc.DefaultClientModel;
import com.yuep.core.client.mvc.validate.AbstractValidateView;


/**
 * <p>
 * Title: ClientModule
 * </p>
 * <p>
 * Description:客户端Module，客户端上层模块(有界面的模块)需要继承ClientModule
 * </p>
 * 
 * @author aaron
 * created 2010-9-27 上午10:14:32
 * modified [who date description]
 * check [who date description]
 */
public abstract class ClientModule extends DefaultModule{
    
    /**
     * 配置菜单解析器
     */
    private MenuInterpreter menuInterpreter;
    
    public ClientModule(){
        super();
    }
    
    /**
     * @param moduleName
     */
    public ClientModule(String moduleName) {
        super();
        setModuleName(moduleName);
    }
    
    /**
     * 初始化配置菜单解析器
     */
    public void initMenuInterpreter() {
        menuInterpreter = new MenuInterpreterImpl(getModuleName());
    }

    /**
     * 根据图片名获取Icon
     * @param iconName 图片名
     * @return Icon
     */
    public Icon getIcon(String iconName) {
        return ClientCoreContext.getResourceFactory().getIcon(getModuleName(), iconName);
    }

    /**
     * 根据图片名获取Image
     * @param imageName 图片名
     * @return Image
     */
    public Image getImage(String imageName) {
        return ClientCoreContext.getResourceFactory().getImage(getModuleName(), imageName);
    }

    /**
     * 根据图片名获取图片的URL
     * @param iconName 图片名
     * @return 图片的URL
     */
    public URL getIconUrl(String iconName) {
        return ClientCoreContext.getResourceFactory().getIconUrl(getModuleName(), iconName);
    }

    /**
     * 获取配置菜单解析器
     * @return 配置菜单解析器
     */
    public MenuInterpreter getMenuInterpreter() {
        return menuInterpreter;
    }

    /**
     * 客户端Module对Controller进行缓存的Map
     */
    private Map<Class, Object> map = new ConcurrentHashMap<Class, Object>();

    /**
     * 根据View的Class，Model的Class，Controller的Class获取Controller实例
     * @param <T>
     * @param <V>
     * @param <M>
     * @param <C>
     * @param modelClass Model的Class
     * @param viewClass View的Class
     * @param controllerClass Controller的Class
     * @return Controller的实例
     */
    public <T extends Object, V extends ClientView<T>, M extends ClientModel<T>, C extends ClientController<T, V, M>> C getController(
            Class<M> modelClass, Class<V> viewClass, Class<C> controllerClass) {
        Object object = map.get(controllerClass);
        if (object == null) {
            try {
                Constructor<C> constructor = controllerClass.getConstructor(viewClass.getClass(), modelClass.getClass());
                C newInstance = constructor.newInstance(viewClass, modelClass);
                map.put(controllerClass, newInstance);
                return newInstance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (C) object;
    }

    /**
     * 根据Controller的实例将Module中对Controller的进行清除
     * @param controller Controller的实例
     */
    public void removeCache(Object controller) {
        Set<Entry<Class, Object>> entrySet = map.entrySet();
        for (Entry<Class, Object> entry : entrySet) {
            Object value = entry.getValue();
            if(value.equals(controller)){
                map.remove(entry.getKey());
            }
        }
        Set<Entry<Class, Object>> entrySet2 = defaultControllerMap.entrySet();
        for (Entry<Class, Object> entry : entrySet2) {
            Object value = entry.getValue();
            if (value.equals(controller)) {
                defaultControllerMap.remove(entry.getKey());
            }
        }
    }
    
    /**
     * 对使用默认实现的Controller、Model进行构建的Controller进行缓存的Map
     */
    private Map<Class, Object> defaultControllerMap = new ConcurrentHashMap<Class, Object>();

    /**
     * 通过clientViewClass获取默认的Controller
     * 
     * @param <T>
     *            数据类型
     * @param <V>
     *            view类型
     * @param clientViewClass
     *            view的实际Class
     * @return 默认的Controller
     */
    public <T extends Object, V extends AbstractValidateView<T>> DefaultClientController<T, V, DefaultClientModel<T>> getDefaultClientController(
            Class<V> clientViewClass) {
        Object object = defaultControllerMap.get(clientViewClass);
        if (object == null) {
            DefaultClientController<T, V, DefaultClientModel<T>> defaultClientController = new DefaultClientController(
                    clientViewClass, DefaultClientModel.class);
            defaultControllerMap.put(clientViewClass, defaultClientController);
            return defaultClientController;
        }
        return (DefaultClientController<T, V, DefaultClientModel<T>>) object;
    }

    /**
     * 通过clientViewClass和clientModelClass获取默认的Controller
     * 
     * @param <T>
     *            数据类型
     * @param <V>
     *            view类型
     * @param clientViewClass
     *            view的实际Class
     * @param clientModelClass
     *            model的实际Class
     * @return 默认的Controller
     */
    public <T extends Object, V extends AbstractValidateView<T>, M extends AbstractClientModel<T>> DefaultClientController<T, V, M> getDefaultClientController(
            Class<V> clientViewClass, Class<M> clientModelClass) {
        Object object = defaultControllerMap.get(clientViewClass);
        if (object == null) {
            DefaultClientController<T, V, M> defaultClientController = new DefaultClientController<T, V, M>(
                    clientViewClass, clientModelClass);
            defaultControllerMap.put(clientViewClass, defaultClientController);
            return defaultClientController;
        }
        return (DefaultClientController<T, V, M>) object;
    }

    /**
     * 根据View的Class，Model的Class，Controller的Class获取Controller实例,支持创建多个Controller实例
     * @param <T>
     * @param <V>
     * @param <M>
     * @param <C>
     * @param modelClass Model的Class
     * @param viewClass View的Class
     * @param controllerClass Controller的Class
     * @param doubleCreate 是否创建多个Controller实例，是为<code>true</code>，否则为<code>false</false>
     * @return Controller的实例
     */
    public <T, V extends ClientView<T>, M extends ClientModel<T>, C extends ClientController<T, V, M>> C getController(
            Class<M> modelClass, Class<V> viewClass, Class<C> controllerClass,
            boolean doubleCreate) {
        if (doubleCreate) {
            try {
                Constructor<C> constructor = controllerClass.getConstructor(
                        viewClass.getClass(), modelClass.getClass());
                C newInstance = constructor.newInstance(viewClass, modelClass);
                return newInstance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Object object = map.get(controllerClass);
            if (object == null) {
                try {
                    Constructor<C> constructor = controllerClass.getConstructor(
                            viewClass.getClass(), modelClass.getClass());
                    C newInstance = constructor.newInstance(viewClass, modelClass);
                    map.put(controllerClass, newInstance);
                    return newInstance;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return (C) object;
        }
        return null;
    }
}
