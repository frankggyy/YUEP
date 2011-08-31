/*
 * $Id: NmsProxyModule.java, 2011-4-14 下午05:38:58 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.nmsproxy.module;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.mocore.module.constants.MoTypeConstants;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.module.constants.SubSystemProxyCoreDefinationModuleConstants;
import com.yuep.nms.core.server.nmsproxy.NmsProxyRequestIntercepter;
import com.yuep.nms.core.server.nmsproxy.NmsServiceAdapterManager;
import com.yuep.nms.core.server.nmsproxy.NmsServiceAdapterManagerImpl;
import com.yuep.nms.core.server.nmsproxy.module.constants.NmsProxyModuleConstants;

/**
 * <p>
 * Title: NmsProxyModule
 * </p>
 * <p>
 * Description:
 * NMSProxy模块定义
 * </p>
 * 
 * @author yangtao
 * created 2011-4-14 下午05:38:58
 * modified [who date description]
 * check [who date description]
 */
public class NmsProxyModule extends DefaultModule {

    private NmsServiceAdapterManager nmsServiceAdapterManager;
    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        buildNmsServiceAdapterManager();
        registerNmsProxyRequestIntercepter();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
    }
    
    private void buildNmsServiceAdapterManager(){
        nmsServiceAdapterManager=new NmsServiceAdapterManagerImpl();
        CoreContext.getInstance().setLocalService(NmsProxyModuleConstants.NMSADAPTERMANAGER_LOCAL_SERVICE, NmsServiceAdapterManager.class, nmsServiceAdapterManager);
    }
    
    
    private void registerNmsProxyRequestIntercepter(){
        NmsProxyRequestIntercepter nmsProxyRequestIntercepter=new NmsProxyRequestIntercepter();
        nmsProxyRequestIntercepter.setNmsServiceAdapterManager(nmsServiceAdapterManager);
       
        SubSystemProxyCore subSystemProxyCore=CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        subSystemProxyCore.registerSubSystemRequestIntecepter(MoTypeConstants.NM, nmsProxyRequestIntercepter);
    }
    
    
}
