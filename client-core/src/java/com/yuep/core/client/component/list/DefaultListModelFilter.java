/*
 * $Id: AbstractListModelFilter.java, 2009-9-21 上午09:54:43 Administrator Exp $
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
 * Description:list 的model
 * </p>
 * 
 * @author Chenyong
 * created 2009-9-23 上午11:18:37
 * modified [who date description]
 * check [who date description]
 */
public class DefaultListModelFilter extends AbstractListModel {

    private static final long serialVersionUID = 7168437514734746527L;
    /**
     * 用来保存被过滤模型的引用
     */
    protected DefaultListModel delegate;

    /**
     * 构造函数 ― 接受单个参数，其中包含被过滤模型的引用
     * 
     * @param delegate
     */
    public DefaultListModelFilter(DefaultListModel delegate) {
        this.delegate = delegate;
    }

    /**
     * 获取元素个数
     * 
     * @see javax.swing.ListModel#getSize()
     */
    public int getSize() {
        return delegate.getSize();
    }

    /**
     * 获取对象
     * 
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public Object getElementAt(int index) {
        return delegate.getElementAt(index);
    }

    /**
     * 添加监听对象
     * 
     * @see javax.swing.AbstractListModel#addListDataListener(javax.swing.event.ListDataListener)
     */
    public void addListDataListener(ListDataListener listener) {
        delegate.addListDataListener(listener);
    }

    /**
     * 移除监听对象
     * 
     * @see javax.swing.AbstractListModel#removeListDataListener(javax.swing.event.ListDataListener)
     */
    public void removeListDataListener(ListDataListener listener) {
        delegate.removeListDataListener(listener);
    }
}
