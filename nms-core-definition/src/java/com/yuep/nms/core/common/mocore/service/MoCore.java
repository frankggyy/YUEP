/*
 * $Id: MoCore.java, 2011-3-28 上午11:34:58 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.service;

import java.util.List;

import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.mocore.cache.MoNode;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MoCore
 * </p>
 * <p>
 * Description:
 * Mo核心服务,主要用于创建、删除、修改Mo
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午11:34:58
 * modified [who date description]
 * check [who date description]
 */
public interface MoCore {
   
    /**
     * 创建管理对象
     * @param mos
     *        管理对象列表
     */
    void createMos(List<Mo> mos);
    /**
     * 更新管理对象
     * @param mos
     *        管理对象列表
     */
    void updateMos(List<Mo> mos);
    /**
     * 删除管理对象
     * @param mos
     *       管理对象列表
     */
    void deleteMos(List<Mo> mos);
    /**
     * 查询管理对象
     * @param moNaming
     *        管理对象标示
     * @return
     *        管理对象
     */
    Mo getMo(MoNaming moNaming);
    /**
     * 查询管理对象
     * @param moFilter
     *        管理对象过滤接口
     * @return
     *        管理对象列表
     */
    List<Mo> getMos(MoFilter moFilter);
    /**
     * 根据父节点查询子管理对象
     * @param moFilter
     *          管理对象过滤接口
     * @param parents
     *          父管理对象
     * @return
     *        管理对象列表
     */
    List<Mo> getChildrenMos(MoFilter moFilter,MoNaming...parents);
    
    /**
     * 获取所有的管理对象
     * @return
     *       所有管理对象
     */
    @FacadeMethod(scopeFilter=true)
    List<Mo> getAllMos();
    
    /**
     * 获取以当前参数为节点树结构
     * @param mo
     * @return
     */
    MoNode getMoNode(MoNaming mo);
    
    /**
     * 获取Mo根节点
     * @return
     */
    public Mo getRootMo();
    /**
     * 查询管理节点连接属性
     * @param nm
     *        管理节点标识
     * @return
     *        管理对象连接属性
     */
    public ManagedNodeProperty getManagedNodeProperty(MoNaming nm);
    /**
     * 更新管理对象连接属性
     * @param managedNodeProperty
     *        管理对象连接属性
     */
    public void  updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty);


}
