/*
 * $Id: RadioButtonDecorator.java, 2010-10-18 上午11:05:15 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.factory.decorator.button;

import java.awt.Dimension;

import javax.swing.JRadioButton;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.DefaultDecorator;

/**
 * <p>
 * Title: RadioButtonDecorator
 * </p>
 * <p>
 * Description:RadioButton的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 上午11:05:15
 * modified [who date description]
 * check [who date description]
 */
public class RadioButtonDecorator extends DefaultDecorator<JRadioButton>{
    
    private String textKey;
    public RadioButtonDecorator(String textKey) {
        this.textKey = textKey;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(JRadioButton t) {
        if(StringUtils.isNotEmpty(textKey))
            t.setText(ClientCoreContext.getString(textKey));
        t.setPreferredSize(new Dimension(100, 20));
    }

}
