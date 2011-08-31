/*
 * $Id: MoModule.java, 2011-3-29 下午02:02:28 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.module.constants.DbModuleConstants;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoStaticInfoManager;
import com.yuep.nms.core.common.mocore.service.MoStaticInfoManagerImpl;
import com.yuep.nms.core.server.mocore.dao.ManagedNodePropertyDao;
import com.yuep.nms.core.server.mocore.dao.MoDao;
import com.yuep.nms.core.server.mocore.dao.impl.ManagedNodePropertyDaoImpl;
import com.yuep.nms.core.server.mocore.dao.impl.MoDaoImpl;
import com.yuep.nms.core.server.mocore.service.ServerMoCore;

/**
 * <p>
 * Title: MoModule
 * </p>
 * <p>
 * Description:
 * Mo模块初始化、销毁
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 下午02:02:28
 * modified [who date description]
 * check [who date description]
 */
public class MoCoreModule extends DefaultModule {

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        /**
         * 初始化Mo静态信息服务
         */
        buildMoStaticInfoService(this.getModuleParams());
        /**
         * 初始化MoCore
         */
        buildMoCore();

    }

    
    private void buildMoStaticInfoService(Map<String,String> moduleParams){
        String mostaicInfoParam=moduleParams.get("mo.staticinfo");
        String[] mostaticInfoFiles=mostaicInfoParam.split(",");
        List<String> fullPathFiles=new ArrayList<String>();
        for(String mostaticInfoFile:mostaticInfoFiles){
            String temp=this.getModulePath()+mostaticInfoFile;
            fullPathFiles.add(temp);
        }
        MoStaticInfoManagerImpl moStaticInfoManager=new MoStaticInfoManagerImpl(fullPathFiles.toArray(new String[0]));
        moStaticInfoManager.init();
        CoreContext.getInstance().setLocalService(MoCoreModuleConstants.MOSTATICINFOMANAGER_SERVICE, MoStaticInfoManager.class, moStaticInfoManager);
    }
    
    
    private void buildMoCore(){
        ServerMoCore serverMoCore=new ServerMoCore();
        
        serverMoCore.setCoreContext(CoreContext.getInstance());
        
        SessionFactoryManager sessionFactoryManager=(SessionFactoryManager)CoreContext.getInstance().local().getService(DbModuleConstants.SESSIONFACTORYMANAGER_LOCAL_SERVICE, SessionFactoryManager.class);
        SessionFactory sessionFactory=sessionFactoryManager.getSessionFactory(Mo.class);
        sessionFactory.openSession();
        MoDao moDao=new MoDaoImpl(sessionFactory);
        serverMoCore.setMoDao(moDao);
        
        ManagedNodePropertyDao managedNodePropertyDao=new ManagedNodePropertyDaoImpl(sessionFactory);
        serverMoCore.setManagedNodePropertyDao(managedNodePropertyDao);

        serverMoCore.init();
        CoreContext.getInstance().setLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class, serverMoCore);
    }
    
    
   
    
    
    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
    }

}
