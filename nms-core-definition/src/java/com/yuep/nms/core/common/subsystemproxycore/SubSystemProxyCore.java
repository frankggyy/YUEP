/*
 * $Id: SubSystemProxyCore.java, 2011-5-24 下午02:51:33 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.subsystemproxycore;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SubSystemProxyCore
 * </p>
 * <p>
 * Description:
 *  SubSystemProxy服务接口
 * </p>
 * 
 * @author yangtao
 * created 2011-5-24 下午02:51:33
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemProxyCore {
    /**
     * 创建子系统代理
     * @param subSystemProxy
     */
    public void createSubSystemProxy(SubSystemProxy subSystemProxy);
    /**
     * 更新子系统代理
     * @param subSystemProxyProperty
     */
    public void updateSubSystemProxy(SubSystemProxyProperty subSystemProxyProperty);
    /**
     * 删除子系统代理系统
     * @param subSystemId
     */
    public void deleteSubSystemProxy(MoNaming subSystemId);
    /**
     * 获取绑定在Mo上的子系统代理
     * @return
     *       子系统代理
     */
    public SubSystemProxy getBindSubSystemProxy(MoNaming mo);
    
    /**
     * 根据子系统Id获取子系统代理
     * @param subSystemId
     *        子系统Id
     * @return
     *        SubSystemProxy
     */
    public SubSystemProxy getSubSystemProxyBySubSystemId(MoNaming subSystemId);
    /**
     * 绑定子系统代理
     * @param mo
     *        被绑定的Mo
     * @param subSystemId
     *        子系统Id
     */
    public void bindSubSystemProxy(MoNaming mo,MoNaming subSystemId);
    /**
     * 根据子系统类型获取
     * @param subSystemType
     *        子系统类型
     * @return 
     *        子系统代理集合
     */
    public List<SubSystemProxy> getSubSystemProxies(String subSystemType);
    /**
     * 注册子系统请求拦截器
     * @param subSystemType
     * @param subSystemRequestIntecepter
     * 
     */
    public void registerSubSystemRequestIntecepter(String subSystemType,SubSystemRequestIntercepter subSystemRequestIntecepter);
   
    /**
     * 根据子系统类型获取子系统请求拦截器
     * @param subSystemType
     *         子系统类型
     * @param SubSystemRequestIntercepter
     *         子系统服务请求拦截器
     */
    public SubSystemRequestIntercepter getSubSystemRequestIntecepter(String subSystemType);
}
