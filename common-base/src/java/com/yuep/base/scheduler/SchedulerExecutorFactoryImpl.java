/*
 * $Id: SchedulerExecutorFactoryImpl.java, 2010-11-10 上午09:37:31 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.scheduler;



/**
 * <p>
 * Title: SchedulerExecutorFactoryImpl
 * </p>
 * <p>
 * Description:
 * <br>调度管理器工厂接口<code>SchedulerExecutorFactory<code>实现类
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 上午09:37:31
 * modified [who date description]
 * check [who date description]
 */
public class SchedulerExecutorFactoryImpl implements SchedulerExecutorFactory {
    
    @Override
    public SchedulerExecutor createSchedulerExecutor() {
        SchedulerExecutor schedulerExecutor= new DefaultSchedulerExecutor();
        schedulerExecutor.start();
        return schedulerExecutor;
    }


}
