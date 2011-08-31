/*
 * $Id: ModuleContextMock.java, 2011-5-9 下午05:36:38 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.message.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.base.log.def.Logger;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.Module;
import com.yuep.core.bootstrap.def.module.ModulePriority;

/**
 * <p>
 * Title: ModuleContextMock
 * </p>
 * <p>
 * Description:模拟一个ModuleContext来方便测试
 * </p>
 * 
 * @author sufeng
 * created 2011-7-26 下午05:36:38
 * modified [who date description]
 * check [who date description]
 */
public class ModuleContextMock extends ModuleContext{

    @Override
    public void cleanup() {
        
    }

    @Override
    public Module getCoreModule() {
        return coreModule;
    }

    private Module coreModule;
    private Map<Class<? extends Module>,Module> modules=new ConcurrentHashMap<Class<? extends Module>, Module>();
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Module> T getModule(Class<T> moduleClass) {
        return (T)modules.get(moduleClass);
    }

    public void putModule(Class<? extends Module> moduleClass,Module module) {
        modules.put(moduleClass, module);
    }
    
    @Override
    public ModulePriority getModulePriority() {
        return null;
    }

    @Override
    public Map<Class<? extends Module>, Module> getModules() {
        return modules;
    }

    private Map<String,String> params=new ConcurrentHashMap<String, String>();
    
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    
    public void putParam(String key,String value){
        this.params.put(key, value);
    }
    
    @Override
    public String getSystemParam(String configKey) {
        return params.get(configKey);
    }

    @Override
    public Map<String, String> getSystemParams() {
        return params;
    }

    private Logger logger;
    
    @Override
    public void setContainerLogger(Logger logger) {
        this.logger=logger;
        this.logger.debug("setContainerLogger invoked");
    }
    
    @Override
    public void setCoreModule(Module coreModule) {
        this.coreModule=coreModule;
    }
    
    public static void init(){
        instance=new ModuleContextMock();
    }

}
