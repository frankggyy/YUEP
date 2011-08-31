/*
 * $Id: FuncationNavigationController.java, 2009-3-16 ����10:51:21 Victor Exp $
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
 * Description:���ܵ���ģ�ͣ�һ���ǵ������Ľڵ��Ӧ��ͬ��������
 * </p>
 * 
 * @author Victor
 * created 2009-3-16 ����10:51:21
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
     * ������ĳ���ڵ�
     * 
     * @param nodeName
     *            �ڵ�����
     * @see AbstractNavigationView.navigationByNodeName
     */
    public void navigationByNodeName(String nodeName) {
        ((FuncationNavigationView<T>) clientView).navigationByNodeName(nodeName);
    }

    /**
     * ������ĳ���ڵ�
     * 
     * @param nodeId
     *            �ڵ�ID
     * @see AbstractNavigationView.navigationByNodeName
     */
    public void navigationByNodeId(Object nodeId) {
        ((FuncationNavigationView<T>) clientView).navigationByNodeId(nodeId);
    }
}
