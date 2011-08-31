/*
 * $Id: SmManagerDefinitionModule.java, 2011-3-24 上午10:13:55 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.sbi;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.sbi.service.SbiAdaptorMgr;
import com.yuep.nms.core.common.sbi.service.SbiRemoteInvocationMgr;
import com.yuep.nms.core.common.sbi.service.SbiSysMgr;
import com.yuep.nms.core.sbi.nemanager.SbiRemoteInvocationMgrImpl;
import com.yuep.nms.core.sbi.nemanager.SbiSysMgrImpl;
import com.yuep.nms.core.sbi.sbicore.SbiAdaptorMgrImpl;

/**
 * <p>
 * Title: SbiCoreModule
 * </p>
 * <p>
 * SBICore模块
 * </p>
 * 
 * @author pl
 * created 2011-4-16 下午04:03:26
 * modified [who date description]
 * check [who date description]
 */
public class SbiCoreModule extends DefaultModule {

    @Override
    public void start() {
        // 发布远程调用接口
        CoreContext.getInstance().setRemoteService("SbiRemoteInvocationMgr", SbiRemoteInvocationMgr.class,
                new SbiRemoteInvocationMgrImpl());
        // 发布Adaptor查询接口
        CoreContext.getInstance().setRemoteService("SbiAdaptorMgr", SbiAdaptorMgr.class, new SbiAdaptorMgrImpl());
        // 发布网元管理接口
        CoreContext.getInstance().setRemoteService("SbiSysMgr", SbiSysMgr.class, new SbiSysMgrImpl());
    }

    @Override
    public void stop() {
    }

}