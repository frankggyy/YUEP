/*
 * $Id: ThreadPoolTest.java, 2010-11-5 下午01:47:47 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import junit.framework.TestCase;

import com.yuep.base.concurrent.def.threadpool.ThreadPoolManager;
import com.yuep.base.concurrent.impl.threadpool.ThreadPoolManagerImpl;

/**
 * <p>
 * Title: ThreadPoolTest
 * </p>
 * <p>
 * Description: 线程池test case
 * </p>
 * 
 * @author sufeng
 * created 2010-11-5 下午01:47:47
 * modified [who date description]
 * check [who date description]
 */
public class ThreadPoolTest extends TestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
     * 创建、获取、使用、删除thread pool
     */
    public void testAddThreadPool(){
        ThreadPoolManager threadPoolManager=new ThreadPoolManagerImpl();
        threadPoolManager.addThreadPool("test", 3);
        ExecutorService threadPool = threadPoolManager.getThreadPool("test");
        threadPool.submit(new Callable<Object>(){

            @Override
            public Object call() throws Exception {
                
                for(int i=0;i<10;i++){
                    System.out.println("execute call");
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e) {
                        break;
                    }
                }
                return null;
            }
            
        });
        threadPoolManager.removeThreadPool("test");
        
        //threadPool = threadPoolManager.getThreadPool("test");
        //assertNull(threadPool);
        
        
        System.out.println("thread pool testing finished.");
    }
    
}
