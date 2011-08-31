/*
 * $Id: DbBaseCase.java, 2011-4-1 上午10:37:46 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.test;

import junit.framework.TestCase;

import com.yuep.core.db.sessionfactory.def.SessionFactory;

/**
 * <p>
 * Title: DbBaseCase
 * </p>
 * <p>
 * Description:数据库这块的测试用例基类
 * </p>
 * 
 * @author yangtao
 * created 2011-4-1 上午10:37:46
 * modified [who date description]
 * check [who date description]
 */
 public class DbBaseCase extends TestCase {
    
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    
    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    
    public void testOut(){
        System.out.println("this is a base class of database test case");
    }

}
