/*
 * $Id: SubSystemBindsDao.java, 2011-5-16 下午06:02:49 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxymanager.property.dao;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemBind;

/**
 * <p>
 * Title: SubSystemBindsDao
 * </p>
 * <p>
 * Description:
 * 子系统绑定信息：管理节点和子系统的绑定关系
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午06:02:49
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemBindDao {
    
    /**
     * 设置子系统绑定信息
     * @param subSystemId
     *        子系统Id
     * @param mangedNode
     *        管理节点
     */
    public void setSubSystemBind(MoNaming subSystemId, MoNaming mangedNode);
    /**
     * 删除子系统绑定信息
     * @param subSystemId
     *        子系统Id
     * @param mangedNode
     *        管理节点
     */
    public void deleteSubSystemBind(MoNaming subSystemId, MoNaming mangedNode);
    
    /**
     * 获取所有的子系统绑定信息
     * @return
     *       所有的子系统绑定信息
     */
    public List<SubSystemBind> getAllSubSystemBinds();

}
