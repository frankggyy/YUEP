/*
 * $Id: SampleMultipleTabNavigatorController1.java, 2010-3-30 ÏÂÎç01:25:55 aaron lee Exp $
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
import com.yuep.nms.biz.client.sample.navigator.model.SampleMultipleTabNavigatorModel1;
import com.yuep.nms.biz.client.sample.navigator.view.SampleMultipleTabNavigatorView1;

/**
 * <p>
 * Title: SampleMultipleTabNavigatorController1
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-30 ÏÂÎç01:25:55
 * modified [who date description]
 * check [who date description]
 */
public class SampleMultipleTabNavigatorController1 extends
        AbstractTabController<Object, SampleMultipleTabNavigatorView1, SampleMultipleTabNavigatorModel1> {

    /**
     * @param v
     * @param m
     */
    public SampleMultipleTabNavigatorController1(Class<SampleMultipleTabNavigatorView1> v,
            Class<SampleMultipleTabNavigatorModel1> m) {
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
