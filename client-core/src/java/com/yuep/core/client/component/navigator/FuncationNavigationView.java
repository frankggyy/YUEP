/*
 * $Id: FuncationNavigationView.java, 2009-3-16 ÉÏÎç10:40:35 Victor Exp $
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

import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: FuncationNavigationView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Victor
 * created 2009-3-16 ÉÏÎç10:40:35
 * modified [who date description]
 * check [who date description]
 */
public abstract class FuncationNavigationView<T extends Object> extends AbstractNavigationView<T> {
    private static final long serialVersionUID = 1389995060823284445L;

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.commponent.module.AutoVerifyClientView#constructUi()
     */
    public void constructUi() {
        super.constructUi();
        setTreeTitle(ClientCoreContext.getString("common.navigation.tree.funcation.title"));
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractNavigationView#showFilter()
     */
    protected boolean showFilter() {
        return false;
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
     * 
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.commponent.module.AutoVerifyClientView#validated(boolean)
     */
    // @Override
    // protected void validated(boolean enabled) {
    // super.validated(enabled);
    // if (selectedNode == null || selectedNode.getUserObject() == null) {
    // return;
    // }
    // UserObject uo = (UserObject) selectedNode.getUserObject();
    // for (AbstractTabController<? extends Bo, ? extends AbstractTabView, ?
    // extends AbstractTabModel<?>> tab : uo
    // .getTabControllers()) {
    // tab.getView().validated(enabled);
    // }
    // }
}