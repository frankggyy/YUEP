/*
 * $Id: ImmediateSchedulerJob.java, 2009-2-18 ����02:56:31 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Communications Industry Group Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.scheduler;

import org.quartz.Trigger;
import org.quartz.TriggerUtils;

/**
 * <p>
 * Title: ImmediateSchedulerJob
 * </p>
 * <p>
 * Description: ���ȹ�����������һ������ִ�й̶�����������;
 * </p>
 * 
 * @author yangtao
 * created 2009-2-18 ����02:56:31
 * modified [who date description]
 * check [who date description]
 */
public abstract class ImmediateSchedulerJob extends AbstractSchedulerJob {
    /** ��ѯ���� */
    private int repeatCount;
    /** ��ѯ��� ��λ���� */
    private long repeatInterval;

    /**
     * 
     * @param repearCount
     *            ����ִ�д���
     * @param repeatInterval
     *            ����ִ������,��λ����
     */
    public ImmediateSchedulerJob(int repeatCount, long repeatInterval) {
        super();
        this.repeatCount = repeatCount;
        this.repeatInterval = repeatInterval;

    }
  
    /**
     * 
     * @param jobName
     *         ��������
     * @param groupName
     *         �������ڵ��������
     * @param repeatCount
     *         �����ظ�ִ�д���
     * @param repeatInterval
     *         ����ִ������,��λ����
     */
    public ImmediateSchedulerJob(String jobName,String groupName,int repeatCount, long repeatInterval) {
        super(jobName,groupName);
        this.repeatCount = repeatCount;
        this.repeatInterval = repeatInterval;

    }
    /**
     * 
     * @see com.yuep.base.scheduler.SchedulerJob#getTrigger()
     */
    @Override
    public Trigger getTrigger() {
        Trigger trigger = TriggerUtils.makeImmediateTrigger(repeatCount - 1,// ������������������ִ��һ��(���ظ������Ϲ���͹��ڿ��ܲ�һ��)��������Ҫ��1
                repeatInterval);
        trigger.setName(this.getJobName());
        trigger.setJobName(this.getJobName());
        trigger.setJobGroup(this.getJobGroupName());
        return trigger;
    }

}