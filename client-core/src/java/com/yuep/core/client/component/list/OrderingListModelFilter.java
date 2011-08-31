/*
 * $Id: OrderingListModelFilter.java, 2009-9-21 上午09:57:59 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * 
 * <p>
 * Title: OrderingListModelFilter
 * </p>
 * <p>
 * Description: 列表JList对象数据排序模型
 * </p>
 * 
 * @author Chenyong
 * created 2009-9-23 上午11:17:04
 * modified [who date description]
 * check [who date description]
 */
public class OrderingListModelFilter extends DefaultListModelFilter {

    private static final long serialVersionUID = 7083777202727442061L;

    private List<String> sortedList;

    /**
     * 当前状态，是否排序
     */
    private boolean isOrdered = false;

    /**
     * 
     * @param delegate 参数为ListModel对象
     */
    public OrderingListModelFilter(DefaultListModel delegate) {
        super(delegate);
    }

    /**
     * 委托给过滤器目标，但使用已排序的索引
     * 
     * @see com.ycignp.veapo.client.framework.component.list.DefaultListModelFilter#getElementAt(int)
     */
    public Object getElementAt(int index) {
        if (isOrdered) {
            return sortedList.get(index);
        } else
            return delegate.get(index);
    }

    /**
     * 外部排序调用该方法，数据显示在有序和无序之间切换
     */
    public void resort() {
        isOrdered = !isOrdered;
        resortElements();
    }

    /**
     * 添加数据对象至末尾，如果参数是集合对象，则将集合中的元素全部添加
     * 
     * @param obj
     *            the component to be added
     */
    @SuppressWarnings("unchecked")
    public void addElement(Object obj) {
        if (obj instanceof Collection) { // 添加对象集合
            addElements((Collection) obj);
        } else {
            List list = new ArrayList(1);
            list.add(obj);
            addElements(list);
        }
    }

    /**
     * 添加对象集合，重复元素仅添加一次
     * 
     * @param objs
     */
    @SuppressWarnings("unchecked")
    public void addElements(Collection objs) {
        for (Object o : objs) {
            if (!delegate.contains(o))
                delegate.addElement(o);
        }
        resortElements();
    }

    /**
     * 元素值设定
     * 
     * @see javax.swing.DefaultListModel#setElementAt(java.lang.Object, int)
     */
    public void setElementAt(Object obj, int index) {
        if (isOrdered) {
            index = getSrcIndex(sortedList.get(index));
        }
        delegate.setElementAt(obj, index);
        resortElements();
    }

    /**
     * 获取对象的原始索引号
     * 
     * @param str
     * @return
     */
    private int getSrcIndex(String str) {
        for (int i = 0; i < delegate.size(); i++)
            if (delegate.getElementAt(i).equals(str))
                return i;

        return -1;
    }

    /**
     * 移除部分元素
     * 
     * @param indices
     *            元素索引
     */
    public void removeElements(int[] indices) {
        if (isOrdered) {
            int[] srcIndices = new int[indices.length];
            for (int i = 0; i < indices.length; i++) {
                srcIndices[i] = getSrcIndex(sortedList.get(indices[i]));
            }
            indices = srcIndices;
        }
        removeElementsBySortedIndex(indices);
    }

    /**
     * 根据序号由大至小删除元素
     * 
     * @param indexs
     */
    private void removeElementsBySortedIndex(int[] indices) {
        Arrays.sort(indices);
        try {
            for (int i = indices.length - 1; i >= 0; i--)
                delegate.removeElementAt(indices[i]);
        } catch (Exception e) {
            // 删除最后一个有序元素会发生UI异常 do nothing
        }
        resortElements();
    }

    /**
     * 需要排序时建立有序列表
     */
    private void resortElements() {
        sortedList = new ArrayList<String>(getSize());
        for (int i = 0; i < getSize(); i++) {
            sortedList.add(delegate.getElementAt(i).toString());
        }
        if (!delegate.isEmpty())
            Collections.sort(sortedList);
    }
}
