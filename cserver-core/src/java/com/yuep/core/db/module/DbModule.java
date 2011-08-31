/*
 * $Id: DbModule.java, 2010-9-20 下午03:20:37 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.exception.YuepException;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.bootstrap.def.module.Module;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.id.def.ObjectID;
import com.yuep.core.db.id.def.ObjectIDGenerateService;
import com.yuep.core.db.id.impl.ObjectIDDaoImpl;
import com.yuep.core.db.id.impl.ObjectIDGenerateServiceImpl;
import com.yuep.core.db.module.constants.DbModuleConstants;
import com.yuep.core.db.server.manager.def.DbServerManager;
import com.yuep.core.db.sessionfactory.configuration.SessionFactoryManagerConfiguration;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;

/**
 * <p>
 * Title: DbModule
 * </p>
 * <p>
 * Description: 数据管理模块，包括数据库服务器管理、数据访问层
 * </p>
 * 
 * @author sufeng
 * created 2010-9-20 下午03:20:37
 * modified [who date description]
 * check [who date description]
 */
public class DbModule extends DefaultModule{
    
    private DbServerManager dbServerManager;
    
    @Override
    public void start() {
        //初始化DbServerManager
        buildDbServerManager(getModuleParams());
        //初始化SessionFactory
        buildSessionFactorys(getModuleParams());
        //初始化对ObjectIDGenerateService
        buildObjectIDGenerateService();
    }

    @Override
    public void stop() {
        if(dbServerManager!=null){
            try{
                dbServerManager.close();
            }catch(Exception ex){
                ModuleContext.getInstance().getCoreModule().getLogger().info(ExceptionUtils.getCommonExceptionInfo(ex));
            }
        }
    }
    
    private void buildObjectIDGenerateService(){
        SessionFactoryManager sessionFactoryManager=(SessionFactoryManager)CoreContext.getInstance().local().getService("sessionFactoryManager", SessionFactoryManager.class);
        ObjectIDDaoImpl objectIDDao=new ObjectIDDaoImpl(sessionFactoryManager.getSessionFactory(ObjectID.class));
        objectIDDao.init();
        
        ObjectIDGenerateServiceImpl objectIDGenerateService=new ObjectIDGenerateServiceImpl();
        objectIDGenerateService.setObjectIDDao(objectIDDao);
        
        CoreContext.getInstance().setLocalService(DbModuleConstants.OBJECTIDGENERATE_LOCAL_SERVICE, ObjectIDGenerateService.class, objectIDGenerateService);
    }
    
    /**
     * 初始化DBServerManager
     * @param moduleParams
     */
    private void buildDbServerManager(Map<String,String> moduleParams){
        String dbServerManagerClassName=moduleParams.get("db.server.manager");
        try {
            Class<?> dbServerManagerClass=this.getClass().getClassLoader().loadClass(dbServerManagerClassName);
            dbServerManager=(DbServerManager)dbServerManagerClass.newInstance();
            dbServerManager.start(moduleParams);
            CoreContext.getInstance().setLocalService(DbModuleConstants.DBSERVERMANAGER_LOCAL_SERVICE, DbServerManager.class, dbServerManager);
        } catch (Exception e) {
            throw new YuepException(e);
        }
      
    }
    
    /**
     * 构建SessionFactory
     * @param moduleParams
     *        模块运行参数
     */
    private void buildSessionFactorys(Map<String,String> moduleParams){
        List<String> dbConfigFiles=YuepObjectUtils.newArrayList(moduleParams.get("db.datasources").split(","));
        SessionFactoryManagerConfiguration sessionFactoryManagerConfiguration=new SessionFactoryManagerConfiguration();
        
        //利用CoreModule的ClassLoader去加载需要持久化的类
        Module coreModule=ModuleContext.getInstance().getCoreModule();
        sessionFactoryManagerConfiguration.setClassLoader(coreModule.getClass().getClassLoader());
        
        sessionFactoryManagerConfiguration.configure(getFullConfigFilePath(dbConfigFiles));
        SessionFactoryManager sessionFactoryManager=sessionFactoryManagerConfiguration.buildSessionFactoryManager();
        CoreContext.getInstance().setLocalService(DbModuleConstants.SESSIONFACTORYMANAGER_LOCAL_SERVICE, SessionFactoryManager.class, sessionFactoryManager);
    }

    private List<String> getFullConfigFilePath(List<String> configFiles){
        List<String> fullConfigFiles=new ArrayList<String>();
        for(String configFile:configFiles){
            String fullConfigFile=this.getModulePath()+configFile;
            fullConfigFiles.add(fullConfigFile);
        }
        return fullConfigFiles;
    }
    
    
}
