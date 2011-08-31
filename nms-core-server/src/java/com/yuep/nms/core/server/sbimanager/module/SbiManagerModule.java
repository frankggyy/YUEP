/*
 * $Id: SbiManagerModule.java, 2011-4-15 ÏÂÎç02:15:16 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbimanager.module;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.id.def.ObjectIDGenerateService;
import com.yuep.core.db.module.constants.DbModuleConstants;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.sbimanager.module.constants.SbiManagerModuleConstants;
import com.yuep.nms.core.common.sbimanager.service.SbiManager;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.module.constants.SubSystemProxyCoreDefinationModuleConstants;
import com.yuep.nms.core.server.nmsproxy.NmsServiceAdapterManager;
import com.yuep.nms.core.server.nmsproxy.module.constants.NmsProxyModuleConstants;
import com.yuep.nms.core.server.sbimanager.adapter.SbiManagerAdapter;
import com.yuep.nms.core.server.sbimanager.dao.impl.SbiBindPropertyDaoImpl;
import com.yuep.nms.core.server.sbimanager.dao.impl.SbiPropertyDaoImpl;
import com.yuep.nms.core.server.sbimanager.facade.SbiManagerFacade;
import com.yuep.nms.core.server.sbimanager.service.SbiManagerImpl;
import com.yuep.nms.core.server.subsystemproxymanager.module.constants.SubSystemProxyModuleConstants;
import com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager;

/**
 * <p>
 * Title: SbiManagerModule
 * </p>
 * <p>
 * Description:
 * SbiManagerÄ£¿é
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 ÏÂÎç02:15:16
 * modified [who date description]
 * check [who date description]
 */
public class SbiManagerModule extends DefaultModule {

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        buildSbiManager();
        buildSbiManagerFacade();
        registerSbiManagerAdapter();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
    }
    
    
    private void buildSbiManager(){
     
        SubSystemProxyManager subSystemProxyService=CoreContext.getInstance().local().getService(SubSystemProxyModuleConstants.SUBSYSTEMPROXY_SERVICE, SubSystemProxyManager.class);
        SessionFactoryManager sessionFactoryManager=CoreContext.getInstance().local().getService(DbModuleConstants.SESSIONFACTORYMANAGER_LOCAL_SERVICE, SessionFactoryManager.class);
        
        SbiManagerImpl sbiManager=new SbiManagerImpl();
        sbiManager.setSubSystemProxyService(subSystemProxyService);
        
        SessionFactory sessionFactory=sessionFactoryManager.getSessionFactory(SbiProperty.class);
        SbiPropertyDaoImpl sbiPropertyDao=new SbiPropertyDaoImpl(sessionFactory);
        sbiManager.setSbiPropertyDao(sbiPropertyDao);
        
        SbiBindPropertyDaoImpl sbiBindPropertyDao=new SbiBindPropertyDaoImpl(sessionFactory);
        sbiManager.setSbiBindPropertyDao(sbiBindPropertyDao);
        
        ObjectIDGenerateService objectIDGenerateService=CoreContext.getInstance().local().getService(DbModuleConstants.OBJECTIDGENERATE_LOCAL_SERVICE, ObjectIDGenerateService.class);
        sbiManager.setObjectIDGenerateService(objectIDGenerateService);
        
        sbiManager.init();
        
        CoreContext.getInstance().setLocalService(SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class, sbiManager);

    }
    
    private void buildSbiManagerFacade(){
        SbiManagerFacade sbiManagerFacade=new SbiManagerFacade();
        SubSystemProxyCore subSystemProxyCore=CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        sbiManagerFacade.setSubSystemProxyCore(subSystemProxyCore);
        CoreContext.getInstance().setRemoteService(SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class, sbiManagerFacade);
             
    }
    
    private void registerSbiManagerAdapter(){
        NmsServiceAdapterManager nmsServiceAdapterManager=CoreContext.getInstance().local().getService(NmsProxyModuleConstants.NMSADAPTERMANAGER_LOCAL_SERVICE, NmsServiceAdapterManager.class);
        nmsServiceAdapterManager.registerNmsServiceAdapter(SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManagerAdapter.class);
    }
    
    
    
 

}
