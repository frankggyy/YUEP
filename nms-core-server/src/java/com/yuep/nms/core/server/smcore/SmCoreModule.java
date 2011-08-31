/*
 * $Id: SmCoreModule.java, 2011-3-24 下午01:23:57 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore;

import org.springframework.context.ApplicationContext;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.spring.impl.ExtendClassPathXmlApplicationContext;
import com.yuep.nms.core.common.smcore.exception.SmException;
import com.yuep.nms.core.common.smcore.service.SmCoreService;
import com.yuep.nms.core.server.smcore.mgmtscope.MgmtScopeManager;
import com.yuep.nms.core.server.smcore.oplog.OpLogManager;

/**
 * <p>
 * Title: SmCoreModule
 * </p>
 * <p>
 * Description: 安全内核模块
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 下午01:23:57
 * modified [who date description]
 * check [who date description]
 */
public class SmCoreModule extends DefaultModule{

    private OpLogManager opLogManager;
    private MgmtScopeManager mgmtScopeManager;
    //private UserManager userManager;
    private SmCoreServiceImpl smCoreService;

    @Override
    public void start() {
        SmCoreContext.setCoreContext(CoreContext.getInstance());
        SmCoreContext.setLogger(getLogger());
        
        // init spring
        String locations = getModuleParams().get("configLocations");
        String[] configLocations = locations.split(",");
        try {
            ApplicationContext ctx = new ExtendClassPathXmlApplicationContext(classLoader,null,configLocations);
            SmCoreContext.setCtx(ctx);
        }catch(RuntimeException ex2){
            throw ex2;
        }catch(Exception ex){
            String errorInfo=ExceptionUtils.getCommonExceptionInfo(ex);
            System.err.println(errorInfo);
            throw new SmException(SmException.INTERNAL_ERROR,errorInfo);
        }
        
        // init sub module
        opLogManager=SmCoreContext.getBean("opLogManager", OpLogManager.class);
        opLogManager.init();
        
        mgmtScopeManager=SmCoreContext.getBean("mgmtScopeManager", MgmtScopeManager.class);
        mgmtScopeManager.init();
        
        // init and export sm core service
        smCoreService=SmCoreContext.getBean("smCoreService", SmCoreServiceImpl.class);
        smCoreService.init();
        CoreContext.getInstance().setLocalService("smCoreService", SmCoreService.class, smCoreService);
    }

    @Override
    public void stop() {

    }

}
