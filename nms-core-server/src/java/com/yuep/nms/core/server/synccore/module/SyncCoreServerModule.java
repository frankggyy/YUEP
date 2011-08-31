/*
 * $Id: SyncModule.java, 2011-5-17 下午05:50:38 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yuep.base.log.def.Logger;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoStaticInfoManager;
import com.yuep.nms.core.server.synccore.configuration.SyncConfigLoaderImpl;
import com.yuep.nms.core.server.synccore.execute.SyncExecutorImpl;
import com.yuep.nms.core.server.synccore.module.constants.SyncCoreServerModuleContants;
import com.yuep.nms.core.server.synccore.service.SyncCore;
import com.yuep.nms.core.server.synccore.service.SyncCoreImpl;
import com.yuep.nms.core.server.synccore.service.SyncMessageListener;

/**
 * <p>
 * Title: SyncModule
 * </p>
 * <p>
 * Description:
 * 同步模块定义
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午05:50:38
 * modified [who date description]
 * check [who date description]
 */
public class SyncCoreServerModule extends DefaultModule {

    private Logger logger;
    private SyncConfigLoaderImpl syncConfigLoader;
    public SyncCoreServerModule(){
        super();
        
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        logger=this.getLogger();
        
        buildSyncConfig(getModuleParams());
        buildSyncCore();
        
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
        
    }
    
    
    private void buildSyncConfig(Map<String,String> moduleParams){
        String syncConfigParam=moduleParams.get("configLocations");
        if(StringUtils.isEmpty(syncConfigParam))
              return;
        String[] syncConfigFiles=syncConfigParam.split(",");
        List<String> fullPathFiles=new ArrayList<String>();
        for(String syncConfigFile:syncConfigFiles){
            String temp=this.getModulePath()+syncConfigFile;
            fullPathFiles.add(temp);
        }
        syncConfigLoader=new SyncConfigLoaderImpl(fullPathFiles.toArray(new String[0]));
        MoStaticInfoManager moStaticInfoManager=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOSTATICINFOMANAGER_SERVICE, MoStaticInfoManager.class);
        syncConfigLoader.setMoStaticInfoManager(moStaticInfoManager);
        syncConfigLoader.init();

    }
    
    private void buildSyncCore(){
        SyncCoreImpl syncCore=new SyncCoreImpl();
        
        MoCore moCore=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        syncCore.setMoCore(moCore);
        
        syncCore.setSyncConfigLoader(syncConfigLoader);
        
        SyncExecutorImpl syncExecutor=new SyncExecutorImpl(5,10);
        syncExecutor.setMoCore(moCore);
        syncExecutor.setLogger(logger);
        syncCore.setSyncExecutor(syncExecutor);
        syncExecutor.start();
        
        SyncMessageListener syncMessageListener=new SyncMessageListener();
        syncMessageListener.setCoreContext(CoreContext.getInstance());
        syncCore.addSyncListener(syncMessageListener);
        
        CoreContext.getInstance().setLocalService(SyncCoreServerModuleContants.SYNCCORE_LOCAL_SERVICE, SyncCore.class, syncCore);
        
    }

}
