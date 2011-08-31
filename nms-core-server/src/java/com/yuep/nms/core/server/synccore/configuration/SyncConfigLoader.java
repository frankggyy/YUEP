/*
 * $Id: SyncConfigLoader.java, 2009-3-9 上午11:24:12 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.configuration;

import java.util.List;

import com.yuep.nms.core.common.synccore.model.SyncNode;

/**
 * <p>
 * Title: SyncConfigLoader
 * </p>
 * <p>
 * Description: <br>
 * 同步配置加载接口
 * </p>
 * 
 * @author yangtao
 * created 2009-3-9 上午11:24:12
 * modified [who date description]
 * check [who date description]
 */
public interface SyncConfigLoader {
  
    /**
     * 根据管理对象类型获取同步节点
     * @param type
     *        管理对象详细类型
     * @return
     *        同步节点
     */
    public List<SyncNode> getSyncNodes(String type);

    /**
     * 根据管理对象详细类型以及同步节点类型获取同步节点
     * 
     * @param type
     * @param syncNodeTypes
     * @return 
     *          同步节点
     */
    public List<SyncNode> getSyncNodesByNodeType(String type, String... syncNodeTypes);

    /**
     * 根据同步业务节点，返回与当前业务节点相关其他节点,并按同步先后次序排列
     * 
     * @return
     *       同步节点
     */
    public List<SyncNode> getSyncNodesByNodeName(String type, String... syncNodes);

}
