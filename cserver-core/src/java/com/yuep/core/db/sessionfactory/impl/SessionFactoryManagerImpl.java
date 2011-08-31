/*
 * $Id: SessionFactoryManagerImpl.java, 2011-3-21 上午10:14:33 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.sessionfactory.impl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.persistence.Entity;

import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.util.CollectionUtils;

import com.yuep.base.exception.YuepException;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;

/**
 * <p>
 * Title: SessionFactoryManagerImpl
 * </p>
 * <p>
 * Description: SessionFactoryManager接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 上午10:14:33
 * modified [who date description]
 * check [who date description]
 */
public class SessionFactoryManagerImpl implements SessionFactoryManager {

    private Map<String, SessionFactory> sessionFactorys = new HashMap<String, SessionFactory>();
    private Map<String, Properties> dataSources = new HashMap<String, Properties>();
    private Map<String, Properties> hibernateProperties = new HashMap<String, Properties>();
    private Map<String, List<String>> mappingClasses = new HashMap<String, List<String>>();
    private ClassLoader classLoader;

    public SessionFactoryManagerImpl(Map<String, Properties> dataSources,Map<String, Properties> hibernateProperties,Map<String, List<String>> mappingClasses,ClassLoader classLoader) {
        this.dataSources = dataSources;
        this.hibernateProperties = hibernateProperties;
        this.mappingClasses = mappingClasses;
        this.classLoader=classLoader;
        if(this.classLoader==null)
            this.classLoader=Thread.currentThread().getContextClassLoader();
        init();
    }

    
    public SessionFactoryManagerImpl(Map<String, Properties> dataSources,Map<String, Properties> hibernateProperties,Map<String, List<String>> mappingClasses){
        this(dataSources,hibernateProperties,mappingClasses,Thread.currentThread().getContextClassLoader());
    }
    /**
     * @see com.yuep.core.db.sessionfactory.def.SessionFactoryManager#getSessionFactory(java.lang.String)
     */
    @Override
    public SessionFactory getSessionFactory(String dbName) {
        return sessionFactorys.get(dbName);
    }

    /**
     * 
     * @see com.yuep.core.db.sessionfactory.def.SessionFactoryManager#openSession(java.lang.Class)
     */
    @Override
    public Session openSession(Class<?> boClass) {
        SessionFactory sessionFactory = null;
        for (Entry<String, List<String>> entry : mappingClasses.entrySet()) {
            if (entry.getValue().contains(boClass.getName()))
                sessionFactory = sessionFactorys.get(entry.getKey());
        }
        if (sessionFactory != null) {
            return sessionFactory.openSession();
        }
        throw new YuepException("object class is not mapping");
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see com.yuep.core.db.sessionfactory.def.SessionFactoryManager#getSessionFactory(java.lang.Class)
     */
    @Override
    public SessionFactory getSessionFactory(Class<?> boClass) {
        for (Entry<String, List<String>> entry : mappingClasses.entrySet()) {
            if (entry.getValue().contains(boClass.getName()))
                return sessionFactorys.get(entry.getKey());
        }
        return null;
    }

    /**
     * 从xml加载bo类的配置信息，并建立在session factory的关联
     */
    private void init() {
        if (CollectionUtils.isEmpty(dataSources))
            return;

        for (Entry<String, Properties> entry : dataSources.entrySet()) {
            Properties configurationProperties = new Properties();
            Properties dataSourceProperties = entry.getValue();
            configurationProperties.putAll(dataSourceProperties);

            Properties hbProerties = hibernateProperties.get(entry.getKey());
            if (hbProerties != null) {
                configurationProperties.putAll(hbProerties);
            }

            AnnotationConfiguration configuration = new AnnotationConfiguration();

            // 加载需要被映射的类
            List<String> mappedClasses = mappingClasses.get(entry.getKey());
            loadMappedClasses(mappedClasses, configuration, classLoader);

            // 设置SessionFactory运行属性
            configuration.setProperties(configurationProperties);

            // 构建SessionFactory
            SessionFactory sessionFactory = buildSessionFactory(configuration,classLoader);
            sessionFactorys.put(entry.getKey(), sessionFactory);
        }
    }

    private SessionFactory buildSessionFactory(AnnotationConfiguration configuration, ClassLoader classLoader) {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader); // core模块的类加载器,它能访问到所有类
            SessionFactory sessionFactory = new SessionFactoryImpl(configuration.buildSessionFactory());
            return sessionFactory;
        } catch (Exception ex) {
            throw new YuepException(ex);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    private void loadMappedClasses(List<String> mappedClasses,AnnotationConfiguration configuration, ClassLoader classLoader) {
        if (mappedClasses == null)
            return;
        for (String className : mappedClasses) {
            try {
                Class<?> persistentClass = classLoader.loadClass(className);
                if(isEntityClass(persistentClass))
                    configuration.addAnnotatedClass(persistentClass);
            } catch (ClassNotFoundException ex) {
                throw new YuepException(ex);
            }
        }
    }
    
    /**
     * 判断是否为Entity类，这样可以过滤掉一些多余的类
     * @param persistentClass
     * @return
     */
    private boolean isEntityClass(Class<?> persistentClass){
        Annotation[] anns = persistentClass.getAnnotations();
        if(anns==null || anns.length==0)
            return false;
        for(int i=0;i<anns.length;i++){
            Class<? extends Annotation> annotationType = anns[i].annotationType();
            if(annotationType.equals(Entity.class)){
                return true;
            }
        }
        return false;
    }

}
