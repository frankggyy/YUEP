/*
 * $Id: SchedulerExecutorTestCase.java, 2010-11-9 上午10:59:33 Administrator Exp $
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
import com.yuep.base.scheduler.SchedulerJob;

/**
 * <p>
 * Title: SchedulerExecutorTestCase
 * </p>
 * <p>
 * Description:
 * 默认任务调度器测<code>DefaultSchedulerExecutor<code>试用例
 * </p>
 * 
 * @author yangtao
 * created 2010-11-9 上午10:59:33
 * modified [who date description]
 * check [who date description]
 */
public class SchedulerExecutorTestCase extends TestCase {

    private SchedulerExecutor defaultSchedulerExecutor;
    private TestSchedulerJob testSchedulerJob;
    @Override
    public void setUp(){
        setUpDefaultSchedulerExecutor();
        setUpTestSchedulerJob();
    }
    
    @Override
    public void tearDown(){
        defaultSchedulerExecutor.shutdown();
    }
    
    
    private void setUpDefaultSchedulerExecutor(){
        SchedulerExecutorFactory schedulerExecutorFactory=new SchedulerExecutorFactoryImpl();
        defaultSchedulerExecutor=schedulerExecutorFactory.createSchedulerExecutor();
        defaultSchedulerExecutor.start();
    }
    
    private void setUpTestSchedulerJob(){
        testSchedulerJob=new TestSchedulerJob("testJob1","testJobGroup2",null,null,5);
    }
    
    
    public void testStartSchedulerJob(){
        defaultSchedulerExecutor.startSchedulerJob(testSchedulerJob);
        SchedulerJob schedulerJob=defaultSchedulerExecutor.getSchedulerJob(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName());
        assertNotNull(schedulerJob);
        defaultSchedulerExecutor.shutdownSchedulerJob(schedulerJob.getJobName(), schedulerJob.getJobGroupName());
     
    }
    
   public void testShutDownSchedulerJob(){
       defaultSchedulerExecutor.startSchedulerJob(testSchedulerJob);
       defaultSchedulerExecutor.shutdownSchedulerJob(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName());
       SchedulerJob schedulerJob=defaultSchedulerExecutor.getSchedulerJob(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName());
       assertNull(schedulerJob);
   }
   
   
   public void testPauseSchedulerJob(){
       defaultSchedulerExecutor.startSchedulerJob(testSchedulerJob);
       defaultSchedulerExecutor.pauseSchedulerJob(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName());
       assertTrue(defaultSchedulerExecutor.isSchedulerJobPaused(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName()));
       defaultSchedulerExecutor.resumeSchedulerJob(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName());
   }
   
   
   public void testResumeSchedulerJob(){
       defaultSchedulerExecutor.startSchedulerJob(testSchedulerJob);
       defaultSchedulerExecutor.pauseSchedulerJob(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName());
       defaultSchedulerExecutor.resumeSchedulerJob(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName());
       assertTrue(!defaultSchedulerExecutor.isSchedulerJobPaused(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName()));
   }
   
   public void testResetSchedulerJob(){
       defaultSchedulerExecutor.startSchedulerJob(testSchedulerJob);
       testSchedulerJob.setInterval(10);
       assertTrue(!defaultSchedulerExecutor.isSchedulerJobShutdown(testSchedulerJob.getJobName(), testSchedulerJob.getJobGroupName()));
   }
   
}
