/*
 * $Id: SchedulerExecutorFactory.java, 2010-11-10 ����09:33:52 Administrator Exp $
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
 * Title: SchedulerExecutorFactory
 * </p>
 * <p>
 * Description:
 * <br>���ȹ����������ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 ����09:33:52
 * modified [who date description]
 * check [who date description]
 */
public interface SchedulerExecutorFactory {
    
    /**
     * ���������������
     * @return
     *        �������������
     */
    public SchedulerExecutor createSchedulerExecutor();


}
