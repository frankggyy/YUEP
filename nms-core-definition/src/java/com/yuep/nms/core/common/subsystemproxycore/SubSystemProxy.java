/*
 * $Id: SubSystemProxy.java, 2011-5-16 下午06:37:06 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.subsystemproxycore;

import java.io.Serializable;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SubSystemProxy
 * </p>
 * <p>
 * Description:
 * 子系统代理接口
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午06:37:06
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemProxy extends Serializable{

    /**
     * 初始化子系统代理
     * @param subSystemProxyProperty
     *        子系统代理属性
     */
    public void init(SubSystemProxyProperty subSystemProxyProperty);
    /**
     * 获取子系统代理基本属性
     * @return
     *        子系统代理基本属性
     */
    public SubSystemProxyProperty getSubSystemProxyProperty();
    
    /**
     * 获取通信对象
     * @return
     *    CommunicateObject
     */
    public CommunicateObject getCommunicateObject();
    /**
     * 销毁子系统代理
     */
    public void destory();
    
    /**
     * 根据服务名称,服务接口获取服务
     * @param <T>
     * @param managedNode
     *        管理节点MoNaming
     * @param serviceName
     *         服务名称
     * @param serviceIntefaceClass
     *         服务接口类
     * @return
     *      子系统远程服务
     */
    public <T> T getService(MoNaming managedNode,String serviceName,Class<T> serviceIntefaceClass);

    /**
     * 通过子系统代理订阅消息
     * @param name
     *        消息名称
     * @param messageReceiver
     *        消息接收器
     */
    public void subscribe(String name,MessageReceiver messageReceiver);
    /**
     * 通过子系统代理解订阅消息
     * @param name
     *        消息名称
     * @param messageReceiver
     *        消息接收器
     */
    public  void unsubscribe(String name,MessageReceiver messageReceiver);
}
