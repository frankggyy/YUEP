/*
 * $Id: SyncJob.java, 2009-3-6 下午03:41:45 yangtao Exp $
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
import java.util.concurrent.Callable;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.exception.YuepException;
import com.yuep.base.log.def.Logger;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.synccore.model.SyncItem;
import com.yuep.nms.core.common.synccore.model.SyncResult;
import com.yuep.nms.core.common.synccore.model.SyncTask;
import com.yuep.nms.core.common.synccore.model.enums.Result;

/**
 * <p>
 * Title: SyncJob
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2009-3-6 下午03:41:45
 * modified [who date description]
 * check [who date description]
 */
public class SyncJob implements Callable<SyncResult> {

    private SyncItem syncItem = null;

    private SyncTask syncTask = null;

    private List<SyncListener> listeners = null;

    private Logger logger;
    
    private MoCore moCore;
    
    private CoreContext coreContext;

    public SyncJob(SyncTask syncTask, SyncItem syncItem, List<SyncListener> listeners) {
        if (syncItem == null)
            throw new NullPointerException();
        this.syncItem = syncItem;
        this.listeners = listeners;
        this.syncTask = syncTask;
    }

    
    public void setLogger(Logger logger){
        this.logger=logger;
    }
    
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
    }
    
    public void setCoreContext(CoreContext coreContext){
        this.coreContext=coreContext;
    }
    /**
     * 
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public SyncResult call() throws Exception {
        logger.info("start sync item " +moCore.getMo(syncItem.getTarget())+" "+syncItem.getSyncNode());
        SyncResult result = null;
        try {
            Sync sync = getSync(syncItem.getSyncAction());
            firedStartSyncItem(syncItem);
            sync.sync(syncItem.getTarget(), listeners);// 执行同步操作
            result = new SyncResult(syncItem, Result.success);
        } catch (Exception e) {
            result = new SyncResult(syncItem, Result.failure);

            // sufeng:YotcException与其他类别的Exception分开处理
            if (e instanceof YuepException){
                result.setFailException((YuepException) e);
            }else{
                if(e.getMessage()==null)
                    result.setFailCause(ExceptionUtils.getExFirstStackInfo(e));
                else
                    result.setFailCause(e.getClass().getSimpleName()+",message="+e.getMessage());
            }
            
            logger.error(syncItem.getSyncNode(), e);
        }
        if (result != null)
            firedEndSyncItem(result);
        logger.info("end sync item:" + moCore.getMo(syncItem.getTarget()).getDisplayName()+" "+syncItem.getSyncNode());
        return result;
    }

    /**
     * 
     * @return
     */
    public SyncItem getSyncItem() {
        return syncItem;
    }

   
    public SyncTask getSyncTask() {
        return syncTask;
    }
    
   
    /**
     * 获取同步对象
     * @param sync
     * @return
     */
    protected Sync getSync(String sync) {
       return coreContext.local().getService(sync, Sync.class);
    }

    /**
     * 开始同步任务项
     * 
     * @param syncItem
     */
    private void firedStartSyncItem(SyncItem syncItem) {
        if (listeners != null)
            for (SyncListener syncListener : listeners) {
                syncListener.startSyncItem(syncItem);
            }
    }

    /**
     * 结束同步任务项
     * 
     * @param result
     */
    private void firedEndSyncItem(SyncResult result) {
        if (listeners != null)
            for (SyncListener syncListener : listeners) {
                syncListener.endSyncItem(result);
            }
    }

}
