/*
 * $Id: SubSystemServiceRequestIntecepter.java, 2011-5-16 下午08:29:02 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.subsystemproxycore;

import java.lang.reflect.Method;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SubSystemServiceRequestIntecepter
 * </p>
 * <p>
 * Description:
 * 子系统服务请求拦截
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午08:29:02
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemRequestIntercepter {
    /**
     * 子系统请求拦截
     * @param <T>
     * @param subSystemProxy
     *         子系统代理
     * @param managedNode
     *          管理节点
     * @param serviceName
     *          服务名称
     * @param serviceIntefaceClass
     *           服务接口
     * @param proxy
     * @param method
     * @param args
     * @return
     */
    public <T> Object invoke(SubSystemProxy subSystemProxy,MoNaming managedNode, String serviceName, Class<T> serviceIntefaceClass,Object proxy, Method method, Object[] args);
    

}
