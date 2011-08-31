/*
 * $Id: SessionFactoryBuilder.java, 2011-4-1 上午11:57:16 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.test;

import java.net.URL;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.sessionfactory.configuration.SessionFactoryManagerConfiguration;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;

/**
 * <p>
 * Title: SessionFactoryBuilder
 * </p>
 * <p>
 * Description: 创建SessionFactory
 * </p>
 * 
 * @author yangtao
 * created 2011-4-1 上午11:57:16
 * modified [who date description]
 * check [who date description]
 */
public class SessionFactoryBuilder {

    public  SessionFactory build(){
        SessionFactoryManagerConfiguration sessionFactoryManagerConfiguration=new SessionFactoryManagerConfiguration();
        URL url=this.getClass().getResource("/com/yuep/core/db/sessionfactory/configuration/test-yuepdb.xml");
        sessionFactoryManagerConfiguration.configure(YuepObjectUtils.newArrayList(url.getPath()));
        SessionFactoryManager sessionFactoryManager=sessionFactoryManagerConfiguration.buildSessionFactoryManager();
        SessionFactory sessionFactory=sessionFactoryManager.getSessionFactory("yueptest");
        return sessionFactory;
    }
    
    
    public SessionFactory build(URL url,String dbName){
        SessionFactoryManagerConfiguration sessionFactoryManagerConfiguration=new SessionFactoryManagerConfiguration();
        sessionFactoryManagerConfiguration.configure(YuepObjectUtils.newArrayList(url.getPath()));
        SessionFactoryManager sessionFactoryManager=sessionFactoryManagerConfiguration.buildSessionFactoryManager();
        SessionFactory sessionFactory=sessionFactoryManager.getSessionFactory("yueptest");
        return sessionFactory;
    }
}
