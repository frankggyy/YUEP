/*
 * $Id: SubSystemPropertyDao.java, 2011-5-16 下午06:01:55 yangtao Exp $
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
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;

/**
 * <p>
 * Title: SubSystemPropertyDao
 * </p>
 * <p>
 * Description:
 * 子系统基本属性数据访问层接口
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午06:01:55
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemProxyPropertyDao {

    /**
     * 创建SubSystemProxyProperty
     * @param subSystemProxyProperty
     */
    public void createSubSystemProxyProperty(SubSystemProxyProperty subSystemProxyProperty);

    /**
     * 更新SubSystemProxyProperty
     * @param subSystemProxyProperty
     */
    public void updateSubSystemProxyProperty(SubSystemProxyProperty subSystemProxyProperty);
    /**
     * 删除子系统代理属性
     * @param subSystemId
     *        子系统Id
     */
    public void deleteSubSystemProxyProperty(MoNaming subSystemId);
    
    /**
     * 获取所有的子系统代理属性
     * @return
     */
    public List<SubSystemProxyProperty> getAllSubSystemProxyProperties();
}
