/*
 * $Id: EverySchedulerJobTestCase.java, 2011-1-7 下午04:20:04 Owner Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.scheduler.test;

import java.util.Date;

import junit.framework.TestCase;

import com.yuep.base.scheduler.CommonSchedulerJob;
import com.yuep.base.scheduler.ImmediateSchedulerJob;
import com.yuep.base.scheduler.OneTimeSchedulerJob;
import com.yuep.base.scheduler.SchedulerExecutor;
import com.yuep.base.scheduler.SchedulerExecutorFactory;
import com.yuep.base.scheduler.SchedulerExecutorFactoryImpl;

/**
 * <p>
 * Title: EverySchedulerJobTestCase
 * </p>
 * <p>
 * Description: 
 * 每种类型调度任务单元测试
 * </p>
 * 
 * @author yangtao
 * created 2011-1-7 下午04:20:04
 * modified [who date description]
 * check [who date description]
 */
public class EverySchedulerJobTestCase extends TestCase {

    private SchedulerExecutor defaultSchedulerExecutor;

    @Override
    public void setUp() {
        SchedulerExecutorFactory schedulerExecutorFactory = new SchedulerExecutorFactoryImpl();
        defaultSchedulerExecutor = schedulerExecutorFactory.createSchedulerExecutor();
    }

    @Override
    public void tearDown() {

    }

    public void testExecuteOneTimeSchedulerJob() {
        TestOneTimeSchedulerJob testOneTimeSchedulerJob = new TestOneTimeSchedulerJob(new Date(System.currentTimeMillis()));
        defaultSchedulerExecutor.startSchedulerJob(testOneTimeSchedulerJob);

        synchronized (testOneTimeSchedulerJob) {
            while (!testOneTimeSchedulerJob.isFinished()) {
                try {
                    testOneTimeSchedulerJob.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        assertTrue(testOneTimeSchedulerJob.count() == 1);
    }
    
    
    public void testExecuteImmediateSchedulerJob(){
        TestImmediateSchedulerJob testImmediateSchedulerJob=new TestImmediateSchedulerJob(3,1000);
        defaultSchedulerExecutor.startSchedulerJob(testImmediateSchedulerJob);
        
        synchronized (testImmediateSchedulerJob) {
            while (!testImmediateSchedulerJob.isFinished()) {
                try {
                    testImmediateSchedulerJob.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        assertTrue(testImmediateSchedulerJob.count() == 3);
    }
    
    
    public void testExecuteCommonSchedulerJob(){
        //每分钟的第15秒
        String expression="15 * * * * ?";
        TestCommonSchedulerJob testCommonSchedulerJob=new TestCommonSchedulerJob(expression);
        defaultSchedulerExecutor.startSchedulerJob(testCommonSchedulerJob);
        synchronized (testCommonSchedulerJob) {
            while (!testCommonSchedulerJob.isFinished()) {
                try {
                    testCommonSchedulerJob.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        assertTrue(testCommonSchedulerJob.count() >=1);
    }
    
    
    public void testExecuteLongSchedulerJob(){
        TestLongSchedulerJob testLongSchedulerJob=new TestLongSchedulerJob(3,1000,1000*3);
        defaultSchedulerExecutor.startSchedulerJob(testLongSchedulerJob);
        synchronized (testLongSchedulerJob) {
            while (!defaultSchedulerExecutor.isSchedulerJobShutdown(testLongSchedulerJob.getJobName(), testLongSchedulerJob.getJobGroupName())) {
                try {
                    testLongSchedulerJob.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        assertTrue(testLongSchedulerJob.count() ==1);
    }
    
    
    private class TestLongSchedulerJob extends ImmediateSchedulerJob{

        private int count=0;
        private long sleeptime;
        public TestLongSchedulerJob(int repeatCount, long repeatInterval,long sleeptime) {
            super(repeatCount, repeatInterval);
            this.sleeptime=sleeptime;
        }

        @Override
        public void execute() {
          
            synchronized (this) {
                ++count;
                if(count==1){
                    try {
                        this.wait(sleeptime);
                    } catch (InterruptedException e) {
                       
                    }
                    defaultSchedulerExecutor.shutdownSchedulerJob(this.getJobName(), this.getJobGroupName());
                    this.notifyAll();
                }
            }
        }
        
        public int count(){
            return count;
        }
        
    }
    
    
    private static class TestCommonSchedulerJob extends CommonSchedulerJob{

        private int count;
        private boolean finish;
        
        public TestCommonSchedulerJob(String cronTrigger) {
            super(cronTrigger);
        }

        @Override
        public void execute() {
            count++;
            synchronized (this) {
                if(count==1){
                    finish=true;
                    this.notifyAll();
                }
              }
        }
        
        public int count(){
            return count;
        }
        
        public boolean isFinished(){
            return finish;
        }
        
       
        
    }
    
    
    private static class TestImmediateSchedulerJob extends ImmediateSchedulerJob{
        
        private int reapeatCount;
        private int count=0;
        private volatile boolean finish=false;
        public TestImmediateSchedulerJob(int repeatCount, long repeatInterval) {
            super(repeatCount, repeatInterval);
            this.reapeatCount=repeatCount;
        }

        @Override
        public void execute() {
              ++count;
              synchronized (this) {
                if(count==reapeatCount){
                    finish=true;
                    this.notifyAll();
                }
              }
            
        }
        
        
        public boolean isFinished(){
            return finish;
        }
        
        
        public int count(){
            return count;
        }
        
    }
    

    private static class TestOneTimeSchedulerJob extends OneTimeSchedulerJob {

        private int count=0;
        private volatile boolean finish = false;

        public TestOneTimeSchedulerJob(Date startTime) {
            super(startTime);
        }

        @Override
        public void execute() {
            synchronized (this) {
                ++count;
                finish = true;
                this.notifyAll();

            }
        }

        public int count() {
            return count;
        }

        public boolean isFinished() {
            return finish;
        }
    }

}
