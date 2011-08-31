/*
 * $Id: SmManagerView.java, 2011-4-1 ÏÂÎç01:11:50 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.awt.Dimension;
import java.util.List;

import com.yuep.core.client.component.navigator.AbstractFilter;
import com.yuep.core.client.component.navigator.DataNavigationView;

/**
 * <p>
 * Title: SmManagerView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-1 ÏÂÎç01:11:50
 * modified [who date description]
 * check [who date description]
 */
public class SmManagerView extends DataNavigationView<Object> {
    private static final long serialVersionUID = 4970741567961530818L;

    /**
     * @see com.yuep.core.client.component.navigator.AbstractNavigationView#getFilters()
     */
    @Override
    protected List<AbstractFilter> getFilters() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.yuep.core.client.component.navigator.AbstractNavigationView#getHelpId()
     */
    @Override
    public String getHelpId() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.yuep.core.client.component.navigator.AbstractNavigationView#getTitle()
     */
    @Override
    public String getTitle() {
        return "smmanager.title";
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

}
