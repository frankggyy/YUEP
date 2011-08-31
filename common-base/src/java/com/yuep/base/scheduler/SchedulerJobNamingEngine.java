/*
 * $Id: OneTimeSchedulerJob.java, 2009-3-3 ����12:49:41 yangtao Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.scheduler;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * <p>
 * Title: SchedulerJobNamingEngine
 * </p>
 * <p>
 * Description:
 * <br>�����������Ʋ����������ڲ������ظ��ĵ�����������
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 ����01:03:26
 * modified [who date description]
 * check [who date description]
 */
final public class SchedulerJobNamingEngine {

    private static final String DEFAULT_SCHEDULER_JOB = "DEFAULT_SCHEDULER_JOB";

    private static AtomicLong count = new AtomicLong(0);

    /**
     * ����һ��Ψһ�ĵ�����������
     * @return
     *      ������������
     */
    public static String createSchedulerJobName() {
        return DEFAULT_SCHEDULER_JOB + count.getAndIncrement();
    }

}
