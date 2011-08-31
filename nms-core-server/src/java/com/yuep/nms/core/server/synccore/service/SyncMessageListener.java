/*
 * $Id: SyncMessageListener.java, 2011-5-19 上午06:17:19 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.service;

import java.io.Serializable;

import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.synccore.message.SyncMessage;
import com.yuep.nms.core.common.synccore.model.SyncItem;
import com.yuep.nms.core.common.synccore.model.SyncResult;
import com.yuep.nms.core.common.synccore.model.SyncTask;
import com.yuep.nms.core.server.synccore.execute.SyncListener;

/**
 * <p>
 * Title: SyncMessageListener
 * </p>
 * <p>
 * Description:
 * 同步消息监听,用于实现消息发送
 * </p>
 * 
 * @author yangtao
 * created 2011-5-19 上午06:17:19
 * modified [who date description]
 * check [who date description]
 */
public class SyncMessageListener implements SyncListener {

    private CoreContext coreContext;
    
    public void setCoreContext(CoreContext coreContext){
        this.coreContext=coreContext;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.execute.SyncListener#endSyncItem(com.yuep.nms.core.common.synccore.model.SyncResult)
     */
    @Override
    public void endSyncItem(SyncResult syncResult) {
        SyncMessage syncMessage=createSyncMessage(SyncMessage.END_SYNC_ITAEM,syncResult);
        publishSyncMessage(syncMessage);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.execute.SyncListener#endSyncTask(com.yuep.nms.core.common.synccore.model.SyncTask)
     */
    @Override
    public void endSyncTask(SyncTask syncTask) {
        SyncMessage syncMessage=createSyncMessage(SyncMessage.END_SYNC,syncTask);
        publishSyncMessage(syncMessage);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.execute.SyncListener#shutdownSyncTask(com.yuep.nms.core.common.synccore.model.SyncTask)
     */
    @Override
    public void shutdownSyncTask(SyncTask syncTask) {
        SyncMessage syncMessage=createSyncMessage(SyncMessage.SHUTDOWN_SYNC,syncTask);
        publishSyncMessage(syncMessage);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.execute.SyncListener#startSyncItem(com.yuep.nms.core.common.synccore.model.SyncItem)
     */
    @Override
    public void startSyncItem(SyncItem syncItem) {
        SyncMessage syncMessage=createSyncMessage(SyncMessage.START_SYNC_ITEM,syncItem);
        publishSyncMessage(syncMessage);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.execute.SyncListener#startSyncTask(com.yuep.nms.core.common.synccore.model.SyncTask)
     */
    @Override
    public void startSyncTask(SyncTask syncTask) {
        SyncMessage syncMessage=createSyncMessage(SyncMessage.START_SYNC,syncTask);
        publishSyncMessage(syncMessage);
    }
    
    
    private SyncMessage createSyncMessage(String messageType, Serializable messageSource){
        SyncMessage syncMessage=new SyncMessage(SyncMessage.SYNC_MESSAGE,messageType,messageSource);
        return syncMessage;
    }
    
    
    private void publishSyncMessage(SyncMessage syncMessage){
        coreContext.publish(SyncMessage.SYNC_MESSAGE, syncMessage);
    }

}
