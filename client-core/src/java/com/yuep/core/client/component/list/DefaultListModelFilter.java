/*
 * $Id: AbstractListModelFilter.java, 2009-9-21 ����09:54:43 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.list;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;

/**
 * 
 * <p>
 * Title: DefaultListModelFilter
 * </p>
 * <p>
 * Description:list ��model
 * </p>
 * 
 * @author Chenyong
 * created 2009-9-23 ����11:18:37
 * modified [who date description]
 * check [who date description]
 */
public class DefaultListModelFilter extends AbstractListModel {

    private static final long serialVersionUID = 7168437514734746527L;
    /**
     * �������汻����ģ�͵�����
     */
    protected DefaultListModel delegate;

    /**
     * ���캯�� �D ���ܵ������������а���������ģ�͵�����
     * 
     * @param delegate
     */
    public DefaultListModelFilter(DefaultListModel delegate) {
        this.delegate = delegate;
    }

    /**
     * ��ȡԪ�ظ���
     * 
     * @see javax.swing.ListModel#getSize()
     */
    public int getSize() {
        return delegate.getSize();
    }

    /**
     * ��ȡ����
     * 
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public Object getElementAt(int index) {
        return delegate.getElementAt(index);
    }

    /**
     * ��Ӽ�������
     * 
     * @see javax.swing.AbstractListModel#addListDataListener(javax.swing.event.ListDataListener)
     */
    public void addListDataListener(ListDataListener listener) {
        delegate.addListDataListener(listener);
    }

    /**
     * �Ƴ���������
     * 
     * @see javax.swing.AbstractListModel#removeListDataListener(javax.swing.event.ListDataListener)
     */
    public void removeListDataListener(ListDataListener listener) {
        delegate.removeListDataListener(listener);
    }
}
