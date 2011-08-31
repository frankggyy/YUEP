/*
 * $Id: SessionFactoryManagerConfigurationTest.java, 2011-3-30 下午03:07:12 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.sessionfactory.configuration.test;

import java.net.URL;

import junit.framework.TestCase;

import org.apache.commons.collections.MapUtils;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.sessionfactory.configuration.SessionFactoryManagerConfiguration;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;
import com.yuep.core.db.test.object.TestDbObject;

/**
 * <p>
 * Title: SessionFactoryManagerConfigurationTest
 * </p>
 * <p>
 * Description:
 * SessionFactoryManagerConfiguration单元测试
 * </p>
 * 
 * @author yangtao
 * created 2011-3-30 下午03:07:12
 * modified [who date description]
 * check [who date description]
 */
public class SessionFactoryManagerConfigurationTest extends TestCase {
    
  
    public void testConfigure(){
        SessionFactoryManagerConfiguration sessionFactoryManagerConfiguration=new SessionFactoryManagerConfiguration();
        URL url=this.getClass().getResource("/com/yuep/core/db/sessionfactory/configuration/test-yuepdb.xml");
        sessionFactoryManagerConfiguration.configure(YuepObjectUtils.newArrayList(url.getPath()));
        assertTrue(MapUtils.isNotEmpty(sessionFactoryManagerConfiguration.getDataSources()));
        assertTrue(MapUtils.isNotEmpty(sessionFactoryManagerConfiguration.getHibernateProperties()));
        assertTrue(MapUtils.isNotEmpty(sessionFactoryManagerConfiguration.getMappingClasses()));
    }
    
    
    public void testBuildSesssionFactoryManager(){
        SessionFactoryManagerConfiguration sessionFactoryManagerConfiguration=new SessionFactoryManagerConfiguration();
        URL url=this.getClass().getResource("/com/yuep/core/db/sessionfactory/configuration/test-yuepdb.xml");
        sessionFactoryManagerConfiguration.configure(YuepObjectUtils.newArrayList(url.getPath()));
        SessionFactoryManager sessionFactoryManager=sessionFactoryManagerConfiguration.buildSessionFactoryManager();
        assertNotNull(sessionFactoryManager);
        
        SessionFactory sessionFactory=sessionFactoryManager.getSessionFactory("yueptest");
        assertNotNull(sessionFactory);
        
        sessionFactory=sessionFactoryManager.getSessionFactory(TestDbObject.class);
        assertNotNull(sessionFactory);
        
    }

}
