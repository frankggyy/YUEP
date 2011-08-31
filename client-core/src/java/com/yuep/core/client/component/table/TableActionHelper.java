/*
 * $Id: TableActionHelper.java, 2009-2-9 ����04:23:15 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table;

import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import twaver.table.TTableListener;
import twaver.table.TTableModelEvent;

import com.yuep.core.client.component.menu.action.InstanceProvider;
import com.yuep.core.client.component.menu.action.SensitiveAction;

/**
 * <p>
 * Title: TableActionHelper
 * </p>
 * <p>
 * Description:����밴ť���Ͽ��Ƶİ�װ�������ݱ�������ݵ�ѡ��������ư�ť��ʹ��״̬
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 ����04:23:15
 * modified [who date description]
 * check [who date description]
 */
public class TableActionHelper extends JComponentActionHelper implements TTableListener {

    /**
     * �������û�ѡ���¼���table for <code>table</code>
     */
    private JTable table;

    /**
     * Constructor.
     * 
     * @param table
     *            �������û�ѡ���¼���table
     */
    public TableActionHelper(JTable table) {
        this.table = table;
    }

    /*
     * (non-Javadoc)
     * 
     * @see twaver.table.TTableListener#beforeCellValueChanged(java.util.Vector,
     * int, java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public boolean beforeCellValueChanged(Vector vector, int i, Object obj, Object obj1) {
        return true;
    }

    /**
     * ��ȡ����ʵ���ṩ�ߡ�
     * 
     * @return �������ʵ���ṩ�ߴ��ڽ��䷵�أ����򷵻�<code>null</code>
     */
    public InstanceProvider getTableInstanceProvider() {
        if (null != table && table instanceof XTable) {
            return (InstanceProvider) table;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see twaver.table.TTableListener#lockedChanged()
     */
    public void lockedChanged() {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see twaver.table.PageListener#pageChanged()
     */
    public void pageChanged() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see twaver.table.TTableListener#rowClicked(int, java.util.Vector, int)
     */
    @SuppressWarnings("unchecked")
    public void rowClicked(int i, Vector vector, int j) {
        // TODO Auto-generated method stub
        if (table == null) {
            return;
        }
        InstanceProvider tableInstanceProvider = getTableInstanceProvider();
        if (tableInstanceProvider == null) {
            return;
        }
        for (SensitiveAction sensitiveAction : actionSet) {
            sensitiveAction.selectionChanged(tableInstanceProvider);
        }
    }

    /**
     * ��дTTable��rowSelectionChanged��������Ҫ�Ǽ���table�е�����ѡ���¼���
     * 
     * @see twaver.table.TTableListener#rowSelectionChanged(java.util.List,
     *      boolean)
     */
    @SuppressWarnings("unchecked")
    public void rowSelectionChanged(List list, boolean flag) {
        if (table == null) {
            return;
        }
        InstanceProvider tableInstanceProvider = getTableInstanceProvider();
        if (tableInstanceProvider == null) {
            return;
        }
        for (SensitiveAction sensitiveAction : actionSet) {
            sensitiveAction.selectionChanged(tableInstanceProvider);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * twaver.table.TTableListener#tableDataChanged(twaver.table.TTableModelEvent
     * )
     */
    public void tableDataChanged(TTableModelEvent ttablemodelevent) {
        if (table == null) {
            return;
        }
        InstanceProvider tableInstanceProvider = getTableInstanceProvider();
        if (tableInstanceProvider == null) {
            return;
        }
        for (SensitiveAction sensitiveAction : actionSet) {
            sensitiveAction.selectionChanged(tableInstanceProvider);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event
     * .ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent e) {
    }
}
