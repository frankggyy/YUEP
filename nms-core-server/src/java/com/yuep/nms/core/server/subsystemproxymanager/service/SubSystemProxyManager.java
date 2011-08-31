/*
 * $Id: SubSystemProxyManager.java, 2011-4-26 下午02:45:52 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxymanager.service;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;

/**
 * <p>
 * Title: SubSystemProxyManager
 * </p>
 * <p>
 * Description:
 * 子系统代理模块服务,用于创建、删除、修改子系统代理
 * </p>
 * 
 * @author yangtao
 * created 2011-4-26 下午02:45:52
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemProxyManager {

    /**
     * 创建子系统代理
     * @param subSystemProperty
     *        子系统基本属性
     */
    public void createSubSystemProxy(SubSystemProxyProperty subSystemProperty);
    
    /**
     * 删除子系统代理
     * @param subSystemId
     *         子系统标识
     */
    public void deleteSubSystemProxy(MoNaming subSystemId);
    
    /**
     * 更新子系统代理
     * @param subSystemProperty
     *          子系统基本属性
     */
    public void updateSubSystemProxy(SubSystemProxyProperty subSystemProperty);
    
    /**
     * 将子系统代理subSystemId设置到Mo上
     * @param subSystemId
     *         子系统标识
     * @param mo
     *         需要被设置子系统的Mo
     */
    public void setSubSystemProxy(MoNaming subSystemId,MoNaming mo);
    
    /**
     * 根据mo获取绑定在该mo上的SubSystemProxy
     * @param mo
     * @return
     *        SubSystemProxy
     */
    public SubSystemProxy getBindSubSystemProxy(MoNaming mo);

    /**
     * 获取根据子系统Id获取子系统代理
     * @param subSystemId
     *        子系统Id
     * @return
     *        SubSystemProxy
     */
    public SubSystemProxy getSubSystemProxyBySubSystemId(MoNaming subSystemId);

}
