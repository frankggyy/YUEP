/*
 * $Id: SyncListener.java, 2009-3-6 下午03:25:40 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.execute;

import com.yuep.nms.core.common.synccore.model.SyncItem;
import com.yuep.nms.core.common.synccore.model.SyncResult;
import com.yuep.nms.core.common.synccore.model.SyncTask;

/**
 * <p>
 * Title: SyncListener
 * </p>
 * <p>
 * Description:
 * 同步任务监听器
 * </p>
 * 
 * @author yangtao
 * created 2009-3-6 下午03:25:40
 * modified [who date description]
 * check [who date description]
 */
public interface SyncListener {

    /**
     * 开始同步任务
     * 
     * @param syncTask
     */
    public void startSyncTask(SyncTask syncTask);

    /**
     * 结束同步任务
     * 
     * @param syncTask
     */
    public void endSyncTask(SyncTask syncTask);

    /**
     * 开始执行同步项
     * 
     * @param syncItem
     */
    public void startSyncItem(SyncItem syncItem);

    /**
     * 结束执行同步项
     * 
     * @param syncResult
     */
    public void endSyncItem(SyncResult syncResult);
    
    /**
     * 关闭同步任务
     * @param syncTask
     */
    public void shutdownSyncTask(SyncTask syncTask);

}
