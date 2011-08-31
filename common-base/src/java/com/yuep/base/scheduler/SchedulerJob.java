/*
 * $Id: ScheduleJob.ja+va, 2009-2-18 ����09:40:46 yangtao Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.scheduler;

import org.quartz.Trigger;

/**
 * <p>
 * Title: ScheduleJob
 * </p>
 * <p>
 * Description: 
 * <br>��������ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2009-2-18 ����09:40:46
 * modified [who date description]
 * check [who date description]
 */
public interface SchedulerJob {

    /**
     * ��ʼ������������Դ
     */
    void init();

    /**
     * ���ٵ���������Դ
     */
    void destory();
    /**
     * ����ִ�з�����,���ݾ�����Ҫʵ��
     */
    void execute();

    /**
     * ��ȡ������ִ�е�Trigger
     * 
     * @return ����Trigger
     */
    Trigger getTrigger();

    /**
     * ��ȡ������������
     * 
     * @return
     *       ������������
     */
    String getJobName();

    /**
     * ��ȡ�������������������
     * 
     * @return
     *     �����������ڵ��������
     */
    String getJobGroupName();
    
    /**
     * ���������Ƿ������ͨ���÷���ʵ���ж����������ҵ���߼�
     * @return
     *     true ���������������رյ�ǰ��������
     *     false:�����������ִ��
     *      
     */
    boolean needFinished();
    
}
