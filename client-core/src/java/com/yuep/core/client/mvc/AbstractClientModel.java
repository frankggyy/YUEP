/*
 * 
 * $Id: AbstractClientModel.java, 2009-2-12 ����10:47:39 aaron lee Exp $
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.SwingUtilities;

/**
 * <p>
 * Title: AbstractClientModel
 * </p>
 * <p>
 * Description: ����Ŀͻ���Model��
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-12 ����10:47:39
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractClientModel<T extends Object> extends Observable implements ClientModel<T> {
    /**
     * model�����ݻ���
     */
    protected List<T> boList = new ArrayList<T>();

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientModel#addDatas(java.util.List)
     */
    @Override
    public void addDatas(List<T> datas) {
        boList.addAll(datas);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientModel#deleteDatas(java.util.List)
     */
    @Override
    public void deleteDatas(final List<T> datas) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                for (T t : datas) {
                    boList.remove(t);
                }
            }

        });
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientModel#modifyDatas(java.util.List)
     */
    @Override
    public void modifyDatas(List<T> datas) {
        // TODO ��Ҫʵ�ֶ�����ģ�������ݵ��޸Ĺ���
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientModel#clearData()
     */
    @Override
    public void clearData() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (boList != null)
                    boList.clear();
            }
        });
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientModel#getModelDatas()
     */
    @Override
    public List<T> getModelDatas() {
        return boList;
    }

}
