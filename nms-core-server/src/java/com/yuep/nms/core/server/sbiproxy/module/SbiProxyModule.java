/*
 * $Id: SbiProxyModule.java, 2011-4-14 下午05:39:23 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbiproxy.module;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.module.constants.SubSystemProxyCoreDefinationModuleConstants;
import com.yuep.nms.core.server.sbiproxy.SbiProxyRequestIntercepter;

/**
 * <p>
 * Title: SbiProxyModule
 * </p>
 * <p>
 * Description:
 * SBIProxy 模块定义
 * </p>
 * 
 * @author yangtao
 * created 2011-4-14 下午05:39:23
 * modified [who date description]
 * check [who date description]
 */
public class SbiProxyModule extends DefaultModule {

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        registerSbiProxyRequestIntercepter();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
    }

    private void registerSbiProxyRequestIntercepter(){
        SbiProxyRequestIntercepter sbiProxyRequestIntercepter=new SbiProxyRequestIntercepter();
        MoCore moCore=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        sbiProxyRequestIntercepter.setMoCore(moCore);
        
        SubSystemProxyCore subSystemProxyCore=CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        subSystemProxyCore.registerSubSystemRequestIntecepter("sbi", sbiProxyRequestIntercepter);
    }
}
