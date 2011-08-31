/*
 * $Id: DataNavigationModel.java, 2009-3-17 ����11:31:02 Victor Exp $
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
 * created 2009-3-17 ����11:31:02
 * @modified aaron lee 2009-9-8 ���<code>initSelectedNode</code>����
 * check [who date description]
 */
public abstract class DataNavigationModel<T> extends AbstractNavigationModel<T> {

    /**
     * ��ʼ��ѡ�еĽڵ�
     */
    protected Node initSelectedNode;
    

    /**
     * ��ȡ��ʼ��ѡ�еĽڵ�
     * @return initSelectedNode ��ʼ��ѡ�еĽڵ�
     */
    public Node getInitSelectedNode() {
        return initSelectedNode;
    }

    /**
     * ���ó�ʼ��ѡ�еĽڵ�
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
