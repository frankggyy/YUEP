/*
 * $Id: SyncManagerImpl.java, 2011-5-17 下午08:06:14 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.syncmanager.service;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.synccore.model.SyncTask;
import com.yuep.nms.core.common.syncmanager.service.SyncManager;
import com.yuep.nms.core.server.synccore.service.SyncCore;

/**
 * <p>
 * Title: SyncManagerImpl
 * </p>
 * <p>
 * Description:
 * SyncManager接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午08:06:14
 * modified [who date description]
 * check [who date description]
 */
public class SyncManagerImpl implements SyncManager {

    private SyncCore syncCore;

    public void setSyncCore(SyncCore syncCore){
        this.syncCore=syncCore;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.syncmanager.service.SyncManager#shutdown(java.lang.Long)
     */
    @Override
    public void shutdown(MoNaming target,Long syncTaskId) {
        syncCore.shutdown(target,syncTaskId);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.syncmanager.service.SyncManager#sync(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SyncTask sync(MoNaming target) {
        return syncCore.sync(target);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.syncmanager.service.SyncManager#sync(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String[])
     */
    @Override
    public SyncTask sync(MoNaming target, String... syncNodes) {
        return syncCore.sync(target, syncNodes);
    }

}
