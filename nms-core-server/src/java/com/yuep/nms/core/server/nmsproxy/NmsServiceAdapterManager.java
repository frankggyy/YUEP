/*
 * $Id: NmsServiceAdapterManager.java, 2011-5-5 下午02:54:52 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.nmsproxy;

/**
 * <p>
 * Title: NmsServiceAdapterManager
 * </p>
 * <p>
 * Description:
 * 下级网管服务适配器管理
 * </p>
 * 
 * @author yangtao
 * created 2011-5-5 下午02:54:52
 * modified [who date description]
 * check [who date description]
 */
public interface NmsServiceAdapterManager {
    /**
     * 注册下级网管服务适配器
     * @param serviceName
     *        下级网管服务名称
     * @param nmsServiceAdapterClass
     */
    public void registerNmsServiceAdapter(String serviceName,Class<?> nmsServiceAdapterClass);
    /**
     * 注销下级网管服务适配器
     * @param serviceName
     *        下级网管服务名称
     */
    public void deRegisterNmsServiceAdapter(String serviceName);
    
    /**
     * 获取下级网管服务适配器
     * @param serviceName
     *        下级网管服务名称
     * @return
     *       Class<?>
     */
    public Class<?> getNmsServiceAdapterClass(String serviceName);
}
