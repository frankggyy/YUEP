/*
 * $Id: MoManagerModule.java, 2011-3-29 ÏÂÎç02:53:18 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.momanager.module;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.id.def.ObjectIDGenerateService;
import com.yuep.core.db.module.constants.DbModuleConstants;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoStaticInfoManager;
import com.yuep.nms.core.common.momanager.module.constants.MoManagerModuleConstants;
import com.yuep.nms.core.common.momanager.service.MoManager;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.module.constants.SubSystemProxyCoreDefinationModuleConstants;
import com.yuep.nms.core.server.momanager.adapter.MoManagerAdapter;
import com.yuep.nms.core.server.momanager.adapter.MoMessageAdapter;
import com.yuep.nms.core.server.momanager.facade.MoManagerFacade;
import com.yuep.nms.core.server.momanager.service.MoManagerImpl;
import com.yuep.nms.core.server.nmsproxy.NmsServiceAdapterManager;
import com.yuep.nms.core.server.nmsproxy.module.constants.NmsProxyModuleConstants;

/**
 * <p>
 * Title: MoManagerModule
 * </p>
 * <p>
 * Description:
 * MoManagerÄ£¿é
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 ÏÂÎç02:53:18
 * modified [who date description]
 * check [who date description]
 */
public class MoManagerModule extends DefaultModule {

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        buildMoManager();
        buildMoManagerFacade();
        buildMoMessageAdapter();
        registerSbiManagerAdapter();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
    }
    
    
    private void buildMoManagerFacade(){
        MoManagerFacade moManagerFacade=new MoManagerFacade();
        MoCore moCore=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        moManagerFacade.setMoCore(moCore);
        
        SubSystemProxyCore subSystemProxyCore=CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        moManagerFacade.setSubSystemProxyCore(subSystemProxyCore);
        
        CoreContext.getInstance().setRemoteService(MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class, moManagerFacade);
    }

    
    private void buildMoManager(){
        MoManagerImpl moManager=new MoManagerImpl();
        
        MoCore moCore=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        moManager.setMoCore(moCore);
        
        MoStaticInfoManager moStaticInfoManager=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOSTATICINFOMANAGER_SERVICE, MoStaticInfoManager.class);
        moManager.setMoStaticInfoManager(moStaticInfoManager);
        
        ObjectIDGenerateService objectIDGenerateService=CoreContext.getInstance().local().getService(DbModuleConstants.OBJECTIDGENERATE_LOCAL_SERVICE, ObjectIDGenerateService.class);
        moManager.setObjectIDGenerateService(objectIDGenerateService);
        
        CoreContext.getInstance().setLocalService(MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class, moManager);
    }
    

    
    
    private void buildMoMessageAdapter(){
        MoCore moCore=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        SubSystemProxyCore subSystemProxyCore=CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        MoMessageAdapter moMessageAdapter=new MoMessageAdapter();
        moMessageAdapter.setCoreContext(CoreContext.getInstance());
        moMessageAdapter.setMoCore(moCore);
        moMessageAdapter.setSubSystemProxyCore(subSystemProxyCore);
        moMessageAdapter.init();
    }
    
    private void registerSbiManagerAdapter(){
        NmsServiceAdapterManager nmsServiceAdapterManager=CoreContext.getInstance().local().getService(NmsProxyModuleConstants.NMSADAPTERMANAGER_LOCAL_SERVICE, NmsServiceAdapterManager.class);
        nmsServiceAdapterManager.registerNmsServiceAdapter(MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManagerAdapter.class);
    }
    
    
}
