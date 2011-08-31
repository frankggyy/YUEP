/*
 * $Id: SubSystemProxyCoreModule.java, 2011-5-24 下午04:39:04 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxycore.module;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.module.constants.SubSystemProxyCoreDefinationModuleConstants;
import com.yuep.nms.core.server.subsystemproxycore.SubSystemProxyCoreImpl;

/**
 * <p>
 * Title: SubSystemProxyCoreModule
 * </p>
 * <p>
 * Description:
 * 子系统核心模块定义
 * </p>
 * 
 * @author yangtao
 * created 2011-5-24 下午04:39:04
 * modified [who date description]
 * check [who date description]
 */
public class SubSystemProxyCoreModule extends DefaultModule {

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#start()
     */
    @Override
    public void start() {
        buildSubSystemProxyCore();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.bootstrap.def.module.Module4Container#stop()
     */
    @Override
    public void stop() {
    }

    /**
     * 构建subSystemProxyCore
     */
    private void buildSubSystemProxyCore(){
        SubSystemProxyCoreImpl subSystemProxyCore=new SubSystemProxyCoreImpl();
        subSystemProxyCore.setCoreContext(CoreContext.getInstance());
        CoreContext.getInstance().setLocalService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class, subSystemProxyCore);
    }
}
