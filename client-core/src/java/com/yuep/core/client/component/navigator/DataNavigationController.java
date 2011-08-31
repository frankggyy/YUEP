/*
 * $Id: DataNavigationController.java, 2009-3-17 上午11:31:13 Victor Exp $
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
 * Description:数据导航模型，一般用于树为相同数据类型，每个树节点对应相同界面操作
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-17 上午11:31:13
 * @modified aaron lee 2009-9-9 添加<code>showTabByTitle,selectedNodeById</code>方法
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
     * 根据tab的name显示被选中的Tab页
     * @param tabTitle
     * @author aaron lee
     */
    public void showTabByTitle(String tabTitle){
        getClientView().showTab(tabTitle);
    }
    
    /**
     * 设置被选中的导航树节点
     * @param node 节点
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
     * 根据节点ID设置选中的导航树节点
     * @param nodeId 节点ID
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
     * 设置导航树的排序比较器。
     * @param comparator 排序比较器
     * @author aaron lee
     */
    public void setSortComparetor(Comparator<Node> comparator){
        getClientView().setTreeSortComparator(comparator);
    }
}
