/*
 * $Id: ContainerSchedulerExecutor.java, 2011-2-12 下午02:50:06 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.container.impl;

import com.yuep.base.scheduler.SchedulerExecutor;
import com.yuep.base.scheduler.SchedulerExecutorFactoryImpl;

/**
 * <p>
 * Title: ContainerSchedulerExecutor
 * </p>
 * <p>
 * Description: 容器自己使用的调度执行器
 * </p>
 * 
 * @author sufeng
 * created 2011-2-12 下午02:50:06
 * modified [who date description]
 * check [who date description]
 */
public class ContainerSchedulerExecutor {

    private static SchedulerExecutor schedulerExecutor;
    static{
        SchedulerExecutorFactoryImpl factory=new SchedulerExecutorFactoryImpl();
        schedulerExecutor=factory.createSchedulerExecutor();
    }
    
    public static SchedulerExecutor getSchedulerExecutor(){
        return schedulerExecutor;
    }
}
