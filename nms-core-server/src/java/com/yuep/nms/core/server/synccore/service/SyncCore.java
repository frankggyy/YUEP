/*
 * $Id: SyncCore.java, 2011-5-17 下午05:49:54 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.service;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.synccore.model.SyncTask;
import com.yuep.nms.core.server.synccore.execute.SyncListener;

/**
 * <p>
 * Title: SyncCore
 * </p>
 * <p>
 * Description:
 * 同步模块核心服务
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午05:49:54
 * modified [who date description]
 * check [who date description]
 */
public interface SyncCore {
    /**
     * 同步
     * @param target
     *        同步对象
     * @param
     *        同步任务
     */
    public SyncTask sync(MoNaming target);
    /**
     * 同步指定的同步节点
     * @param target
     *        同步对象
     * @param syncNodes
     *        同步节点名称
     * @return
     *        同步任务
     */
    public SyncTask sync(MoNaming target,String...syncNodes);
    /**
     * 关闭同步任务
     * @param target
     *        同步对象
     * @param syncTaskId
     *        同步任务Id
     */
    public void shutdown(MoNaming target,Long syncTaskId);
    
    /**
     * 添加同步监听器
     * 
     * @param syncListener
     *        同步进度监听接口
     */
    public void addSyncListener(SyncListener syncListener);

    /**
     * 移除同步监听器
     * 
     * @param syncListener
     *          同步进度监听接口
     */
    public void removeSyncListener(SyncListener syncListener);
    

}
