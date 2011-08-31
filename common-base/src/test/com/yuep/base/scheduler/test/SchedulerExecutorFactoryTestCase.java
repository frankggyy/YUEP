/*
 * $Id: SchedulerExecutorFactoryTestCase.java, 2011-1-7 ÏÂÎç04:11:04 Owner Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.scheduler.test;

import junit.framework.TestCase;

import com.yuep.base.scheduler.SchedulerExecutor;
import com.yuep.base.scheduler.SchedulerExecutorFactory;
import com.yuep.base.scheduler.SchedulerExecutorFactoryImpl;

/**
 * <p>
 * Title: SchedulerExecutorFactoryTestCase
 * </p>
 * <p>
 * Description:
 * ²âÊÔSchedulerExecutorFactoryImpl
 * </p>
 * 
 * @author yangtao
 * created 2011-1-7 ÏÂÎç04:11:04
 * modified [who date description]
 * check [who date description]
 */
public class SchedulerExecutorFactoryTestCase extends TestCase {
    
    private SchedulerExecutorFactory schedulerExecutorFactory;
    @Override
    public void setUp(){
        schedulerExecutorFactory=new SchedulerExecutorFactoryImpl();
    }
    
    @Override
    public void tearDown(){
        
    }
    
    
    public void testCreateSchedulerExecutor(){
        SchedulerExecutor schedulerExecutor=schedulerExecutorFactory.createSchedulerExecutor();
        assertNotNull(schedulerExecutor);    
        assertTrue(schedulerExecutor.isStarted());
    }
    
    


}
