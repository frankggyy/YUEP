/*
 * $Id: SessionFactoryConfiguration.java, 2011-3-24 上午09:04:19 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.sessionfactory.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.util.CollectionUtils;

import com.yuep.base.resource.ClassPathFileSearcher;
import com.yuep.base.resource.ClassPathFileSearcherImpl;
import com.yuep.base.resource.FileLoader;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;
import com.yuep.core.db.sessionfactory.impl.SessionFactoryManagerImpl;

/**
 * <p>
 * Title: SessionFactoryConfiguration
 * </p>
 * <p>
 * Description:
 * SessionFactory配置
 * </p>
 * 
 * @author yangtao
 * created 2011-3-24 上午09:04:19
 * modified [who date description]
 * check [who date description]
 */
public class SessionFactoryManagerConfiguration {
    /**数据源配置*/
    private Map<String,Properties> dataSources=new HashMap<String,Properties>();
    /**Hibernate配置*/
    private Map<String,Properties> hibernateProperties=new HashMap<String,Properties>();
    /**映射配置*/
    private Map<String,List<String>> mappingClasses=new HashMap<String,List<String>>();
    
    private ClassLoader classLoader;
    public SessionFactoryManagerConfiguration(){
        
    }
    
    public Map<String, Properties> getDataSources() {
        return dataSources;
    }
    public void setDataSources(Map<String, Properties> dataSources) {
        this.dataSources = dataSources;
    }
    public Map<String, Properties> getHibernateProperties() {
        return hibernateProperties;
    }
    public void setHibernateProperties(Map<String, Properties> hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }
    public Map<String, List<String>> getMappingClasses() {
        return mappingClasses;
    }
    public void setMappingClasses(Map<String, List<String>> mappingClasses) {
        this.mappingClasses = mappingClasses;
    }
    
    public void setClassLoader(ClassLoader classLoader){
        this.classLoader=classLoader;
    }
    /**
     * 加载数据库配置文件
     * 
     */
    public void configure(List<String> dbConfigFiles){
        if(CollectionUtils.isEmpty(dbConfigFiles))
            return;
        for(String dbConfigFile:dbConfigFiles)
            configure(dbConfigFile);
    }
    
    @SuppressWarnings("unchecked")
    private void configure(String dbConfigFile){
        // 用sax读取解析xml
        InputStream inputStream=FileLoader.getInputStream(dbConfigFile);
        
        Document doc = null;
        try {
            SAXBuilder builder = new SAXBuilder(false);
            doc = builder.build(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            try {
                inputStream.close();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
        }
        
        Element rootElement = doc.getRootElement();
        if(!ObjectUtils.equals("datasources", rootElement.getName()))
            return;
        
        List children = rootElement.getChildren();
        if (CollectionUtils.isEmpty(children))
            return;
        
        ClassPathFileSearcher search=new ClassPathFileSearcherImpl();
        for (Object object : children) {
            if (!(object instanceof Element)) 
                continue;
            Element dataSourceElement=(Element)object;
            if(!ObjectUtils.equals(dataSourceElement.getName(),"datasource"))
                continue;
            
            String dataSourceName=dataSourceElement.getAttributeValue("name");
            
            dataSources.put( dataSourceName, new Properties());
            hibernateProperties.put( dataSourceName, new Properties());
            mappingClasses.put( dataSourceName, new ArrayList<String>());
            
            for(Object child:dataSourceElement.getChildren()){
                Element element=(Element)child;
                if(ObjectUtils.equals(element.getName(),"properties")){
                    for (Object obj : element.getChildren()) {
                        if (!(obj instanceof Element)) 
                            continue;
                        Element propertyElement=(Element)obj;
                        if(!ObjectUtils.equals(propertyElement.getName(),"property"))
                            continue;
                        String propertyName=propertyElement.getAttributeValue("name");
                        String propertyValue=propertyElement.getAttributeValue("value");
                        dataSources.get(dataSourceName).put(propertyName,propertyValue);
                    }
                    
                }else if(ObjectUtils.equals(element.getName(),"mappingclasses")){
                    for (Object obj : element.getChildren()) {
                        if (!(obj instanceof Element)) 
                            continue;
                        Element mappingClassElement=(Element)obj;
                        if(!ObjectUtils.equals(mappingClassElement.getName(),"mappingclass"))
                            continue;
                        List<String> classes = mappingClasses.get(dataSourceName);
                        String classname=mappingClassElement.getAttributeValue("classname");
                        if(StringUtils.isNotBlank(classname))
                            classes.add(classname);
                        // 如果是包名,则需要search一下
                        String packagename=mappingClassElement.getAttributeValue("packagename");
                        if(StringUtils.isNotBlank(packagename)){
                            String pattern=packagename.replace(".", "/")+"/**/*.class";
                            List<String> searchClass = search.searchClass(pattern,classLoader);
                            if(searchClass!=null){
                                classes.addAll(searchClass);
                            }
                        }
                    }  
                }else if(ObjectUtils.equals(element.getName(),"hibernateProperties")){
                    for (Object obj : element.getChildren()) {
                        if (!(obj instanceof Element)) 
                            continue;
                        Element propertyElement=(Element)obj;
                        if(!ObjectUtils.equals(propertyElement.getName(),"property"))
                            continue;
                        String propertyName=propertyElement.getAttributeValue("key");
                        String propertyValue=propertyElement.getText();
                        hibernateProperties.get(dataSourceName).put(propertyName,propertyValue);
                    }
                }
            }
        
        }
    }
    /**
     * 构建SessionFactoryManager
     * @return
     *      SessionFactoryManager
     */
    public SessionFactoryManager buildSessionFactoryManager(){
        SessionFactoryManager sessionFactoryManager=new SessionFactoryManagerImpl(dataSources,hibernateProperties,mappingClasses,classLoader);
        return sessionFactoryManager;
    }
    
}
