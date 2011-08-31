/*
 * $Id: MoCoreClientModule.java, 2010-11-11 上午10:22:39 yangtao Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.mocore.module;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.client.mocore.service.ClientMoCore;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;

/**
 * <p>
 * Title: MoCoreClientModule
 * </p>
 * <p>
 * Description:
 * Mo客户端模块定义
 * </p>
 * 
 * @author yangtao
 * created 2010-11-11 上午10:22:39
 * modified [who date description]
 * check [who date description]
 */
public class MoCoreClientModule extends DefaultModule{

    /**
     * @see com.yuep.core.module.def.ModuleManager4Container#start()
     */
    @Override
    public void start() {
        initClientMoCore();
    }

    /**
     * @see com.yuep.core.module.def.ModuleManager4Container#stop()
     */
    @Override
    public void stop() {
    }
    
    private void initClientMoCore(){
        ClientMoCore clientMoCore=new ClientMoCore();
        clientMoCore.setCoreContext(CoreContext.getInstance());
        // clientMoCore.init();
        // 把mocore服务注册为local service
        CoreContext.getInstance().setLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class, clientMoCore);
    }


}
