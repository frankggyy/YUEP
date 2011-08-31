/*
 * $Id: InsidePoolName.java, 2010-7-1 上午10:43:51 jimsu Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.impl.threadpool;

/**
 * <p>
 * Title: InsidePoolName
 * </p>
 * <p>
 * Description: 缺省的几个线程池名称
 * </p>
 * 
 * @author sufeng
 * created 2010-7-1 上午10:43:51
 * modified [who date description]
 * check [who date description]
 */
public class InsidePoolName {

    /**
     * 缺省pool,一般临时用一下就释放
     */
    public static final String POOL_NAME_DEFAULT="default";
    
    /**
     * 定期轮询任务
     */
    public static final String POOL_NAME_POLLTASK="polltask";
    
    /**
     * 批量处理的pool
     */
    public static final String POOL_NAME_BATCH="batch";
    
}
