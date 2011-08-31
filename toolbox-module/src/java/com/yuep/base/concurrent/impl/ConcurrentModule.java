/*
 * $Id: ConcurrentModule.java, 2011-3-7 上午10:28:10 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.impl;

import com.yuep.base.concurrent.def.collaboration.CollaborationService;
import com.yuep.base.concurrent.def.threadpool.ThreadPoolManager;
import com.yuep.base.concurrent.impl.collaboration.CollaborationServiceImpl;
import com.yuep.base.concurrent.impl.collaboration.producerconsumer.ProducerConsumerManager;
import com.yuep.base.concurrent.impl.threadpool.ThreadPoolManagerImpl;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;

/**
 * <p>
 * Title: ConcurrentModule
 * </p>
 * <p>
 * Description:并发处理模块，包含线程池ThreadPoolManager，任务间协调CollaborationService的服务
 * </p>
 * 
 * @author sufeng
 * created 2011-3-7 上午10:28:10
 * modified [who date description]
 * check [who date description]
 */
public class ConcurrentModule extends DefaultModule{

    private ThreadPoolManagerImpl threadPoolManager;
    private CollaborationServiceImpl collaborationService;
    
    @Override
    public void start() {
        threadPoolManager=new ThreadPoolManagerImpl();
        CoreContext.getInstance().setLocalService("threadPoolManager",ThreadPoolManager.class, threadPoolManager);
        
        ProducerConsumerManager pcManager=new ProducerConsumerManager();
        pcManager.setThreadPoolManager(threadPoolManager);
        collaborationService=new CollaborationServiceImpl(pcManager);
        CoreContext.getInstance().setLocalService("collaborationService",CollaborationService.class, collaborationService);
        
    }

    @Override
    public void stop() {
        threadPoolManager.destroy();
    }

}
