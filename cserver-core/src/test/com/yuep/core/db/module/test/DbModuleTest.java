/*
 * $Id: DbModuleTestSuite.java, 2011-4-1 ÉÏÎç10:33:28 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.module.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.yuep.core.db.access.hibernate.template.test.HibernateTemplateTest;
import com.yuep.core.db.access.jdbc.template.test.JDBCTemplateTest;
import com.yuep.core.db.session.test.SessionTest;
import com.yuep.core.db.test.DbTestSetUp;

/**
 * <p>
 * Title: DbModuleTestSuite
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2011-4-1 ÉÏÎç10:33:28
 * modified [who date description]
 * check [who date description]
 */
public class DbModuleTest extends TestCase {
    
    public static Test suite() {
        TestSuite suite = new TestSuite("DB Module Test");
        suite.addTestSuite(SessionTest.class);
        suite.addTestSuite(HibernateTemplateTest.class);
        suite.addTestSuite(JDBCTemplateTest.class);
        return new DbTestSetUp(suite);
   }

}
