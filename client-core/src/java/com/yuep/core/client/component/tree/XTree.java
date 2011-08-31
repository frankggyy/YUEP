/*
 * $Id: XTree.java, 2011-3-2 下午01:45:32 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.tree;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import twaver.Element;
import twaver.TDataBox;
import twaver.tree.TTree;

/**
 * <p>
 * Title: XTree
 * </p>
 * <p>
 * Description:YUEP 包装TWaver的树控件
 * </p>
 * 
 * @author aaron
 * created 2011-3-2 下午01:45:32
 * modified [who date description]
 * check [who date description]
 */
public class XTree extends TTree {

    private static final long serialVersionUID = 7694302637099440692L;

    /**
     * Tree的数据是否动态更新
     */
    private boolean dynamicState = false;

    /**
     * Tree节点是否显示的过滤器
     */
    private XNodeFilter nodeFilter;

    public XTree() {
        TDataBox dataBox = new TDataBox();
        setDataBox(dataBox);
        setTTreeSelectionMode(DEFAULT_SELECTION);
    }

    /**
     * 添加节点
     * @param node 要添加的节点
     */
    public void addNode(Element node) {
        if (node != null) {
            getDataBox().addElement(node);
            showFilter(node);
        }
    }

    /**
     * 修改节点
     * @param node 要修改的节点
     */
    public void updateNode(Element node) {
        if(node == null)
            return;
        Object id = node.getID();
        getDataBox().removeElementByID(id);
        addNode(node);
    }

    /**
     * 删除节点
     * @param node 要删除的节点
     */
    public void deleteNode(Element node) {
        if(node == null)
            return;
        Object id = node.getID();
        getDataBox().removeElementByID(id);
    }

    /**
     * 设置Tree是否支持动态更新
     * @param enabled 动态更新状态
     */
    public void setDynamicState(Boolean enabled) {
        this.dynamicState = enabled;
    }

    /**
     * 设置Tree节点显示过滤器
     * @param nodeFilter 节点显示过滤器
     */
    public void setFilter(XNodeFilter nodeFilter) {
        this.nodeFilter = nodeFilter;
        if(nodeFilter == null)
            return;
        List allElements = getDataBox().getAllElements();
        for (Object object : allElements) {
            if (object instanceof Element) {
                Element node = (Element) object;
                boolean filter = nodeFilter.filter(node);
                node.setVisible(filter);
            }
        }
    }

    /**
     * 根据树节点显示过滤器过滤节点
     * @param node 要过滤的节点
     */
    private void showFilter(Element node) {
        if(nodeFilter == null)
            return;
        boolean filter = nodeFilter.filter(node);
        node.setVisible(filter);
    }

    /**
     * 设置tree的节点选中的显示方式过滤（默认为对象选中）
     * @see twaver.tree.TTree#setCheckableFilter(twaver.CheckableFilter)
     */
    public void setCheckableFilter(YuepCheckableFilter checkableFilter) {
        super.setCheckableFilter(checkableFilter);
    }

    /**
     * 获取当前选中的节点列表
     * @return List<Node> 当前选中的节点列表
     */
    public List<Element> getSelectedNodes() {
        return getDataBox().getSelectionModel().getAllSelectedElement();
    }

    /**
     * 设置树的根节点，根节点的上级节点不会被显示
     * @param rootNode 根节点对象
     */
    public void setRootNode(Element rootNode) {
        setRootElement(rootNode);
    }

    /**
     * 为某一类节点设置图片
     * @param <T>
     * @param nodeClass 节点的类型
     * @param iconUrl 图片的URL
     */
    public <T extends Element> void setNodeIcon(Class<T> nodeClass, String iconUrl) {
        if (nodeClass == null || StringUtils.isEmpty(iconUrl))
            return;
        List elements = getDataBox().getElementsByType(nodeClass);
        if (CollectionUtils.isNotEmpty(elements)) {
            for (Object object : elements) {
                if (object instanceof Element) {
                    ((Element) object).setIcon(iconUrl);
                }
            }
        }
    }

    /**
     * 选中一个或者多个树节点
     * @param nodeId 节点的ID数组
     */
    public void setSelectedNode(Object... nodeId) {
        if (nodeId == null)
            return;
        for (Object object : nodeId) {
            Element node = getXNode(object);
            getDataBox().getSelectionModel().setSelection(node);
        }
    }

    /**
     * 根据nodeId获取XNode
     * @param nodeId 节点ID
     * @return XNode 节点
     */
    public Element getXNode(Object nodeId) {
        if (nodeId == null)
            return null;
        Element element = getDataBox().getElementByID(nodeId);
        return element;
    }

    @Override
    public boolean isCheckable(Element element) {
        return super.isCheckable(element);
    }
}
