/*
 * $Id: DataNavigationController.java, 2009-3-17 ����11:31:13 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import java.util.Comparator;

import javax.swing.SwingUtilities;

import twaver.Element;
import twaver.Node;

/**
 * <p>
 * Title: DataNavigationController
 * </p>
 * <p>
 * Description:���ݵ���ģ�ͣ�һ��������Ϊ��ͬ�������ͣ�ÿ�����ڵ��Ӧ��ͬ�������
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-17 ����11:31:13
 * @modified aaron lee 2009-9-9 ���<code>showTabByTitle,selectedNodeById</code>����
 * check [who date description]
 */
public abstract class DataNavigationController<T, V extends DataNavigationView<T>, M extends DataNavigationModel<T>>
    extends AbstractNavigationController<T, V, M> {
    /**
     * @param viewClass
     * @param modelClass
     */
    public DataNavigationController(Class<V> viewClass, Class<M> modelClass) {
        super(viewClass, modelClass);
    }
    
    /**
     * ����tab��name��ʾ��ѡ�е�Tabҳ
     * @param tabTitle
     * @author aaron lee
     */
    public void showTabByTitle(String tabTitle){
        getClientView().showTab(tabTitle);
    }
    
    /**
     * ���ñ�ѡ�еĵ������ڵ�
     * @param node �ڵ�
     * @author aaron lee
     */
    public void selectedNode(final Node node){
        getClientView().getNavigationTree().clearSelection();
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                getClientView().onSelectOne(node);
                node.setSelected(true);
            }
        });
    }
    
    /**
     * ���ݽڵ�ID����ѡ�еĵ������ڵ�
     * @param nodeId �ڵ�ID
     * @author aaron lee
     */
    public void selectedNodeById(final Object nodeId){
        getClientView().getNavigationTree().clearSelection();
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Element node = getClientView().getNodeById(nodeId);
                selectedNode((Node) node);
                node.setSelected(true);
            }
        });
    }
    
    /** 
     * ���õ�����������Ƚ�����
     * @param comparator ����Ƚ���
     * @author aaron lee
     */
    public void setSortComparetor(Comparator<Node> comparator){
        getClientView().setTreeSortComparator(comparator);
    }
}
