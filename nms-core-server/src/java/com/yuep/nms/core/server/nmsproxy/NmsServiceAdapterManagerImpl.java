/*
 * $Id: NmsServiceAdapterManagerImpl.java, 2011-5-5 下午02:57:32 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.nmsproxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * Title: NmsServiceAdapterManagerImpl
 * </p>
 * <p>
 * Description:
 * NmsServiceAdapterManager接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-5 下午02:57:32
 * modified [who date description]
 * check [who date description]
 */
public class NmsServiceAdapterManagerImpl implements NmsServiceAdapterManager {

    private Map<String,Class<?>> nmsServiceAdapters=new ConcurrentHashMap<String,Class<?>>();
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.nmsproxy.NmsServiceAdapterManager#deRegisterNmsServiceAdapter(java.lang.String)
     */
    @Override
    public void deRegisterNmsServiceAdapter(String serviceName) {
        nmsServiceAdapters.remove(serviceName);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.nmsproxy.NmsServiceAdapterManager#registerNmsServiceAdapter(java.lang.String, java.lang.Class)
     */
    @Override
    public void registerNmsServiceAdapter(String serviceName,Class<?> nmsServiceAdapterClass) {
        nmsServiceAdapters.put(serviceName, nmsServiceAdapterClass);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.nmsproxy.NmsServiceAdapterManager#getNmsServiceAdapterClass(java.lang.String)
     */
    @Override
    public Class<?> getNmsServiceAdapterClass(String serviceName) {
        return nmsServiceAdapters.get(serviceName);
    }

}
