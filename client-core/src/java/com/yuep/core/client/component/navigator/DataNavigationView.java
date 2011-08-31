/*
 * $Id: DataNavigationView.java, 2009-3-17 上午11:30:50 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import java.util.List;

import twaver.Node;
import twaver.PopupMenuFactory;

import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: DataNavigationView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-17 上午11:30:50
 * @modified aaron lee 2009-9-8 添加<code>showTab(String tabTitle)</code>方法
 * check [who date description]
 */
public abstract class DataNavigationView<T> extends AbstractNavigationView<T> {
    private static final long serialVersionUID = -8295322785378203888L;

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.commponent.module.AutoVerifyClientView#constructUi()
     */
    public void constructUi() {
        super.constructUi();
        setTreeTitle(ClientCoreContext.getString("common.navigation.tree.data.title"));
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractNavigationView#onSelectMulti(java.util.List)
     */
    @Override
    protected void onSelectMulti(List<Node> nodes) {
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractNavigationView#getPopupMenuFactory()
     */
    @Override
    protected PopupMenuFactory getPopupMenuFactory() {
        return null;
    }

    /**
     * 根据Tab的名字显示前景tab
     * 
     * @param tabTitle Tab页名
     * @author aaron lee
     */
    protected void showTab(String tabTitle) {
        if(tp==null){
            return;
        }
        
        int index = tp.indexOfTab(tabTitle);
        if (index >= 0)
            tp.setSelectedIndex(index);
    }
}
