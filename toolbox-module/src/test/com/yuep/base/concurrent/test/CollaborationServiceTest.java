/*
 * $Id: CollaborationServiceTest.java, 2010-11-5 下午02:16:40 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.test;

import java.util.Collection;

import junit.framework.TestCase;

import com.yuep.base.concurrent.def.collaboration.CollaborationService;
import com.yuep.base.concurrent.def.collaboration.producerconsumer.ConsumerListener;
import com.yuep.base.concurrent.def.collaboration.producerconsumer.ProducerConsumer;
import com.yuep.base.concurrent.def.collaboration.syncwait.ResultHandler;
import com.yuep.base.concurrent.def.collaboration.syncwait.ResultStauts;
import com.yuep.base.concurrent.def.threadpool.ThreadPoolManager;
import com.yuep.base.concurrent.impl.collaboration.CollaborationServiceImpl;
import com.yuep.base.concurrent.impl.collaboration.producerconsumer.ProducerConsumerManager;
import com.yuep.base.concurrent.impl.threadpool.ThreadPoolManagerImpl;
import com.yuep.base.util.sys.SysUtils;

/**
 * <p>
 * Title: CollaborationServiceTest
 * </p>
 * <p>
 * Description: 协作服务test case
 * </p>
 * 
 * @author sufeng
 * created 2010-11-5 下午02:16:40
 * modified [who date description]
 * check [who date description]
 */
public class CollaborationServiceTest extends TestCase {

    static int count=0;
    
    /**
     * 消费者-生成者模式
     */
    public void testProducerConsumer(){
        ProducerConsumerManager producerConsumerManager=new ProducerConsumerManager();
        ThreadPoolManager poolMgr=new ThreadPoolManagerImpl();
        producerConsumerManager.setThreadPoolManager(poolMgr);
        
        CollaborationService collaborationService=new CollaborationServiceImpl(producerConsumerManager);
        ProducerConsumer pc = collaborationService.addProducerConsumer("pro", 100);
        pc.addConsumerListener(new ConsumerListener(){
            @Override
            public void handle(Collection<Object> objsInQueue) {
                if(objsInQueue==null || objsInQueue.size()==0)
                    return;
                for(Object obj : objsInQueue){
                    count++;
                    System.out.println("receive:"+obj);
                }
            }
        });
        for(int i=0;i<10;i++){
            pc.produce(i);
        }

        SysUtils.sleepNotException(1000);
        assertEquals(10, count);
    }
    
    /**
     * 同步等待
     */
    public void testSyncWait(){
        CollaborationService collaborationService=new CollaborationServiceImpl(null);
        long t1=System.currentTimeMillis();
        ResultStauts s=collaborationService.syncWait(new ResultHandler(){

            @Override
            public ResultStauts getSingleResult() {
                // 执行获取结果的操作,这里可能是从数据库查询，也可能下配置到设备
                ResultStauts status=new ResultStauts(true);
                status.setSuccessed(false);
                return status;
            }

            @Override
            public void postData(Object data) {
                // do nothing
            }
            
        }, 500, 2000);
        long t2=System.currentTimeMillis();
        long cost=t2-t1;
        System.out.println("cost "+cost+"ms");
        assertEquals(true, cost<2000);
        assertEquals(true, s.isFinished());
        assertEquals(false, s.isSuccessed());
    }
    
}
