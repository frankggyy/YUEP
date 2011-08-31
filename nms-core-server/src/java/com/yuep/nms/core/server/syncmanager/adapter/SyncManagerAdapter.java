/*
 * $Id: SyncManagerAdapter.java, 2011-5-17 œ¬ŒÁ08:32:20 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.syncmanager.adapter;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingFactory;
import com.yuep.nms.core.common.synccore.model.SyncItem;
import com.yuep.nms.core.common.synccore.model.SyncTask;
import com.yuep.nms.core.common.syncmanager.service.SyncManager;
import com.yuep.nms.core.server.nmsproxy.NmsServiceAdapter;

/**
 * <p>
 * Title: SyncManagerAdapter
 * </p>
 * <p>
 * Description:
 * SyncManager  ≈‰∆˜
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 œ¬ŒÁ08:32:20
 * modified [who date description]
 * check [who date description]
 */
public class SyncManagerAdapter extends NmsServiceAdapter<SyncManager> implements SyncManager {

    public SyncManagerAdapter(MoNaming subSystemId,SyncManager syncManager){
        super(subSystemId,syncManager);
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.syncmanager.service.SyncManager#shutdown(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.Long)
     */
    @Override
    public void shutdown(MoNaming target,Long syncTaskId) {
        MoNaming remoteTarget=MoNamingFactory.toRemoteMoNaming(this.nm, target);
        nmsRemoteService.shutdown(remoteTarget, syncTaskId);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.syncmanager.service.SyncManager#sync(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SyncTask sync(MoNaming target) {
        MoNaming remoteTarget=MoNamingFactory.toRemoteMoNaming(this.nm, target);
        SyncTask syncTask=nmsRemoteService.sync(remoteTarget);
        syncTask.setTarget(MoNamingFactory.toLocalMoNaming(this.nm, syncTask.getTarget()));
        for(SyncItem syncItem:syncTask.getSyncItems()){
            syncItem.setTarget(MoNamingFactory.toLocalMoNaming(this.nm, syncItem.getTarget()));
        }
        return syncTask;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.syncmanager.service.SyncManager#sync(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String[])
     */
    @Override
    public SyncTask sync(MoNaming target, String... syncNodes) {
        MoNaming remoteTarget=MoNamingFactory.toRemoteMoNaming(this.nm, target);
        SyncTask syncTask=nmsRemoteService.sync(remoteTarget,syncNodes);
        syncTask.setTarget(MoNamingFactory.toLocalMoNaming(this.nm, syncTask.getTarget()));
        for(SyncItem syncItem:syncTask.getSyncItems()){
            syncItem.setTarget(MoNamingFactory.toLocalMoNaming(this.nm, syncItem.getTarget()));
        }
        return syncTask;
    }

}
