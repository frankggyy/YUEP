/*
 * $Id: SampleDataNavigatorView.java, 2010-3-30 上午10:32:23 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.navigator.view;

import java.awt.Dimension;
import java.util.List;

import com.yuep.core.client.component.navigator.AbstractFilter;
import com.yuep.core.client.component.navigator.DataNavigationView;

/**
 * <p>
 * Title: SampleDataNavigatorView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-30 上午10:32:23
 * modified [who date description]
 * check [who date description]
 */
public class SampleDataNavigatorView extends DataNavigationView<Object>{

    private static final long serialVersionUID = -7386706530996512500L;

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractNavigationView#getFilters()
     */
    @Override
    protected List<AbstractFilter> getFilters() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "下面是一个数据导航的例子";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractNavigationView#getTitle()
     */
    @Override
    public String getTitle() {
        return "数据导航的例子";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.validate.AbstractValidateView#getHeader()
     */
    @Override
    protected String getHeader() {
        return "数据导航的例子";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractNavigationView#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractNavigationView#getHelpId()
     */
    @Override
    public String getHelpId() {
        return "Data Navigator View";
    }

}
