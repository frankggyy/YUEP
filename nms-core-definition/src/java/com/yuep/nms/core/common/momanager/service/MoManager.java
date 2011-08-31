/*
 * $Id: MoManager.java, 2011-3-28 上午11:47:09 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.momanager.service;

import java.util.List;

import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoFilter;

/**
 * <p>
 * Title: MoManager
 * </p>
 * <p>
 * Description:
 * Mo管理接口，上层应用需要实现
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午11:47:09
 * modified [who date description]
 * check [who date description]
 */
public interface MoManager {
    /**
     * 查询所有管理对象
     * @return
     *       管理对象列表
     */
    @FacadeMethod(scopeFilter=true)
    List<Mo> getAllMos();
    /**
     * 查询管理对象
     * @param moNaming
     *        管理对象标识
     * @return
     *        管理对象
     */
    Mo getMo(MoNaming moNaming);
    /**
     * 查询管理对象
     * @param moFilter
     *        管理对象过滤
     * @return
     *         管理对象列表
     */
    @FacadeMethod(scopeFilter=true)
    List<Mo> getMos(MoFilter moFilter);
    /**
     * 查询子管理对象
     * @param moFilter
     *          管理对象过滤
     * @param parents
     *          父管理节点
     * @return
     *          管理对象列表
     */
    @FacadeMethod(scopeFilter=true)
    List<Mo> getChildrenMos(MoFilter moFilter,MoNaming...parents);
    /**
     * 更新管理节点连接属性
     * @param managedNodeProperty
     *          管理节点连接属性
     *         
     */
    void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty);
    /**
     * 查询管理节点连接属性
     * @param nm
     *        管理节点标识
     * @return
     *      管理节点连接属性
     */
    ManagedNodeProperty getManagedNodeProperty (MoNaming nm);
    /**
     * 创建管理节点连接属性
     * @param parent
     *        父管理节点
     * @param managedNodeProperty
     *        管理节点连接属性
     * @param type
     *        管理节点类型
     * @return
     *       管理节点Mo
     */
    @FacadeMethod(scopeFilter=true)
    Mo  createManagedNode(MoNaming parent,ManagedNodeProperty managedNodeProperty,String type);
    /**
     * 删除管理节点
     * @param nm
     *        管理节点标识
     */
    void deleteManagedNode(MoNaming nm);
    /**
     * 创建域或者组
     * @param parent
     *        父管理节点
     * @param domainName
     *        域或者组的名称
     * @param type
     *        域或者组的类型
     * @return
     *        域或者组Mo
     */
    @FacadeMethod(scopeFilter=true)
    Mo createManagedDomain(MoNaming parent,String domainName,String type);
    /**
     * 删除域或者组
     * @param domainNaming
     *        Mo标识
     */
    void deleteManagedDomain (MoNaming domainNaming);

}
