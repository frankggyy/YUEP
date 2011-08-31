/*
 * $Id: SyncTaskIDGenerate.java, 2009-3-6 下午05:18:07 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.execute;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * Title: SyncTaskIDGenerate
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2009-3-6 下午05:18:07
 * modified [who date description]
 * check [who date description]
 */
abstract public class SyncTaskIDGenerate {

    private static AtomicLong count = new AtomicLong(0);

    /**
     * 调用该方法获取同步任务Id
     * @return
     */
    public static Long generate() {
        return count.incrementAndGet();
    }

}
