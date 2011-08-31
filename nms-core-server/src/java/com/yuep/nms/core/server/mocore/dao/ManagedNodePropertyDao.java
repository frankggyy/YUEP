/*
 * $Id: ManagedNodePropertyDao.java, 2011-3-28 下午03:20:00 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.dao;

import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: ManagedNodePropertyDao
 * </p>
 * <p>
 * Description:
 * 管理节点连接属性数据访问接口
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 下午03:20:00
 * modified [who date description]
 * check [who date description]
 */
public interface ManagedNodePropertyDao {

    /**
     * 根据IP地址获取管理节点
     * @param nm
     * @return
     */
    public ManagedNodeProperty getManagedNodeProperty(String ip);
    /**
     * 查询管理节点连接属性
     * @param nm
     *        Mo标识
     * @return
     *        管理节点连接属性
     */
    public ManagedNodeProperty getManagedNodeProperty(MoNaming nm);
    
    /**
     * 更新管理节点连接属性
     * @param managedNodeProperty
     *        管理节点连接属性
     */
    public void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty);
    
    /**
     * 删除管理节点连接属性
     * @param nm
     *        Mo标识
     */
    public void deleteManagerNodeProperty(MoNaming nm);
    
    /**
     * 创建管理节点连接属性
     * @param managedNodeProperty
     *        管理节点连接属性
     */        
    public void createManagerNodeProperty(ManagedNodeProperty managedNodeProperty);
}
