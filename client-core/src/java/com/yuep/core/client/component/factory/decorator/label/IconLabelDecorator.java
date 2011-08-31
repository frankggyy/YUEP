/*
 * $Id: IconLabelDecorator.java, 2010-10-18 上午11:36:04 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.factory.decorator.label;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.DefaultDecorator;

/**
 * <p>
 * Title: IconLabelDecorator
 * </p>
 * <p>
 * Description:图标型Label的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 上午11:36:04
 * modified [who date description]
 * check [who date description]
 */
public class IconLabelDecorator extends DefaultDecorator<JLabel>{

    private String iconName;
    public IconLabelDecorator(String iconName) {
        this.iconName = iconName;
    }
    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(JLabel t) {
        if(StringUtils.isNotEmpty(iconName)) {
            Icon icon = ClientCoreContext.getResourceFactory().getIcon(iconName);
            t.setIcon(icon);
        }
    }


}
