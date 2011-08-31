/*
 * $Id: SyncManager.java, 2011-5-17 下午05:45:07 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.syncmanager.service;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.synccore.model.SyncTask;

/**
 * <p>
 * Title: SyncManager
 * </p>
 * <p>
 * Description:
 * 同步管理接口
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午05:45:07
 * modified [who date description]
 * check [who date description]
 */
public interface SyncManager {
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
    
    
    
}
