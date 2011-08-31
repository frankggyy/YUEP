/*
 * $Id: FuncationNavigationController.java, 2009-3-16 上午10:51:21 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;


/**
 * <p>
 * Title: FuncationNavigationController
 * </p>
 * <p>
 * Description:功能导航模型，一般是导航树的节点对应不同操作界面
 * </p>
 * 
 * @author Victor
 * created 2009-3-16 上午10:51:21
 * modified [who date description]
 * check [who date description]
 */
public abstract class FuncationNavigationController<T extends Object, V extends FuncationNavigationView<T>, M extends FuncationNavigationModel<T>>
    extends AbstractNavigationController<T, V, M> {
    /**
     * @param viewClass
     * @param modelClass
     */
    public FuncationNavigationController(Class<V> viewClass, Class<M> modelClass) {
        super(viewClass, modelClass);
    }

    /**
     * 导航到某个节点
     * 
     * @param nodeName
     *            节点名称
     * @see AbstractNavigationView.navigationByNodeName
     */
    public void navigationByNodeName(String nodeName) {
        ((FuncationNavigationView<T>) clientView).navigationByNodeName(nodeName);
    }

    /**
     * 导航到某个节点
     * 
     * @param nodeId
     *            节点ID
     * @see AbstractNavigationView.navigationByNodeName
     */
    public void navigationByNodeId(Object nodeId) {
        ((FuncationNavigationView<T>) clientView).navigationByNodeId(nodeId);
    }
}
