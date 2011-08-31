/*
 * $Id: CollaborationServiceImpl.java, 2010-7-1 上午11:17:49 jimsu Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.impl.collaboration;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.yuep.base.concurrent.def.collaboration.CollaborationService;
import com.yuep.base.concurrent.def.collaboration.producerconsumer.ProducerConsumer;
import com.yuep.base.concurrent.def.collaboration.syncwait.ResultHandler;
import com.yuep.base.concurrent.def.collaboration.syncwait.ResultStauts;
import com.yuep.base.concurrent.impl.collaboration.producerconsumer.ProducerConsumerManager;
import com.yuep.base.concurrent.impl.collaboration.syncwait.WaitForSingleObject;


/**
 * <p>
 * Title: CollaborationServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2010-7-1 上午11:17:49
 * modified [who date description]
 * check [who date description]
 */
public class CollaborationServiceImpl implements CollaborationService{

    /**
     * 生产者-消费者管理器
     */
    private ProducerConsumerManager producerConsumerManager;
    public CollaborationServiceImpl(ProducerConsumerManager producerConsumerManager) {
        this.producerConsumerManager = producerConsumerManager;
    }
    
    @Override
    public ProducerConsumer addProducerConsumer(String producerConsumerName, int queueCapacity, String threadPoolName) {
        return producerConsumerManager.addProducerConsumer(producerConsumerName, queueCapacity,threadPoolName);
    }

    @Override
    public ProducerConsumer addProducerConsumer(String producerConsumerName, int queueCapacity) {
        return producerConsumerManager.addProducerConsumer(producerConsumerName, queueCapacity);
    }

    @Override
    public void removeProducerConsumer(String producerConsumerName) {
        producerConsumerManager.removeProducerConsumer(producerConsumerName);
    }

    @Override
    public ResultStauts syncWait(ResultHandler resultHandler,long checkInterval,long timeoutMillSec) {
        WaitForSingleObject waitObject=new WaitForSingleObject(resultHandler);
        waitObject.setInterval(checkInterval);
        ResultStauts resStatus = waitObject.get(timeoutMillSec);
        return resStatus;
    }

    @Override
    public Object asyncWait(Future<Object> future, long timeoutMillSec) {
        try{
            return future.get(timeoutMillSec, TimeUnit.MILLISECONDS);
        }catch(Exception e){
            System.err.println(e);
        }
        return null;
    }
    
}
