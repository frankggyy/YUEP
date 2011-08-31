/*
 * $Id: SessionFactoryTest.java, 2011-3-30 œ¬ŒÁ04:09:23 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.sessionfactory.test;

import java.net.URL;

import junit.framework.TestCase;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.configuration.SessionFactoryManagerConfiguration;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;

/**
 * <p>
 * Title: SessionFactoryTest
 * </p>
 * <p>
 * Description:
 * SessionFactory≤‚ ‘¿‡
 * </p>
 * 
 * @author yangtao
 * created 2011-3-30 œ¬ŒÁ04:09:23
 * modified [who date description]
 * check [who date description]
 */
public class SessionFactoryTest extends TestCase {
    
    private SessionFactory sessionFactory;
    
    @Override
    public void setUp(){
        SessionFactoryManagerConfiguration sessionFactoryManagerConfiguration=new SessionFactoryManagerConfiguration();
        URL url=this.getClass().getResource("/com/yuep/core/db/sessionfactory/configuration/test-yuepdb.xml");
        sessionFactoryManagerConfiguration.configure(YuepObjectUtils.newArrayList(url.getPath()));
        SessionFactoryManager sessionFactoryManager=sessionFactoryManagerConfiguration.buildSessionFactoryManager();
        sessionFactory=sessionFactoryManager.getSessionFactory("yueptest");
    }
    
    @Override
    public void tearDown(){
        if(sessionFactory!=null&&!sessionFactory.isClosed())
            sessionFactory.close();
    }
    
    
    public void testOpenSession(){
       Session session=sessionFactory.openSession();
       assertNotNull(session);
       if(session!=null)
           session.close();
    }
    
    
    public void testClose(){
        sessionFactory.close();
        assertTrue(sessionFactory.isClosed());
    }

}
