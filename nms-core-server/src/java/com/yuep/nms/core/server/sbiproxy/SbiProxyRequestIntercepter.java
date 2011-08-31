/*
 * $Id: SbiProxyRequestIntercepter.java, 2011-5-16 下午09:00:15 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbiproxy;

import java.lang.reflect.Method;

import com.yuep.base.exception.YuepException;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.service.impl.remote.importer.RemoteServiceProxyFactory;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.sbi.service.SbiAdaptorMgr;
import com.yuep.nms.core.common.sbi.service.SbiSysMgr;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemRequestIntercepter;

/**
 * <p>
 * Title: SbiProxyRequestIntercepter
 * </p>
 * <p>
 * Description:
 * SBI子系统服务请求拦截
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午09:00:15
 * modified [who date description]
 * check [who date description]
 */
public class SbiProxyRequestIntercepter implements SubSystemRequestIntercepter {

    private MoCore moCore;
    
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemRequestIntercepter#invoke(com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy, com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String, java.lang.Class, java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public <T> Object invoke(SubSystemProxy subSystemProxy,MoNaming managedNode, String serviceName,
            Class<T> serviceIntefaceClass, Object proxy, Method method,
            Object[] args) {
        if (serviceIntefaceClass == SbiSysMgr.class || serviceIntefaceClass == SbiAdaptorMgr.class) {
            RemoteCommunicateObject remote = CoreContext.getInstance().remote(subSystemProxy.getSubSystemProxyProperty().getIp(), subSystemProxy.getSubSystemProxyProperty().getPort());
            return remote.getService(serviceIntefaceClass.getSimpleName(), serviceIntefaceClass);
        }
        Mo mo = moCore.getMo(managedNode);
        String deviceType = mo.getActualtype();
        String version = "V1";
        SbiRemoteProxyAdvice sbiRemoteProxyAdvic = new SbiRemoteProxyAdvice((RemoteCommunicateObject)subSystemProxy.getCommunicateObject(), deviceType,
                version, serviceName);
        T obj = RemoteServiceProxyFactory.getRemoteServiceByAdvice(serviceIntefaceClass, sbiRemoteProxyAdvic);

        try {
            return method.invoke(obj, args);
        } catch (Exception ex) {
            throw new YuepException(ex);
        }
    }

  

}
