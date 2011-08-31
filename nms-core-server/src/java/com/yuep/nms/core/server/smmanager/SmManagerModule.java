/*
 * $Id: SmManagerModule.java, 2011-3-24 下午01:29:00 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smmanager;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.smcore.service.SmCoreService;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: SmManagerModule
 * </p>
 * <p>
 * Description:sm管理模块
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 下午01:29:00
 * modified [who date description]
 * check [who date description]
 */
public class SmManagerModule extends DefaultModule{

    private SmManagerServiceImpl smManagerService;
    
    @Override
    public void start() {
        SmManagerContext.setCoreContext(CoreContext.getInstance());
        // init sm manager and export to remote service
        SmCoreService smCoreService = CoreContext.getInstance().local().getService("smCoreService", SmCoreService.class);
        smManagerService=new SmManagerServiceImpl(smCoreService);
        SmManagerContext.setRemoteService("smManagerService", SmManagerService.class, smManagerService);
    }

    @Override
    public void stop() {
    }

}
