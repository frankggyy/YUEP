/*
 * $Id: SmCoreClientModule.java, 2011-3-29 上午10:59:19 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smcore.impl;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.module.ClientModule;
import com.yuep.nms.core.client.smcore.def.SmCoreClientService;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SmCoreClientModule
 * </p>
 * <p>
 * Description:sm core客户端模块
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 上午10:59:19
 * modified [who date description]
 * check [who date description]
 */
public class SmCoreClientModule extends ClientModule{

    private SmCoreClientServiceImpl smCoreClientService;
    
    private final static String MO_TYPE_CARD="card";
    private final static String MO_TYPE_PORT="port";
    
    
    @Override
    public void start() {
        // 从服务端获取所有MO的权限cache
        smCoreClientService=new SmCoreClientServiceImpl();
        ClientCoreContext.setLocalService("smCoreClientService", SmCoreClientService.class, smCoreClientService);
    }

    @Override
    public void stop() {
    }
    
    /**
     * 是不可管理的mo吗
     * @param moNaming
     * @return
     */
    public static boolean isUnmanagedMo(MoNaming moNaming){
        String moType = moNaming.getMoType();
        if(MO_TYPE_CARD.equals(moType))
            return true;
        if(MO_TYPE_PORT.equals(moType))
            return true;
        return false;
    }

}
