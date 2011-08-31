/*
 * $Id: SyncExecutor.java, 2009-4-14 上午08:58:35 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.execute;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.synccore.model.SyncTask;

/**
 * <p>
 * Title: SyncExecutor
 * </p>
 * <p>
 * Description: <br>
 * 同步任务执行接口
 * </p>
 * 
 * @author yangtao
 * created 2009-4-14 上午08:58:35
 * modified [who date description]
 * check [who date description]
 */
public interface SyncExecutor {

    /**
     * 关闭同步任务
     * 
     * @param target
     *        同步对象
     */
    public void shutdown(MoNaming target);

    /**
     * 执行同步任务
     * 
     * @param target
     *            同步任务所在的网元
     * @param syncTask
     *            同步任务
     */
    public void execute(MoNaming target, SyncTask syncTask);

    /**
     * 获取当前所有的同步任务
     * 
     * @return
     */
    public List<SyncTask> getAllSyncTasks();
    /**
     * 获取指定对象同步任务
     * @param target
     *          同步对象
     * @return
     */
    public List<SyncTask> getSyncTasks(MoNaming target);
    
    /**
     * 获取指定对象正在运行的同步任务
     * @param target
     *          同步对象
     * @return
     *          正在运行的同步任务
     */
    public SyncTask getRunningSyncTask(MoNaming target);
    /**
     * 同步任务是否已经存在
     * 
     * @param syncTask
     *        同步任务
     * @return
     *        true存在，false不存在
     */
    public boolean isExisted(SyncTask syncTask);

    /**
     * 网元target是否存在同步任务
     * 
     * @param target
     *        同步对象
     * @return
     */
    public boolean isRunningSyncTasks(MoNaming target);

    /**
     * 获取正在同步的对象
     * 
     * @return
     */
    public List<MoNaming> getAllSyncTargets();

    /**
     * 关闭同步任务
     * 
     * @param syncTask
     */
    public void shutdown(SyncTask syncTask);

    /**
     * 启动同步任务执行器
     */
    public void start();

    /**
     * 关闭同步任务执行器
     */
    public void close();

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