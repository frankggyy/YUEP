/*
 * $Id: NmsManagerModule.java, 2011-5-24 上午09:47:45 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.nmsmanager.module;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.momanager.module.constants.MoManagerModuleConstants;
import com.yuep.nms.core.common.momanager.service.MoManager;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.module.constants.SubSystemProxyCoreDefinationModuleConstants;
import com.yuep.nms.core.server.nmsmanager.NmsManager;
import com.yuep.nms.core.server.nmsmanager.sync.NmsMoSync;
import com.yuep.nms.core.server.subsystemproxymanager.module.constants.SubSystemProxyModuleConstants;
import com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager;
import com.yuep.nms.core.server.synccore.execute.Sync;

/**
 * <p>
 * Title: NmsManagerModule
 * </p>
 * <p>
 * Description:
 * Nms管理模块定义
 * </p>
 * 
 * @author yangtao
 * created 2011-5-24 上午09:47:45
 * modified [who date description]
 * check [who date description]
 */
public class NmsManagerModule extends DefaultModule {

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        buildNmsManager();
        buildNmsMoSync();
        createRootNmsMo();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
    }

    private void buildNmsManager(){
        MoCore moCore=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        SubSystemProxyManager subSystemProxyService=(SubSystemProxyManager)CoreContext.getInstance().local().getService(SubSystemProxyModuleConstants.SUBSYSTEMPROXY_SERVICE, SubSystemProxyManager.class);
        NmsManager  nmsManager=new NmsManager();
        nmsManager.setCoreContext(CoreContext.getInstance());
        nmsManager.setMoCore(moCore);
        nmsManager.setSubSystemProxyService(subSystemProxyService);
        nmsManager.init();
    }
    
    private void buildNmsMoSync(){
        SubSystemProxyCore subSystemProxyCore=CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        MoCore moCore=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        NmsMoSync nmsMoSync=new NmsMoSync();
        nmsMoSync.setSubSystemProxyCore(subSystemProxyCore);
        nmsMoSync.setMoCore(moCore);
        CoreContext.getInstance().setLocalService("nmsMoSync", Sync.class, nmsMoSync);
    }
    
    private void createRootNmsMo(){
        MoCore moCore=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        if(moCore.getRootMo()!=null)
            return;
        MoManager moManager=CoreContext.getInstance().local().getService(MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        ManagedNodeProperty managedNodeProperty=new ManagedNodeProperty();
        managedNodeProperty.setIp("localhost");    
        moManager.createManagedNode(null, managedNodeProperty, "nm");
        Mo mo=moCore.getMo(managedNodeProperty.getManagedNode());
        mo.setDisplayName("root");
        moCore.updateMos(YuepObjectUtils.newArrayList(mo));
    }
}
