/*
 * $Id: DefaultModule.java, 2010-9-17 下午03:06:41 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.def.module;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.SimpleLogger;
import com.yuep.core.bootstrap.def.ContainerConst;
import com.yuep.core.bootstrap.def.ModuleContext;


/**
 * <p>
 * Title: DefaultModule
 * </p>
 * <p>
 * Description: 模块定义的缺省基类,上层网管必须继承该抽象类
 * </p>
 * 
 * @author aaron
 * created 2010-9-17 下午03:06:41
 * modified [who date description]
 * check [who date description]
 */
public abstract class DefaultModule implements Module,Module4Container{

    /**
     * 模块参数
     */
    protected Map<String, String> moduleParams = new ConcurrentHashMap<String, String>();
    
    /**
     * 模块状态
     */
    protected String moduleStatus = STATUS_NONE;

    /**
     * 模块标识名
     */
    protected String moduleName;
 
    /**
     * 模块的类加载器
     */
    protected ClassLoader classLoader;
    
    /**
     * 模块的日志
     */
    protected Logger logger;
    
    protected Logger simpleLogger=new SimpleLogger("simple");

    @Override
    public void setModuleName(String name) {
        this.moduleName = name;
    }

    /**
     * @see com.yuep.core.bootstrap.def.module.module.def.Module#getModuleParams()
     */
    @Override
    public Map<String, String> getModuleParams() {
        return moduleParams;
    }

    /**
     * @see com.yuep.core.bootstrap.def.module.module.def.Module#getModuleStatus()
     */
    @Override
    public String getModuleStatus() {
        return moduleStatus;
    }
    
    /**
     * @see com.yuep.core.bootstrap.def.module.module.def.Module#getModuleName()
     */
    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public void setModuleStatus(String moduleStatus) {
        this.moduleStatus=moduleStatus;
    }
    
    @Override
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    @Override
    public Logger getLogger() {
        return logger==null ? simpleLogger : logger;
    }
    
    @Override
    public void setLogger(Logger logger) {
        this.logger=logger;
    }
    
    @Override
    public String toString() {
        return getModuleName();
    }

    @Override
    public String getModulePath(){
    	String homeDir=ModuleContext.getInstance().getSystemParam(ContainerConst.KEY_HOME_DIR);
        String modulePath=homeDir+File.separator+"modules"+File.separator+moduleName+File.separator;
        return modulePath;
    }
    
}
