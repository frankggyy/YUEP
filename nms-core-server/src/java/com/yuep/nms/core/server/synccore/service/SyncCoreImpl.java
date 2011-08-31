/*
 * $Id: SyncCoreImpl.java, 2011-5-17 下午05:51:33 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.service;

import java.util.ArrayList;
import java.util.List;

import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.synccore.model.SyncItem;
import com.yuep.nms.core.common.synccore.model.SyncNode;
import com.yuep.nms.core.common.synccore.model.SyncTask;
import com.yuep.nms.core.server.synccore.configuration.SyncConfigLoader;
import com.yuep.nms.core.server.synccore.execute.SyncExecutor;
import com.yuep.nms.core.server.synccore.execute.SyncListener;
import com.yuep.nms.core.server.synccore.execute.SyncTaskIDGenerate;

/**
 * <p>
 * Title: SyncCoreImpl
 * </p>
 * <p>
 * Description:
 * SyncCore接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午05:51:33
 * modified [who date description]
 * check [who date description]
 */
public class SyncCoreImpl implements SyncCore{
    
    private SyncExecutor syncExecutor;
    private SyncConfigLoader syncConfigLoader;
    private MoCore moCore;
    
    public void setSyncExecutor(SyncExecutor syncExecutor){
        this.syncExecutor=syncExecutor;
    }
    
    public void setSyncConfigLoader(SyncConfigLoader syncConfigLoader){
        this.syncConfigLoader=syncConfigLoader;
    }
    
    
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
    }
    
    
    public void init(){
        syncExecutor.start();
    }
    
    
    public void destory(){
        
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.service.SyncCore#shutdown(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.Long)
     */
    @Override
    public void shutdown(MoNaming target,Long syncTaskId) {
        
       
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.service.SyncCore#sync(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SyncTask sync(MoNaming target) {
        SyncTask syncTask=createSyncTask(target);
        syncExecutor.execute(target, syncTask);
        return syncTask;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.service.SyncCore#sync(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String[])
     */
    @Override
    public SyncTask sync(MoNaming target, String... syncNodes) {
        SyncTask syncTask=createSyncTask(target,syncNodes);
        syncExecutor.execute(target, syncTask);
        return syncTask;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.service.SyncCore#addSyncListener(com.yuep.nms.core.server.synccore.execute.SyncListener)
     */
    @Override
    public void addSyncListener(SyncListener syncListener) {
        syncExecutor.addSyncListener(syncListener);
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.service.SyncCore#removeSyncListener(com.yuep.nms.core.server.synccore.execute.SyncListener)
     */
    @Override
    public void removeSyncListener(SyncListener syncListener) {
        syncExecutor.removeSyncListener(syncListener);
    }

    
    
    private SyncTask createSyncTask(MoNaming target){
        SyncTask syncTask=new SyncTask();
        syncTask.setTaskId(SyncTaskIDGenerate.generate());
        syncTask.setTarget(target);
        
        Mo mo=moCore.getMo(target);
        List<SyncNode> syncNodes=syncConfigLoader.getSyncNodes(mo.getType());
        
        List<SyncItem> syncItems=new ArrayList<SyncItem>();
        syncTask.setSyncItems(syncItems);
        
        for(SyncNode syncNode:syncNodes){
            SyncItem syncItem=new SyncItem();
            syncItems.add(syncItem);
            
            syncItem.setTaskId(syncTask.getTaskId());
            syncItem.setTarget(target);
            syncItem.setRequested(syncNode.isRequested());
            syncItem.setSyncAction(syncNode.getSyncAction());
            syncItem.setSyncNode(syncNode.getName());
            syncItem.setSyncNodeType(syncNode.getNodeType());
        }
        
        return syncTask;
    }
    
    
    private SyncTask createSyncTask(MoNaming target, String... syncNodes){
        SyncTask syncTask=new SyncTask();
        syncTask.setTaskId(SyncTaskIDGenerate.generate());
        syncTask.setTarget(target);
        
        Mo mo=moCore.getMo(target);
        List<SyncNode> list=syncConfigLoader.getSyncNodesByNodeName(mo.getType(),syncNodes);
        
        List<SyncItem> syncItems=new ArrayList<SyncItem>();
        syncTask.setSyncItems(syncItems);
        
        for(SyncNode syncNode:list){
            SyncItem syncItem=new SyncItem();
            syncItems.add(syncItem);
            
            syncItem.setTaskId(syncTask.getTaskId());
            syncItem.setTarget(target);
            syncItem.setRequested(syncNode.isRequested());
            syncItem.setSyncAction(syncNode.getSyncAction());
            syncItem.setSyncNode(syncNode.getName());
            syncItem.setSyncNodeType(syncNode.getNodeType());
        }
        
        return syncTask;
    }
    
    
}
