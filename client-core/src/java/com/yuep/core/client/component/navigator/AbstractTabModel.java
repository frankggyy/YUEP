/*
 * $Id: AbstractTabModel.java, 2009-3-16 上午10:55:55 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.SwingUtilities;

import twaver.Node;

import com.yuep.base.log.def.Logger;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.ClientModel;

/**
 * <p>
 * Title: AbstractTabModel
 * </p>
 * <p>
 * Description:导航控件中展示具体内容的Tab页Model的抽象类
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-16 上午10:55:55
 * @modified aaron lee 2010-3-30 将其实现<code>ClientModel<T></code>接口
 * check [who date description]
 */
public abstract class AbstractTabModel<T extends Object> extends Observable implements ClientModel<T> {
    protected Logger logger ;
    private Node selectNode = null;
    protected List<T> boList = new ArrayList<T>();

    public AbstractTabModel(){
        logger=ClientCoreContext.getLogger();
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#addDatas(java.util.List)
     */
    @Override
    public void addDatas(List<T> datas) {
        boList.addAll(datas);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#deleteDatas(java.util.List)
     */
    @Override
    public void deleteDatas(List<T> datas) {
        for (T t : datas) {
            boList.remove(t);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#getModelDatas()
     */
    @Override
    public List<T> getModelDatas() {
        return boList;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#modifyDatas(java.util.List)
     */
    @Override
    public void modifyDatas(List<T> datas) {

    }

    /**
     * @return the selectNode
     */
    public Node getSelectNode() {
        return selectNode;
    }

    /**
     * @param selectNode
     *            the selectNode to set
     */
    public void setSelectNode(Node selectNode) {
        this.selectNode = selectNode;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#clearData()
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
}
