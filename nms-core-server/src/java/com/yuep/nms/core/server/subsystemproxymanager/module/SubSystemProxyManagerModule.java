/*
 * $Id: SubSystemProxyManagerModule.java, 2011-4-26 下午03:58:06 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxymanager.module;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemBind;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.common.subsystemproxycore.module.constants.SubSystemProxyCoreDefinationModuleConstants;
import com.yuep.nms.core.server.subsystemproxymanager.module.constants.SubSystemProxyModuleConstants;
import com.yuep.nms.core.server.subsystemproxymanager.property.dao.impl.SubSystemBindDaoImpl;
import com.yuep.nms.core.server.subsystemproxymanager.property.dao.impl.SubSystemProxyPropertyDaoImpl;
import com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager;
import com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManagerImpl;

/**
 * <p>
 * Title: SubSystemProxyManagerModule
 * </p>
 * <p>
 * Description:
 * 子系统代理模块
 * </p>
 * 
 * @author yangtao
 * created 2011-4-26 下午03:58:06
 * modified [who date description]
 * check [who date description]
 */
public class SubSystemProxyManagerModule extends DefaultModule {

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        buildSubSystemProxyManager();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
    }
    
    /**
     * 构建SubSystemProxyManager服务
     */
    private void buildSubSystemProxyManager(){
        SubSystemProxyCore subSystemProxyCore=CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        SubSystemProxyManagerImpl subSystemProxyService=new SubSystemProxyManagerImpl();
        subSystemProxyService.setSubSystemProxyCore(subSystemProxyCore);
        
        SessionFactoryManager sessionFactoryManager=CoreContext.getInstance().local().getService("sessionFactoryManager", SessionFactoryManager.class);
        SubSystemBindDaoImpl subSystemBindDaoImpl=new SubSystemBindDaoImpl(sessionFactoryManager.getSessionFactory(SubSystemBind.class));
        subSystemProxyService.setSubSystemBindsDao(subSystemBindDaoImpl);
        
        SubSystemProxyPropertyDaoImpl subSystemProxyPropertyDaoImpl=new SubSystemProxyPropertyDaoImpl(sessionFactoryManager.getSessionFactory(SubSystemProxyProperty.class));
        subSystemProxyService.setSubSystemProxyPropertyDao(subSystemProxyPropertyDaoImpl);
        
        subSystemProxyService.setCoreContext(CoreContext.getInstance());
        
        subSystemProxyService.init();
        CoreContext.getInstance().setLocalService(SubSystemProxyModuleConstants.SUBSYSTEMPROXY_SERVICE, SubSystemProxyManager.class, subSystemProxyService);
    }

}
