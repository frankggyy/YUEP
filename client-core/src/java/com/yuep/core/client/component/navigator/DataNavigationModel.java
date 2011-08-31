/*
 * $Id: DataNavigationModel.java, 2009-3-17 上午11:31:02 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import twaver.Node;


/**
 * <p>
 * Title: DataNavigationModel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-17 上午11:31:02
 * @modified aaron lee 2009-9-8 添加<code>initSelectedNode</code>变量
 * check [who date description]
 */
public abstract class DataNavigationModel<T> extends AbstractNavigationModel<T> {

    /**
     * 初始化选中的节点
     */
    protected Node initSelectedNode;
    

    /**
     * 获取初始化选中的节点
     * @return initSelectedNode 初始化选中的节点
     */
    public Node getInitSelectedNode() {
        return initSelectedNode;
    }

    /**
     * 设置初始化选中的节点
     * @param initSelectedNode
     *            the initSelectedNode to set
     */
    public void setInitSelectedNode(Node initSelectedNode) {
        this.initSelectedNode = initSelectedNode;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {

    }
}
