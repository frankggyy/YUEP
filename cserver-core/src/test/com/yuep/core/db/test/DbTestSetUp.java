/*
 * $Id: DbModuleTestSetUp.java, 2011-4-1 上午10:20:27 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.test;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.yuep.core.db.sessionfactory.def.SessionFactory;

/**
 * <p>
 * Title: DbModuleTestSetUp
 * </p>
 * <p>
 * Description:
 * 数据库模块测试类
 * </p>
 * 
 * @author yangtao
 * created 2011-4-1 上午10:20:27
 * modified [who date description]
 * check [who date description]
 */
public class DbTestSetUp extends TestSetup {

    private SessionFactory sessionFactory;
    
    public DbTestSetUp(Test test) {
        super(test);
    }

    /**
     * 
     * (non-Javadoc)
     * @see junit.extensions.TestSetup#setUp()
     */
    @Override
    protected void setUp() throws Exception{
        super.setUp();
        sessionFactory=new SessionFactoryBuilder().build();
        setSessionFactory(this.getTest());
    }

    private  void setSessionFactory(Test test){
        if(test instanceof TestSuite){
            TestSuite testSuite=(TestSuite)test;
            for(int i=0;i<testSuite.testCount();i++){
                Test subTest=testSuite.testAt(i);
                if(subTest instanceof DbBaseCase){
                    DbBaseCase dbModuleTestCase=(DbBaseCase)subTest;
                    dbModuleTestCase.setSessionFactory(sessionFactory);
                }else if(subTest instanceof TestSuite){
                    setSessionFactory(subTest);
                }
            }
        }
    }
    
    /**
     * 
     * (non-Javadoc)
     * @see junit.extensions.TestSetup#tearDown()
     */
    @Override
    protected void tearDown()throws Exception{
        super.tearDown();
        sessionFactory.close();
    }
}
