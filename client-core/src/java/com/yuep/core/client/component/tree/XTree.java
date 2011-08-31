/*
 * $Id: XTree.java, 2011-3-2 ����01:45:32 aaron Exp $
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
 * Description:YUEP ��װTWaver�����ؼ�
 * </p>
 * 
 * @author aaron
 * created 2011-3-2 ����01:45:32
 * modified [who date description]
 * check [who date description]
 */
public class XTree extends TTree {

    private static final long serialVersionUID = 7694302637099440692L;

    /**
     * Tree�������Ƿ�̬����
     */
    private boolean dynamicState = false;

    /**
     * Tree�ڵ��Ƿ���ʾ�Ĺ�����
     */
    private XNodeFilter nodeFilter;

    public XTree() {
        TDataBox dataBox = new TDataBox();
        setDataBox(dataBox);
        setTTreeSelectionMode(DEFAULT_SELECTION);
    }

    /**
     * ��ӽڵ�
     * @param node Ҫ��ӵĽڵ�
     */
    public void addNode(Element node) {
        if (node != null) {
            getDataBox().addElement(node);
            showFilter(node);
        }
    }

    /**
     * �޸Ľڵ�
     * @param node Ҫ�޸ĵĽڵ�
     */
    public void updateNode(Element node) {
        if(node == null)
            return;
        Object id = node.getID();
        getDataBox().removeElementByID(id);
        addNode(node);
    }

    /**
     * ɾ���ڵ�
     * @param node Ҫɾ���Ľڵ�
     */
    public void deleteNode(Element node) {
        if(node == null)
            return;
        Object id = node.getID();
        getDataBox().removeElementByID(id);
    }

    /**
     * ����Tree�Ƿ�֧�ֶ�̬����
     * @param enabled ��̬����״̬
     */
    public void setDynamicState(Boolean enabled) {
        this.dynamicState = enabled;
    }

    /**
     * ����Tree�ڵ���ʾ������
     * @param nodeFilter �ڵ���ʾ������
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
     * �������ڵ���ʾ���������˽ڵ�
     * @param node Ҫ���˵Ľڵ�
     */
    private void showFilter(Element node) {
        if(nodeFilter == null)
            return;
        boolean filter = nodeFilter.filter(node);
        node.setVisible(filter);
    }

    /**
     * ����tree�Ľڵ�ѡ�е���ʾ��ʽ���ˣ�Ĭ��Ϊ����ѡ�У�
     * @see twaver.tree.TTree#setCheckableFilter(twaver.CheckableFilter)
     */
    public void setCheckableFilter(YuepCheckableFilter checkableFilter) {
        super.setCheckableFilter(checkableFilter);
    }

    /**
     * ��ȡ��ǰѡ�еĽڵ��б�
     * @return List<Node> ��ǰѡ�еĽڵ��б�
     */
    public List<Element> getSelectedNodes() {
        return getDataBox().getSelectionModel().getAllSelectedElement();
    }

    /**
     * �������ĸ��ڵ㣬���ڵ���ϼ��ڵ㲻�ᱻ��ʾ
     * @param rootNode ���ڵ����
     */
    public void setRootNode(Element rootNode) {
        setRootElement(rootNode);
    }

    /**
     * Ϊĳһ��ڵ�����ͼƬ
     * @param <T>
     * @param nodeClass �ڵ������
     * @param iconUrl ͼƬ��URL
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
     * ѡ��һ�����߶�����ڵ�
     * @param nodeId �ڵ��ID����
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
     * ����nodeId��ȡXNode
     * @param nodeId �ڵ�ID
     * @return XNode �ڵ�
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
