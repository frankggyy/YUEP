/*
 * $Id: SyncManagerFacade.java, 2011-5-17 下午08:33:17 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.syncmanager.service;

import com.yuep.nms.core.common.mocore.module.constants.MoTypeConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.synccore.model.SyncTask;
import com.yuep.nms.core.common.syncmanager.module.constants.SyncManagerDefModuleConstants;
import com.yuep.nms.core.common.syncmanager.service.SyncManager;

/**
 * <p>
 * Title: SyncManagerFacade
 * </p>
 * <p>
 * Description:
 * SyncManager服务Facade实现，用于判断下级网管服务
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午08:33:17
 * modified [who date description]
 * check [who date description]
 */
public class SyncManagerFacade implements SyncManager {

    private SubSystemProxyCore subSystemProxyCore;
    
    public void setSubSystemProxyCore(SubSystemProxyCore subSystemProxyCore){
        this.subSystemProxyCore=subSystemProxyCore;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.syncmanager.service.SyncManager#shutdown(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.Long)
     */
    @Override
    public void shutdown(MoNaming target,Long syncTaskId) {
        MoNaming nm=target.getParentByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        SyncManager syncManager=subSystemProxy.getService(nm, SyncManagerDefModuleConstants.SYNCMANAGER_REMOTE_SERVICE, SyncManager.class);
        syncManager.shutdown(target, syncTaskId);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.syncmanager.service.SyncManager#sync(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SyncTask sync(MoNaming target) {
        MoNaming nm=target.getParentByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        SyncManager syncManager=subSystemProxy.getService(nm, SyncManagerDefModuleConstants.SYNCMANAGER_REMOTE_SERVICE, SyncManager.class);
        return syncManager.sync(target);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.syncmanager.service.SyncManager#sync(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String[])
     */
    @Override
    public SyncTask sync(MoNaming target, String... syncNodes) {
        MoNaming nm=target.getParentByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        SyncManager syncManager=subSystemProxy.getService(nm, SyncManagerDefModuleConstants.SYNCMANAGER_REMOTE_SERVICE, SyncManager.class);
        return syncManager.sync(target, syncNodes);
    }

}
