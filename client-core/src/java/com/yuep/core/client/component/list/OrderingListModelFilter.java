/*
 * $Id: OrderingListModelFilter.java, 2009-9-21 ����09:57:59 Administrator Exp $
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
 * Description: �б�JList������������ģ��
 * </p>
 * 
 * @author Chenyong
 * created 2009-9-23 ����11:17:04
 * modified [who date description]
 * check [who date description]
 */
public class OrderingListModelFilter extends DefaultListModelFilter {

    private static final long serialVersionUID = 7083777202727442061L;

    private List<String> sortedList;

    /**
     * ��ǰ״̬���Ƿ�����
     */
    private boolean isOrdered = false;

    /**
     * 
     * @param delegate ����ΪListModel����
     */
    public OrderingListModelFilter(DefaultListModel delegate) {
        super(delegate);
    }

    /**
     * ί�и�������Ŀ�꣬��ʹ�������������
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
     * �ⲿ������ø÷�����������ʾ�����������֮���л�
     */
    public void resort() {
        isOrdered = !isOrdered;
        resortElements();
    }

    /**
     * ������ݶ�����ĩβ����������Ǽ��϶����򽫼����е�Ԫ��ȫ�����
     * 
     * @param obj
     *            the component to be added
     */
    @SuppressWarnings("unchecked")
    public void addElement(Object obj) {
        if (obj instanceof Collection) { // ��Ӷ��󼯺�
            addElements((Collection) obj);
        } else {
            List list = new ArrayList(1);
            list.add(obj);
            addElements(list);
        }
    }

    /**
     * ��Ӷ��󼯺ϣ��ظ�Ԫ�ؽ����һ��
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
     * Ԫ��ֵ�趨
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
     * ��ȡ�����ԭʼ������
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
     * �Ƴ�����Ԫ��
     * 
     * @param indices
     *            Ԫ������
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
     * ��������ɴ���Сɾ��Ԫ��
     * 
     * @param indexs
     */
    private void removeElementsBySortedIndex(int[] indices) {
        Arrays.sort(indices);
        try {
            for (int i = indices.length - 1; i >= 0; i--)
                delegate.removeElementAt(indices[i]);
        } catch (Exception e) {
            // ɾ�����һ������Ԫ�ػᷢ��UI�쳣 do nothing
        }
        resortElements();
    }

    /**
     * ��Ҫ����ʱ���������б�
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
