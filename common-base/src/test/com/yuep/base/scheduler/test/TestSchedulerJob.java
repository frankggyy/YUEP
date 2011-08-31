/*
 * $Id: TestSchedulerJob.java, 2010-11-9 обнГ04:14:03 Administrator Exp $
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

import com.yuep.base.scheduler.FixedIntervalSchedulerJob;

/**
 * <p>
 * Title: TestSchedulerJob
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2010-11-9 обнГ04:14:03
 * modified [who date description]
 * check [who date description]
 */
public class TestSchedulerJob extends FixedIntervalSchedulerJob {

    public TestSchedulerJob(String jobName, String groupName, Date starttime, Date endtime, int interval) {
        super(jobName, groupName, starttime, endtime, interval);
    }

    /**
     * @see com.yuep.base.scheduler.SchedulerJob#execute()
     */
    @Override
    public void execute() {
         System.out.println("running job");
    }

}
