/*
 * $Id: LabelDecorator.java, 2010-10-18 上午11:32:48 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.factory.decorator.label;

import javax.swing.JLabel;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.DefaultDecorator;

/**
 * <p>
 * Title: LabelDecorator
 * </p>
 * <p>
 * Description:字符显示型Label的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 上午11:32:48
 * modified [who date description]
 * check [who date description]
 */
public class LabelDecorator extends DefaultDecorator<JLabel>{

    private String textKey;
    public LabelDecorator(String textKey) {
        this.textKey = textKey;
    }
    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(JLabel t) {
        if(StringUtils.isNotEmpty(textKey)){
            String text = ClientCoreContext.getString(textKey);
            t.setText(text);
        }
    }

}
