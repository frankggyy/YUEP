/*
 * $Id: ClientModule.java, 2010-9-27 ����10:14:32 aaron Exp $
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
 * Description:�ͻ���Module���ͻ����ϲ�ģ��(�н����ģ��)��Ҫ�̳�ClientModule
 * </p>
 * 
 * @author aaron
 * created 2010-9-27 ����10:14:32
 * modified [who date description]
 * check [who date description]
 */
public abstract class ClientModule extends DefaultModule{
    
    /**
     * ���ò˵�������
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
     * ��ʼ�����ò˵�������
     */
    public void initMenuInterpreter() {
        menuInterpreter = new MenuInterpreterImpl(getModuleName());
    }

    /**
     * ����ͼƬ����ȡIcon
     * @param iconName ͼƬ��
     * @return Icon
     */
    public Icon getIcon(String iconName) {
        return ClientCoreContext.getResourceFactory().getIcon(getModuleName(), iconName);
    }

    /**
     * ����ͼƬ����ȡImage
     * @param imageName ͼƬ��
     * @return Image
     */
    public Image getImage(String imageName) {
        return ClientCoreContext.getResourceFactory().getImage(getModuleName(), imageName);
    }

    /**
     * ����ͼƬ����ȡͼƬ��URL
     * @param iconName ͼƬ��
     * @return ͼƬ��URL
     */
    public URL getIconUrl(String iconName) {
        return ClientCoreContext.getResourceFactory().getIconUrl(getModuleName(), iconName);
    }

    /**
     * ��ȡ���ò˵�������
     * @return ���ò˵�������
     */
    public MenuInterpreter getMenuInterpreter() {
        return menuInterpreter;
    }

    /**
     * �ͻ���Module��Controller���л����Map
     */
    private Map<Class, Object> map = new ConcurrentHashMap<Class, Object>();

    /**
     * ����View��Class��Model��Class��Controller��Class��ȡControllerʵ��
     * @param <T>
     * @param <V>
     * @param <M>
     * @param <C>
     * @param modelClass Model��Class
     * @param viewClass View��Class
     * @param controllerClass Controller��Class
     * @return Controller��ʵ��
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
     * ����Controller��ʵ����Module�ж�Controller�Ľ������
     * @param controller Controller��ʵ��
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
     * ��ʹ��Ĭ��ʵ�ֵ�Controller��Model���й�����Controller���л����Map
     */
    private Map<Class, Object> defaultControllerMap = new ConcurrentHashMap<Class, Object>();

    /**
     * ͨ��clientViewClass��ȡĬ�ϵ�Controller
     * 
     * @param <T>
     *            ��������
     * @param <V>
     *            view����
     * @param clientViewClass
     *            view��ʵ��Class
     * @return Ĭ�ϵ�Controller
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
     * ͨ��clientViewClass��clientModelClass��ȡĬ�ϵ�Controller
     * 
     * @param <T>
     *            ��������
     * @param <V>
     *            view����
     * @param clientViewClass
     *            view��ʵ��Class
     * @param clientModelClass
     *            model��ʵ��Class
     * @return Ĭ�ϵ�Controller
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
     * ����View��Class��Model��Class��Controller��Class��ȡControllerʵ��,֧�ִ������Controllerʵ��
     * @param <T>
     * @param <V>
     * @param <M>
     * @param <C>
     * @param modelClass Model��Class
     * @param viewClass View��Class
     * @param controllerClass Controller��Class
     * @param doubleCreate �Ƿ񴴽����Controllerʵ������Ϊ<code>true</code>������Ϊ<code>false</false>
     * @return Controller��ʵ��
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
