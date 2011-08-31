/*
 * $Id: SampleMultipleTabNavigatorController2.java, 2010-3-30 ÏÂÎç01:26:31 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.navigator.controller;

import java.util.List;

import com.yuep.core.client.component.navigator.AbstractFilter;
import com.yuep.core.client.component.navigator.AbstractTabController;
import com.yuep.nms.biz.client.sample.navigator.model.SampleMultipleTabNavigatorModel2;
import com.yuep.nms.biz.client.sample.navigator.view.SampleMultipleTabNavigatorView2;

/**
 * <p>
 * Title: SampleMultipleTabNavigatorController2
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-30 ÏÂÎç01:26:31
 * modified [who date description]
 * check [who date description]
 */
public class SampleMultipleTabNavigatorController2 extends
            AbstractTabController<Object, SampleMultipleTabNavigatorView2, SampleMultipleTabNavigatorModel2>{

    /**
     * @param v
     * @param m
     */
    public SampleMultipleTabNavigatorController2(Class<SampleMultipleTabNavigatorView2> v,
            Class<SampleMultipleTabNavigatorModel2> m) {
        super(v, m);
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractTabController#initFilters(java.util.List)
     */
    @Override
    protected void initFilters(List<AbstractFilter> filterList) {
    }

}
