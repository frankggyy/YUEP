/*
 * $Id: SyncManagerModule.java, 2011-5-17 下午08:07:38 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.syncmanager.module;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.module.constants.SubSystemProxyCoreDefinationModuleConstants;
import com.yuep.nms.core.common.syncmanager.module.constants.SyncManagerDefModuleConstants;
import com.yuep.nms.core.common.syncmanager.service.SyncManager;
import com.yuep.nms.core.server.synccore.module.constants.SyncCoreServerModuleContants;
import com.yuep.nms.core.server.synccore.service.SyncCore;
import com.yuep.nms.core.server.syncmanager.service.SyncManagerFacade;
import com.yuep.nms.core.server.syncmanager.service.SyncManagerImpl;

/**
 * <p>
 * Title: SyncManagerModule
 * </p>
 * <p>
 * Description:
 * SyncManager模块定义类
 * </p>
 * 
 * @author Administrator
 * created 2011-5-17 下午08:07:38
 * modified [who date description]
 * check [who date description]
 */
public class SyncManagerModule extends DefaultModule {
    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        buildSyncManager();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
    }

    private void buildSyncManager(){
        SyncManagerImpl syncManager=new SyncManagerImpl();
        
        SyncCore syncCore=CoreContext.getInstance().local().getService(SyncCoreServerModuleContants.SYNCCORE_LOCAL_SERVICE, SyncCore.class);
        syncManager.setSyncCore(syncCore);
        CoreContext.getInstance().setLocalService(SyncManagerDefModuleConstants.SYNCMANAGER_REMOTE_SERVICE, SyncManager.class, syncManager);
        
        SubSystemProxyCore subSystemProxyCore=CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        SyncManagerFacade syncManagerFacade=new SyncManagerFacade();
        syncManagerFacade.setSubSystemProxyCore(subSystemProxyCore);
        CoreContext.getInstance().setRemoteService(SyncManagerDefModuleConstants.SYNCMANAGER_REMOTE_SERVICE, SyncManager.class, syncManagerFacade);

    }
}
