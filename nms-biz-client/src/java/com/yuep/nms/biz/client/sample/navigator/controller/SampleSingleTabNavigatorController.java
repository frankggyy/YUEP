/*
 * $Id: SampleSingleTabNavigatorController.java, 2010-3-30 ÉÏÎç11:28:28 aaron lee Exp $
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
import com.yuep.nms.biz.client.sample.navigator.model.SampleSingleTabNavigatorModel;
import com.yuep.nms.biz.client.sample.navigator.view.SampleSingleTabNavigatorView;

/**
 * <p>
 * Title: SampleSingleTabNavigatorController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-30 ÉÏÎç11:28:28
 * modified [who date description]
 * check [who date description]
 */
public class SampleSingleTabNavigatorController extends AbstractTabController<Object, SampleSingleTabNavigatorView, SampleSingleTabNavigatorModel>{

    /**
     * @param v
     * @param m
     */
    public SampleSingleTabNavigatorController(Class<SampleSingleTabNavigatorView> v,
            Class<SampleSingleTabNavigatorModel> m) {
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
